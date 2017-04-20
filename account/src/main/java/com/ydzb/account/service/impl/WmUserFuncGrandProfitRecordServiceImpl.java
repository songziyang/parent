package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserFuncGrandProfitRecord;
import com.ydzb.account.repository.WmUserFuncGrandProfitRecordRepository;
import com.ydzb.account.service.IWmUserFuncGrandProfitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户赠予（红包）收益记录service实现
 */
@Service
public class WmUserFuncGrandProfitRecordServiceImpl implements IWmUserFuncGrandProfitRecordService {

    @Autowired
    private WmUserFuncGrandProfitRecordRepository userFuncGrandProfitRecordRepository;

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
    @Override
    public WmUserFuncGrandProfitRecord createOne(Long userId, Long productId, BigDecimal fund, Integer pType, Integer type, Long recordDate) {
        if (fund != null && fund.doubleValue() > 0) {
            WmUserFuncGrandProfitRecord profitRecord = new WmUserFuncGrandProfitRecord();
            profitRecord.setUserId(userId);
            profitRecord.setProductId(productId);
            profitRecord.setFund(fund);
            profitRecord.setPtype(pType);
            profitRecord.setType(type);
            profitRecord.setRecordDate(recordDate);
            return (WmUserFuncGrandProfitRecord) userFuncGrandProfitRecordRepository.createOrUpdate(profitRecord);
        }
        return null;
    }
}