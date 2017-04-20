package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmCurrentUserAccountRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 存管户业务流程service实现
 */
@Service("depositoryUserProcessService")
public class WmDepositoryUserProcessServiceImpl implements IWmYinduoUserProcessService {

    @Autowired
    private IWmCurrentSettlementService currentSettlementService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmCurrentUserAccountService currentUserAccountService;
    @Autowired
    private IWmUserUsersService userUsersService;
    @Autowired
    private WmCurrentUserAccountRepository currentUserAccountRepository;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsBuyQueueService cmsBuyQueueService;


    @Value("${current.newuser.fund}")
    private int currentNewUserFund; //存管用户活期宝复利投资基准

    /**
     * 处理活期宝结算流程
     * @param currentUserAccount 用户活期宝账户
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
    public void handleCurrentSettlementProcess(WmCurrentUserAccount currentUserAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) {

        Long userId = currentUserAccount.getUserId();
        Long productId = productInfo.getId();
        //保存活期每日结算
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

    /**
     * 处理复利复投
     * @param userId
     */
    public void handleProfitRecast(Long userId) {
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
                }
            }
        }
    }
}
