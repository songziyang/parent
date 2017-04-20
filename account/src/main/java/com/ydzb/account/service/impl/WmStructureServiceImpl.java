package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmStructure;
import com.ydzb.account.repository.WmStructureRepository;
import com.ydzb.account.service.IWmStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sy on 2016/7/28.
 */
@Service
public class WmStructureServiceImpl implements IWmStructureService {

    @Autowired
    private WmStructureRepository structureRepository;

    @Override
    public WmStructure queryById(Long id) {
        return structureRepository.queryById(id);
    }
}