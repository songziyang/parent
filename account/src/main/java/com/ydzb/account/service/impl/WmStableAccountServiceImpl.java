package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmStableDealRepository;
import com.ydzb.account.repository.WmStableRepository;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.account.service.IWmStableAccountservice;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基金产品结算service实现
 */
@Service
public class WmStableAccountServiceImpl implements IWmStableAccountservice {

    @Autowired
    private WmStableRepository wmStableRepository;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private WmStableDealRepository wmStableDealRepository;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    /**
     * 根据申购结束日期查询状态为申购中的
     *
     * @param date 申购结束日期
     * @return
     */
    @Override
    public List<WmStable> findByEndDate(Long date) {
        return wmStableRepository.findByEndDate(date);
    }

    /**
     * 根据到期日期查询基金产品
     *
     * @param closeDate 到期日期
     * @return
     */
    @Override
    public List<WmStable> findByCloseDate(Long closeDate) {
        return wmStableRepository.findByCloseDate(closeDate);
    }

    /**
     * 根据到期日期查询基金产品
     *
     * @param closeDate 到期日期
     * @return
     */
    @Override
    public List<WmStable> findByCloseDateAndNoApr(Long closeDate) {
        return wmStableRepository.findByCloseDateAndNoApr(closeDate);
    }

    /**
     * 根据申购结束日期更新满标状态
     * 将申购结束日期为指定日期并且状态为申购的基金产品状态置为满标状态
     * 并且设置满标日期以及结束日期
     *
     * @param stable 基金产品
     */
    @Override
    public void updateFullStandardState(WmStable stable) throws Exception {
        stable.setState(WmStable.STATE_FULLSTANDARD);
        //满标日期为结束申购日期 + 1(结算日期)
        stable.setFullTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        //到期日期为满标日日期 + 锁定期
        stable.setCloseTime(stable.getFullTime() + stable.getDays() * 24 * 3600);
        wmStableRepository.updateStable(stable);
    }


    /**
     * 结算收益
     *
     * @param stable 基金产品
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountStableRevenue(WmStable stable, Long userId) throws Exception {
        List<WmStableDeal> stableDeals = wmStableDealRepository.findWmStableDeal(userId, stable.getId());
        if (stableDeals != null && !stableDeals.isEmpty()) {
            for (WmStableDeal stableDeal : stableDeals) {
                if (stableDeal != null) {
                    BigDecimal income = culculateIncome(stable, stableDeal);  //收益
                    BigDecimal principal = BigDecimal.valueOf(stableDeal.getCopies());    //本金
                    //更新用户资金
                    updateUserMoney(userId, principal, income);
                    //更新用户投资信息
                    updateUserInvest(userId, principal);
                    //更新用户收益
                    updateUserIncome(userId, income);
                    //增加用户收益记录
                    saveWmUserIncomeRecord(userId, income, stable.getName() + "收益", WmUserIncomeRecord.FUNDATION_PRODUCT);
                    //增加用户入账日志
                    Long inLogId = saveWmUserFundInLog(userId, stableDeal.getId(), 22, principal, income);
                    //增加用户资金记录
                    saveWmUserFundRecord(userId, stable.getName() + "收益", income, 1, 3, inLogId);
                    saveWmUserFundRecord(userId, stable.getName() + "本金", principal, 1, 2, inLogId);
                    //更新基金产品交易记录
                    updateStableDeal(stableDeal, income);
                    sendSiteContent(userId, stable.getName(), stableDeal.getCopies(), income);
                }
            }
        }
    }

    /**
     * 更新基金产品信息
     *
     * @param stable 基金产品
     */
    @Override
    public void updateStable(WmStable stable) throws Exception {
        stable.setState((byte) 3);
        wmStableRepository.updateStable(stable);
    }

    /**
     * 更新基金产品交易信息
     *
     * @param stableDeal 基金产品交易
     * @param income     收益
     */
    private void updateStableDeal(WmStableDeal stableDeal, BigDecimal income) {
        stableDeal.setState(1); //变为已结算
        stableDeal.setIncome(income);
        wmStableDealRepository.update(stableDeal);
    }

    /**
     * 计算收益
     *
     * @param stable 基金产品
     * @return
     */
    private BigDecimal culculateIncome(WmStable stable, WmStableDeal stableDeal) {
        //收益 = 用户购买份数 * 利率 * 锁定期(购买天数)/36500天
        return BigDecimal.valueOf(stableDeal.getCopies())
                .multiply(stable.getApr())
                .multiply(BigDecimal.valueOf(stable.getDays()))
                .divide(BigDecimal.valueOf(36500), 6, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 发送站内信和短信
     *
     * @param userId 用户id
     * @param income
     */
    public void sendSiteContent(Long userId, String productName, Integer fund, BigDecimal income) {
        try {
            WmUser user = wmStableRepository.findWmUserById(userId);
            String replaceStr = "name:" + user.getUsername() + ",pname:" + productName + ",fund:" + fund + ",income:" + income.setScale(2, BigDecimal.ROUND_DOWN);
            //发送收益通知
            if (user != null) {
//                smsHandleService.addSiteContent("stable_expire", user.getId(), "基金产品到期", replaceStr, 0);
                infoSmsTimerService.sendWmInfoSmsTimer("stable_expire", user.getMobile(), replaceStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新用户收益信息
     *
     * @param userId 用户id
     * @param income 收益
     */
    private void updateUserIncome(Long userId, BigDecimal income) {
        WmUserIncome userIncome = wmStableRepository.findWmUserIncomeByUserId(userId);
        if (userIncome != null) {
            //现基金收益 = 原基金收益 + 收益
            userIncome.setAllIncomeWjb(userIncome.getAllIncomeWjb().add(income));
            //现总收益 = 原总收益 + 收益
            userIncome.setAllIncome(userIncome.getAllIncome().add(income));
            //现昨日收益 = 昨日收益 + 收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome().add(income));
            wmStableRepository.updateWmUserIncome(userIncome);
        }
    }

    /**
     * 更新用户投资
     *
     * @param userId   用户id
     * @param pricipal 本金
     */
    private void updateUserInvest(Long userId, BigDecimal pricipal) {
        WmUserInvestinfo userInvest = wmStableRepository.findWmUserInvestinfoByUserId(userId);
        if (userInvest != null) {
            //现基金投资总额 = 原基金投资总额 - 本金
            userInvest.setAllInvestWjb(userInvest.getAllInvestWjb().subtract(pricipal));
            //现投资总额 = 原投资总额 - 本金
            userInvest.setAllInvest(userInvest.getAllInvest().subtract(pricipal));
            wmStableRepository.updateWmUserInvestinfo(userInvest);
        }
    }

    /**
     * 更新用户资金
     *
     * @param userId    用户id
     * @param principal 本金
     * @param income    收益
     */
    private void updateUserMoney(Long userId, BigDecimal principal, BigDecimal income) {
        WmUserMoney userMoney = wmStableRepository.findWmUserMoneyByUserId(userId);
        if (userMoney != null) {
            //现余额 = 原余额 + 本金 + 收益
            userMoney.setUsableFund(userMoney.getUsableFund().add(principal).add(income));
            //现总额 = 原总额 + 收益
            userMoney.setTotalFund(userMoney.getTotalFund().add(income));
            wmStableRepository.updateWmUserMoney(userMoney);
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
        wmStableRepository.saveWmUserIncomeRecord(userIncomeRecord);
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
        WmUserMoney userMoney = wmStableRepository.findWmUserMoneyByUserId(userId);
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
            wmStableRepository.saveWmUserFundInLog(log);
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
        WmUserMoney userMoney = wmStableRepository.findWmUserMoneyByUserId(userId);
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
            wmStableRepository.saveWmUserFundRecord(userFundRecord);
        }
    }
}