package com.ydzb.user.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.VipGrade;
import com.ydzb.user.entity.VipSetRecord;
import com.ydzb.user.repository.IVipSetRecordRepository;
import com.ydzb.user.service.IVipSetRecordService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * vip设置记录service实现
 */
@Service
public class VipSetRecordServiceImpl extends BaseServiceImpl<VipSetRecord, Long> implements IVipSetRecordService {

    @Autowired
    private IVipSetRecordRepository vipSetRecordRepository;

    /**
     * 创建
     * @param userId
     * @param vipGradeId
     * @return
     */
    @Override
    public VipSetRecord createOne(Long userId, Long vipGradeId) throws Exception {

        Long now = DateUtil.getSystemTimeSeconds();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Long expireTime = DateUtil.getSystemTimeDay(calendar.get(Calendar.YEAR) + "-" + (calendar.get(calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE));
        return createOne(new User(userId), new VipGrade(vipGradeId), now, expireTime);
    }

    /**
     * 创建
     * @param user
     * @param vipGrade
     * @param created
     * @param expireTime
     * @return
     */
    @Override
    public VipSetRecord createOne(User user, VipGrade vipGrade, Long created, Long expireTime) throws Exception {

        VipSetRecord vipSetRecord = new VipSetRecord();
        vipSetRecord.setUser(user);
        vipSetRecord.setCreatedUser(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUsername());
        vipSetRecord.setVipGrade(vipGrade);
        vipSetRecord.setCreated(created);
        vipSetRecord.setExpireTime(expireTime);
        vipSetRecord.setStatus(VipSetRecord.STATUS_NOTEXPIRE);
        return save(vipSetRecord);
    }

    @Override
    public void updateStatus(Long userId, Integer sourceStatus, Integer targetStatus) throws Exception {
        vipSetRecordRepository.updateStatus(userId, sourceStatus, targetStatus);
    }
}