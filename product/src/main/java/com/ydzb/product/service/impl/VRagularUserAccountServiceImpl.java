package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.VRagularUserAccount;
import com.ydzb.product.repository.IVRagularUserAccountRepository;
import com.ydzb.product.service.IVRagularUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户定存宝记录service实现类
 * @author sy
 */
@Service
public class VRagularUserAccountServiceImpl extends BaseServiceImpl<VRagularUserAccount, Long> implements IVRagularUserAccountService {

    @Autowired
    private IVRagularUserAccountRepository vRagularUserAccountRepository;

    /**
     * 查询三个月定存总额
     * @return
     */
    @Override
    public BigDecimal queryThreeMonthFund() {
        return vRagularUserAccountRepository.queryThreeMonthFund();
    }

    /**
     * 查询六个月定存总额
     * @return
     */
    @Override
    public BigDecimal querySixMonthFund() {
        return vRagularUserAccountRepository.querySixMonthFund();
    }

    /**
     * 查询十二个月定存总额
     * @return
     */
    @Override
    public BigDecimal queryTwelveMonthFund() {
        return vRagularUserAccountRepository.queryTwelveMonthFund();
    }

    /**
     * 查询一个月定存总额
     *
     * @return
     */
    @Override
    public BigDecimal queryOneMonthFund() {
        return vRagularUserAccountRepository.queryOneMonthFund();
    }


    public IVRagularUserAccountRepository getvRagularUserAccountRepository() {
        return vRagularUserAccountRepository;
    }

    public void setvRagularUserAccountRepository(IVRagularUserAccountRepository vRagularUserAccountRepository) {
        this.vRagularUserAccountRepository = vRagularUserAccountRepository;
    }
}