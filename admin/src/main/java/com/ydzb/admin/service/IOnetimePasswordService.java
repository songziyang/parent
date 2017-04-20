package com.ydzb.admin.service;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.entity.OnetimePassword;
import com.ydzb.core.service.IBaseService;

/**
 * 数字令牌service
 */
public interface IOnetimePasswordService extends IBaseService<OnetimePassword, Long> {

    /**
     * 保存
     * @param onetimePassword
     */
    public String saveOne(OnetimePassword onetimePassword);

    /**
     * 删除
     * @param otpId
     */
    public void deleteOne(Long otpId);

    /**
     * 数字令牌同步
     * @param otpId
     * @param password1
     * @param password2
     * @return
     */
    public String sync(Long otpId, String password1, String password2);
}