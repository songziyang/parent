package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RedPacketCash;
import com.ydzb.packet.repository.IExpMoneyRepository;
import com.ydzb.packet.service.IExpMoneyService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体验金service实现类
 * @author sy
 */
@Service
public class ExpMoneyServiceImpl extends BaseServiceImpl<ExpMoney, Long> implements IExpMoneyService {

    @Autowired
    private IExpMoneyRepository expMoneyRepository;

    /**
     * 保存
     * @param expMoney
     * @return
     */
    @Override
    public String saveOne(ExpMoney expMoney) {
        if (expMoney == null) {
            return null;
        }
        boolean isCreate = expMoney.getId() == null? true: false;
        //如果是创建
        if (isCreate) {
            ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            expMoney.setCreated(DateUtil.getSystemTimeSeconds());
            expMoney.setCreateUser(user.getId());
        } else {
            ExpMoney sourceExpMoney = expMoneyRepository.findOne(expMoney.getId());
            expMoney.setCreated(sourceExpMoney.getCreated());
            expMoney.setCreateUser(sourceExpMoney.getCreateUser());
        }
        expMoneyRepository.save(expMoney);
        return null;
    }

    /**
     * 根据id改变其对应状态
     * @param id
     * @param state
     */
    @Override
    public void updateState(Long id, byte state) {
        expMoneyRepository.updateState(id, state);
    }

    /**
     * 停用
     * @param id
     */
    @Override
    public void disable(Long id) {
        updateState(id, ExpMoney.STATE_DISABLED);
    }

    /**
     * 开启
     * @param id
     */
    @Override
    public void enable(Long id) {
        updateState(id, ExpMoney.STATE_INUSE);
    }

    /**
     * 根据状态查询所有
     * @param state
     * @return
     */
    @Override
    public List<ExpMoney> findAll(byte state) {
        return expMoneyRepository.findAll(state);
    }
}
