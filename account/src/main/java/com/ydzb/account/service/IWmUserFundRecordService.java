package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserFundRecord;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/7/25.
 */
public interface IWmUserFundRecordService {

    WmUserFundRecord saveOrUpdate(WmUserFundRecord entity);


    /**
     * 创建
     * @param userId 用户id
     * @param fundflow 来源去向
     * @param fund 金额
     * @param logId 日志id
     * @param type 类型
     * @param fundType 资金类别
     * @param balance 余额
     * @param recordTime 记录时间
     * @return
     */
    WmUserFundRecord createOne(Long userId, String fundflow, BigDecimal fund, Long logId, Integer type, Integer fundType, BigDecimal balance, Long recordTime);
}
