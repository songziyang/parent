package com.ydzb.account.repository;

import com.ydzb.account.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class WmProductInfoRepository extends WmBaseRepository<WmProductInfo> {

    /**
     * 查询产品
     *
     * @return
     */
    public WmProductInfo findWmProductInfo() {
        String sql = "select wp from WmProductInfo as wp where wp.type = 1 and  wp.status = 0 and wp.productClas = 1 order by id desc ";
        TypedQuery<WmProductInfo> query = entityManager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmProductInfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询当前使用的产品是否是当天发布的产品
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public WmProductInfo findWmProductInfo(Long startDate, Long endDate) {
        String sql = "select wp from WmProductInfo as wp where wp.type = 1 and  wp.status = 0 and wp.productClas = 1 and wp.created >= " + startDate + " and wp.created < " + endDate + " order by  id desc ";
        TypedQuery<WmProductInfo> query = entityManager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmProductInfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询活期宝利率对照表
     *
     * @return
     */
    public WmCurrentRate findWmCurrentRate(Integer timeType) {
        String sql = "select wcr from WmCurrentRate as wcr where  wcr.status = 0  and wcr.timeType = " + timeType + " order by  id desc ";
        TypedQuery<WmCurrentRate> query = entityManager.createQuery(sql, WmCurrentRate.class);
        if (query != null) {
            List<WmCurrentRate> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存产品
     *
     * @param productInfo
     */
    public Long saveWmProductInfo(WmProductInfo productInfo) {
        entityManager.persist(productInfo);
        return productInfo.getId();
    }

    /**
     * 更新产品
     *
     * @param productInfo
     */
    public void updateWmProductInfo(WmProductInfo productInfo) {
        entityManager.merge(productInfo);
    }


    /**
     * 保存活期宝往期记录
     *
     * @param currentHistory
     */
    public void saveWmProductCurrentHistory(WmProductCurrentHistory currentHistory) {
        entityManager.persist(currentHistory);
    }

    /**
     * 查询预投份数
     *
     * @return
     */
    public Integer findTotalCopies() {
        String sql = "select sum(wcq.prepay_copies)  from wm_current_queue as wcq ";
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                BigDecimal b = (BigDecimal) obj;
                if (b != null) return b.intValue();
            }
        }
        return 0;
    }


    /**
     * 查询产品
     *
     * @return
     */
    public WmProductInfo findRagularProductInfo() {
        String sql = "select wp from WmProductInfo as wp where wp.type = 7 and  wp.status = 0 and wp.productClas = 1 order by id desc ";
        TypedQuery<WmProductInfo> query = entityManager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return entityManager.find(WmProductInfo.class, lst.get(0).getId(), LockModeType.PESSIMISTIC_WRITE);
            }
        }
        return null;
    }


    /**
     * 查询定存宝产品
     *
     * @return
     */
    public List<WmProductInfo> findRagular() {
        String sql = "select wp from WmProductInfo as wp where wp.type = 2 and  wp.status = 0 and wp.productClas = 1  order by wp.id desc ";
        TypedQuery<WmProductInfo> query = entityManager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询定存宝利率设置
     *
     * @return
     */
    public WmRagularRate findWmRagularRate(Integer days) {
        String sql = "select wcr from WmRagularRate as wcr where  wcr.status = 0 and  wcr.days = " + days + " order by   wcr.id desc ";
        TypedQuery<WmRagularRate> query = entityManager.createQuery(sql, WmRagularRate.class);
        if (query != null) {
            List<WmRagularRate> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 根据ID查询 行锁
     *
     * @param id 主键
     * @return
     */
    public WmProductInfo findWmProductInfoById(Long id) {
        return entityManager.find(WmProductInfo.class, id, LockModeType.PESSIMISTIC_WRITE);
    }


    /**
     * 查询定存宝利率设置
     *
     * @return
     */
    public WmNewStandard findWmNewStandard() {
        String sql = "select ws from WmNewStandard as ws where  ws.status = 0 order by ws.id desc ";
        TypedQuery<WmNewStandard> query = entityManager.createQuery(sql, WmNewStandard.class);
        if (query != null) {
            List<WmNewStandard> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }

    public WmProductInfo queryRagularById(Long ragularId) {
        String jpql = " FROM WmProductInfo WHERE id = " + ragularId + " AND type = 2 AND productClas = 1 ";
        List<WmProductInfo> resultList = entityManager.createQuery(jpql, WmProductInfo.class).getResultList();
        return (resultList != null && !resultList.isEmpty()) ? resultList.get(0) : null;
    }

    /**
     * 更新活期新手份额
     *
     * @param newStandard
     */
    public void updateWmNewStandard(WmNewStandard newStandard) {
        entityManager.merge(newStandard);
    }


    /**
     * 保存活期新手份额
     *
     * @param standard
     */
    public void saveWmNewStandard(WmNewStandard standard) {
        entityManager.persist(standard);
    }


    /**
     * 查询产品统计数据
     *
     * @param productId
     * @return
     */
    public WmProductSalesRecord findWmProductSalesRecord(Long productId) {
        String sql = "select wp from WmProductSalesRecord as wp where wp.productId = " + productId + " order by wp.id desc ";
        TypedQuery<WmProductSalesRecord> query = entityManager.createQuery(sql, WmProductSalesRecord.class);
        if (query != null) {
            List<WmProductSalesRecord> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存产品销售统计数据
     *
     * @param productSalesRecord
     */
    public void saveWmProductSalesRecord(WmProductSalesRecord productSalesRecord) {
        entityManager.persist(productSalesRecord);
    }


    /**
     * 查询产品往期剩余份数
     *
     * @param typeId     类型ID
     * @param cylcleDays 产品天数
     * @param startTime  开始时间
     * @return
     */
    public List<WmProductInfo> findProductInfoAllSurplus(Long typeId, Integer cylcleDays, Long startTime) {
        String sql = "select wpi from WmProductInfo as wpi where wpi.surplus > 0 and wpi.productClas = 1 and  wpi.type = " + typeId + " and wpi.cylcleDays = " + cylcleDays + " and  wpi.created >= " + startTime;
        TypedQuery<WmProductInfo> query = entityManager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            List<WmProductInfo> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

    /**
     * 查询单个产品
     * @param type 产品类型
     * @param status 产品状态
     * @param productClas 产品类别
     * @return
     */
    public WmProductInfo queryOne(Integer type, Integer status, Integer productClas) {
        String ql = " FROM WmProductInfo WHERE type = " + type + " AND status = " + status + " AND productClas = " + productClas + " ORDER BY id DESC ";
        List<WmProductInfo> resultList = entityManager.createQuery(ql, WmProductInfo.class).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
    /**
     * 根据主键查询
     * @param id 主键id
     * @param lockModeType 锁类型
     * @return
     */
    public WmProductInfo queryById(Long id, LockModeType lockModeType) {
        if (lockModeType == null) lockModeType = LockModeType.NONE;
        return super.queryById(id, WmProductInfo.class, lockModeType);
    }

    /**
     * 查询产品
     * @param type 类型
     * @param status 状态
     * @param productClas 类别
     * @param days 周期天数
     * @return
     */
    public WmProductInfo queryOne(Integer type, Integer status, Integer productClas, Integer days) {
        String sql = " FROM WmProductInfo WHERE type = " + type + " AND status = " + status + " AND productClas = " + productClas + " AND cylcleDays = " + days + " ORDER BY ID DESC";
        List<WmProductInfo> resultList = entityManager.createQuery(sql, WmProductInfo.class).getResultList();
        return (resultList == null || resultList.isEmpty())? null: resultList.get(0);
    }


}
