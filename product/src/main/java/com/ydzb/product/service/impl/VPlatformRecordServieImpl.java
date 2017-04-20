package com.ydzb.product.service.impl;


import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.VPlatformRecord;
import com.ydzb.product.repository.IVPlatformRecordRepository;
import com.ydzb.product.service.IVPlatformRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VPlatformRecordServieImpl extends BaseServiceImpl<VPlatformRecord, String> implements IVPlatformRecordService {

    @Autowired
    private IVPlatformRecordRepository platformRecordRepository;


    public IVPlatformRecordRepository getPlatformRecordRepository() {
        return platformRecordRepository;
    }

    public void setPlatformRecordRepository(IVPlatformRecordRepository platformRecordRepository) {
        this.platformRecordRepository = platformRecordRepository;
    }
}
