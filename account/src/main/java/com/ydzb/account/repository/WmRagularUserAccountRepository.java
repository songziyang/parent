package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
public class WmRagularUserAccountRepository extends WmBaseRepository<WmRagularUserAccount> {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询用户定存结算
     *
     * @param userId  用户ID
     * @param curDate 系统当前日期
     * @return
     */
    public List<WmRagularRefund> findWmRagularRefundByIdAndDate(Long userId, Long curDate) {
        String sql = "select wrr from WmRagularRefund as wrr where wrr.state = 0 and  wrr.userId = " + userId + "  and wrr.refundTime = " + curDate;
        TypedQuery<WmRagularRefund> query = manager.createQuery(sql, WmRagularRefund.class);
        if (query != null) {
            List<WmRagularRefund> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

    /**
     * 查询定存产品记录
     *
     * @param id
     * @return
     */
    public WmRagularUserAccount findWmRagularUserAccountById(Long id) {
        return manager.find(WmRagularUserAccount.class, id, LockModeType.PESSIMISTIC_WRITE);
    }


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


    /**
     * 查询用户收益
     *
     * @param userId
     * @return
     */
    public WmUserIncome findWmUserIncomeByUserId(Long userId) {
        String sql = "select wm from WmUserIncome as wm where wm.userId = " + userId + " order by wm.id desc ";
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
     * 更新用户定存投资
     *
     * @param ragularUserAccount
     */
    public void updateWmRagularUserAccount(WmRagularUserAccount ragularUserAccount) {
        manager.merge(ragularUserAccount);
    }


    /**
     * 保存定存到期
     *
     * @param ragularOverLog
     */
    public Long saveWmRagularOverLog(WmRagularOverLog ragularOverLog) {
        manager.persist(ragularOverLog);
        return ragularOverLog.getId();
    }

    /**
     * 保存定存交易记录
     *
     * @param ragularTradeRecored
     */
    public void saveWmRagularTradeRecored(WmRagularTradeRecored ragularTradeRecored) {
        manager.persist(ragularTradeRecored);
    }

    /**
     * 保存资金记录
     *
     * @param userFundRecord
     */
    public void saveWmUserFundRecord(WmUserFundRecord userFundRecord) {
        manager.persist(userFundRecord);
    }

    /**
     * 保存用户进账日志
     *
     * @param userFundInLog
     */
    public void saveWmUserFundInLog(WmUserFundInLog userFundInLog) {
        manager.persist(userFundInLog);
    }


    /**
     * 更新用户
     *
     * @param ragularRefund
     */
    public void updateWmRagularRefund(WmRagularRefund ragularRefund) {
        manager.merge(ragularRefund);
    }


    /**
     * 查询产品
     *
     * @return
     */
    public WmProductInfo findWmProductInfo(Integer days) {
        String sql = "select wp from WmProductInfo as wp where wp.type = 2 and  wp.status = 0 and wp.productClas = 1 and wp.cylcleDays = " + days + " order by  wp.id desc ";
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
     * 保存用户定存交易
     *
     * @param ragularUserAccount
     * @return
     */
    public Long saveWmRagularUserAccount(WmRagularUserAccount ragularUserAccount) {
        manager.persist(ragularUserAccount);
        return ragularUserAccount.getId();
    }


    /**
     * 保存用户定存购买交易日志
     *
     * @param ragularBuyLog
     * @return
     */
    public Long saveWmRagularBuyLog(WmRagularBuyLog ragularBuyLog) {
        manager.persist(ragularBuyLog);
        return ragularBuyLog.getId();
    }


    /**
     * 保存定存结算记录
     *
     * @param ragularRefund
     */
    public void saveWmRagularRefund(WmRagularRefund ragularRefund) {
        manager.persist(ragularRefund);
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
     * 定存转让记录
     *
     * @param userId 用户ID
     * @return
     */
    public List<WmRagularUserAccount> findWmRagularUserAccount(Long userId, Long curDate) {
        String sql = "select wrua from WmRagularUserAccount as wrua  where wrua.status = 2 and  wrua.applyTransferTime = " + curDate + " and wrua.userId = " + userId;
        TypedQuery<WmRagularUserAccount> query = manager.createQuery(sql, WmRagularUserAccount.class);
        if (query != null) {
            List<WmRagularUserAccount> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询定存转让记录
     *
     * @param userId
     * @param accountId
     * @return
     */
    public List<WmRagularTransfer> findWmRagularTransfer(Long userId, Long accountId) {
        String sql = "select wrt from WmRagularTransfer as wrt  where wrt.status  = 1  and  wrt.accountId = " + accountId + " and wrt.applyUserId = " + userId;
        TypedQuery<WmRagularTransfer> query = manager.createQuery(sql, WmRagularTransfer.class);
        if (query != null) {
            List<WmRagularTransfer> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 更新用户转让
     *
     * @param ragularTransfer
     */
    public void updateWmRagularTransfer(WmRagularTransfer ragularTransfer) {
        manager.merge(ragularTransfer);
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
     * 查询用户等级
     *
     * @param id
     * @return
     */
    public WmVipGrade findWmVipGradeById(Long id) {
        return manager.find(WmVipGrade.class, id);
    }


    /**
     * 根据产品ID 查询产品
     *
     * @param pid
     * @return
     */
    public WmProductInfo findWmProductInfoById(Long pid) {
        return manager.find(WmProductInfo.class, pid);
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
     * 保存活期预投
     *
     * @param currentPrepay
     * @return
     */
    public Long saveWmCurrentPrepay(WmCurrentPrepay currentPrepay) {
        manager.persist(currentPrepay);
        return currentPrepay.getId();
    }


    /**
     * 保存活期预投排队
     *
     * @param currentQueue
     */
    public void saveWmCurrentQueue(WmCurrentQueue currentQueue) {
        manager.persist(currentQueue);
    }


    /**
     * 保存收益记录
     *
     * @param userIncomeRecord
     */
    public void saveWmUserIncomeRecord(WmUserIncomeRecord userIncomeRecord) {
        manager.persist(userIncomeRecord);
    }


    /**
     * 查询产品
     *
     * @param productId
     * @return
     */
    public WmProductInfo findWmProductInfoByProductId(Long productId) {
        return manager.find(WmProductInfo.class, productId);
    }

    /**
     * 根据起始时间查询复投定存宝的用户id以及购买金额
     * 复投类型 未到期状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public List<Object[]> queryRagularInfoBetweenTime(Long startTime, Long endTime) {

        String jpql = " SELECT userId, buyFund, days, id FROM WmRagularUserAccount WHERE buyType = 1 AND status = 0" +
                " AND buyTime >= " + startTime + " AND buyTime < " + endTime;
        List<Object[]> resultList = manager.createQuery(jpql).getResultList();
        return resultList == null? new ArrayList<Object[]>(): resultList;
    }

    /**
     * 查询未使用代金券并且不是债权转让的定存信息
     * @param startTime 购买开始时间
     * @param endTime 购买结束时间
     * @return
     */
    public List<Object[]> queryWithoutVoucherAndNotTransfer(Long startTime, Long endTime) {

        String sql = " SELECT ragular.user_id, ragular.buy_fund, ragular.days, ragular.id" +
                " FROM" +
                        " (SELECT account.user_id AS user_id, account.buy_fund AS buy_fund, account.days AS days," +
                        " account.id AS id, redpacket.id AS redpacketId" +
                        " FROM wm_ragular_user_account AS account" +
                        " LEFT JOIN wm_redpacket_used AS redpacket ON account.id = redpacket.link_id" +
                        " AND redpacket.product_type = 4" +
                        " WHERE account.buy_time >= " + startTime +
                        " AND account.buy_time < " + endTime +
                        " AND account.status = 0 AND trans_count = 0 " +")" +
                " AS ragular" +
                " WHERE ragular.redpacketId IS NULL ";
        return manager.createNativeQuery(sql).getResultList();
    }

    /**
     * 查询复投购买
     * @param startTime 购买开始时间
     * @param endTime 购买结束时间
     * @return
     */
    public List<Object[]> queryRebuy(Long startTime, Long endTime) {

        String sql = " SELECT account.user_id AS user_id, account.buy_fund AS buy_fund, account.days AS days," +
                " account.id AS id" +
                " FROM wm_ragular_user_account AS account" +
                " WHERE account.buy_time >= " + startTime +
                " AND account.buy_time < " + endTime +
                " AND account.buy_type = 1 ";
        return manager.createNativeQuery(sql).getResultList();
    }

    public WmRagularUserAccount queryById(Long id) {
        return manager.find(WmRagularUserAccount.class, id);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}