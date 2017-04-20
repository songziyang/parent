package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.Integral;
import com.ydzb.packet.repository.IIntegralRepository;
import com.ydzb.packet.service.IIntegralService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sy on 2016/6/6.
 */
@Service
public class IntegralServiceImpl extends BaseServiceImpl<Integral, Long> implements IIntegralService {

    @Autowired
    private IIntegralRepository iIntegralRepository;

    /**
     * 保存
     * @param integral
     * @return
     */
    @Override
    public Integral saveOne(Integral integral) {
        try {
            if (integral != null) {
                if (integral.getId() == null) {
                    return execCreate(integral);
                }
                return execEdit(integral);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行创建操作
     * @param integral 积分
     * @return
     */
    private Integral execCreate(Integral integral) {
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        integral.setCreated(DateUtil.getSystemTimeSeconds());
        integral.setCreatedUser(currentUser.getUsername());
        integral.setStatus(0);
        return iIntegralRepository.save(integral);
    }

    /**
     * 执行编辑操作
     * @param integral 积分
     * @return
     */
    private Integral execEdit(Integral integral) {
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        integral.setUpdated(DateUtil.getSystemTimeSeconds());
        integral.setUpdatedUser(currentUser.getUsername());
        return iIntegralRepository.save(integral);
    }

    /**
     * 停用
     * @param id
     */
    @Override
    public void disable(Long id) {
        updateStatus(id, Integral.STATE_DISABLED);
    }

    /**
     * 启用
     * @param id
     */
    @Override
    public void enable(Long id) {
        updateStatus(id, Integral.STATE_INUSE);
    }

    /**
     * 改变状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        iIntegralRepository.updateStatus(id, status);
    }

    @Override
    /**
     * 根据状态查询所有所有积分
     * @param status
     * @return
     */
    public List<Integral> findAll(Integer status) {
        return iIntegralRepository.findAll(status);
    }
}