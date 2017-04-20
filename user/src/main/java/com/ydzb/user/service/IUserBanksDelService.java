package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserBanksDel;

/**
 * 用户银行解绑service接口
 */
public interface IUserBanksDelService extends IBaseService<UserBanksDel, Long> {

    /**
     * 审核操作
     * @param operation success-操作成功    failure-操作失败
     * @param reason 失败原因
     * @param banksDelId 银行卡解绑id
     * @throws Exception
     */
    public void validate(final String operation, final String reason, final Long banksDelId) throws Exception;

    /**
     * 根据银行卡解绑查找用户
     * @param id
     * @return
     */
    public User findUserByBanksDel(final Long id);
}