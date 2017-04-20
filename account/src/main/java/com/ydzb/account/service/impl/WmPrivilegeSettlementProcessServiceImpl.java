package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmPrivilegeUserAccountRepository;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 *新手宝结算流程service
 */
@Service("privilegeSettlementProcessService")
public class WmPrivilegeSettlementProcessServiceImpl implements IWmPrivilegeSettlementProcessService {

    @Autowired
    private IWmCurrentUserAccountService currentUserAccountService;
    @Autowired
    private WmPrivilegeUserAccountRepository privilegeUserAccountRepository;
    @Autowired
    private WmProductInfoRepository productInfoRepository;
    @Autowired
    private IWmPrivilegeSettlementService privilegeSettlementService;
    @Autowired
    private IWmPrivilegeUserAccountService privilegeAccountService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserInvestInfoService userInvestInfoService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmPrivilegeBuyLogService privilegeBuyLogService;
    @Autowired
    private IWmPrivilegeTradeRecordService privilegeTradeRecordService;
    @Autowired
    private IWmPrivilegeOverLogService privilegeOverLogService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmUserUsersService userUsersService;
    @Autowired
    private IWmCmsCreditorCanmatchTotalService cmsCreditorCanmatchTotalService;
    @Autowired
    private IWmCmsBuyQueueService cmsBuyQueueService;
    @Autowired
    private IWmCurrentBuylogService currentBuylogService;
    @Autowired
    private IWmCurrentTradeRecordService currentTradeRecordService;
    @Autowired
    private IWmPrivilegeUserAccountService privilegeUserAccountService;
    @Autowired
    private IWmCmsUserCreditorRecordService cmsUserCreditorRecordService;
    @Value("${privilege.newuser.fund}")
    private int privilegeNewUserFund;
    @Value("${privilege.newuser.profistamdard}")
    private Double privilegeNewUserProfitStandard;

    /**
     * 执行结算
     * @param userId 用户id
     * @throws Exception
     */
    @Override
    public void executeSettlement(Long userId) throws Exception {
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());//系统当前日期
        //查询用户新手特权持有信息
        WmPrivilegeUserAccount privilegeUserAccount = privilegeUserAccountRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (privilegeUserAccount != null) {
            //将lastSettlementDate初始化为0
            if (privilegeUserAccount.getLastSettlementDate() == null) privilegeUserAccount.setLastSettlementDate(0L);
            //判断用户当前是否有投资金额并且未结算
            if (curDate > privilegeUserAccount.getLastSettlementDate()) {
                BigDecimal allFund = privilegeUserAccount.getAllFund() == null? BigDecimal.ZERO: privilegeUserAccount.getAllFund();
                //根据用户id查找用户信息记录
                WmUser user = userUsersService.findById(privilegeUserAccount.getUserId());
                if (user != null) {
                    //管存用户总资金去除复利
                    if (user.isDepositoryUser()) {
                        BigDecimal profit = privilegeUserAccount.getProfit() == null ? BigDecimal.ZERO : privilegeUserAccount.getProfit();
                        allFund = allFund.subtract(profit);
                    }
                    //判断用户是否有投资金额
                    if (allFund.doubleValue() > 0) {
                        //查找产品利率
                        WmProductInfo productInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_PRIVILEGE, WmProductInfo.STATUS_USE, (int) WmProductInfo.YINDUO);
                        if (productInfo != null) {
                            //利率
                            BigDecimal apr = productInfo.getInterestRate();
                            //存管用户计算当天收益
                            BigDecimal income = apr.multiply(allFund).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN);
                            //存管用户
                            if (user.isDepositoryUser()) {
                                handleDepositorySettlement(privilegeUserAccount, productInfo, allFund,null,apr,null,
                                        null, income, null, null, null, curDate);
                                //新手宝到期
                                if (!handleDepositoryExpire(user)) {
                                    //预处理复利
                                    handleDepositoryPretreatment(userId);
                                }
                            } else {
                                handleOldUserSettlement(privilegeUserAccount, productInfo, allFund,null, apr,null,
                                        null, income, null, null, null, curDate);
                                handleOldUserExpire(user);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理存管复利用户预处理
     * @param userId 用户id
     * @throws Exception
     */
    @Override
    public void handleDepositoryPretreatment(Long userId) throws Exception {
        WmUser user = userUsersService.findById(userId);
        if (user != null) {
            //查询用户新手特权持有信息
            WmPrivilegeUserAccount privilegeUserAccount = privilegeUserAccountRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
            if (privilegeUserAccount != null) {
                //原复利
                BigDecimal profit = privilegeUserAccount.getProfit() == null ? BigDecimal.ZERO : privilegeUserAccount.getProfit();
                //如果原复利大于标准
                if (profit.compareTo(new BigDecimal(privilegeNewUserFund)) == 1) {
                    //剩余复利
                    BigDecimal remainProfit = profit.remainder(new BigDecimal(privilegeNewUserFund));
                    //购买金额
                    BigDecimal buyfund = profit.subtract(remainProfit);
                    //处理新手宝账户复利复投
                    privilegeUserAccountService.handleProfileRecast(privilegeUserAccount, remainProfit, buyfund);
                    //生成产品交易冻结记录
                    WmJxFreezeRecord record = jxFreezeRecordService.createOne(userId, user.getAccountId(), buyfund, null, WmJxFreezeRecord.TYPE_PROFIT, WmJxFreezeRecord.LINKTYPE_PRIVILEGE,
                            null, null, null, null, null, WmJxFreezeRecord.DEVICE_SYSTEM, null, null, null,
                            WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(), null);
                    //创建用户购买队列
                    cmsBuyQueueService.createOne(userId, record == null? null: record.getId(), null, buyfund, WmCmsBuyQueue.TYPE_PROFIT, null, WmCmsBuyQueue.STATUS_UNDERMATCH, null);
                    //增加总债券购买数
                    cmsCreditorCanmatchTotalService.increaseBuy(buyfund);
                }
            }
        }
    }

    /**
     * 处理存管用户结算
     * @param dailyProductAccount 用户新手宝账户
     * @param productInfo 新手宝产品
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
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception{

        WmPrivilegeUserAccount privilegeUserAccount = (WmPrivilegeUserAccount)dailyProductAccount;
        if (null != privilegeUserAccount) {
            Long userId = privilegeUserAccount.getUserId();
            //生成新手宝每日结算
            privilegeSettlementService.createOne(privilegeUserAccount.getUserId(), productInfo.getId(), allFund, dayApr, income, DateUtil.getSystemTimeDay(DateUtil.curDate()));
            //增加用户总资金
            userMoneyService.increaseTotalFund(userId, income);
            //更新用户收益
            userIncomeService.increasePrivilegetSettlementIncome(userId, income);
            //生成用户收益记录
            userIncomeRecordService.createOne(userId, FundFlow.XSBSY, income, WmUserIncomeRecord.PTYPE_PRIVILEGE, DateUtil.getSystemTimeSeconds());
            //更新用户新手宝记录
            privilegeAccountService.increaseProfit(privilegeUserAccount, income ,curDate);
        }
    }

    /**
     * 处理老用户结算
     * @param dailyProductAccount 每日产品账户(currentUserAccount等）
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
     * @throws Exception
     */
    @Override
    public void handleOldUserSettlement(IWmDailyProductAccount dailyProductAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception {

        WmPrivilegeUserAccount wmPrivilegeUserAccount=(WmPrivilegeUserAccount)dailyProductAccount;
        if(null!=wmPrivilegeUserAccount) {
            Long userId=wmPrivilegeUserAccount.getUserId();
            //创建新手标每日结算
            privilegeSettlementService.createOne(userId, productInfo.getId(), allFund, dayApr, income, DateUtil.getSystemTimeDay(DateUtil.curDate()));
            //更新用户资金
            userMoneyService.increaseTotalFund(userId, income);
            //更新用户投资
            userInvestInfoService.increasePrivilegeInvest(userId, income);
            //更新用户收益
            userIncomeService.increasePrivilegetSettlementIncome(userId, income);
            //收益记录
            userIncomeRecordService.createOne(userId, FundFlow.XSBSY, income, WmUserIncomeRecord.PTYPE_PRIVILEGE, DateUtil.getSystemTimeSeconds());
            //购买日志
            WmPrivilegeBuyLog buyLog = privilegeBuyLogService.createOne(productInfo.getId(), userId, income, 0, dayApr, DateUtil.getSystemTimeSeconds(), WmPrivilegeBuyLog.DEVICE_SYSTEM);
            //交易记录
            privilegeTradeRecordService.createOne(userId, FundFlow.XSBFLTJ, income, DateUtil.getSystemTimeSeconds(),
                    WmPrivilegeTradeRecord.TYPE_PURCHASE, WmPrivilegeTradeRecord.FUNDSOURCE_INCOME, buyLog == null ? null : buyLog.getId());
            //增加复利
            privilegeAccountService.increaseProfit(wmPrivilegeUserAccount, income, DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        }
    }

    /**
     * 处理老用户到期
     * @param user 用户
     * @throws Exception
     */
    @Override
    public void handleOldUserExpire(WmUser user) throws Exception {
        if (user != null) {
            Long userId = user.getId();
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
            WmPrivilegeUserAccount privilegeUserAccount = privilegeUserAccountRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
            //判断是否今天到期
            if (privilegeUserAccount != null && curDate.equals(privilegeUserAccount.getExpireTime())) {
                //新手特权全部金额
                BigDecimal fund = privilegeUserAccount.getAllFund();
                //新手特权部分
                //新增赎回日志
                WmPrivilegeOverLog overLog = privilegeOverLogService.createOne(userId, WmPrivilegeOverLog.TYPE_REDEEM_EXPIRE,
                        privilegeUserAccount.getAllFund(), DateUtil.getSystemTimeSeconds());
                //新增赎回交易记录
                privilegeTradeRecordService.createOne(userId, FundFlow.XSBSH, privilegeUserAccount.getAllFund(),
                        DateUtil.getSystemTimeSeconds(), WmPrivilegeTradeRecord.TYPE_REDEEM, WmPrivilegeTradeRecord.FUNDSOURCE_EXPIREFUND, overLog == null ? null : overLog.getId());
                //清空新手特权账户金额
                privilegeUserAccountService.emptyFund(privilegeUserAccount);

                //活期宝部分
                WmProductInfo productInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_CURRENT, WmProductInfo.STATUS_USING, WmProductInfo.PRODUCTCLAS_YINDUO);
                //活期宝购买日志
                WmCurrentBuylog currentBuylog = currentBuylogService.createOne(productInfo == null? null : productInfo.getId(), userId, fund, BigDecimal.ZERO,
                        fund.intValue(), productInfo == null? null: productInfo.getInterestRate(), BigDecimal.ZERO, DateUtil.getSystemTimeSeconds(), WmCurrentBuylog.DEVICE_SYSTEM);
                //活期宝交易记录
                currentTradeRecordService.createOne(userId, FundFlow.HQBTZ, fund, DateUtil.getSystemTimeSeconds(), WmCurrentTradeRecored.TYPE_PURCHASE,
                        WmCurrentTradeRecored.FUNDSOURCE_PRIVILEGE, currentBuylog == null? null: currentBuylog.getId());
                //增加活期宝账户金额
                currentUserAccountService.increaseAllFund(userId, fund);
                //更新活期宝投资
                userInvestInfoService.convertPrvilegeToCurrent(userId, fund);
            }
        }
    }

    /**
     * 处理存管用户到期
     * @param user
     * @return 是否到期
     * @throws Exception
     */
    @Override
    public boolean handleDepositoryExpire(WmUser user) throws Exception {
        if (user != null) {
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
            WmPrivilegeUserAccount privilegeUserAccount = privilegeUserAccountRepository.queryByUser(user.getId(), LockModeType.PESSIMISTIC_WRITE);
            //判断是否今天到期
            if (privilegeUserAccount != null && curDate.equals(privilegeUserAccount.getExpireTime())) {
                //新手特权全部金额
                BigDecimal fund = privilegeUserAccount.getAllFund() == null? BigDecimal.ZERO: privilegeUserAccount.getAllFund();
                BigDecimal profit = privilegeUserAccount.getProfit() == null? BigDecimal.ZERO : privilegeUserAccount.getProfit();
                //清空新手宝账户金额和和复利
                privilegeUserAccountService.emptyFund(privilegeUserAccount);
                //复利金额大于等于复利标准
                if (profit.compareTo(new BigDecimal(privilegeNewUserProfitStandard)) >= 0) {
                    //增加债权总数-总购买
                    cmsCreditorCanmatchTotalService.increaseBuy(profit);
                    //生成产品交易冻结记录
                    WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), fund.subtract(profit), profit,
                            WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_PRIVILEGE,
                            privilegeUserAccount.getId(), null, null, null, null, WmJxFreezeRecord.DEVICE_SYSTEM,
                            null, null, null, WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(), null);
                    //生成交易冻结记录
                    cmsBuyQueueService.createOne(user.getId(), record == null? null: record.getId(), null, profit, WmCmsBuyQueue.TYPE_PROFIT, null, WmCmsBuyQueue.STATUS_UNDERMATCH, null);
                } else if (fund.compareTo(BigDecimal.ZERO) == 1) {  //如果有新手宝投资

                    //如果复利为0，则全部为本金
                    if (profit.compareTo(BigDecimal.ZERO) == 0) {
                        WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), fund, BigDecimal.ZERO,
                                WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_PRIVILEGE,
                                privilegeUserAccount.getId(), null, null, null, null, WmJxFreezeRecord.DEVICE_SYSTEM,
                                null, null, null, WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(), null);
                        //生成交易冻结记录
                        cmsBuyQueueService.createOne(user.getId(), record == null ? null : record.getId(), null, profit, WmCmsBuyQueue.TYPE_PROFIT, null, WmCmsBuyQueue.STATUS_UNDERMATCH, null);//生成购买队列记录
                    } else {
                        //利息部分直接转活期
                        //增加活期宝账户金额
                        currentUserAccountService.increaseAllFund(user.getId(), profit);
                        //增加活期宝投资，减少新手宝投资
                        userInvestInfoService.convertPrvilegeToCurrent(user.getId(), profit);
                        //本金部分
                        //增加债权总数-总购买
                        //生成产品交易冻结记录
                        WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), fund.subtract(profit), profit,
                                WmJxFreezeRecord.TYPE_EXPIRE, WmJxFreezeRecord.LINKTYPE_PRIVILEGE,
                                privilegeUserAccount.getId(), null, null, null, null, WmJxFreezeRecord.DEVICE_SYSTEM,
                                null, null, null, WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(), null);
                        //生成交易冻结记录
                        cmsBuyQueueService.createOne(user.getId(), record == null ? null : record.getId(), null, profit, WmCmsBuyQueue.TYPE_PROFIT, null, WmCmsBuyQueue.STATUS_UNDERMATCH, null);//生成购买队列记录
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 处理管存用户预处理成功
     * @param jxFreezeRecordId 即信交易冻结记录id
     */
    @Override
    public void handleDepositoryPretreatmentSuccess(Long jxFreezeRecordId) throws Exception {
        WmJxFreezeRecord jxFreezeRecord = jxFreezeRecordService.queryById(jxFreezeRecordId, LockModeType.NONE);
        if (jxFreezeRecord != null) {
            Long privilegeUserAccountId = jxFreezeRecord.getLinkId();
            WmPrivilegeUserAccount privilegeUserAccount = privilegeUserAccountRepository.queryOne(privilegeUserAccountId, LockModeType.PESSIMISTIC_WRITE);
            if (privilegeUserAccount != null) {
                Long userId = jxFreezeRecord.getUserId();
                BigDecimal fund = privilegeUserAccount.getAllFund();
                //新手特权部分
                //新增赎回日志
                WmPrivilegeOverLog overLog = privilegeOverLogService.createOne(userId, WmPrivilegeOverLog.TYPE_REDEEM_EXPIRE, fund, DateUtil.getSystemTimeSeconds());
                //新增赎回交易记录
                privilegeTradeRecordService.createOne(userId, FundFlow.XSBSH, fund, DateUtil.getSystemTimeSeconds(), WmPrivilegeTradeRecord.TYPE_REDEEM, WmPrivilegeTradeRecord.FUNDSOURCE_EXPIREFUND, overLog == null ? null : overLog.getId());
                //清空新手特权账户金额
                privilegeUserAccountService.emptyFund(privilegeUserAccount);

                //活期宝部分
                WmProductInfo productInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_CURRENT, WmProductInfo.STATUS_USING, WmProductInfo.PRODUCTCLAS_YINDUO);
                //活期宝购买日志
                WmCurrentBuylog currentBuylog = currentBuylogService.createOne(productInfo == null? null : productInfo.getId(), userId, fund, BigDecimal.ZERO,
                        fund.intValue(), productInfo == null? null: productInfo.getInterestRate(), BigDecimal.ZERO, DateUtil.getSystemTimeSeconds(), WmCurrentBuylog.DEVICE_SYSTEM);
                //活期宝交易记录
                currentTradeRecordService.createOne(userId, FundFlow.HQBTZ, fund, DateUtil.getSystemTimeSeconds(), WmCurrentTradeRecored.TYPE_PURCHASE,
                        WmCurrentTradeRecored.FUNDSOURCE_PRIVILEGE, currentBuylog == null? null: currentBuylog.getId());
                //增加活期宝账户金额
                WmCurrentUserAccount currentUserAccount = currentUserAccountService.increaseAllFund(userId, fund);
                //更新活期宝投资
                userInvestInfoService.convertPrvilegeToCurrent(userId, fund);

                //把用户持有债权id改成现有id
                cmsUserCreditorRecordService.updateDealId(currentUserAccount.getId(), jxFreezeRecord.getLinkId(), userId, WmCmsUserCreditorRecord.PRODUCTTYPE_PRIVILEGE);
                //更新冻结记录状态
                jxFreezeRecordService.updateStatus(jxFreezeRecord, WmJxFreezeRecord.STATE_HANDLEFINISH);
            }
        }
    }
}