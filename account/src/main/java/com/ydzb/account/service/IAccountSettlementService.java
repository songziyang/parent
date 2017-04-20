package com.ydzb.account.service;


import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmUser;

/**
 * 用户结算service接口
 */
public interface IAccountSettlementService {

    /**
     * 查询现有用户的最大ID 和 最小ID
     *
     * @return ID 区间 最大ID 和 最小ID
     */
    public IDRange findWmUserMaxIdAndMinId();


    /**
     * 根据用户ID 查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public WmUser findWmUserUserById(Long userId);

}
