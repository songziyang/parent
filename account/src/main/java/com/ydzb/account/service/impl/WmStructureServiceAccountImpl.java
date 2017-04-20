package com.ydzb.account.service.impl;

import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmStructureDealRepository;
import com.ydzb.account.repository.WmStructureRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sy on 2016/10/23.
 */
@Service
public class WmStructureServiceAccountImpl implements IWmStructureAccountService {

    @Autowired
    private WmStructureRepository wmStructureRepository;
    @Autowired
    private WmStructureDealRepository wmStructureDealRepository;
    @Autowired
    private IWmUserMoneyService wmUserMoneyService;
    @Autowired
    private IWmUserInvestInfoService wmUserInvestInfoService;
    @Autowired
    private IWmUserIncomeService wmUserIncomeService;
    @Autowired
    private IWmUserIncomeRecordService wmUserIncomeRecordService;
    @Autowired
    private IWmUserFundInLogService wmUserFundInLogService;
    @Autowired
    private IWmUserFundRecordService wmUserFundRecordService;
    @Autowired
    private IWmStructureDealService wmStructureDealService;
    @Autowired
    private IWmUserUsersService wmUserUsersService;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;
    @Autowired
    private IWmPlatformUserService wmPlatformUserService;


    @Override
    public List<WmStructure> findByEndDate(Long date) {
        return wmStructureRepository.findByEndDate(date);
    }

    /**
     * 根据申购结束日期更新满标状态
     * 将申购结束日期为指定日期并且状态为申购的基金产品状态置为满标状态
     * 并且设置满标日期以及结束日期
     */
    @Override
    public void updateFullStandardState(WmStructure structure) throws Exception {
        structure.setState(WmStructure.STATE_FULLSTANDARD);
        //满标日期为结束申购日期 + 1(结算日期)
        structure.setFullDate(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        //到期日期为满标日日期 + 锁定期
        structure.setCloseDate(structure.getFullDate() + structure.getDays() * 24 * 3600);
        wmStructureRepository.saveOrUpdate(structure);
    }

    @Override
    public List<WmStructure> findByCloseDate(Long date) {
        return wmStructureRepository.findByCloseDate(date);
    }

    /**
     * 更新基金产品信息
     */
    @Override
    public void updateStable(WmStructure structure) throws Exception {
        structure.setState(WmStructure.STATE_END);
        wmStructureRepository.saveOrUpdate(structure);
    }

    /**
     * 结算收益
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountStructureRevenue(WmStructure structure, Long userId) throws Exception {
        if (structure != null && userId != null) {
            List<WmStructureDeal> structureDeals = wmStructureDealRepository.findWmStructureDeal(userId, structure.getId());
            if (structureDeals != null && !structureDeals.isEmpty()) {
                for (WmStructureDeal structureDeal : structureDeals) {
                    if (structureDeal != null) {
                        BigDecimal income = culculateIncome(structure, structureDeal);  //收益
                        BigDecimal principal = structureDeal.getCopies() == null ? BigDecimal.ZERO : BigDecimal.valueOf(structureDeal.getCopies());    //本金
                        //更新用户资金
                        updateUserMoney(userId, principal, income);
                        //更新用户投资信息
                        updateUserInvest(userId, principal);
                        //更新用户收益
                        updateUserIncome(userId, income);
                        //增加用户收益记录
                        saveWmUserIncomeRecord(userId, income, structure.getName() + "收益", WmUserIncomeRecord.FUNDATION_PRODUCT);
                        //增加用户入账日志
                        Long inLogId = saveWmUserFundInLog(userId, structureDeal.getId(), 22, principal, income);
                        //增加用户资金记录
                        saveWmUserFundRecord(userId, structure.getName() + "收益", income, 1, 3, inLogId);
                        saveWmUserFundRecord(userId, structure.getName() + "本金", principal, 1, 2, inLogId);
                        //更新基金产品交易记录
                        updateStructureDeal(structureDeal, income);
                        sendSiteContent(userId, structure.getName(), principal.intValue(), income);
                    }
                }
            }
        }
    }

    @Override
    public void accountStructureRevenueNew(WmStructure structure, WmStructureDeal structureDeal, Long userId) throws Exception {
        if (structureDeal != null && userId != null) {
            BigDecimal income = culculateIncome(structure, structureDeal);  //收益
            BigDecimal principal = structureDeal.getCopies() == null ? BigDecimal.ZERO : BigDecimal.valueOf(structureDeal.getCopies());    //本金
            //更新用户资金
            updateUserMoney(userId, principal, income);
            //更新用户投资信息
            updateUserInvest(userId, principal);
            //更新用户收益
            updateUserIncome(userId, income);
            //增加用户收益记录
            saveWmUserIncomeRecord(userId, income, structure.getName() + "收益", WmUserIncomeRecord.FUNDATION_PRODUCT);
            //增加用户入账日志
            Long inLogId = saveWmUserFundInLog(userId, structureDeal.getId(), 22, principal, income);
            //增加用户资金记录
            saveWmUserFundRecord(userId, structure.getName() + "收益", income, 1, 3, inLogId);
            saveWmUserFundRecord(userId, structure.getName() + "本金", principal, 1, 2, inLogId);
            //更新基金产品交易记录
            updateStructureDeal(structureDeal, income);
            sendSiteContent(userId, structure.getName(), principal.intValue(), income);

            //转转到期
            wmPlatformUserService.redeem(principal, structureDeal.getUserId(), WmPlatformRecordLinkType.EXPIRE_ZZZ, WmPlatformRecordLinkType.EXPIRE_ZZZ.getDesc());


        }
    }

    /**
     * 计算收益
     *
     * @return
     */
    private BigDecimal culculateIncome(WmStructure structure, WmStructureDeal structureDeal) {

        if (structure != null && structureDeal != null) {
            BigDecimal apr = structureDeal.getApr() == null ? (structure.getApr() == null ? WmStructure.APR_DEFAULT : structure.getApr()) : structureDeal.getApr();
            BigDecimal addApr = structureDeal.getAddApr() == null ? WmStructure.HELP_MAX_APR_DEFAULT : structureDeal.getAddApr();
            BigDecimal copies = (structureDeal.getCopies() == null || structureDeal.getCopies().compareTo(0) == -1) ?
                    BigDecimal.ZERO : BigDecimal.valueOf(structureDeal.getCopies());
            BigDecimal days = (structure.getDays() == null || structure.getDays().compareTo(0) == -1) ?
                    BigDecimal.valueOf(WmStructure.DAYS_DEFAULT) : BigDecimal.valueOf(structure.getDays());
            //收益 = 用户购买份数 * (利率+加息利率) * 锁定期(购买天数)/36500天
            return copies.multiply(apr.add(addApr)).multiply(days).divide(BigDecimal.valueOf(36500), 6, BigDecimal.ROUND_HALF_DOWN);
        }
        return BigDecimal.ZERO;
    }


    /**
     * 更新用户资金
     *
     * @param userId    用户id
     * @param principal 本金
     * @param income    收益
     */
    private void updateUserMoney(Long userId, BigDecimal principal, BigDecimal income) {
        WmUserMoney userMoney = wmUserMoneyService.queryOne(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            //现余额 = 原余额 + 本金 + 收益
            userMoney.setUsableFund(userMoney.getUsableFund().add(principal).add(income));
            //现总额 = 原总额 + 收益
            userMoney.setTotalFund(userMoney.getTotalFund().add(income));
            wmUserMoneyService.saveOrUpdate(userMoney);
        }
    }

    /**
     * 更新用户投资
     *
     * @param userId   用户id
     * @param pricipal 本金
     */
    private void updateUserInvest(Long userId, BigDecimal pricipal) throws Exception {
        WmUserInvestinfo userInvest = wmUserInvestInfoService.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userInvest != null) {
            //现基金投资总额 = 原基金投资总额 - 本金
            userInvest.setAllInvestWjb(userInvest.getAllInvestWjb().subtract(pricipal));
            //现投资总额 = 原投资总额 - 本金
            userInvest.setAllInvest(userInvest.getAllInvest().subtract(pricipal));
            wmUserInvestInfoService.saveOrUpdate(userInvest);
        }
    }

    /**
     * 更新用户收益信息
     *
     * @param userId 用户id
     * @param income 收益
     */
    private void updateUserIncome(Long userId, BigDecimal income) {
        WmUserIncome userIncome = wmUserIncomeService.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //现基金收益 = 原基金收益 + 收益
            userIncome.setAllIncomeWjb(userIncome.getAllIncomeWjb().add(income));
            //现总收益 = 原总收益 + 收益
            userIncome.setAllIncome(userIncome.getAllIncome().add(income));
            //现昨日收益 = 昨日收益 + 收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome().add(income));
            wmUserIncomeService.saveOrUpdate(userIncome);
        }
    }


    /**
     * 保存用户收益记录
     *
     * @param userId 用户id
     * @param income 收益
     * @param name   产品名称
     * @param ptype  产品类型
     */
    private void saveWmUserIncomeRecord(Long userId, BigDecimal income, String name, Integer ptype) {
        WmUserIncomeRecord userIncomeRecord = new WmUserIncomeRecord();
        userIncomeRecord.setUserId(userId);
        userIncomeRecord.setIncome(income);
        userIncomeRecord.setName(name);
        userIncomeRecord.setOptime(DateUtil.getSystemTimeSeconds());
        userIncomeRecord.setPtype(ptype);
        wmUserIncomeRecordService.saveOrUpdate(userIncomeRecord);
    }

    /**
     * 保存进账日志
     *
     * @param userId
     * @param linkId
     * @param type
     * @param incomeFund
     * @param incomeInterest
     * @return
     */
    public Long saveWmUserFundInLog(Long userId, Long linkId, Integer type, BigDecimal incomeFund, BigDecimal incomeInterest) {
        WmUserMoney userMoney = wmUserMoneyService.queryOne(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            // 进账日志
            WmUserFundInLog log = new WmUserFundInLog();
            log.setUserId(userId);
            log.setLinkId(linkId);
            log.setType(type);
            log.setReceiptsTime(DateUtil.getSystemTimeSeconds());
            log.setIncomeFund(incomeFund);
            log.setIncomeInterest(incomeInterest);
            log.setUsableFund(userMoney.getUsableFund());
            log.setRemark(null);
            wmUserFundInLogService.saveOrUpdate(log);
            return log.getId();
        }
        return null;
    }

    /**
     * 保存用户资金记录
     *
     * @param userId   用户id
     * @param fundflow 来源去向
     * @param fund     金额
     * @param type     类型
     * @param fundType 资金类别
     * @param logId    日志id
     */
    public void saveWmUserFundRecord(Long userId, String fundflow, BigDecimal fund, Integer type, Integer fundType, Long logId) {
        WmUserMoney userMoney = wmUserMoneyService.queryOne(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            WmUserFundRecord userFundRecord = new WmUserFundRecord();
            userFundRecord.setUserId(userId);
            userFundRecord.setFundflow(fundflow);
            userFundRecord.setFund(fund);
            userFundRecord.setType(type);
            userFundRecord.setFundType(fundType);
            userFundRecord.setBalance(userMoney.getUsableFund());
            userFundRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
            userFundRecord.setLogId(logId);
            wmUserFundRecordService.saveOrUpdate(userFundRecord);
        }
    }

    /**
     * 更新基金产品交易信息
     *
     * @param income 收益
     */
    private void updateStructureDeal(WmStructureDeal structureDeal, BigDecimal income) {
        structureDeal.setState(1); //变为已结算
        structureDeal.setIncome(income);
        wmStructureDealService.saveOrUpdate(structureDeal);
    }


    /**
     * 发送站内信和短信
     *
     * @param userId 用户id
     * @param income
     */
    public void sendSiteContent(Long userId, String productName, Integer fund, BigDecimal income) {
        try {
            WmUser user = wmUserUsersService.findById(userId);
            String replaceStr = "name:" + user.getUsername() + ",pname:" + productName + ",fund:" + fund + ",income:" + income.setScale(2, BigDecimal.ROUND_DOWN);
            //发送收益通知
            if (user != null) {
//                smsHandleService.addSiteContent("structure_expire", user.getId(), "转转赚产品到期", replaceStr, 0);
                infoSmsTimerService.sendWmInfoSmsTimer("structure_expire", user.getMobile(), replaceStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public WmStructureRepository getWmStructureRepository() {
        return wmStructureRepository;
    }

    public void setWmStructureRepository(WmStructureRepository wmStructureRepository) {
        this.wmStructureRepository = wmStructureRepository;
    }

    public WmStructureDealRepository getWmStructureDealRepository() {
        return wmStructureDealRepository;
    }

    public void setWmStructureDealRepository(WmStructureDealRepository wmStructureDealRepository) {
        this.wmStructureDealRepository = wmStructureDealRepository;
    }

    public IWmUserMoneyService getWmUserMoneyService() {
        return wmUserMoneyService;
    }

    public void setWmUserMoneyService(IWmUserMoneyService wmUserMoneyService) {
        this.wmUserMoneyService = wmUserMoneyService;
    }

    public IWmUserInvestInfoService getWmUserInvestInfoService() {
        return wmUserInvestInfoService;
    }

    public void setWmUserInvestInfoService(IWmUserInvestInfoService wmUserInvestInfoService) {
        this.wmUserInvestInfoService = wmUserInvestInfoService;
    }

    public IWmUserIncomeService getWmUserIncomeService() {
        return wmUserIncomeService;
    }

    public void setWmUserIncomeService(IWmUserIncomeService wmUserIncomeService) {
        this.wmUserIncomeService = wmUserIncomeService;
    }

    public IWmUserIncomeRecordService getWmUserIncomeRecordService() {
        return wmUserIncomeRecordService;
    }

    public void setWmUserIncomeRecordService(IWmUserIncomeRecordService wmUserIncomeRecordService) {
        this.wmUserIncomeRecordService = wmUserIncomeRecordService;
    }

    public IWmUserFundInLogService getWmUserFundInLogService() {
        return wmUserFundInLogService;
    }

    public void setWmUserFundInLogService(IWmUserFundInLogService wmUserFundInLogService) {
        this.wmUserFundInLogService = wmUserFundInLogService;
    }

    public IWmUserFundRecordService getWmUserFundRecordService() {
        return wmUserFundRecordService;
    }

    public void setWmUserFundRecordService(IWmUserFundRecordService wmUserFundRecordService) {
        this.wmUserFundRecordService = wmUserFundRecordService;
    }

    public IWmStructureDealService getWmStructureDealService() {
        return wmStructureDealService;
    }

    public void setWmStructureDealService(IWmStructureDealService wmStructureDealService) {
        this.wmStructureDealService = wmStructureDealService;
    }

    public IWmUserUsersService getWmUserUsersService() {
        return wmUserUsersService;
    }

    public void setWmUserUsersService(IWmUserUsersService wmUserUsersService) {
        this.wmUserUsersService = wmUserUsersService;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }

    public IWmPlatformUserService getWmPlatformUserService() {
        return wmPlatformUserService;
    }

    public void setWmPlatformUserService(IWmPlatformUserService wmPlatformUserService) {
        this.wmPlatformUserService = wmPlatformUserService;
    }
}