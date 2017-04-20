package com.ydzb.product.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.DepositTypeExpire;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IDepositTypeExpireRepository extends IBaseRepository<DepositTypeExpire, Long> {

    @Modifying
    @Query("DELETE FROM DepositTypeExpire as re ")
    void removDepositTypeExpires();


}
