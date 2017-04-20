package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserWithdrawNum;
import com.ydzb.account.repository.WmUserWithDrawNumRepository;
import com.ydzb.account.service.IWmUserWithDrawNumService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WmUserWithdrawNumServiceImpl implements IWmUserWithDrawNumService {

    @Autowired
    private WmUserWithDrawNumRepository userWithDrawNumRepository;

    /**
     * 统计七天内提现次数
     *
     * @param endDate 系统当前时间
     * @throws Exception
     */
    @Override
    public void accountQueryWithDraw(Long endDate) throws Exception {

        //清空表
        userWithDrawNumRepository.removeWmUserWithdrawNum();

        //开始时间
        //一共计算7天 前6天+今天
        Long startDate = endDate - 6 * 24 * 3600;

        //查询前七天提现次数大于2的
        List<Map<String, Object>> lst = userWithDrawNumRepository.findWithDrawNum(startDate, DateUtil.getSystemTimeSeconds());
        //遍历
        if (lst != null && lst.size() > 0) {
            for (Map<String, Object> map : lst) {
                saveWmUserWithdrawNum((long) map.get("uid"), (int) map.get("num"));
            }
        }

        //查询前七天充值次数大于2的
        List<Map<String, Object>> rechargeLst = userWithDrawNumRepository.findRechargeNum(startDate, DateUtil.getSystemTimeSeconds());
        //遍历
        if (rechargeLst != null && rechargeLst.size() > 0) {
            for (Map<String, Object> map : rechargeLst) {
                saveRechargeNum((long) map.get("uid"), (int) map.get("num"));
            }
        }

    }

    /**
     * 保存或更新用户七天提现统计和
     *
     * @param userId 用户ID
     * @param num    次数统计
     */
    public void saveWmUserWithdrawNum(Long userId, Integer num) {
        WmUserWithdrawNum userWithDrawNum = userWithDrawNumRepository.findWmUserWithdrawNumByUserId(userId);
        if (userWithDrawNum != null) {
            userWithDrawNum.setCountNum(num);
            userWithDrawNumRepository.updateWmUserWithdrawNum(userWithDrawNum);
        } else {
            userWithDrawNum = new WmUserWithdrawNum();
            userWithDrawNum.setCountNum(num);
            userWithDrawNum.setRechargeNum(0);
            userWithDrawNum.setUserId(userId);
            userWithDrawNum.setCreated(DateUtil.getSystemTimeSeconds());
            userWithDrawNumRepository.saveWmUserWithdrawNum(userWithDrawNum);
        }
    }


    /**
     * 保存或更新用户前七天充值统计之和
     *
     * @param userId 用户ID
     * @param num    次数统计
     */
    public void saveRechargeNum(Long userId, Integer num) {
        WmUserWithdrawNum userWithDrawNum = userWithDrawNumRepository.findWmUserWithdrawNumByUserId(userId);
        if (userWithDrawNum != null) {
            userWithDrawNum.setRechargeNum(num);
            userWithDrawNumRepository.updateWmUserWithdrawNum(userWithDrawNum);
        } else {
            userWithDrawNum = new WmUserWithdrawNum();
            userWithDrawNum.setRechargeNum(num);
            userWithDrawNum.setCountNum(0);
            userWithDrawNum.setUserId(userId);
            userWithDrawNum.setCreated(DateUtil.getSystemTimeSeconds());
            userWithDrawNumRepository.saveWmUserWithdrawNum(userWithDrawNum);
        }
    }

    public WmUserWithDrawNumRepository getUserWithDrawNumRepository() {
        return userWithDrawNumRepository;
    }

    public void setUserWithDrawNumRepository(WmUserWithDrawNumRepository userWithDrawNumRepository) {
        this.userWithDrawNumRepository = userWithDrawNumRepository;
    }
}
