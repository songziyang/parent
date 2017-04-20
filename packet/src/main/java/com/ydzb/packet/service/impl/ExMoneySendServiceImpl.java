package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.repository.IExpMoneyRepository;
import com.ydzb.packet.repository.RpUserRepository;
import com.ydzb.packet.service.IExMoneySendService;
import com.ydzb.packet.service.IExpMoneySendService;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.user.entity.*;
import com.ydzb.user.repository.*;
import com.ydzb.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Administrator on 15-10-26.
 */
@Service
public class ExMoneySendServiceImpl  extends BaseServiceImpl<ExpMoney, Long> implements IExMoneySendService {

    @Autowired
    private RpUserRepository rpUserRepository;
    @Autowired
    private UserExpMoneyRepository userExpMoneyRepository;
    @Autowired
    private IExpMoneyRepository expMoneyRepository;
    @Autowired
    private IUserMoneyRepository userMoneyRepository;
    @Autowired
    private IUserFundInLogRepository userFundInLogRepository;
    @Autowired
    private IUserExpMoneyRepository iUserExpMoneyRepository;
    @Autowired
    private IUserExpMoneyRecordRepository userExpMoneyRecordRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISmsHandleService smsHandleService;

    /**
     * 发送体验金
     *
     * @param rpUser
     * @param expMoneyId
     * @return
     */
    @Override
    public String sendMoney(RpUser rpUser, Long expMoneyId) throws Exception{
        ExpMoney expMoney = expMoneyRepository.findOne(expMoneyId);
        //发送体验金
        rpUserRepository.sendExpmoney(rpUser, expMoneyId);
        //查询发送用户id
        List<BigInteger> userIds = rpUserRepository.querySendUserIds(rpUser);
        //更新用户体验金
        userExpMoneyRepository.updateUserMoney(userIds, new BigDecimal(expMoney.getCopies()));
        //添加用户进账日志以及体验金流水记录
        addLog(userIds, expMoney);
        //发送短信
        sendSms(userIds, expMoney);
        //发送站内信
        sendSiteSms(userIds, expMoney.getCopies());
        return "发送成功";
    }

    /**
     * 添加日志
     *
     * @param userIds
     * @param expMoney
     */
    private void addLog(List<BigInteger> userIds, ExpMoney expMoney) throws Exception {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        for (BigInteger userId : userIds) {
            if (userId != null) {
                Long currentTime = DateUtil.getSystemTimeSeconds();
                UserFundInLog inLog = addUserFundInLog(userId.longValue(), expMoney, currentTime);
                addUserExpMoneyRecord(userId.longValue(), expMoney.getCopies(), inLog.getId(), currentTime);
            }
        }
    }

    /**
     * 添加用户进账日志
     *
     * @param userId
     * @param expMoney
     * @param currentTime
     * @return
     */
    private UserFundInLog addUserFundInLog(Long userId, ExpMoney expMoney, Long currentTime) throws Exception {
        UserMoney userMoney = userMoneyRepository.findUserMoney(userId);
        UserFundInLog inLog = new UserFundInLog();
        inLog.setUserId(userId);
        inLog.setType(21);
        inLog.setReceiptsTime(currentTime);
        if(expMoney != null ) inLog.setIncomeFund(new BigDecimal(expMoney.getCopies()));
        if(userMoney != null ) inLog.setUsableFund(userMoney.getUsableFund());
        inLog.setLinkId(expMoney.getId());
        return userFundInLogRepository.save(inLog);
    }


    /**
     * 添加用户体验金记录
     *
     * @param userId
     * @param expMoneyFund
     * @param inLogId
     * @param currentTime
     */
    private void addUserExpMoneyRecord(Long userId, Integer expMoneyFund, Long inLogId, Long currentTime) throws Exception {
        UserExMoney userExpMoney = iUserExpMoneyRepository.findOneByUserId(userId);
        UserExpMoneyRecord record = new UserExpMoneyRecord();
        record.setUserId(userId);
        record.setFundFlow("体验金活动");
        record.setFund(new BigDecimal(expMoneyFund));
        record.setBalance(userExpMoney.getAbleMoney());
        record.setType((byte) 1);
        record.setRecordTime(currentTime);
        record.setLogId(inLogId);
        userExpMoneyRecordRepository.save(record);
    }


    /**
     * 发送站内信
     *
     * @param userIds
     * @param expMoney
     */
    public void sendSiteSms(List<BigInteger> userIds, Integer expMoney) throws Exception {
        for (BigInteger userId : userIds) {
            smsHandleService.addSiteContent("invest_send", userId.longValue(), "体验金发放", "type:平台,fund:" + expMoney, 0);
        }
    }

    /**
     * 发送短信
     *
     * @param userIds
     * @param expMoney
     */
    private void sendSms(List<BigInteger> userIds, ExpMoney expMoney) throws Exception {
        if (expMoney.getIsSend() == ExpMoney.NOTSEND) {
            return;
        }
        User user;
        for (BigInteger userId : userIds) {
            user = userService.findOne(userId.longValue());
            if (user == null) {
                continue;
            }
            smsHandleService.sendUserSms("invest_send_old", user.getMobile(), "content:" + expMoney.getContent());
        }
    }

    public RpUserRepository getRpUserRepository() {
        return rpUserRepository;
    }

    public void setRpUserRepository(RpUserRepository rpUserRepository) {
        this.rpUserRepository = rpUserRepository;
    }

    public UserExpMoneyRepository getUserExpMoneyRepository() {
        return userExpMoneyRepository;
    }

    public void setUserExpMoneyRepository(UserExpMoneyRepository userExpMoneyRepository) {
        this.userExpMoneyRepository = userExpMoneyRepository;
    }

    public IExpMoneyRepository getExpMoneyRepository() {
        return expMoneyRepository;
    }

    public void setExpMoneyRepository(IExpMoneyRepository expMoneyRepository) {
        this.expMoneyRepository = expMoneyRepository;
    }

    public IUserMoneyRepository getUserMoneyRepository() {
        return userMoneyRepository;
    }

    public void setUserMoneyRepository(IUserMoneyRepository userMoneyRepository) {
        this.userMoneyRepository = userMoneyRepository;
    }

    public IUserFundInLogRepository getUserFundInLogRepository() {
        return userFundInLogRepository;
    }

    public void setUserFundInLogRepository(IUserFundInLogRepository userFundInLogRepository) {
        this.userFundInLogRepository = userFundInLogRepository;
    }

    public IUserExpMoneyRepository getiUserExpMoneyRepository() {
        return iUserExpMoneyRepository;
    }

    public void setiUserExpMoneyRepository(IUserExpMoneyRepository iUserExpMoneyRepository) {
        this.iUserExpMoneyRepository = iUserExpMoneyRepository;
    }

    public IUserExpMoneyRecordRepository getUserExpMoneyRecordRepository() {
        return userExpMoneyRecordRepository;
    }

    public void setUserExpMoneyRecordRepository(IUserExpMoneyRecordRepository userExpMoneyRecordRepository) {
        this.userExpMoneyRecordRepository = userExpMoneyRecordRepository;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }
}