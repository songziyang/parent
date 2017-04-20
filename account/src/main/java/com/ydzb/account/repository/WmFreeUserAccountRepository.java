package com.ydzb.account.repository;

import com.ydzb.account.entity.WmFreeRefund;
import com.ydzb.account.entity.WmFreeUserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * 随心存账户repository
 */
@Repository
public class WmFreeUserAccountRepository extends WmBaseRepository<WmFreeUserAccount> {

    /**
     * 根据用户id，还息记录状态以及还息时间查询
     * @param userId 用户id
     * @param refundTime 还息时间
     * @param state 状态
     * @return
     */
    public List<WmFreeRefund> queryOnes(Long userId, Long refundTime, Integer state) {
        String ql = " FROM WmFreeRefund WHERE userId = " + userId + " AND refundTime = " + refundTime + " AND state =" + state;
        return entityManager.createQuery(ql, WmFreeRefund.class).getResultList();
    }

    /**
     * 根据主键和锁类型查询
     * @param id 主键
     * @param lockModeType 锁类型
     * @return
     */
    public WmFreeUserAccount queryById(Long id, LockModeType lockModeType) {
        return super.queryById(id, WmFreeUserAccount.class, lockModeType);
    }
}