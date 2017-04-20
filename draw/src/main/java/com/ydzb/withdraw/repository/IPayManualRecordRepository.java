package com.ydzb.withdraw.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.withdraw.entity.PayManualRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 代付手动打款repository
 */
public interface IPayManualRecordRepository extends IBaseRepository<PayManualRecord, Long> {

    /**
     * 根据id设置状态
     * @param id
     * @param status
     */
    @Modifying
    @Query(" UPDATE PayManualRecord SET status = :status WHERE id = :id ")
    public void setStatus(@Param("id") Long id, @Param("status") Byte status);
}