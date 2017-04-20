package com.ydzb.account.repository;

import com.ydzb.account.entity.WmCurrentUserAccount;
import com.ydzb.account.entity.WmUserIntegral;
import com.ydzb.account.entity.WmUserIntegralRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class WmUserIntegralRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据用户ID 查询用户积分表
     *
     * @param userId 用户ID
     * @return 用户积分
     */
    public WmUserIntegral findWmUserIntegralByUserId(Long userId) {
        String sql = "select wui from WmUserIntegral as wui where wui.userId = " + userId;
        TypedQuery<WmUserIntegral> query = manager.createQuery(sql, WmUserIntegral.class);
        if (query != null) {
            List<WmUserIntegral> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmUserIntegral.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 保存积分记录
     *
     * @param wmUserIntegralRecord 积分记录
     */
    public void saveWmUserIntegralRecord(WmUserIntegralRecord wmUserIntegralRecord) {
        manager.persist(wmUserIntegralRecord);
    }


    /**
     * 更新用户积分
     *
     * @param wmUserIntegral 用户积分
     */
    public void updateWmUserIntegral(WmUserIntegral wmUserIntegral) {
        manager.merge(wmUserIntegral);
    }


    /**
     * 保存用户积分
     *
     * @param wmUserIntegral 用户积分
     */
    public void saveWmUserIntegral(WmUserIntegral wmUserIntegral) {
        manager.persist(wmUserIntegral);
    }


    /**
     * 更新用户积分
     */
    public void emptyUserIntegral() {
        String sql = "UPDATE wm_user_integral AS wui SET wui.integral = 0 ";
        Query query = manager.createNativeQuery(sql);
        query.executeUpdate();
    }

    /**
     * 根据用户查询用户积分
     * @param userId
     * @param lockType
     * @return
     */
    public WmUserIntegral queryByUser(Long userId, LockModeType lockType) {
        if (lockType == null || lockType == LockModeType.NONE) {
            String ql = " FROM WmUserIntegral WHERE userId = " + userId;
            List<WmUserIntegral> resultList = manager.createQuery(ql, WmUserIntegral.class).getResultList();
            return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
        }
        String ql = " SELECT id FROM WmUserIntegral WHERE userId = " + userId;
        List<Long> resultList = manager.createQuery(ql).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return manager.find(WmUserIntegral.class, resultList.get(0), lockType);
        }
        return null;
    }

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmUserIntegral createOrUpdate(WmUserIntegral entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return manager.merge(entity);
        manager.persist(entity);
        return entity;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
