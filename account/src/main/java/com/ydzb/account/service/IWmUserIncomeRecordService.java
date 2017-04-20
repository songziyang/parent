package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserIncomeRecord;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/7/25.
 */
public interface IWmUserIncomeRecordService {

    WmUserIncomeRecord saveOrUpdate(WmUserIncomeRecord entity);

    /**
     * 创建
     * @param userId 用户id
     * @param name 来源名称
     * @param income 收益
     * @param pType 产品类型
     * @param optime 操作时间
     * @return
     */
    WmUserIncomeRecord createOne(Long userId, String name, BigDecimal income, Integer pType, Long optime);
}
