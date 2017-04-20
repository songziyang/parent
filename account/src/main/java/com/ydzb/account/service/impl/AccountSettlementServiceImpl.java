package com.ydzb.account.service.impl;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.repository.AccountSettlementRepository;
import com.ydzb.account.service.IAccountSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户结算service实现类
 */
@Service
public class AccountSettlementServiceImpl implements IAccountSettlementService {

    @Autowired
    private AccountSettlementRepository settlementRepository;


    /**
     * 查询现有用户的最大ID 和 最小ID
     *
     * @return ID 区间 最大ID 和 最小ID
     */
    @Override
    public IDRange findWmUserMaxIdAndMinId() {
        return settlementRepository.findWmUserMaxIdAndMinId();
    }



    /**
     * 根据用户ID 查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public WmUser findWmUserUserById(Long userId) {
        return settlementRepository.findWmUserUserById(userId);
    }

    public AccountSettlementRepository getSettlementRepository() {
        return settlementRepository;
    }

    public void setSettlementRepository(AccountSettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }
}
