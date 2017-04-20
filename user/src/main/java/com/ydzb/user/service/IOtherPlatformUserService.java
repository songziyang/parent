package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.OtherPlatform;
import com.ydzb.user.entity.OtherPlatformUser;
import com.ydzb.user.entity.User;

/**
 * 其他平台用户service接口
 */
public interface IOtherPlatformUserService extends IBaseService<OtherPlatformUser, Long> {


    /**
     * 创建
     * @param user
     * @param mobile
     * @param realname
     * @param otherPlatform
     * @param regFlag
     * @param created
     * @return
     */
    OtherPlatformUser createOne(User user, String mobile, String realname, OtherPlatform otherPlatform, Integer regFlag, Long regTime, Long created) throws Exception;

    /**
     * 创建
     * @param mobile 电话
     * @param realname 真实姓名
     * @param platformId 所属平台id
     */
    void createOne(String mobile, String realname, Long platformId) throws Exception;
}
