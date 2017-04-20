package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmCurrentUserAccountRepository;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝结算流程service
 */
@Service("currentSettlementProcessService")
public class WmCurrentSettlementProcessServiceImpl implements IWmDailyProductSettlementProcessService {

    @Autowired
    private WmCurrentUserAccountRepository currentUserAccountRepository;
    @Autowired
    private IWmCurrentSettlementService currentSettlementService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserInvestInfoService userInvestInfoService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmCurrentBuylogService currentBuylogService;
    @Autowired
    private IWmCurrentTradeRecordService currentTradeRecordService;
    @Autowired
    private IWmCurrentUserAccountService currentUserAccountService;
    @Autowired
    private IWmUserUsersService userUsersService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsBuyQueueService cmsBuyQueueService;
    @Autowired
    private IWmCmsCreditorCanmatchTotalService cmsCreditorCanmatchTotalService;
    @Autowired
    private IWmCurrentIncomeCulculateService currentIncomeCulculateService;
    @Autowired
    private WmProductInfoRepository productInfoRepository;
    @Value("${current.newuser.fund}")
    private int currentNewUserFund;

    /**
     * 结算活期宝
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void executeSettlement(Long userId) throws Exception {
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        //根据用户ID查询用户活期账户
        WmCurrentUserAccount currentUserAccount = currentUserAccountRepository.findWmCurrentUserAccountByUserId(userId);
        if (currentUserAccount != null) {
            //判断日期是否为空 为空设置为0
            if (currentUserAccount.getDlLastSettlementDate() == null) currentUserAccount.setDlLastSettlementDate(0L);
            //判断用户是否有活期账户并且当日未结算
            if (curDate > currentUserAccount.getDlLastSettlementDate()) {
                BigDecimal allFund = currentUserAccount.getAllFund() == null? BigDecimal.ZERO: currentUserAccount.getAllFund();
                BigDecimal expFund = currentUserAccount.getExpFund() == null? BigDecimal.ZERO: currentUserAccount.getExpFund();
                WmUser user = userUsersService.findById(currentUserAccount.getUserId());
                if (user != null) {
                    //管存用户总资金去除复利
                    if (user.isDepositoryUser()) {
                        BigDecimal profit = currentUserAccount.getProfit() == null ? BigDecimal.ZERO : currentUserAccount.getProfit();
                        allFund = allFund.subtract(profit);
                    }
                    //判断用户当前是否有投资金额
                    if (allFund.doubleValue() > 0 || expFund.doubleValue() > 0) {
                        //查找产品利率
                        WmProductInfo productInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_CURRENT, WmProductInfo.STATUS_USE, (int) WmProductInfo.YINDUO);
                        if (productInfo != null) {
                            //计算收益
                            WmIncomeEntity incomeEntity = currentIncomeCulculateService.culculateIncome(user, productInfo, allFund, expFund);
                            WmAprEntity aprEntity = incomeEntity.getAprEntity();
                            if (user.isDepositoryUser()) {
                                //执行存管用户活期宝结算流程
                                handleDepositorySettlement(currentUserAccount, productInfo, allFund, expFund, aprEntity.getDayApr(),
                                        aprEntity.getMonthApr(), aprEntity.getVipApr(), incomeEntity.getIncome(), incomeEntity.getExpIncome(),
                                        incomeEntity.getInterestIncome(), incomeEntity.getVipIncome(), curDate);
                                //处理复利复投
                                handleDepositoryPretreatment(userId);
                            } else {
                                //执行老用户活期宝结算流程
                                handleOldUserSettlement(currentUserAccount, productInfo, allFund, expFund, aprEntity.getDayApr(),
                                        aprEntity.getMonthApr(), aprEntity.getVipApr(), incomeEntity.getIncome(), incomeEntity.getExpIncome(),
                                        incomeEntity.getInterestIncome(), incomeEntity.getVipIncome(), curDate);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理存管用户结算
     * @param dailyProductAccount 用户活期宝账户
     * @param productInfo 活期宝产品
     * @param allFund 投资金额
     * @param expFund 体验金金额
     * @param dayApr 日利率
     * @param monthApr 月利率
     * @param vipApr vip利率
     * @param income 收益
     * @param investIncome 体验金收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param curDate 结算日期
     */
    @Override
    public void handleDepositorySettlement(IWmDailyProductAccount dailyProductAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception {

        WmCurrentUserAccount currentUserAccount = (WmCurrentUserAccount)dailyProductAccount;
        if (currentUserAccount != null) {
            Long userId = currentUserAccount.getUserId();
            Long productId = productInfo.getId();
            //创建活期每日结算
            currentSettlementService.createOne(userId, productId, allFund,
                    expFund, productInfo.getInterestRate(), dayApr, monthApr, vipApr, income, DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()), DateUtil.getSystemTimeSeconds());
            //增加用户总资金
            userMoneyService.increaseTotalFund(userId, income);
            //增加用户收益
            userIncomeService.increaseCurrentSettlementIncome(userId, income, investIncome, interestIncome, vipIncome);
            //创建用户赠予（红包）体验金收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_EXPERIENCE, DateUtil.getSystemTimeSeconds());
            //创建用户赠予（红包）加息券收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_INTEREST, DateUtil.getSystemTimeSeconds());
            //创建用户赠予（红包）VIP收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
            //创建收益记录
            userIncomeRecordService.createOne(userId, FundFlow.HQBSY, income, WmUserIncomeRecord.CURRENT, DateUtil.getSystemTimeSeconds());
            //更新用户活期账户
            currentUserAccountService.increaseProfit(currentUserAccount, income, curDate);
        }
    }

    /**
     * 处理
     * @param userId
     * @throws Exception
     */
    @Override
    public void handleDepositoryPretreatment(Long userId) throws Exception {
        WmUser user = userUsersService.findById(userId);
        //只处理新用户
        if (user != null && user.isDepositoryUser()) {
            WmCurrentUserAccount currentUserAccount = currentUserAccountRepository.findWmCurrentUserAccountByUserId(userId);
            if (currentUserAccount != null) {
                //如果复利>=新手复利复投标准，则进行处理
                BigDecimal profit = currentUserAccount.getProfit();
                BigDecimal newUserFund = new BigDecimal(currentNewUserFund);   //活期宝新手复利复投资金基准
                if (profit != null && profit.compareTo(newUserFund) >= 0) {
                    //剩余复利
                    BigDecimal remainingProfit = profit.remainder(newUserFund);
                    //购买金额 = 原复利 - 剩余复利
                    BigDecimal buyFund = profit.subtract(remainingProfit);
                    //处理活期宝账户所持与冻结
                    currentUserAccountService.handleDepositoryUserProfileReach(currentUserAccount, remainingProfit, buyFund);
                    //创建交易冻结记录
                    WmJxFreezeRecord record = jxFreezeRecordService.createOne(userId, user.getAccountId(), buyFund, null, WmJxFreezeRecord.TYPE_PROFIT, WmJxFreezeRecord.LINKTYPE_CURRENT,
                            null, null, null, null, null, WmJxFreezeRecord.DEVICE_SYSTEM, null, null, null,
                            WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(), null);
                    //创建用户购买队列
                    cmsBuyQueueService.createOne(userId, record == null ? null : record.getId(), null, buyFund, WmCmsBuyQueue.TYPE_PROFIT, null, WmCmsBuyQueue.STATUS_UNDERMATCH, null);
                    //增加总债券购买数
                    cmsCreditorCanmatchTotalService.increaseBuy(buyFund);
                }
            }
        }
    }

    /**
     * 处理活期宝结算流程
     * @param dailyProductAccount 用户活期宝账户
     * @param productInfo 活期宝产品
     * @param allFund 投资金额
     * @param expFund 体验金金额
     * @param dayApr 日利率
     * @param monthApr 月利率
     * @param vipApr vip利率
     * @param income 收益
     * @param investIncome 体验金收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param curDate 结算日期
     */
    @Override
    public void handleOldUserSettlement(IWmDailyProductAccount dailyProductAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception{

        WmCurrentUserAccount currentUserAccount = (WmCurrentUserAccount)dailyProductAccount;
        if (currentUserAccount != null) {
            Long userId = currentUserAccount.getUserId();
            Long productId = productInfo.getId();
            //保存活期每日结算
            currentSettlementService.createOne(userId, productId, allFund,
                    expFund, productInfo.getInterestRate(), dayApr, monthApr, vipApr, income, DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()), DateUtil.getSystemTimeSeconds());
            //增加用户总资金
            userMoneyService.increaseTotalFund(userId, income);
            //增加用户活期宝投资
            userInvestInfoService.increaseCurrentInvest(userId, income);
            //增加用户收益
            userIncomeService.increaseCurrentSettlementIncome(userId, income, investIncome, interestIncome, vipIncome);
            //创建用户赠予（红包）体验金收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_EXPERIENCE, DateUtil.getSystemTimeSeconds());
            //创建用户赠予（红包）加息券收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_INTEREST, DateUtil.getSystemTimeSeconds());
            //创建用户赠予（红包）VIP收益记录
            userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
            //创建收益记录
            userIncomeRecordService.createOne(userId, FundFlow.HQBSY, income, WmUserIncomeRecord.CURRENT, DateUtil.getSystemTimeSeconds());
            //创建购买日志
            WmCurrentBuylog buylog = currentBuylogService.createOne(productId, userId, income, BigDecimal.ZERO, 0, productInfo.getInterestRate(),
                    BigDecimal.ZERO, DateUtil.getSystemTimeSeconds(), WmCurrentBuylog.DEVICE_SYSTEM);
            //创建活期宝交易记录
            currentTradeRecordService.createOne(userId, FundFlow.HQBFLTJ, income, DateUtil.getSystemTimeSeconds(),
                    WmCurrentTradeRecored.TYPE_PURCHASE, WmCurrentTradeRecored.FUNDSOURCE_INCOME, buylog == null? null: buylog.getId());
            //更新用户活期账户
            currentUserAccountService.increaseProfit(currentUserAccount, income, curDate);
        }
    }
}
