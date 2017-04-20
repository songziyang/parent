package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.RedPacketCash;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.repository.IRedPacketCashRepository;
import com.ydzb.packet.service.IRedPacketCashService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 红包-现金service实现类
 * @author
 */
@Service
public class RedPacketCashServiceImpl extends BaseServiceImpl<RedPacketCash, Long> implements IRedPacketCashService {

    @Autowired
    private IRedPacketCashRepository cashRepository;

    /**
     * 保存
     * @param cash
     * @param aBeginTime
     * @param aFinishTime
     * @return
     */
    @Override
    public String saveOne(RedPacketCash cash, String aBeginTime, String aFinishTime) {
        if (cash == null) {
            return null;
        }
        Long id = cash.getId();
        Long currentTime = DateUtil.getSystemTimeSeconds();
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //如果是新建
        if (id == null) {
            cash.setCreated(currentTime);
            cash.setCreatedUser(curUser.getId());
        }
        //如果是编辑
        else {
            RedPacketCash sourceCash = cashRepository.findOne(id);
            sourceCash.setUseDays(cash.getUseDays());
            sourceCash.setFund(cash.getFund());
            sourceCash.setUpdated(currentTime);
            sourceCash.setUpdateUser(curUser.getId());
            sourceCash.setName(cash.getName());
            cash = sourceCash;
        }
        cash.setBeginTime(DateUtil.getSystemTimeDay(aBeginTime));
        cash.setFinishTime(DateUtil.getSystemTimeDay(aFinishTime));
        cashRepository.save(cash);
        return null;
    }

    /**
     * 停用
     * @param id
     */
    @Override
    public void disable(Long id) {
        updateStatus(id, RedPacketCash.STATE_DISABLED);
    }

    /**
     * 启用
     * @param id
     */
    @Override
    public void enable(Long id) {
        updateStatus(id, RedPacketCash.STATE_INUSE);
    }

    /**
     * 改变状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, byte status) {
        cashRepository.updateStatus(id, status);
    }

    /**
     * 根据status查询所有
     * @param status
     * @return
     */
    @Override
    public List<RedPacketCash> findAll(byte status) {
            return cashRepository.findAll(status);
    }
}