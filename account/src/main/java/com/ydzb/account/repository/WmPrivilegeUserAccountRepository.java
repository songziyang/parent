package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 */
@Repository
public class WmPrivilegeUserAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param privilegeUserAccount
     * @return
     */
    public WmPrivilegeUserAccount createOrUpdate(WmPrivilegeUserAccount privilegeUserAccount) {
        if (privilegeUserAccount == null) return null;
        if (privilegeUserAccount.getId() != null) return entityManager.merge(privilegeUserAccount);
        entityManager.persist(privilegeUserAccount);
        return privilegeUserAccount;
    }

    /**
     * 根据用户查询
     * @param userId 用户id
     * @return
     */
    public WmPrivilegeUserAccount queryByUser(Long userId) {
        String ql = " FROM WmPrivilegeUserAccount WHERE userId = " + userId;
        return (WmPrivilegeUserAccount) entityManager.createQuery(ql).getSingleResult();
    }

    /**
     * 根据用户查询
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    public WmPrivilegeUserAccount queryByUser(Long userId, LockModeType lockType) {
        if (lockType == null || lockType == LockModeType.NONE) {
            return queryByUser(userId);
        }
        Long id = queryIdByUser(userId);
        return entityManager.find(WmPrivilegeUserAccount.class, id, lockType);
    }

    /**
     * 根据用户查询新手特权id
     * @param userId 用户id
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String ql = " SELECT id FROM WmPrivilegeUserAccount WHERE userId = " + userId;
        return (Long) entityManager.createQuery(ql).getSingleResult();
    }

    /**
     * 根据到期时间查询
     * @param expireTime 到期时间
     * @return
     */
    public List<Long> queryIdByExpireTime(Long expireTime) {
        String ql = " SELECT id FROM WmPrivilegeUserAccount WHERE expireTime = " + expireTime;
        return entityManager.createQuery(ql).getResultList();
    }

    /**
     * 根据主键查询
     * @param id 主键Id
     * @param lockType 锁类型
     * @return
     */
    public WmPrivilegeUserAccount queryOne(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return entityManager.find(WmPrivilegeUserAccount.class, id, lockType);
    }
}
