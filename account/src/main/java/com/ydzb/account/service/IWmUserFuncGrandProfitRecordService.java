package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserFuncGrandProfitRecord;

import java.math.BigDecimal;

/**
  *用户赠予（红包）收益记录service接口
  */
public interface IWmUserFuncGrandProfitRecordService {

    /**
     * 创建
     * @param userId 用户id
     * @param productId 产品id
     * @param fund 金额
     * @param pType 产品类型
     * @param type 红包类别
     * @param recordDate 记录日期
     * @return
     */
    WmUserFuncGrandProfitRecord createOne(Long userId, Long productId, BigDecimal fund, Integer pType, Integer type, Long recordDate);
}