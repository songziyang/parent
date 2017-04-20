package com.ydzb.product.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.Stable;
import com.ydzb.product.repository.IStableRepository;
import com.ydzb.product.service.IStableService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * 稳进宝service实现
 */
@Service
public class StableServiceImpl extends BaseServiceImpl<Stable, Long> implements IStableService {

    @Autowired
    private IStableRepository stableRepository;

    @Override
    public void deleteStable(Long id) throws Exception {

    }

    /**
     * 保存稳进宝
     *
     * @param stable    稳进宝实体
     * @param startDate 申购开始日期
     * @param endDate   申购结束日期
     * @return
     * @throws Exception
     */
    @Override
    public String saveStable(Stable stable, String startDate, String endDate) throws Exception {
        if (stable == null) {
            return null;
        }
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        //判断如果是新建
        if (stable.getId() == null) {
            Stable targetStable = stableRepository.findStableByName(stable.getName());
            if (targetStable != null) {
                return "稳进宝已存在！";
            }
            initData(stable, shiroUser.getId());
        } else {
            stable.setUpdateuser(shiroUser.getId());
        }
        stable.setStartTime(DateUtil.getSystemTimeDay(startDate));
        stable.setEndTime(DateUtil.getSystemTimeDay(endDate));
        stableRepository.save(stable);
        return null;
    }

    @Override
    public List<Stable> findAllStable() {
        return null;
    }

    /**
     * @param stable
     * @param curUserId
     * @throws Exception
     */
    private void initData(Stable stable, Long curUserId) throws Exception {
        try {
            stable.setCreated(DateUtil.getSystemTimeSeconds(DateUtil.curDateTime()));
            stable.setCreateUser(curUserId);
            stable.setState(Stable.STATE_APPLYING);
            stable.setRemainingCopies(stable.getCopies());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 根据用户ID 查询用户稳进宝投资总额
     *
     * @param userId
     * @return
     */
    @Override
    public BigDecimal findSumCopiesFromStableDealByUserId(Long userId) {
        return stableRepository.findSumCopiesFromStableDealByUserId(userId);
    }
}