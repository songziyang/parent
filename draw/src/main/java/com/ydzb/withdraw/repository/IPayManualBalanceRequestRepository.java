package com.ydzb.withdraw.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.withdraw.entity.PayManualRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IPayManualBalanceRequestRepository extends IBaseRepository<PayManualRequest, Long> {


    @Query("select pmr from PayManualRequest as pmr where pmr.bussflowno  = :bussflowno ")
    public PayManualRequest findPayManualRequestByBussflowno(@Param("bussflowno") String bussflowno);


}
