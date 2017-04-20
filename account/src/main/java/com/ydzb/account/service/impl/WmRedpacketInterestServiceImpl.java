package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmRedpacketVoucher;
import com.ydzb.account.repository.WmRedpacketInterestRepository;
import com.ydzb.account.repository.WmRedpacketVoucherRepository;
import com.ydzb.account.service.IWmRedpacketInterestService;
import com.ydzb.account.service.IWmRedpacketVoucherService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 定存红包service实现
 */
@Service
public class WmRedpacketInterestServiceImpl implements IWmRedpacketInterestService {

    @Autowired
    private WmRedpacketInterestRepository wmRedpacketInterestRepository;

    /**
     * 根据触发类型查询
     * @param triggerType 触发类型
     * @return
     */
    @Override
    public WmRedPacketInterest queryByTriggerType(Integer triggerType) {
        return wmRedpacketInterestRepository.queryByTriggerType(triggerType);
    }

    /**
     * 创建
     * @param name 红包名称
     * @param productType 产品类别
     * @param triggerType 触发类型
     * @param beginDate 活动开始时间
     * @param endDate 活动结束时间
     * @param redpacketType 红包类型
     * @param useDays 使用天数
     * @param giveValue 赠送值
     * @param investDays 投资天数
     * @return
     */
    @Override
    public WmRedPacketInterest createOne(String name, Long productType, Integer triggerType, Long beginDate, Long endDate,
            Integer redpacketType, Integer useDays, BigDecimal giveValue, Integer investDays) {
        WmRedPacketInterest interest = new WmRedPacketInterest();
        interest.setName(name);
        interest.setProductType(productType);
        interest.setTriggerType(triggerType);
        interest.setActivityBeginTime(beginDate);
        interest.setActivityFinishTime(endDate);
        interest.setRedpacketType(redpacketType);
        interest.setUseDays(useDays);
        interest.setGiveValue(giveValue);
        interest.setStatus((byte)0);
        interest.setCreated(DateUtil.getSystemTimeSeconds());
        interest.setCreatedUser("系统");
        interest.setInvestDays(investDays);
        interest.setRemark("");
        return wmRedpacketInterestRepository.saveWmRedPacketInterest(interest);
    }

}