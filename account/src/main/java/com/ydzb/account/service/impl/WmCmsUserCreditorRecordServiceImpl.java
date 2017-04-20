package com.ydzb.account.service.impl;

import com.ydzb.account.repository.WmCmsUserCreditorRecordRepository;
import com.ydzb.account.service.IWmCmsUserCreditorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户所持债券service实现
 */
@Service
public class WmCmsUserCreditorRecordServiceImpl implements IWmCmsUserCreditorRecordService {

    @Autowired
    private WmCmsUserCreditorRecordRepository cmsUserCreditorRecordRepository;

    /**
     * 根据原交易id,用户ID和产品类型来更改新的交易id
     * @param dealId 新的交易id
     * @param sourceDealId 原交易id
     * @param userId 用户id
     * @param productType 产品类别
     */
    @Override
    public void updateDealId(Long dealId, Long sourceDealId, Long userId, Integer productType) throws Exception {
        cmsUserCreditorRecordRepository.updateDealId(dealId, sourceDealId, userId, productType);
    }
}
