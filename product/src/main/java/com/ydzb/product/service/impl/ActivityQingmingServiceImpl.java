package com.ydzb.product.service.impl;

import com.google.gson.Gson;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.ActivityQingming;
import com.ydzb.product.repository.IActivityQingmingRepository;
import com.ydzb.product.service.IActivityQingmingService;
import com.ydzb.user.entity.User;
import com.ydzb.user.repository.IUserBanksRepository;
import com.ydzb.user.repository.IUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 清明活动service实现
 */
@Service
public class ActivityQingmingServiceImpl extends BaseServiceImpl<ActivityQingming, Long> implements IActivityQingmingService {

    @Autowired
    private IActivityQingmingRepository activityQingmingRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserBanksRepository userBanksRepository;

    /**
     * 根据用户超找用户清明活动记录
     * @param userId 用户id
     * @return
     */
    @Override
    public ActivityQingming findByUser(Long userId) {
        return activityQingmingRepository.findByUser(userId);
    }

    /**
     * 添加抽奖次数
     * @param userId 用户id
     * @return
     */
    @Override
    public String addSignNum(Long userId) {
        String flag = "success";
        ActivityQingming record =  null;
        //判断是否实名认证
        if (!haveIdCard(userId)) {
            flag = "noIdCard";
        }
        //判断是否绑定银行卡
        else if (!haveBankCards(userId)) {
            flag = "noBankCard";
        } else {
            //获得活动记录
            record = getActivityRecord(userId);
            //增加抽奖次数
            addNumber(record);
        }
        return getActivityJSON(record, flag);
    }

    /**
     * 获得清明活动记录
     * @param userId
     * @return
     */
    private ActivityQingming getActivityRecord(Long userId) {
        ActivityQingming record = findByUser(userId);
        //如果不存在则创建
        if (record == null) {
            User user = userRepository.findOne(userId);
            record = new ActivityQingming();
            record.setUser(user);
            activityQingmingRepository.save(record);
        }
        return record;
    }

    /**
     * 是否今天已经抽奖
     * @param record
     * @return
     */
    private boolean ifAddSignInThisDay(ActivityQingming record) {
        Long lastSign = record.getLastAddSign();    //最后一次添加抽奖时间
        if (lastSign == null) {
            return false;
        }
        Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        Long tormorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()));
        //如果最后一次增加抽奖时间是在今天
        if (lastSign >= today && lastSign < tormorrow) {
            return true;
        }
        return false;
    }

    /**
     * 是否可以添加抽奖次数
     * @param record 清明节抽奖记录
     * @return
     */
    private boolean isMaxNumber(ActivityQingming record) {
        Integer sign = record.getSign();
        Integer newUser = record.getNewUser();
        if (newUser == ActivityQingming.NEWUSER) {
            if (sign >= ActivityQingming.NEWUSER_SIGNNUM) {
                return true;
            }
        } else if (newUser == ActivityQingming.OLDUSER) {
            if (sign >= ActivityQingming.OLDUSER_SIGNNUM) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加抽奖次数
     * @param record 清明节活动记录
     */
    private void addNumber(ActivityQingming record) {
        record.setSign(record.getSign() == null? 1: record.getSign() + 1);
        record.setSurplusSign(record.getSurplusSign() == null? 1: record.getSurplusSign() + 1);
        record.setLastAddSign(DateUtil.getSystemTimeSeconds());
        activityQingmingRepository.save(record);
    }


    private String getActivityJSON(ActivityQingming record, String flag) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("flag", flag);
        jsonMap.put("sign", record == null? 0: record.getSign());
        jsonMap.put("surplusSign", record == null? 0: record.getSurplusSign());
        return new Gson().toJson(jsonMap);
    }

    /**
     * 是否认证身份证号
     * @param userId 用户id
     * @return
     */
    private boolean haveIdCard(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return false;
        }
        return StringUtils.isNotEmpty(user.getIdCard())? true: false;
    }

    /**
     * 是否绑定银行卡
     * @param userId 用户id
     * @return
     */
    private boolean haveBankCards(Long userId) {
        return userBanksRepository.findUserCardsCount(userId) > 0? true: false;
    }

    /**
     * 判断是否在活动时间期间
     * @return
     */
    private boolean inActivityTime() {
        if (DateUtil.getSystemTimeSeconds() >= ActivityQingming.ACITVITY_THREE_STARTTIME
                && DateUtil.getSystemTimeSeconds() < ActivityQingming.ACTIVITY_THREE_ENDTIME) {
            return true;
        }
        return false;
    }
}
