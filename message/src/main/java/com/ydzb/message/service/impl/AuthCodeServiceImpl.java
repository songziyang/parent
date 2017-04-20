package com.ydzb.message.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.message.repository.IAuthCodeRepository;
import com.ydzb.message.service.IAuthCodeService;
import com.ydzb.sms.entity.AuthCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthCodeServiceImpl extends BaseServiceImpl<AuthCode, Long> implements IAuthCodeService {

    @Autowired
    private IAuthCodeRepository authCodeRepository;

    public IAuthCodeRepository getAuthCodeRepository() {
        return authCodeRepository;
    }

    public void setAuthCodeRepository(IAuthCodeRepository authCodeRepository) {
        this.authCodeRepository = authCodeRepository;
    }


}
