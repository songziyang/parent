package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserSilverThirty;
import com.ydzb.account.repository.WmUserSilverThirtyRepository;
import com.ydzb.account.service.IWmUserSilverThirtyService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;

/**
 * 30亿活动用户银多币service实现
 */
@Service
public class WmUserSilverThirtyServiceImpl implements IWmUserSilverThirtyService {

    @Autowired
    private WmUserSilverThirtyRepository wmUserSilverThirtyRepository;

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    @Override
    public WmUserSilverThirty queryByUser(Long userId, LockModeType lockType) {
        if (lockType == null || lockType == LockModeType.NONE) {
            return wmUserSilverThirtyRepository.queryByUser(userId);
        }
        Long id = wmUserSilverThirtyRepository.queryIdByUser(userId);
        return wmUserSilverThirtyRepository.queryById(id, lockType);
    }

    /**
     * 创建
     * @param userId
     * @return
     */
    @Override
    public WmUserSilverThirty createOne(Long userId) {
        WmUserSilverThirty wmUserSilverThirty = new WmUserSilverThirty();
        wmUserSilverThirty.setUserId(userId);
        wmUserSilverThirty.setTotalFund(0);
        wmUserSilverThirty.setUsableFund(0);
        wmUserSilverThirty.setCreated(DateUtil.getSystemTimeSeconds());
        return wmUserSilverThirtyRepository.saveOrUpdate(wmUserSilverThirty);
    }

    /**
     * 更新银多币数
     * @param wmUserSilverThirty
     * @param count
     */
    @Override
    public void updateCoins(WmUserSilverThirty wmUserSilverThirty, Integer count) {

        if (wmUserSilverThirty != null && count != null && count.compareTo(0) == 1) {

            Integer totalFund = wmUserSilverThirty.getTotalFund();
            Integer usableFund = wmUserSilverThirty.getUsableFund();

            wmUserSilverThirty.setTotalFund(totalFund == null? count: totalFund + count);
            wmUserSilverThirty.setUsableFund(usableFund == null? count: usableFund + count);
            wmUserSilverThirtyRepository.saveOrUpdate(wmUserSilverThirty);
        }
    }
}