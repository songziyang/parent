package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.Structure;

/**
 * Created by sy on 2016/7/24.
 */
public interface IStructureService extends IBaseService<Structure, Long> {

    void saveOne(Structure structure, String startDate, String endDate);
}