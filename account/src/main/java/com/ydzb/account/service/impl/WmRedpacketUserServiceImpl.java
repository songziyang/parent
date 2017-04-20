package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmRedpacketUsedRepository;
import com.ydzb.account.repository.WmRedpacketUserRepository;
import com.ydzb.account.service.IWmRedpacketUserService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户红包service实现
 */
@Service
public class WmRedpacketUserServiceImpl implements IWmRedpacketUserService {

    @Autowired
    private WmRedpacketUserRepository wmRedpacketUserRepository;
    @Autowired
    private WmRedpacketUsedRepository wmRedpacketUsedRepository;

    /**
     * 发送定存红包
     * @param userId 用户id
     * @param fund 红包基恩
     * @param voucher 红包
     * @param triggerType 触发类型
     * @param limitFund 满多少可用
     * @return
     */
    @Override
    public WmRedpacketUser sendVoucher(Long userId, BigDecimal fund, WmRedpacketVoucher voucher, int triggerType, int limitFund) {
        if (voucher == null) return null;
        return createOne(userId, voucher.getId(), voucher.getName(), 2, 3, triggerType, voucher.getBeginDate(),
                voucher.getEndDate(), voucher.getUseDays(), fund, limitFund);
    }

    /**
     * 发送加息券
     * @param userId 用户Id
     * @param productType 产品类型
     * @param giveValue 赠送值
     * @param interest 红包
     * @param triggerType 触发类型
     * @return
     */
    @Override
    public WmRedpacketUser sendInterest(Long userId, Integer productType, BigDecimal giveValue, WmRedPacketInterest interest, int triggerType) {
        if (interest == null) return null;
        return createOne(userId, interest.getId(), interest.getName(), productType, 2, triggerType, interest.getActivityBeginTime(),
                interest.getActivityFinishTime(), interest.getUseDays(), giveValue, 0);
    }

    /**
     * 创建
     * @param userId 用户id
     * @param linkId 红包id
     * @param redpacketName 红包名称
     * @param productType 产品类型
     * @param redpacketType 红包类型
     * @param triggerType 触发类型
     * @param beginTime 开始时间
     * @param finishTime 结束时间
     * @param useDays 使用天数
     * @param value 红包值
     * @param limitFund 满多少可使用(如不需则填0)
     * @return
     */
    @Override
    public WmRedpacketUser createOne(Long userId, Long linkId, String redpacketName, Integer productType, Integer redpacketType,
            Integer triggerType, Long beginTime, Long finishTime, Integer useDays, BigDecimal value, Integer limitFund) {
        WmRedpacketUser redpacketUser = new WmRedpacketUser();
        redpacketUser.setUserId(userId);
        redpacketUser.setRedpacketId(linkId);
        redpacketUser.setRedpacketName(redpacketName);
        redpacketUser.setProductType(productType);
        redpacketUser.setRedpacketType(redpacketType);
        redpacketUser.setTriggerType(triggerType);
        redpacketUser.setBeginTime(beginTime);
        redpacketUser.setFinishTime(finishTime);
        redpacketUser.setGetTime(DateUtil.getSystemTimeSeconds());
        redpacketUser.setUseFinishTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + (useDays == null? 0: useDays) * 24 * 3600);
        redpacketUser.setGiveValue(value);
        redpacketUser.setStatus(1);
        redpacketUser.setLimitFund(limitFund);
        redpacketUser.setCreated(DateUtil.getSystemTimeSeconds());
        redpacketUser.setCreatedUser("系统");
        return wmRedpacketUserRepository.createOrUpdate(redpacketUser);
    }

    /**
     * 发放现金红包
     * @param userId 用户Id
     * @param cash 红包
     * @param fund 红包金额
     * @param triggerType 出发类型
     * @return
     */
    @Override
    public WmRedpacketUser sendCash(Long userId, WmRedPacketCash cash, BigDecimal fund, int triggerType) {
        if (cash == null) return null;
        return createOne(userId, cash.getId(), cash.getName(), 0, 1, triggerType, cash.getBeginTime(), cash.getFinishTime(), cash.getUseDays(), fund, 0);
    }

    /**
     * 根据用户id，账户id（例如ragularAccountId)和产品类别查询所使用的红包id
     * @param userId 用户id
     * @param accountId 账户id
     * @param productType 产品类别
     */
    @Override
    public Long queryRedpacketId(Long userId, Long accountId, Integer productType) {
        Long redpacketId = null;
        //查询使用的使用的红包
        List<WmRedpacketUsed> usedRedpackets = wmRedpacketUsedRepository.queryOnes(userId, accountId, productType);
        if (usedRedpackets != null && !usedRedpackets.isEmpty()) {
            WmRedpacketUsed redpacketUsed = usedRedpackets.get(0);
            WmRedpacketUser redpacketUser = wmRedpacketUserRepository.findOne(redpacketUsed.getRedpacketUsersId(), LockModeType.NONE);
            if (redpacketUser != null) redpacketId = redpacketUser.getRedpacketId();
        }
        return redpacketId;
    }
}
