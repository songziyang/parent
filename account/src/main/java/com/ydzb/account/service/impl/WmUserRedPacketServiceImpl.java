package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmUserRedPacketRepository;
import com.ydzb.account.service.IWmUserRedPacketService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class WmUserRedPacketServiceImpl implements IWmUserRedPacketService {

    @Autowired
    private WmUserRedPacketRepository userRedPacketRepository;

    @Autowired
    private ISmsHandleService smsHandleService;


    /**
     * 根据用户 结算未投资的红包
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountUsedUserRedPacket(Long userId) throws Exception {
        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        //用户即将到期红包
        List<WmUserRedPacket> userRedPackets = userRedPacketRepository.findWmUserRedPacketUsed(userId, curDate);

        if (userRedPackets != null && !userRedPackets.isEmpty()) {
            for (WmUserRedPacket userRedPacket : userRedPackets) {
                //判读是否有要结算的未投资的红包
                if (userRedPacket != null) {
                    //更新红包状态
                    userRedPacket.setStatus(WmUserRedPacket.STATUS_OVERTIME);
                    userRedPacketRepository.updateWmUserRedPacket(userRedPacket);
                    //红包过期日志
                    saveWmUserRedPacketLog(userRedPacket.getId(), userRedPacket.getProductTradeId(),
                            userRedPacket.getProductType(), userRedPacket.getUserId(), null, userRedPacket.getRedpacketName(), 3,
                            userRedPacket.getRedpacketType(), userRedPacket.getGiveValue());
                    //发送站内信
                    sendOutSiteContent(userRedPacket.getUserId(), userRedPacket.getGiveValue(), userRedPacket.getRedpacketType());

                }
            }
        }
    }


    /**
     * 发送站内信
     *
     * @param userId
     * @param giveValue
     * @param redpacketType
     */
    public void sendOutSiteContent(Long userId, BigDecimal giveValue, Integer redpacketType) {
        WmUser user = userRedPacketRepository.findWmUserById(userId);
        //发送收益通知
        if (user != null) {
            String typeName;
            if (redpacketType == 1) {
                typeName = "现金";
                smsHandleService.addSiteContent("redpacket_outdate", user.getId(), "红包过期", "name:" + user.getUsername() + ",value:" + giveValue + ",type:" + typeName, 0);
            } else if (redpacketType == 2) {
                typeName = "加息券";
                smsHandleService.addSiteContent("redpacket_outdate", user.getId(), "红包过期", "name:" + user.getUsername() + ",value:" + giveValue + "%,type:" + typeName, 0);
            } else if (redpacketType == 3) {
                typeName = "定存红包";
                smsHandleService.addSiteContent("redpacket_outdate", user.getId(), "红包过期", "name:" + user.getUsername() + ",value:" + giveValue + ",type:" + typeName, 0);
            }

        }
    }


    /**
     * 根据用户 结算投资的红包
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountInvestedUserRedPacket(Long userId) throws Exception {

        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        //用户已使用的红包
        List<WmUserRedPacket> userRedPackets = userRedPacketRepository.findWmUserRedPacketInvested(userId, curDate);

        if (userRedPackets != null && !userRedPackets.isEmpty()) {

            for (WmUserRedPacket userRedPacket : userRedPackets) {

                //判读是否有要结算的未投资的红包
                if (userRedPacket != null) {
                    //更新红包状态
                    userRedPacket.setStatus(WmUserRedPacket.STATUS_OVERTIME);
                    userRedPacketRepository.updateWmUserRedPacket(userRedPacket);

                    //产品ID
                    Long productId = null;
                    //查询产品交易ID
                    if (userRedPacket.getProductTradeId() != null) {
                        //查询定存产品交易ID
                        WmRagularUserAccount userAccount = userRedPacketRepository.findWmRagularUserAccountById(userRedPacket.getProductTradeId());
                        //判断是否存在定存产品交易
                        if (userAccount != null) {
                            productId = userAccount.getProductId();
                        }
                    }

                    //红包过期日志
                    saveWmUserRedPacketLog(userRedPacket.getId(), userRedPacket.getProductTradeId(),
                            userRedPacket.getProductType(), userRedPacket.getUserId(), productId, userRedPacket.getRedpacketName(), 2,
                            userRedPacket.getRedpacketType(), userRedPacket.getGiveValue());

                    //查询红包使用记录
                    WmRedpacketUsed redpacketUsed = userRedPacketRepository.findWmRedpacketUsed(userRedPacket.getUserId(), userRedPacket.getId());

                    //判断是否有使用记录
                    if (redpacketUsed != null) {
                        userRedPacketRepository.removeWmRedpacketUsed(redpacketUsed);
                    }

                    //发送站内信
                    sendExpireSiteContent(userRedPacket.getUserId(), userRedPacket.getGiveValue(), userRedPacket.getRedpacketType());

                }
            }
        }
    }


    /**
     * 发送站内信
     *
     * @param userId
     * @param giveValue
     * @param redpacketType
     */
    public void sendExpireSiteContent(Long userId, BigDecimal giveValue, Integer redpacketType) {
        WmUser user = userRedPacketRepository.findWmUserById(userId);
        //发送到期通知
        if (user != null) {
            String typeName;
            if (redpacketType == 1) {
                typeName = "现金";
                smsHandleService.addSiteContent("redpacket_expire", user.getId(), "红包到期", "name:" + user.getUsername() + ",value:" + giveValue + ",type:" + typeName, 0);
            } else if (redpacketType == 2) {
                typeName = "加息券";
                smsHandleService.addSiteContent("redpacket_expire", user.getId(), "红包到期", "name:" + user.getUsername() + ",value:" + giveValue + "%,type:" + typeName, 0);
            } else if (redpacketType == 3) {
                typeName = "定存红包";
                smsHandleService.addSiteContent("redpacket_expire", user.getId(), "红包到期", "name:" + user.getUsername() + ",value:" + giveValue + ",type:" + typeName, 0);
            }

        }
    }


    /**
     * 保存红包操作日志
     *
     * @param redpacketUsersId
     * @param linkId
     * @param productType
     * @param userId
     * @param productId
     * @param redpackeName
     * @param operationType
     * @param redpacketType
     * @param giveValue
     */
    public void saveWmUserRedPacketLog(Long redpacketUsersId, Long linkId, Integer productType, Long userId, Long productId,
                                       String redpackeName, Integer operationType, Integer redpacketType, BigDecimal giveValue) {
        //红包操作日志
        WmUserRedPacketLog userRedPacketLog = new WmUserRedPacketLog();
        //用户红包ID
        userRedPacketLog.setRedpacketUsersId(redpacketUsersId);
        //产品交易ID
        userRedPacketLog.setLinkId(linkId);
        //产品类别  0-现金 1-活期宝 2-定存宝
        userRedPacketLog.setProductType(productType);
        //用户ID
        userRedPacketLog.setUserId(userId);
        //产品ID
        userRedPacketLog.setProductId(productId);
        //红包名称
        userRedPacketLog.setRedpacketName(redpackeName);
        //操作类型 1-使用 ，2-到期 ， 3-过期
        userRedPacketLog.setOperationType(operationType);
        //红包类型： 1现金红包，2加息卷
        userRedPacketLog.setRedpacketType(redpacketType);
        //红包赠送值
        userRedPacketLog.setGiveValue(giveValue);
        //操作时间
        userRedPacketLog.setOperationTime(DateUtil.getSystemTimeSeconds());
        //保存红包操作日志
        userRedPacketRepository.saveWmUserRedPacketLog(userRedPacketLog);

    }

    @Override
    public WmUserRedPacket createOne(Long userId, Long productTradeId, Long redpacketId, String redpacketName,
            Integer productType, Integer redpacketType, Integer triggerType, Long beginTime, Long finishTime,
            Integer useDays, Integer investDays, BigDecimal giveValue) {
        WmUserRedPacket userRedPacket = new WmUserRedPacket();
        userRedPacket.setUserId(userId);
        userRedPacket.setProductTradeId(productTradeId);
        userRedPacket.setRedpacketId(redpacketId);
        userRedPacket.setRedpacketName(redpacketName);
        userRedPacket.setProductType(productType);
        userRedPacket.setRedpacketType(redpacketType);
        userRedPacket.setTriggerType(triggerType);
        userRedPacket.setBeginTime(beginTime);
        userRedPacket.setFinishTime(finishTime);
        userRedPacket.setGetTime(DateUtil.getSystemTimeSeconds());
        userRedPacket.setUseFinishTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + (useDays == null? Integer.valueOf(0): useDays * 24 * 3600));
        userRedPacket.setInvestDays(investDays);
        userRedPacket.setGiveValue(giveValue);
        userRedPacket.setStatus(1);
        userRedPacket.setCreated(DateUtil.getSystemTimeSeconds());
        return userRedPacketRepository.saveWmUserRedPacket(userRedPacket);
    }

    /**
     * 根据到期时间查询红包信息
     * @param finishTime
     * @return
     */
    @Override
    public List<Object[]> queryRedpacketInfoByFinishTime(Long finishTime) {
        return userRedPacketRepository.queryRedpacketInfoByFinishTime(finishTime);
    }

    public WmUserRedPacketRepository getUserRedPacketRepository() {
        return userRedPacketRepository;
    }

    public void setUserRedPacketRepository(WmUserRedPacketRepository userRedPacketRepository) {
        this.userRedPacketRepository = userRedPacketRepository;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }
}
