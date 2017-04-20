package com.ydzb.account.service.impl;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmCurrentQueueRepository;
import com.ydzb.account.service.IWmCurrentQueueService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class WmCurrentQueueServiceImpl implements IWmCurrentQueueService {

    @Autowired
    private WmCurrentQueueRepository currentQueueRepository;

    @Autowired
    private ISmsHandleService smsHandleService;

    /**
     * 查询产品
     *
     * @return
     */
    @Override
    public WmProductInfo findWmProductInfo() {
        return currentQueueRepository.findWmProductInfo();
    }

    /**
     * 查询产品总分数
     *
     * @return
     */
    @Override
    public Integer findTotalCopies() {
        return currentQueueRepository.findTotalCopies();
    }

    /**
     * 更新产品
     *
     * @param productInfo
     */
    @Override
    public void updateWmProductInfo(WmProductInfo productInfo) {
        currentQueueRepository.updateWmProductInfo(productInfo);
    }


    /**
     * 查询排队表 最大ID 和 最小ID
     *
     * @return
     */
    @Override
    public IDRange findMaxIdAndMinId() {
        return currentQueueRepository.findMaxIdAndMinId();
    }


    @Override
    public int accountCurrentQueue(Long id, Long productId, BigDecimal interestRate) throws Exception {

        //查询活期宝队列
        WmCurrentQueue currentQueue = currentQueueRepository.findWmCurrentQueue(id);
        //判断是否存在活期宝队列
        if (currentQueue != null) {
            //查询活期宝预投
            WmCurrentPrepay currentPrepay = currentQueueRepository.findWmCurrentPrepay(currentQueue.getDayloanPrepayId());
            //判断是否存在预投
            if (currentPrepay != null) {

                //保存活期日志和交易记录
                Long linkId = saveCurrentBuy(currentQueue.getUserId(), productId, interestRate, currentQueue.getPrepayCopies(), currentQueue.getType());

                //用户出账日志
                Long outLogId = saveWmUserFundOutLog(currentQueue.getUserId(), linkId, 2, currentQueue.getPrepayCopies(), "");

                //用户资金流水
                saveFundRecord(currentQueue.getUserId(), currentQueue.getPrepayCopies(), currentQueue.getType(), currentPrepay.getId(), outLogId);

                //保存或者更新活期宝账户
                updateCurrentUserAccount(currentQueue.getUserId(), currentQueue.getPrepayCopies(), currentQueue.getType());

                //更新用户账户信息 用户投资 用户资金 用户体验金
                updateUser(currentQueue.getUserId(), currentQueue.getPrepayCopies(), currentQueue.getType());

                //更新用户预投
                currentPrepay.setStatus(1);
                currentQueueRepository.updateWmCurrentPrepay(currentPrepay);

                //删除预投排队
                currentQueueRepository.removeWmCurrentQueue(currentQueue);

                //发送站内信
                sendSiteContent(currentPrepay.getUserId(), currentPrepay.getBuyFund());

                return currentPrepay.getBuyCopies();
            }
        }

        return 0;
    }


    /**
     * 购买记录和日志
     *
     * @param userId
     * @param productId
     * @param apr
     * @param fund
     * @param type
     * @return 0、本金 1、体验金
     */
    public Long saveCurrentBuy(Long userId, Long productId, BigDecimal apr, BigDecimal fund, Integer type) {

        //活期宝购买日志
        WmCurrentBuylog wmCurrentBuylog = new WmCurrentBuylog();
        wmCurrentBuylog.setUserId(userId);
        wmCurrentBuylog.setProductId(productId);
        wmCurrentBuylog.setGrandApr(BigDecimal.ZERO);
        wmCurrentBuylog.setDevice(4);
        wmCurrentBuylog.setBuyTime(DateUtil.getSystemTimeSeconds());
        wmCurrentBuylog.setApr(apr);
        wmCurrentBuylog.setBuyCopies(fund.intValue());
        //判断是否是体验金
        if (type == 1) {
            wmCurrentBuylog.setExpFund(fund);
            wmCurrentBuylog.setBuyFund(BigDecimal.ZERO);
        } else {
            wmCurrentBuylog.setExpFund(BigDecimal.ZERO);
            wmCurrentBuylog.setBuyFund(fund);
        }

        Long logId = currentQueueRepository.saveWmCurrentBuylog(wmCurrentBuylog);

        //活期宝交易
        WmCurrentTradeRecored currentTradeRecored = new WmCurrentTradeRecored();
        currentTradeRecored.setBuyTime(DateUtil.getSystemTimeSeconds());
        currentTradeRecored.setUserId(userId);
        currentTradeRecored.setFund(fund);
        currentTradeRecored.setType(1);
        currentTradeRecored.setLogId(logId);
        //判断是否是体验金
        if (type == 1) {
            currentTradeRecored.setNames(FundFlow.TYJTJ);
            currentTradeRecored.setFundSource(1);
        } else {
            currentTradeRecored.setNames(FundFlow.CURRENT);
            currentTradeRecored.setFundSource(0);
        }

        return currentQueueRepository.saveWmCurrentTradeRecored(currentTradeRecored);

    }


    /**
     * 保存或者更新活期宝账户
     *
     * @param userId
     * @param fund
     * @param type   0、本金 1、体验金
     */
    public void updateCurrentUserAccount(Long userId, BigDecimal fund, Integer type) {

        WmCurrentUserAccount currentUserAccount = currentQueueRepository.findWmCurrentUserAccount(userId);

        if (currentUserAccount != null) {
            //0、本金 1、体验金
            if (type == 1) {
                currentUserAccount.setExpFund(currentUserAccount.getExpFund().add(fund));
            } else {
                currentUserAccount.setAllFund(currentUserAccount.getAllFund().add(fund));
            }

            //更新
            currentQueueRepository.updateCurrentUserAccount(currentUserAccount);

        } else {

            currentUserAccount = new WmCurrentUserAccount();

            //0、本金 1、体验金
            if (type == 1) {
                currentUserAccount.setAllFund(BigDecimal.ZERO);
                currentUserAccount.setExpFund(fund);
            } else {
                currentUserAccount.setAllFund(fund);
                currentUserAccount.setExpFund(BigDecimal.ZERO);
            }

            currentUserAccount.setUserId(userId);
            currentUserAccount.setProfit(BigDecimal.ZERO);
            currentUserAccount.setDlLastSettlementDate(0L);
            //保存
            currentQueueRepository.saveCurrentUserAccount(currentUserAccount);
        }
    }


    /**
     * 更新用户
     *
     * @param userId 用户ID
     * @param fund   金额
     * @param type   类型
     */
    public void updateUser(Long userId, BigDecimal fund, Integer type) {

        WmUserInvestinfo userInvestinfo = currentQueueRepository.findWmUserInvestinfoByUserId(userId);

        if (userInvestinfo != null) {
            //投资总额
            userInvestinfo.setAllInvest(userInvestinfo.getAllInvest().add(fund));
            //活期投资总额
            userInvestinfo.setAllInvestDayloan(userInvestinfo.getAllInvestDayloan().add(fund));
            //体验金投资总额
            if (type == 1) {
                userInvestinfo.setAllInvestInvest(userInvestinfo.getAllInvestInvest().add(fund));
            }
            //更新用户投资
            currentQueueRepository.updateWmUserInvestinfo(userInvestinfo);
        }

        //体验金
        if (type == 1) {
            //体验金账户
            WmUserExMoney userExMoney = currentQueueRepository.findWmUserExMoneyyByUserId(userId);
            if (userExMoney != null) {
                //冻结体验金金额
                userExMoney.setBlockedMoney(userExMoney.getBlockedMoney().subtract(fund));
                //已用体验金金额
                userExMoney.setUsedMoney(userExMoney.getUsedMoney().add(fund));
                //更新体验金
                currentQueueRepository.updateWmUserExMoney(userExMoney);
            }

        } else {
            //资金账户
            WmUserMoney userMoney = currentQueueRepository.findWmUserMoneyByUserId(userId);
            if (userMoney != null) {
                //资金冻结金额
                userMoney.setFreezeFund(userMoney.getFreezeFund().subtract(fund));
                //更新用户金额
                currentQueueRepository.updateWmUserMoney(userMoney);
            }

        }
    }


    /**
     * 用户出账日志
     *
     * @param userId
     * @param linkId
     * @param type
     * @param outFund
     * @param remark
     * @return
     */
    public Long saveWmUserFundOutLog(Long userId, Long linkId, Integer type, BigDecimal outFund, String remark) {
        WmUserMoney userMoney = currentQueueRepository.findWmUserMoneyByUserId(userId);
        WmUserFundOutLog userFundOutLog = new WmUserFundOutLog();
        userFundOutLog.setUserId(userId);
        userFundOutLog.setLinkId(linkId);
        userFundOutLog.setType(type);
        userFundOutLog.setOutFund(outFund);
        if (userMoney != null) userFundOutLog.setBalance(userMoney.getUsableFund());
        userFundOutLog.setRemark(remark);
        userFundOutLog.setOutTime(DateUtil.getSystemTimeSeconds());
        return currentQueueRepository.saveWmUserFundOutLog(userFundOutLog);
    }


    /**
     * 用户资金流水
     *
     * @param userId
     * @param fund
     * @param type
     * @param prepayId
     * @param outLogId
     */
    public void saveFundRecord(Long userId, BigDecimal fund, Integer type, Long prepayId, Long outLogId) {
        WmUserMoney userMoney = currentQueueRepository.findWmUserMoneyByUserId(userId);
        if (type == 1) {
            WmUserExMoney userExMoney = currentQueueRepository.findWmUserExMoneyyByUserId(userId);
            if (userExMoney != null) {
                //体验金解冻日志
                Long blokedLogId = saveWmUserFundBlokedLog(userId, 2, 3, prepayId, fund, userExMoney.getAbleMoney().add(fund));
                //体验金解冻流水
                saveWmUserExpMoneyRecord(userId, FundFlow.TYJYTJD, 3, fund, userExMoney.getAbleMoney().add(fund), blokedLogId);
                //体验金出账流水
                saveWmUserExpMoneyRecord(userId, FundFlow.TYJTJ, 0, fund, userExMoney.getAbleMoney(), outLogId);
            }
        } else {
            if (userMoney != null) {
                //资金解冻日志
                Long blokedLogId = saveWmUserFundBlokedLog(userId, 2, 2, prepayId, fund, userMoney.getUsableFund().add(fund));
                //资金流水解冻
                saveWmUserFundRecord(userId, FundFlow.ADVANCE, fund, 3, 6, userMoney.getUsableFund().add(fund), blokedLogId);
                //资金流水购买
                saveWmUserFundRecord(userId, FundFlow.CURRENT, fund, 0, 6, userMoney.getUsableFund(), outLogId);
            }
        }
    }


    /**
     * 体验金流水
     *
     * @param userId
     * @param fundFlow
     * @param type
     * @param fund
     * @param usableFund
     * @param logId
     */
    public void saveWmUserExpMoneyRecord(Long userId, String fundFlow, Integer type, BigDecimal fund, BigDecimal usableFund, Long logId) {
        //体验金解冻流水
        WmUserExpMoneyRecord expMoneyRecord = new WmUserExpMoneyRecord();
        expMoneyRecord.setUserId(userId);
        expMoneyRecord.setFundflow(fundFlow);
        expMoneyRecord.setType(type);
        expMoneyRecord.setFund(fund);
        expMoneyRecord.setLogId(logId);
        expMoneyRecord.setBalance(usableFund);
        expMoneyRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
        currentQueueRepository.saveWmUserExpMoneyRecord(expMoneyRecord);

    }

    /**
     * 解冻日志
     *
     * @param userId
     * @param type
     * @param linkType
     * @param linkId
     * @param fund
     * @param usableFund
     * @return
     */
    public Long saveWmUserFundBlokedLog(Long userId, Integer type, Integer linkType, Long linkId, BigDecimal fund, BigDecimal usableFund) {
        //资金解冻日志
        WmUserFundBlokedLog userFundBlokedLog = new WmUserFundBlokedLog();
        userFundBlokedLog.setUserId(userId);
        userFundBlokedLog.setType(type);
        userFundBlokedLog.setLinkType(linkType);
        userFundBlokedLog.setLinkId(linkId);
        userFundBlokedLog.setFund(fund);
        userFundBlokedLog.setUsableFund(usableFund);
        userFundBlokedLog.setOperationTime(DateUtil.getSystemTimeSeconds());
        return currentQueueRepository.saveWmUserFundBlokedLog(userFundBlokedLog);
    }

    /**
     * 资金流水购买
     *
     * @param userId
     * @param fundFlow
     * @param fund
     * @param type
     * @param fundType
     * @param usableFund
     * @param logId
     */
    public void saveWmUserFundRecord(Long userId, String fundFlow, BigDecimal fund, Integer type, Integer fundType, BigDecimal usableFund, Long logId) {
        WmUserFundRecord fundRecord = new WmUserFundRecord();
        fundRecord.setUserId(userId);
        fundRecord.setFundflow(fundFlow);
        fundRecord.setFund(fund);
        fundRecord.setType(type);
        fundRecord.setFundType(fundType);
        fundRecord.setBalance(usableFund);
        fundRecord.setLogId(logId);
        fundRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
        currentQueueRepository.saveWmUserFundRecord(fundRecord);
    }

    /**
     * 发送站内信
     *
     * @param userId
     * @param fund
     */
    public void sendSiteContent(Long userId, BigDecimal fund) {
        WmUser user = currentQueueRepository.findWmUserById(userId);
        WmUserMoney userMoney = currentQueueRepository.findWmUserMoneyByUserId(userId);
        if (user != null && userMoney != null) {
            // 发送站内信
            smsHandleService.addSiteContent("buy_dayloan", userId, "活期宝购买", "name:" + user.getUsername() + ",money:" + fund.intValue() + ",usable:" + userMoney.getUsableFund().setScale(2, BigDecimal.ROUND_DOWN), 0);
        }
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public WmCurrentQueueRepository getCurrentQueueRepository() {
        return currentQueueRepository;
    }

    public void setCurrentQueueRepository(WmCurrentQueueRepository currentQueueRepository) {
        this.currentQueueRepository = currentQueueRepository;
    }

}
