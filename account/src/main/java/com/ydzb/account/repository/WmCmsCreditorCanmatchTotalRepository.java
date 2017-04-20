package com.ydzb.account.repository;

import com.ydzb.account.entity.WmCmsCreditorCanmatchTotal;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

/**
 * 修改债券总数
 */
@Repository
public class WmCmsCreditorCanmatchTotalRepository extends WmBaseRepository {

    /**
     * 查询最新的一条记录
     * lockModeType 锁类型
     * @return
     */
    public WmCmsCreditorCanmatchTotal queryLatestOne(LockModeType lockModeType) {
        if (lockModeType == null) lockModeType = LockModeType.NONE;
        String ql = " SELECT id FROM WmCmsCreditorCanmatchTotal ORDER BY id DESC ";
        Long id = (Long) entityManager.createQuery(ql).getSingleResult();
        return entityManager.find(WmCmsCreditorCanmatchTotal.class, id, lockModeType);
    }
}