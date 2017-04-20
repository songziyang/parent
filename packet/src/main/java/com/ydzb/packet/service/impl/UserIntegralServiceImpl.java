package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.packet.entity.Integral;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.repository.IIntegralRepository;
import com.ydzb.packet.repository.IUserIntegralRepository;
import com.ydzb.packet.repository.RpUserRepository;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.packet.service.IUserIntegralService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserIntegral;
import com.ydzb.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by sy on 2016/6/6.
 */
@Service
public class UserIntegralServiceImpl extends BaseServiceImpl<UserIntegral, Long> implements IUserIntegralService{

    @Autowired
    private IUserIntegralRepository userIntegralRepository;
    @Autowired
    private RpUserRepository rpUserRepository;
    @Autowired
    private IIntegralRepository integralRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserIntegralRecordService userIntegralRecordService;

    private UserIntegral createOrUpdate(User user, Integral integral) {
        if (user != null) {
            UserIntegral userIntegral = userIntegralRepository.queryByUserId(user.getId());
            if (userIntegral == null) {
                UserIntegral newIntegral = new UserIntegral();
                newIntegral.setUser(user);
                newIntegral.setTotalIntegral(integral.getIntegral());
                newIntegral.setIntegral(integral.getIntegral());
                newIntegral.setVip(0);
                return userIntegralRepository.save(newIntegral);
            } else {
                BigDecimal integralIntegral = integral.getIntegral();
                integralIntegral = integralIntegral == null? BigDecimal.ZERO: integralIntegral;
                BigDecimal integralValue = userIntegral.getIntegral();
                BigDecimal totalIntegralValue = userIntegral.getTotalIntegral();
                userIntegral.setIntegral(integralValue == null? integralIntegral: integralValue.add(integralIntegral));
                userIntegral.setTotalIntegral(totalIntegralValue == null? integralIntegral: totalIntegralValue.add(integralIntegral));
                return userIntegralRepository.save(userIntegral);
            }
        }
        return null;
    }

    /**
     * 发送现金红包
     * @param rpUser
     * @param integralId
     * @return
     */
    @Override
    public String sendIntegral(RpUser rpUser, Long integralId) throws Exception {
        if (integralId != null) {
            Integral integral = integralRepository.findOne(integralId);
            if (integral != null) {
                List<BigInteger> userIds = rpUserRepository.queryUserIds(rpUser);
                if (userIds != null) {
                    //生成积分记录
                    rpUserRepository.sendUserIntegral(rpUser, integralId);
                    //创建/更新用户积分
                    for (BigInteger userId : userIds) {
                        if (userId != null) {
                            Long id = userId.longValue();
                            User user = userRepository.findOne(id);
                            //更新用户积分
                            createOrUpdate(user, integral);
                        }
                    }
                    return "发送成功";
                }
            }
        }
        return "发送失败";
    }
}
