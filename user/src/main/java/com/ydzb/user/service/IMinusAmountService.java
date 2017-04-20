package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.MinusAmount;

public interface IMinusAmountService extends IBaseService<MinusAmount, Long> {


    public String saveMinusamount(MinusAmount minusAmount) throws Exception;

    public Integer findSumMinusAmount();

}
