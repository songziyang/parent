package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.ActivityQingming;

/**
 * 清明活动service接口
 */
public interface IActivityQingmingService extends IBaseService<ActivityQingming, Long> {

    /**
     * 根据用户查找用户清明活动记录
     * @param userId 用户id
     * @return
     */
    public ActivityQingming findByUser(Long userId);

    /**
     * 添加抽奖次数
     * @param userId 用户id
     * @return
     */
    public String addSignNum(Long userId);
}