package com.ydzb.user.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.MinusAmount;
import com.ydzb.user.repository.IMinusAmountRepository;
import com.ydzb.user.service.IMinusAmountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MinusAmoutServiceImpl extends BaseServiceImpl<MinusAmount, Long> implements IMinusAmountService {

    @Autowired
    private IMinusAmountRepository minusAmountRepository;

    @Override
    public String saveMinusamount(MinusAmount minusAmount) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        if (minusAmount != null) {
            if (minusAmount.getId() != null) {
                minusAmount.setUpdateUser(shiroUser.getUsername());
            } else {
                minusAmount.setCreated(DateUtil.getSystemTimeSeconds());
                minusAmount.setCreateUser(shiroUser.getUsername());
            }
            minusAmountRepository.save(minusAmount);
        }
        return null;
    }

    @Override
    public Integer findSumMinusAmount() {
        Integer fund = minusAmountRepository.findSumMinusAmount();
        if (fund != null) return fund;
        return 0;
    }

    public IMinusAmountRepository getMinusAmountRepository() {
        return minusAmountRepository;
    }

    public void setMinusAmountRepository(IMinusAmountRepository minusAmountRepository) {
        this.minusAmountRepository = minusAmountRepository;
    }


}
