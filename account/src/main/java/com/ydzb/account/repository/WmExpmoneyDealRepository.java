package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class WmExpmoneyDealRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询活期宝账户
     *
     * @param userId 用户ID
     * @return
     */
    public WmCurrentUserAccount findWmCurrentUserAccount(Long userId) {
        String sql = "select wcua from WmCurrentUserAccount as wcua where wcua.userId =  " + userId + " order by wcua.id desc ";
        TypedQuery<WmCurrentUserAccount> query = manager.createQuery(sql, WmCurrentUserAccount.class);
        if (query != null) {
            List<WmCurrentUserAccount> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmCurrentUserAccount.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询用户体验金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserExMoney findWmUserExMoneyyByUserId(Long userId) {
        String sql = "select wm from WmUserExMoney as wm where wm.userId = " + userId + " order by wm.id desc  ";
        TypedQuery<WmUserExMoney> query = manager.createQuery(sql, WmUserExMoney.class);
        if (query != null) {
            List<WmUserExMoney> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmUserExMoney.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户体验金
     *
     * @param userExMoney
     */
    public void updateWmUserExMoney(WmUserExMoney userExMoney) {
        manager.merge(userExMoney);
    }

    /**
     * 保存用户出账日志
     *
     * @param userFundOutLog
     */
    public void saveWmUserFundOutLog(WmUserFundOutLog userFundOutLog) {
        manager.persist(userFundOutLog);
    }


    /**
     * 保存体验金记录
     *
     * @param userExpMoneyRecord
     */
    public void saveWmUserExpMoneyRecord(WmUserExpMoneyRecord userExpMoneyRecord) {
        manager.persist(userExpMoneyRecord);
    }

    /**
     * 保存冻结记录
     *
     * @param userFundBlokedLog
     * @return
     */
    public Long saveWmUserFundBlokedLog(WmUserFundBlokedLog userFundBlokedLog) {
        manager.persist(userFundBlokedLog);
        return userFundBlokedLog.getId();
    }

    /**
     * 保存活期交易记录
     *
     * @param wmCurrentTradeRecored
     * @return
     */
    public Long saveWmCurrentTradeRecored(WmCurrentTradeRecored wmCurrentTradeRecored) {
        manager.persist(wmCurrentTradeRecored);
        return wmCurrentTradeRecored.getId();
    }


    /**
     * 保存活期赎回日志
     *
     * @param currentOverlog
     * @return
     */
    public Long saveWmCurrentOverlog(WmCurrentOverlog currentOverlog) {
        manager.persist(currentOverlog);
        return currentOverlog.getId();
    }

    /**
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public WmUserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = "select wm from WmUserInvestinfo as wm where wm.userId = " + userId + " order by wm.id desc  ";
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
     * 更新用户活期账户
     *
     * @param wmCurrentUserAccount
     */
    public void updateCurrentUserAccount(WmCurrentUserAccount wmCurrentUserAccount) {
        manager.merge(wmCurrentUserAccount);
    }


    /**
     * 查询用户基础信息
     *
     * @param id
     * @return
     */
    public WmUser findWmUserById(Long id) {
        return manager.find(WmUser.class, id);
    }

    /**
     * 保存或更新
     * @param expmoneyDeal
     * @return
     */
    public WmExpmoneyDeal saveOrUpdate(WmExpmoneyDeal expmoneyDeal) {
        if (expmoneyDeal == null) return null;
        if (expmoneyDeal.getId() != null) return manager.merge(expmoneyDeal);
        manager.persist(expmoneyDeal);
        manager.refresh(expmoneyDeal);
        return expmoneyDeal;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
