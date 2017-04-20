package com.ydzb.account.repository;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Repository
public class WmCurrentQueueRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询排队表 最大ID 和 最小ID
     *
     * @return
     */
    public IDRange findMaxIdAndMinId() {
        String sql = "select max(wcq.id) as maxId, min(wcq.id) as minId from wm_current_queue as wcq ";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object[] obj = (Object[]) query.getSingleResult();
            if (obj != null && obj.length > 0) {
                IDRange idRange = new IDRange((BigInteger) obj[0], (BigInteger) obj[1]);
                return idRange;
            }
        }
        return null;
    }


    /**
     * 查询产品
     *
     * @return
     */
    public WmProductInfo findWmProductInfo() {
        String sql = "select wp from WmProductInfo as wp where wp.type = 1 and  wp.status = 0 and wp.productClas = 1 order by id desc ";
        TypedQuery<WmProductInfo> query = manager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return manager.find(WmProductInfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }

    /**
     * 查询活期宝队列
     *
     * @param id
     * @return
     */
    public WmCurrentQueue findWmCurrentQueue(Long id) {
        return manager.find(WmCurrentQueue.class, id);
    }


    /**
     * 查询活期宝预投
     *
     * @param id
     * @return
     */
    public WmCurrentPrepay findWmCurrentPrepay(Long id) {
        return manager.find(WmCurrentPrepay.class, id);
    }


    /**
     * 更新产品
     *
     * @param productInfo
     */
    public void updateWmProductInfo(WmProductInfo productInfo) {
        manager.merge(productInfo);
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
     * 更新用户活期账户
     *
     * @param wmCurrentUserAccount
     */
    public void updateCurrentUserAccount(WmCurrentUserAccount wmCurrentUserAccount) {
        manager.merge(wmCurrentUserAccount);
    }

    /**
     * 保存用户活期账户
     *
     * @param wmCurrentUserAccount
     */
    public void saveCurrentUserAccount(WmCurrentUserAccount wmCurrentUserAccount) {
        manager.persist(wmCurrentUserAccount);
    }


    /**
     * 查询活期宝账户
     *
     * @param userId
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
     * 查询用户投资
     *
     * @param userId
     * @return
     */
    public WmUserInvestinfo findWmUserInvestinfoByUserId(Long userId) {
        String sql = "select wm from WmUserInvestinfo as wm where wm.userId = " + userId + " order by  wm.id desc ";
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
        String sql = "select wm from WmUserMoney as wm where wm.userId = " + userId + " order by  wm.id desc ";
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
     * 查询用户体验金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserExMoney findWmUserExMoneyyByUserId(Long userId) {
        String sql = "select wm from WmUserExMoney as wm where wm.userId = " + userId + " order by  wm.id desc ";
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
     * @return
     */
    public Long saveWmUserFundOutLog(WmUserFundOutLog userFundOutLog) {
        manager.persist(userFundOutLog);
        return userFundOutLog.getId();
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
     * 保存体验金记录
     *
     * @param userExpMoneyRecord
     */
    public void saveWmUserExpMoneyRecord(WmUserExpMoneyRecord userExpMoneyRecord) {
        manager.persist(userExpMoneyRecord);
    }

    /**
     * 更新用户预投记录
     *
     * @param currentPrepay
     */
    public void updateWmCurrentPrepay(WmCurrentPrepay currentPrepay) {
        manager.merge(currentPrepay);
    }


    /**
     * 删除预投排队
     *
     * @param currentQueue
     */
    public void removeWmCurrentQueue(WmCurrentQueue currentQueue) {
        manager.remove(currentQueue);
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
     * 查询预投份数
     *
     * @return
     */
    public Integer findTotalCopies() {
        String sql = "select sum(wcq.prepay_copies)  from wm_current_queue as wcq ";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                BigDecimal b = (BigDecimal) obj;
                if (b != null) return b.intValue();
            }
        }
        return 0;
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
