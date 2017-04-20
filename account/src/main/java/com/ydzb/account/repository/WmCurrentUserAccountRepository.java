package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class WmCurrentUserAccountRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据用户id查询活期宝账户
     * @param userId
     * @return
     */
    public WmCurrentUserAccount queryByUser(Long userId) {
        String ql = " FROM WmCurrentUserAccount WHERE userId = :userId";
        return manager.createQuery(ql, WmCurrentUserAccount.class).setParameter("userId", userId).getSingleResult();
    }

    /**
     * 根据user查询活期宝账户id
     * @param userId 用户id
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String ql = " SELECT id FROM WmCurrentUserAccount WHERE userId = :userId";
        return (Long) manager.createQuery(ql).setParameter("userId", userId).getSingleResult();
    }

    /**
     * 根据主键和锁查询
     * @param id
     * @param lockType
     * @return
     */
    public WmCurrentUserAccount queryById(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return manager.find(WmCurrentUserAccount.class, id, lockType);
    }

    /**
     * 更新签到概率
     *
     * @throws Exception
     */
    public int accountSignProbability() {
        String sql = "UPDATE wm_sign_probability SET one = 0 , two = 0 , three = 0 , four = 0 , firsts = 0 , seconds = 0 ";
        Query query = manager.createNativeQuery(sql);
        return query.executeUpdate();
    }


    /**
     * 更新每天第一次签到
     *
     * @return
     */
    public int accountSign() {
        String sql = "UPDATE wm_user_info SET sign = 0 ";
        Query query = manager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    /**
     * 更新用户昨日收益
     *
     * @return
     */
    public int updateYesterdayIncome() {
        String sql = "UPDATE wm_user_income SET yesterday_income = 0 ";
        Query query = manager.createNativeQuery(sql);
        return query.executeUpdate();
    }


    /**
     * 根据用户ID 查询用户活期投资
     *
     * @param userId 用户ID
     * @return
     */
    public WmCurrentUserAccount findWmCurrentUserAccountByUserId(Long userId) {
        String sql = "select  wm from WmCurrentUserAccount as wm where  wm.userId = " + userId + " order by id desc ";
        TypedQuery<WmCurrentUserAccount> query = manager.createQuery(sql, WmCurrentUserAccount.class);
        if (query != null) {
            List<WmCurrentUserAccount> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmCurrentUserAccount.class,lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询活期产品产品
     *
     * @return
     */
    public WmProductInfo findWmProductInfo() {
        String sql = "select wp from WmProductInfo as wp where wp.type = 1 and  wp.status = 0 and wp.productClas = 1  order by id desc ";
        TypedQuery<WmProductInfo> query = manager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 查询用户活期红包使用
     *
     * @param userId 用户ID
     *
     * @return
     */
    public List<WmRedpacketUsed> findWmRedpacketUsedLst(Long userId) {
        String sql = "select  wm from WmRedpacketUsed as wm where  wm.userId = " + userId;
        TypedQuery<WmRedpacketUsed> query = manager.createQuery(sql, WmRedpacketUsed.class);
        if (query != null) {
            List<WmRedpacketUsed> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 保存活期宝每日结算
     *
     * @param wmCurrentSettlement
     */
    public void saveWmCurrentSettlement(WmCurrentSettlement wmCurrentSettlement) {
        manager.persist(wmCurrentSettlement);
    }


    /**
     * 查询用户资金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserMoney findWmUserMoneyByUserId(Long userId) {
        String sql = "select wm from WmUserMoney as wm where wm.userId = " + userId + " order by id desc  ";
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

    /**
     * 查询用户收益
     *
     * @param userId
     * @return
     */
    public WmUserIncome findWmUserIncomeByUserId(Long userId) {
        String sql = "select wm from WmUserIncome as wm where wm.userId = " + userId + " order by id desc  ";
        TypedQuery<WmUserIncome> query = manager.createQuery(sql, WmUserIncome.class);
        if (query != null) {
            List<WmUserIncome> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmUserIncome.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 更新用户收益
     *
     * @param userIncome
     */
    public void updateWmUserIncome(WmUserIncome userIncome) {
        manager.merge(userIncome);
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
     * 保存活期购买记录
     *
     * @param wmCurrentBuylog
     * @return
     */
    public Long saveWmCurrentBuylog(WmCurrentBuylog wmCurrentBuylog) {
        manager.persist(wmCurrentBuylog);
        return wmCurrentBuylog.getId();
    }


    /**
     * 查询用户等级
     *
     * @param id
     * @return
     */
    public WmVipGrade findWmVipGradeById(Long id) {
        return manager.find(WmVipGrade.class, id);
    }


    /**
     * 用户赠予（红包）收益记录
     *
     * @param profitRecord
     */
    public void saveWmUserFuncGrandProfitRecord(WmUserFuncGrandProfitRecord profitRecord) {
        manager.persist(profitRecord);
    }

    /**
     * 保存收益记录
     *
     * @param userIncomeRecord
     */
    public void saveWmUserIncomeRecord(WmUserIncomeRecord userIncomeRecord) {
        manager.persist(userIncomeRecord);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
