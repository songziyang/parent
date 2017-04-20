package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmFreeRefund;
import com.ydzb.account.repository.WmFreeRefundRepository;
import com.ydzb.account.service.IWmFreeRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;

/**
 * 随心存还息记录service实现
 */
@Service
public class WmFreeRefundServiceImpl implements IWmFreeRefundService {

    @Autowired
    private WmFreeRefundRepository freeRefundRepository;

    /**
     * 更新状态
     * @param freeRefund 随心存还息记录
     * @param state 目标状态
     * @return
     */
    @Override
    public WmFreeRefund updateState(WmFreeRefund freeRefund, int state)  {
        if (freeRefund != null) {
            freeRefund.setState(state);
            freeRefundRepository.createOrUpdate(freeRefund);
        }
        return freeRefund;
    }

    /**
     * 根据主键查询
     * @param id 主键
     * @param lockModeType 锁类型
     * @return
     */
    public WmFreeRefund queryById(Long id, LockModeType lockModeType) {
        return freeRefundRepository.queryById(id, lockModeType);
    }

    /**
     * 根据LinkId和锁类型查询实体
     * @param linkId 定存账户id
     * @param lockModeType 锁类型
     * @return
     */
    @Override
    public WmFreeRefund queryOneByLinkId(Long linkId, LockModeType lockModeType) {
        if (lockModeType == null || lockModeType == LockModeType.NONE) return freeRefundRepository.queryOneByLinkId(linkId);
        Long refundId = freeRefundRepository.queryIdByLinkId(linkId);
        return freeRefundRepository.queryById(refundId, lockModeType);
    }
}