package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.PlatformException;
import com.ydzb.product.repository.IPlatformExceptionRepository;
import com.ydzb.product.service.IPlatformExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PlatformExceptionServiceImpl extends BaseServiceImpl<PlatformException, Long> implements IPlatformExceptionService {

    @Autowired
    private IPlatformExceptionRepository platformExceptionRepository;


    public IPlatformExceptionRepository getPlatformExceptionRepository() {
        return platformExceptionRepository;
    }

    public void setPlatformExceptionRepository(IPlatformExceptionRepository platformExceptionRepository) {
        this.platformExceptionRepository = platformExceptionRepository;
    }
}