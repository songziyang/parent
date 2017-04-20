package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmStructureDeal;
import com.ydzb.account.repository.WmStructureDealRepository;
import com.ydzb.account.service.IWmStructureDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sy on 2016/7/25.
 */
@Service
public class WmStructureDealServiceImpl implements IWmStructureDealService {

    @Autowired
    private WmStructureDealRepository wmStructureDealRepository;

    @Override
    public WmStructureDeal saveOrUpdate(WmStructureDeal entity) {
        return wmStructureDealRepository.saveOrUpdate(entity);
    }

    @Override
    public List<WmStructureDeal> queryByUserAndCloseDate(Long userId, Long closeDate) {
        return wmStructureDealRepository.queryByUserAndCloseDate(userId, closeDate);
    }
}