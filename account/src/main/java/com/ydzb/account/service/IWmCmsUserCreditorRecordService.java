package com.ydzb.account.service;

/**
 * 用户所持债券service接口
 */
public interface IWmCmsUserCreditorRecordService {

    /**
     * 根据原交易id,用户ID和产品类型来更改新的交易id
     * @param dealId 新的交易id
     * @param sourceDealId 原交易id
     * @param userId 用户id
     * @param productType 产品类别
     */
    void updateDealId(Long dealId, Long sourceDealId, Long userId, Integer productType) throws Exception;
}
