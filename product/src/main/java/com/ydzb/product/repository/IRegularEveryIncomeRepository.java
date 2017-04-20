package com.ydzb.product.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.RegularEveryIncome;
import com.ydzb.product.entity.StableDeal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface IRegularEveryIncomeRepository extends IBaseRepository<RegularEveryIncome, Long> {

    @Modifying
    @Query("DELETE FROM RegularEveryIncome as re ")
    public void removeAllInfo();


}
