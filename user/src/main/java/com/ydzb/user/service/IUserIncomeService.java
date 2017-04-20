package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserIncome;

/**
 * 用户收益信息service接口
 * @author sy
 */
public interface IUserIncomeService extends IBaseService<UserIncome, Long> {

    /**
     * 根据用户id查询用户收益信息
     * @param userId
     * @return
     */
    public UserIncome queryByUser(Long userId);
}
