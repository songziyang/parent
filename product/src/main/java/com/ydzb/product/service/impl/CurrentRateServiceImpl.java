package com.ydzb.product.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.CurrentRate;
import com.ydzb.product.repository.ICurrentRateRepository;
import com.ydzb.product.service.ICurrentRateService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝利率service实现类
 *
 * @author sy
 */
@Service
public class CurrentRateServiceImpl extends BaseServiceImpl<CurrentRate, Long> implements ICurrentRateService {

    @Autowired
    private ICurrentRateRepository rateRepository;

    /**
     * 保存
     *
     * @param rate
     * @return
     */
    @Override
    public String saveOne(CurrentRate rate, String dCurrentDate) {
        if (rate == null) {
            return null;
        }
        Long rateId = rate.getId();
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long currentDate = DateUtil.getSystemTimeDay(dCurrentDate);
        //如果是新建
        if (rateId == null) {
            rate.setNewCopies(rate.getNewCopies() == null ? 0 : rate.getNewCopies());
            rate.setCurrentRate(rate.getCurrentRate() == null ? BigDecimal.ZERO : rate.getCurrentRate());
            rate.setCreated(DateUtil.getSystemTimeSeconds());
            rate.setCreatedUser(user.getId());
            rate.setComRate(rate.culculateComRate());
            //将原来的使用的利率变为停用
            updateStatus(rate.getTimeType(), CurrentRate.STATUS_DISABLED, CurrentRate.STATUS_INUSE);

        } else {
            updateStatus(rateId, rate.getTimeType(), CurrentRate.STATUS_DISABLED, CurrentRate.STATUS_INUSE);
            CurrentRate sourceRate = rateRepository.findOne(rateId);
            sourceRate.setCopies(rate.getCopies());
            sourceRate.setCurrentRate(rate.getCurrentRate());
            sourceRate.setUpdatedUser(user.getId());
            sourceRate.setTimeType(rate.getTimeType());
            sourceRate.setComRate(rate.culculateComRate());
            sourceRate.setReleaseType(rate.getReleaseType());
            sourceRate.setNewCopies(rate.getNewCopies());
            rate = sourceRate;
        }
        rate.setCurrentDate(currentDate);
        rateRepository.save(rate);
        return null;
    }

    /**
     * 根据id更改状态
     *
     * @param currentRateId
     * @param status
     */
    @Override
    public void updateStatus(Long currentRateId, Byte status) {
        rateRepository.updateStatus(currentRateId, status);
    }

    @Override
    public void updateStatus(Byte status, Byte sourceStatus) {
        rateRepository.updateStatus(status, sourceStatus);
    }

    @Override
    public void updateStatus(Byte timeType, Byte status, Byte sourceStatus) {
        rateRepository.updateStatus(timeType, status, sourceStatus);
    }

    @Override
    public void updateStatus(Long rateId, Byte timeType, Byte status, Byte sourceStatus) {
        rateRepository.updateStatus(rateId, timeType, status, sourceStatus);
    }

    /**
     * 停用
     *
     * @param currentRateId
     */
    @Override
    public void disable(Long currentRateId) {
        updateStatus(currentRateId, CurrentRate.STATUS_DISABLED);
    }
}
