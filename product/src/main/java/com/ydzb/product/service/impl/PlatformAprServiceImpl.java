package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.PlatformApr;
import com.ydzb.product.repository.IPlatformAprRepository;
import com.ydzb.product.service.IPlatformAprService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlatformAprServiceImpl extends BaseServiceImpl<PlatformApr, Long> implements IPlatformAprService {


    @Autowired
    private IPlatformAprRepository platformAprRepository;


    public IPlatformAprRepository getPlatformAprRepository() {
        return platformAprRepository;
    }

    public void setPlatformAprRepository(IPlatformAprRepository platformAprRepository) {
        this.platformAprRepository = platformAprRepository;
    }
}
