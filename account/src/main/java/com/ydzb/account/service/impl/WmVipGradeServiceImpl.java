package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmVipGradeRepository;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.account.service.IWmVipGradeService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
public class WmVipGradeServiceImpl implements IWmVipGradeService {

    //签名
    private static final String SIGN = "kekechao";

    Logger logger = Logger.getLogger(WmVipGradeServiceImpl.class);

    //php接口地址
    @Value("${futo.url}")
    private String url;

    @Autowired
    private WmVipGradeRepository gradeRepository;

    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    /**
     * 发送短信
     *
     * @param userId 用户ID
     */
    private void sendContent(Long userId) {
        try {
            WmUser user = gradeRepository.findWmUserById(userId);
            if (user != null) {
                infoSmsTimerService.sendWmInfoSmsTimer("vip_upgrade", user.getMobile(), "name:" + user.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存VIP变化记录
     *
     * @param userId     用户ID
     * @param oldGradeId 原等级ID
     * @param newGradeId 新等级ID
     * @param cType      等级变化
     * @param oType      操作类型
     */
    private void saveWmUserVipGradeChange(Long userId, Long oldGradeId, Long newGradeId, Integer cType, Integer oType) {
        // 系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
        WmUserVipGradeChange userVipGradeChange = new WmUserVipGradeChange();
        userVipGradeChange.setUserId(userId);
        userVipGradeChange.setOldGradeId(oldGradeId);
        userVipGradeChange.setNewGradeId(newGradeId);
        userVipGradeChange.setcType(cType);
        userVipGradeChange.setoType(oType);
        userVipGradeChange.setOptime(curDate);
        gradeRepository.saveWmUserVipGradeChange(userVipGradeChange);
    }


    /**
     * 用户VIP等级变更
     *
     * @param userId 用户ID
     * @throws Exception
     */
    @Override
    public void accountUserVipGrade(Long userId) throws Exception {
        //系统当前时间
        Long currentTime = DateUtil.getSystemTimeSeconds();
        //系统当前日期
        Long currentDate = DateUtil.getSystemTimeDay(DateUtil.curDate());

        if (userId != null) {
            WmUser user = gradeRepository.findWmUserById(userId);

            //查询是否有手动设置
            WmVipSetGrade wmVipSetGrade = gradeRepository.findWmVipSetGrade(userId, currentDate, 1);
            //存在手动设置记录并且到期日期大于系统当前日期直接跳过
            if (wmVipSetGrade != null && wmVipSetGrade.getExpireTime().compareTo(currentDate) > 0) {
                return;
            }

            //存在手动设置记录并且到期日期等于系统当前日期
            if (wmVipSetGrade != null && wmVipSetGrade.getExpireTime().compareTo(currentDate) == 0) {
                wmVipSetGrade.setStatus(2);
                gradeRepository.updateWmVipSetGrade(wmVipSetGrade);
            }

            //无手动设置记录或者是最后一天按照正常投资记录走
            WmUserInvestinfo userInvestinfo = gradeRepository.findWmUserInvestinfoByUserId(userId);
            //查询用户信息 和 用户投资信息
            if (user != null && userInvestinfo != null) {
                //根据用户投资金额 获取应属于等级的VIP记录
                //自由定存投资总额
                if (userInvestinfo.getAllInvestFree() == null) userInvestinfo.setAllInvestFree(BigDecimal.ZERO);
                //定存投资总额
                if (userInvestinfo.getAllInvestDeposit() == null) userInvestinfo.setAllInvestDeposit(BigDecimal.ZERO);
                //稳进宝投资总额
                if (userInvestinfo.getAllInvestWjb() == null) userInvestinfo.setAllInvestWjb(BigDecimal.ZERO);
                //私人定制投资
                if (userInvestinfo.getAllInvestSelf() == null) userInvestinfo.setAllInvestSelf(BigDecimal.ZERO);

                BigDecimal investFund = userInvestinfo.getAllInvestDeposit().add(userInvestinfo.getAllInvestWjb().add(userInvestinfo.getAllInvestSelf())).add(userInvestinfo.getAllInvestFree());
                WmVipGrade wmVipGrade = gradeRepository.findVipGradeByInvestAllFund(investFund);

                if (wmVipGrade != null) {

                    //用户VIP等级升级
                    if (user.getUserLeve().compareTo(wmVipGrade.getId()) < 0) {
                        try {
                            //发送请求
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(user.getId()));
                            params.put("sign", SIGN);
                            String result = HttpClientUtil.POST(url + "/Deposit/updateUserLevel", params);
                            logger.info("result = " + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    //用户VIP等级降级
                    if (user.getUserLeve().compareTo(wmVipGrade.getId()) > 0) {
                        //保存VIP等级降级记录
                        saveWmUserVipGradeChange(userId, user.getUserLeve(), wmVipGrade.getId(), 2, 1);
                        user.setUserLeve(wmVipGrade.getId());
                        user.setLeveTime(currentTime);
                        gradeRepository.updateWmUser(user);

                    }

                }
            }
        }
    }


    public WmVipGradeRepository getGradeRepository() {
        return gradeRepository;
    }

    public void setGradeRepository(WmVipGradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }
}
