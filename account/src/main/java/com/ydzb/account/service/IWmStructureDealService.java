package com.ydzb.account.service;

import com.ydzb.account.entity.WmStructureDeal;

import java.util.List;

/**
 * Created by sy on 2016/7/25.
 */
public interface IWmStructureDealService {

    WmStructureDeal saveOrUpdate(WmStructureDeal entity);

    List<WmStructureDeal> queryByUserAndCloseDate(Long userId, Long closeDate);
}