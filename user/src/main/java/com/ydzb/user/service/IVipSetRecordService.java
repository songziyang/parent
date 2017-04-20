package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.VipGrade;
import com.ydzb.user.entity.VipSetRecord;

/**
 * vip设置记录service接口
 */
public interface IVipSetRecordService extends IBaseService<VipSetRecord, Long> {

    /**
     * 创建
     * @param userId
     * @param vipGradeId
     * @return
     */
    VipSetRecord createOne(Long userId, Long vipGradeId) throws Exception;

    /**
     * 创建
     * @param user
     * @param vipGrade
     * @param created
     * @param expireTime
     * @return
     */
    VipSetRecord createOne(User user, VipGrade vipGrade, Long created, Long expireTime) throws Exception;

    void updateStatus(Long userId, Integer sourceStatus, Integer targetStatus) throws Exception;
}
