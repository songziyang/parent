package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.VipGrade;

/**
 * vip等级service接口
 * @author sy
 */
public interface IVipGradeService extends IBaseService<VipGrade, Long> {

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    VipGrade queryByUser(Long userId);
}
