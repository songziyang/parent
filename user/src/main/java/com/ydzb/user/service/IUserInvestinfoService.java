package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserInvestinfo;

/**
 * 用户投资信息service接口
 * @author sy
 */
public interface IUserInvestinfoService extends IBaseService<UserInvestinfo, Long> {

    /**
     * 通过用户id查找该用户投资金额
     * @param userId
     * @return
     */
    public UserInvestinfo queryByUser(Long userId);
}
