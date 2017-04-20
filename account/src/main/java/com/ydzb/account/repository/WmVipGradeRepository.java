package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import com.ydzb.core.entity.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class WmVipGradeRepository extends WmBaseRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询用户基础信息
     *
     * @param id 用户ID
     * @return
     */
    public WmUser findWmUserById(Long id) {
        return manager.find(WmUser.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * 根据主键查询实体
     * @param id 主键
     * @param c 实体的class
     * @param lockType 锁类型
     * @return
     */
    public WmVipGrade queryById(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return entityManager.find(WmVipGrade.class, id, lockType);
    }

    /**
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public WmUserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = "select wm from WmUserInvestinfo as wm where wm.userId = " + userId + " order by id desc  ";
        TypedQuery<WmUserInvestinfo> query = manager.createQuery(sql, WmUserInvestinfo.class);
        if (query != null) {
            List<WmUserInvestinfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存用户等级变化
     *
     * @param userVipGradeChange 用户等级变化
     */
    public void saveWmUserVipGradeChange(WmUserVipGradeChange userVipGradeChange) {
        manager.persist(userVipGradeChange);
    }


    /**
     * 根据用户投资金额查询VIP等级记录
     *
     * @param investAllFund 投资金额
     * @return
     */
    public WmVipGrade findVipGradeByInvestAllFund(BigDecimal investAllFund) {
        String sql = "select wvg from WmVipGrade as wvg  where wvg.investAllFund <= " + investAllFund + " order by wvg.investAllFund desc ";
        TypedQuery<WmVipGrade> query = manager.createQuery(sql, WmVipGrade.class);
        if (query != null) {
            List<WmVipGrade> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    public void updateWmUser(WmUser user) {
        manager.merge(user);
    }


    /**
     * 查询用户VIP等级设置
     *
     * @param userId     用户ID
     * @param expireTime 到期日期
     * @param status     状态
     * @return
     */
    public WmVipSetGrade findWmVipSetGrade(Long userId, Long expireTime, Integer status) {
        String sql = "select wvsg from WmVipSetGrade as wvsg  where wvsg.userId = " + userId + " and wvsg.expireTime >= " + expireTime + " and wvsg.status = " + status + " order by wvsg.id desc ";
        TypedQuery<WmVipSetGrade> query = manager.createQuery(sql, WmVipSetGrade.class);
        if (query != null) {
            List<WmVipSetGrade> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 更新用户VIP等级设置
     *
     * @param wmVipSetGrade
     */
    public void updateWmVipSetGrade(WmVipSetGrade wmVipSetGrade) {
        manager.merge(wmVipSetGrade);
    }


    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    public WmUserInfo findWmUserInfoByUser(Long userId) {
        String jpql = " FROM WmUserInfo WHERE userId = " + userId;
        Query query = manager.createQuery(jpql, WmUserInfo.class);
        if (query != null) {
            List<WmUserInfo> resultSet = query.getResultList();
            if (resultSet != null && !resultSet.isEmpty()) {
                return resultSet.get(0);
            }
        }
        return null;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
