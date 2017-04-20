package com.ydzb.account.service;

import com.ydzb.account.entity.WmStructure;
import com.ydzb.account.entity.WmStructureDeal;

import java.util.List;

/**
 * Created by sy on 2016/10/23.
 */
public interface IWmStructureAccountService {

    List<WmStructure> findByEndDate(Long date);

    void updateFullStandardState(WmStructure structure) throws Exception;

    List<WmStructure> findByCloseDate(Long date);

    void updateStable(WmStructure structure) throws Exception;

    void accountStructureRevenue(WmStructure structure, Long userId) throws Exception;

    void accountStructureRevenueNew(WmStructure structure, WmStructureDeal structureDeal, Long userId) throws Exception;
}