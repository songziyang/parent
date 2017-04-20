package com.ydzb.product.repository;


import com.ydzb.user.entity.UserInvestinfo;
import com.ydzb.user.entity.UserMoney;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 30亿活动用户银多币
 */
@Repository
public class PlatformUserNewRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public UserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = "select wm from UserInvestinfo as wm where  wm.user.id = " + userId + " order by wm.id desc ";
        TypedQuery<UserInvestinfo> query = manager.createQuery(sql, UserInvestinfo.class);
        if (query != null) {
            List<UserInvestinfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(UserInvestinfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户投资
     *
     * @param userInvestinfo
     */
    public void updateUserInvestinfo(UserInvestinfo userInvestinfo) {
        manager.merge(userInvestinfo);
    }


    /**
     * 查询用户资金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public UserMoney findWmUserMoneyByUserId(Long userId) {
        String sql = "select wm from UserMoney as wm where wm.user.id = " + userId + " order by wm.id desc ";
        TypedQuery<UserMoney> query = manager.createQuery(sql, UserMoney.class);
        if (query != null) {
            List<UserMoney> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(UserMoney.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户资金
     *
     * @param userMoney
     */
    public void updateUserMoney(UserMoney userMoney) {
        manager.merge(userMoney);
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}