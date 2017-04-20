package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmExpmoneyDealRepository;
import com.ydzb.account.service.IWmExpmoneyDealService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class WmExpmoneyDealServiceImpl implements IWmExpmoneyDealService {

    @Autowired
    private WmExpmoneyDealRepository expmoneyDealRepository;

    @Autowired
    private ISmsHandleService smsHandleService;

    /**
     * 结算体验金
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountExpmoneyDeal(Long userId) throws Exception {
        //用户体验金账户
        WmUserExMoney userExMoney = expmoneyDealRepository.findWmUserExMoneyyByUserId(userId);
        if (userExMoney != null) {
            //活期用户资金
            WmCurrentUserAccount userAccount = expmoneyDealRepository.findWmCurrentUserAccount(userId);
            if (userAccount != null && userAccount.getExpFund().intValue() > 0) {

                //发送站内信
                sendSiteContent(userId, userAccount.getExpFund());

                //活期宝赎回日志
                Long overId = saveWmCurrentOverlog(userId, userAccount.getExpFund());

                //活期宝赎回记录
                saveWmCurrentTradeRecored(userId, FundFlow.TYJDQ, userAccount.getExpFund(), overId, 2, 1);

                //用户出账日志
                Long logId = saveWmUserFundOutLog(userId, null, 11, userAccount.getExpFund(), userExMoney.getAbleMoney());

                //体验金流水
                saveWmUserExpMoneyRecord(userId, logId, userAccount.getExpFund(), userExMoney.getAbleMoney(), 0, FundFlow.TYJDQ);

                //更新投资
                updateUserInvestinfo(userId, userAccount.getExpFund());

                //体验金总额
                userExMoney.setAmount(userExMoney.getAmount().subtract(userAccount.getExpFund()));
                //体验金使用额
                userExMoney.setUsedMoney(userExMoney.getUsedMoney().subtract(userAccount.getExpFund()));
                //更新体验金
                expmoneyDealRepository.updateWmUserExMoney(userExMoney);

                //更新活期宝
                userAccount.setExpFund(BigDecimal.ZERO);
                expmoneyDealRepository.updateCurrentUserAccount(userAccount);


            }
        }
    }


    /**
     * 更新用户投资
     *
     * @param userId 用户ID
     * @param fund   用户本金
     */

    public void updateUserInvestinfo(Long userId, BigDecimal fund) {
        WmUserInvestinfo userInvestinfo = expmoneyDealRepository.findWmUserInvestinfoByUserId(userId);
        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest().subtract(fund));
            //活期投资总额
            userInvestinfo.setAllInvestDayloan(userInvestinfo.getAllInvestDayloan().subtract(fund));
            //体验金投资总额
            userInvestinfo.setAllInvestInvest(userInvestinfo.getAllInvestInvest().subtract(fund));
            //更新用户投资
            expmoneyDealRepository.updateWmUserInvestinfo(userInvestinfo);
        }
    }


    /**
     * 活期赎回日志
     *
     * @param userId
     * @param fund
     * @return
     */
    public Long saveWmCurrentOverlog(Long userId, BigDecimal fund) {
        WmCurrentOverlog currentOverlog = new WmCurrentOverlog();
        currentOverlog.setUserId(userId);
        currentOverlog.setExpFund(fund);
        currentOverlog.setRedemptionTime(DateUtil.getSystemTimeSeconds());
        currentOverlog.setType(1);
        currentOverlog.setRedemptionFund(BigDecimal.ZERO);
        return expmoneyDealRepository.saveWmCurrentOverlog(currentOverlog);

    }


    /**
     * 活期宝流水记录
     *
     * @param userId
     * @param names
     * @param fund
     * @param logId
     * @param type
     * @param fundSource
     */
    public void saveWmCurrentTradeRecored(Long userId, String names, BigDecimal fund, Long logId, Integer type, Integer fundSource) {
        //活期宝交易
        WmCurrentTradeRecored currentTradeRecored = new WmCurrentTradeRecored();
        currentTradeRecored.setBuyTime(DateUtil.getSystemTimeSeconds());
        currentTradeRecored.setUserId(userId);
        currentTradeRecored.setNames(names);
        currentTradeRecored.setFund(fund);
        currentTradeRecored.setFundSource(fundSource);
        currentTradeRecored.setType(type);
        currentTradeRecored.setLogId(logId);
        expmoneyDealRepository.saveWmCurrentTradeRecored(currentTradeRecored);
    }


    /**
     * 用户出账日志
     *
     * @param userId
     * @param linkId
     * @param type
     * @param outFund
     * @param balance
     * @return
     */
    public Long saveWmUserFundOutLog(Long userId, Long linkId, Integer type, BigDecimal outFund, BigDecimal balance) {
        WmUserFundOutLog userFundOutLog = new WmUserFundOutLog();
        userFundOutLog.setUserId(userId);
        userFundOutLog.setLinkId(linkId);
        userFundOutLog.setType(type);
        userFundOutLog.setOutFund(outFund);
        userFundOutLog.setBalance(balance);
        userFundOutLog.setRemark("");
        userFundOutLog.setOutTime(DateUtil.getSystemTimeSeconds());
        expmoneyDealRepository.saveWmUserFundOutLog(userFundOutLog);
        return userFundOutLog.getId();
    }


    /**
     * 体验金流水
     *
     * @param userId
     * @param logId
     * @param fund
     * @param balance
     * @param type
     * @param name
     */
    public void saveWmUserExpMoneyRecord(Long userId, Long logId, BigDecimal fund, BigDecimal balance, Integer type, String name) {
        WmUserExpMoneyRecord expMoneyRecord = new WmUserExpMoneyRecord();
        expMoneyRecord.setUserId(userId);
        expMoneyRecord.setLogId(logId);
        expMoneyRecord.setFund(fund);
        expMoneyRecord.setBalance(balance);
        expMoneyRecord.setType(type);
        expMoneyRecord.setFundflow(name);
        expMoneyRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
        expmoneyDealRepository.saveWmUserExpMoneyRecord(expMoneyRecord);
    }


    /**
     * 发送站内信
     *
     * @param userId
     * @param fund
     */
    public void sendSiteContent(Long userId, BigDecimal fund) {
        WmUser user = expmoneyDealRepository.findWmUserById(userId);
        //发送收益通知
        if (user != null) {
            smsHandleService.addSiteContent("invest_over", user.getId(), "体验金到期", "name:" + user.getUsername() + ",value:" + fund.intValue(), 0);
        }
    }

    /**
     * 创建
     * @param userId
     * @param fund
     * @param type
     * @return
     */
    @Override
    public WmExpmoneyDeal createOne(Long userId, BigDecimal fund, Integer type) {

        WmExpmoneyDeal expmoneyDeal = new WmExpmoneyDeal();
        expmoneyDeal.setUserId(userId);
        expmoneyDeal.setFund(fund);
        expmoneyDeal.setCreated(DateUtil.getSystemTimeSeconds());
        expmoneyDeal.setCloseDate(DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())));
        expmoneyDeal.setStatus(0);
        expmoneyDeal.setType(type);

        return expmoneyDealRepository.saveOrUpdate(expmoneyDeal);
    }

    public WmExpmoneyDealRepository getExpmoneyDealRepository() {
        return expmoneyDealRepository;
    }

    public void setExpmoneyDealRepository(WmExpmoneyDealRepository expmoneyDealRepository) {
        this.expmoneyDealRepository = expmoneyDealRepository;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }


}
