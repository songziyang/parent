package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.entity.WmUserMoney;
import com.ydzb.account.entity.WmUserSilverThirty;
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
public class WmPlatformUserRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public WmUserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = "select wm from WmUserInvestinfo as wm where wm.userId = " + userId + " order by wm.id desc ";
        TypedQuery<WmUserInvestinfo> query = manager.createQuery(sql, WmUserInvestinfo.class);
        if (query != null) {
            List<WmUserInvestinfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmUserInvestinfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户投资
     *
     * @param userInvestinfo
     */
    public void updateWmUserInvestinfo(WmUserInvestinfo userInvestinfo) {
        manager.merge(userInvestinfo);
    }



    /**
     * 查询用户资金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserMoney findWmUserMoneyByUserId(Long userId) {
        String sql = "select wm from WmUserMoney as wm where wm.userId = " + userId + " order by wm.id desc ";
        TypedQuery<WmUserMoney> query = manager.createQuery(sql, WmUserMoney.class);
        if (query != null) {
            List<WmUserMoney> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmUserMoney.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户资金
     *
     * @param userMoney
     */
    public void updateWmUserMoney(WmUserMoney userMoney) {
        manager.merge(userMoney);
    }




    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}