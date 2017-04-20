package com.ydzb.product.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.CurrentRate;
import com.ydzb.product.entity.RagularRate;
import com.ydzb.product.repository.IRagularRateRepository;
import com.ydzb.product.service.IRagularRateService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定存宝利率service实现
 */
@Service
public class RagularRateServiceImpl extends BaseServiceImpl<RagularRate, Long> implements IRagularRateService {

    @Autowired
    private IRagularRateRepository rateRepository;

    /**
     * 保存
     * @param rate 定存宝利率
     * @return
     */
    @Override
    public String saveOne(RagularRate rate) {
        try {
            Long rateId = rate.getId();
            if (rateId == null) {
                return execCreate(rate);
            }
            return execEdit(rate);
        } catch (Exception e) {
            return "failure";
        }
    }

    /**
     * 执行编辑操作
     * @param rate 定存宝利率
     * @return
     */
    private String execEdit(RagularRate rate) throws Exception {
        //删除其他同等产品
        updateStatus(rate.getId(), rate.getDays(), CurrentRate.STATUS_DISABLED, CurrentRate.STATUS_INUSE);
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        RagularRate oldRate = rateRepository.findOne(rate.getId());
        oldRate.setCopies(rate.getCopies());
        oldRate.setDays(rate.getDays());
        oldRate.setReleaseType(rate.getReleaseType());
        oldRate.setRagularRate(rate.getRagularRate());
        oldRate.setActivityRate(rate.getActivityRate());
        oldRate.setUpdatedUser(user.getId());
        rateRepository.save(oldRate);
        return "success";
    }

    /**
     * 执行创建操作
     * @param rate 定存宝利率
     * @return
     * @throws Exception
     */
    private String execCreate(RagularRate rate) throws Exception {
        //将原来的使用的利率变为停用
        updateStatus(rate.getDays(), CurrentRate.STATUS_DISABLED, CurrentRate.STATUS_INUSE);
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        rate.setCreated(DateUtil.getSystemTimeSeconds());
        rate.setCreatedUser(user.getId());
        rateRepository.save(rate);
        return "success";
    }

    /**
     * 根据id更改状态
     * @param currentRateId
     * @param status
     */
    /*@Override
    public void updateStatus(Long currentRateId, Byte status) {
        rateRepository.updateStatus(currentRateId, status);
    }

    @Override
    public void updateStatus(Byte status, Byte sourceStatus) {
        rateRepository.updateStatus(status, sourceStatus);
    }
*/

    /**
     * 更改状态
     * @param days 天数
     * @param status 目标状态
     * @param sourceStatus 原状态
     */
    @Override
    public void updateStatus(Integer days, Byte status, Byte sourceStatus) throws Exception {
        rateRepository.updateStatus(days, status, sourceStatus);
    }

    /**
     * 更改状态
     * @param rateId 利率id
     * @param days 天数
     * @param status 目标状态
     * @param sourceStatus 原状态
     */
    @Override
    public void updateStatus(Long rateId, Integer days, Byte status, Byte sourceStatus) throws Exception {
        rateRepository.updateStatus(rateId, days, status, sourceStatus);
    }
    /*
    *//**//**
     * 停用
     * @param currentRateId
     *//**//*
    @Override
    public void disable(Long currentRateId) {
        updateStatus(currentRateId, CurrentRate.STATUS_DISABLED);
    }*/
}