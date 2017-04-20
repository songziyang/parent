package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 基金产品repository
 */
@Repository
public class WmStableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据申购结束日期查询状态为申购中的
     *
     * @param date 申购结束日期
     * @return
     */
    public List<WmStable> findByEndDate(final Long date) {
        final String sql = " SELECT * FROM wm_stable WHERE end_date = " + date + " AND state = " + WmStable.STATE_APPLYING;
        final Query query = entityManager.createNativeQuery(sql, WmStable.class);
        if (query != null) {
            List<WmStable> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 根据到期日期查询基金产品
     *
     * @param closeDate 到期日期
     * @return
     */
    public List<WmStable> findByCloseDateAndNoApr(final Long closeDate) {
        final String sql = " SELECT * FROM wm_stable WHERE close_date = " + closeDate + " AND apr is null ";
        final Query query = entityManager.createNativeQuery(sql, WmStable.class);
        if (query != null) {
            List<WmStable> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 根据到期日期查询基金产品
     *
     * @param closeDate 到期日期
     * @return
     */
    public List<WmStable> findByCloseDate(Long closeDate) {
        String sql = " SELECT * FROM wm_stable WHERE close_date = " + closeDate;
        Query query = entityManager.createNativeQuery(sql, WmStable.class);
        if (query != null) {
            List<WmStable> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

    /**
     * 保存
     *
     * @param stable
     */
    public void updateStable(final WmStable stable) {
        entityManager.merge(stable);
    }

    /**
     * 保存资金记录
     *
     * @param userFundRecord
     */
    public void saveWmUserFundRecord(WmUserFundRecord userFundRecord) {
        entityManager.persist(userFundRecord);
    }

    /**
     * 保存用户进账日志
     *
     * @param userFundInLog
     */
    public void saveWmUserFundInLog(WmUserFundInLog userFundInLog) {
        entityManager.persist(userFundInLog);
    }

    /**
     * 查询用户资金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserMoney findWmUserMoneyByUserId(Long userId) {
        String sql = " FROM WmUserMoney WHERE userId = " + userId + " order by id desc ";
        TypedQuery<WmUserMoney> query = entityManager.createQuery(sql, WmUserMoney.class);
        if (query != null) {
            List<WmUserMoney> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmUserMoney.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 保存收益记录
     *
     * @param userIncomeRecord
     */
    public void saveWmUserIncomeRecord(WmUserIncomeRecord userIncomeRecord) {
        entityManager.persist(userIncomeRecord);
    }

    /**
     * 更新用户资金
     *
     * @param userMoney
     */
    public void updateWmUserMoney(WmUserMoney userMoney) {
        entityManager.merge(userMoney);
    }

    /**
     * 更新用户投资
     *
     * @param userInvestinfo
     */
    public void updateWmUserInvestinfo(WmUserInvestinfo userInvestinfo) {
        entityManager.merge(userInvestinfo);
    }

    /**
     * 更新用户收益
     *
     * @param userIncome
     */
    public void updateWmUserIncome(WmUserIncome userIncome) {
        entityManager.merge(userIncome);
    }

    /**
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public WmUserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = " FROM WmUserInvestinfo WHERE userId = " + userId + " order by id desc ";
        TypedQuery<WmUserInvestinfo> query = entityManager.createQuery(sql, WmUserInvestinfo.class);
        if (query != null) {
            List<WmUserInvestinfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmUserInvestinfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询用户收益
     *
     * @param userId
     * @return
     */
    public WmUserIncome findWmUserIncomeByUserId(Long userId) {
        String sql = " FROM WmUserIncome WHERE userId = " + userId + " order by id desc  ";
        TypedQuery<WmUserIncome> query = entityManager.createQuery(sql, WmUserIncome.class);
        if (query != null) {
            List<WmUserIncome> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmUserIncome.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 查询用户基础信息
     *
     * @param id
     * @return
     */
    public WmUser findWmUserById(Long id) {
        return entityManager.find(WmUser.class, id);
    }

}