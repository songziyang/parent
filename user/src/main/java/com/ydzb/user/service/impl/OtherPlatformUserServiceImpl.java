package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.OtherPlatform;
import com.ydzb.user.entity.OtherPlatformUser;
import com.ydzb.user.entity.User;
import com.ydzb.user.repository.IOtherPlatformUserRepository;
import com.ydzb.user.repository.IUserRepository;
import com.ydzb.user.service.IOtherPlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 其他平台用户service实现
 */
@Service
public class OtherPlatformUserServiceImpl extends BaseServiceImpl<OtherPlatformUser, Long> implements IOtherPlatformUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOtherPlatformUserRepository otherPlatformUserRepository;

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
    @Override
    public OtherPlatformUser createOne(User user, String mobile, String realname, OtherPlatform otherPlatform, Integer regFlag, Long regTime, Long created) {
        OtherPlatformUser otherPlatformUser = new OtherPlatformUser();
        otherPlatformUser.setUser(user);
        otherPlatformUser.setMobile(mobile);
        otherPlatformUser.setRealname(realname);
        otherPlatformUser.setOtherPlatform(otherPlatform);
        otherPlatformUser.setRegFlag(regFlag);
        otherPlatformUser.setCreated(created);
        otherPlatformUser.setRegTime(regTime);
        otherPlatformUserRepository.save(otherPlatformUser);
        return otherPlatformUser;
    }

    /**
     * 创建
     * @param mobile 电话
     * @param realname 真实姓名
     * @param platformId 所属平台id
     */
    @Override
    public void createOne(String mobile, String realname, Long platformId) throws Exception {
        List<OtherPlatformUser> otherPlatformUsers = otherPlatformUserRepository.queryByMobile(mobile);
        //过滤重复用户
        if (otherPlatformUsers == null || otherPlatformUsers.isEmpty()) {
            List<User> users = userRepository.findByMobile(mobile, 0);
            //如果是银多用户
            try {
                if (users != null && !users.isEmpty()) {
                    User user = users.get(0);
                    createOne(user, mobile, realname, platformId == null ? null : new OtherPlatform(platformId), OtherPlatformUser.REGFLAG_REGIST, user.getCreated(), DateUtil.getSystemTimeSeconds());
                } else {
                    createOne(null, mobile, realname, platformId == null ? null : new OtherPlatform(platformId), OtherPlatformUser.REGFLAG_NOTREGIST, null, DateUtil.getSystemTimeSeconds());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
