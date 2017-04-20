package com.ydzb.account.repository;

import com.ydzb.account.entity.WmFreeRefund;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.List;

/**
 * 随心存还息记录repository
 */
@Repository
public class WmFreeRefundRepository extends WmBaseRepository<WmFreeRefund> {

    /**
     * 根据主键查询
     * @param id 主键
     * @param lockModeType 锁类型
     * @return
     */
    public WmFreeRefund queryById(Long id, LockModeType lockModeType) {
        return super.queryById(id, WmFreeRefund.class, lockModeType);
    }

    /**
     * 根据linkId查询id
     * @param linkId
     * @return
     */
    public Long queryIdByLinkId(Long linkId) {
        String ql = " SELECT id FROM WmFreeRefund WHERE linkId = :linkId ORDER BY id DESC ";
        Query query = entityManager.createQuery(ql);
        query.setParameter("linkId", linkId);
        List<Long> resultList = query.getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }

    /**
     * 根据LinkId查询实体
     * @param linkId
     * @return
     */
    public WmFreeRefund queryOneByLinkId(Long linkId) {
        String ql = " FROM WmFreeRefund WHERE linkId = :linkId ORDER BY id DESC ";
        Query query = entityManager.createQuery(ql, WmFreeRefund.class);
        query.setParameter("linkId", linkId);
        List<WmFreeRefund> resultList = query.getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
}