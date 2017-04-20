package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedpacketUsed;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 红包使用记录Repository
 */
@Repository
public class WmRedpacketUsedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据用户id，linkId以及productType查询
     * @param userId 用户id
     * @param linkId linkId
     * @param productType 产品类型
     * @return
     */
    public List<WmRedpacketUsed> queryOnes(Long userId, Long linkId, Integer productType) {
        String ql = " FROM WmRedpacketUsed WHERE userId = " + userId + " AND linkId = " + linkId + " AND productType = " + productType;
        return entityManager.createQuery(ql, WmRedpacketUsed.class).getResultList();
    }

    /**
     * 根据用户查询红包使用情况
     * @param userId 用户id
     * @return
     */
    public List<WmRedpacketUsed> queryByUser(Long userId) {
        String ql = " FROM WmRedpacketUsed where userId = " + userId;
        return entityManager.createQuery(ql, WmRedpacketUsed.class).getResultList();
    }
}