package com.ydzb.account.service;

import com.ydzb.account.entity.WmActivityGuoqingRecord;

/**
 * 国庆活动记录service接口
 */
public interface IWmActivityGuoqingRecordService {

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param linkId 外链id
     * @param fund 福袋数量
     * @param usableFund 剩余福袋数量
     * @return
     */
    WmActivityGuoqingRecord createOne(Long userId, int type, Long linkId, int fund, int usableFund);
}
