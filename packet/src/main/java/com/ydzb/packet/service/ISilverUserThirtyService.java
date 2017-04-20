package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.SilverUserThirty;

import javax.persistence.LockModeType;

/**
 * Created by sy on 2016/7/3.
 */
public interface ISilverUserThirtyService extends IBaseService<SilverUserThirty, Long> {

    SilverUserThirty createOne(Long userId, Integer totalFund, Integer usableFund);

    SilverUserThirty queryByUser(Long userId, LockModeType lockType);
}