package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmStable;
import com.ydzb.account.entity.WmStructure;
import com.ydzb.account.service.IWmCurrentUserAccountService;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.account.service.IWmStableAccountservice;
import com.ydzb.account.service.IWmStructureAccountService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class AccountQuartz {

    //活期宝
    @Autowired
    private IWmCurrentUserAccountService wmCurrentUserAccountService;

    //稳进宝
    @Autowired
    private IWmStableAccountservice wmStableAccountservice;

    //短信
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;


    public void accountJob() {

//        //更新用户签到
//        accountSignProbability();

        //更新用户昨日收益
        accountYesterdayIncome();

//        //更新基金类产品状态和提前短信提醒
//        accountStable();
    }


    /**
     * 更新签到概率
     *
     * @throws Exception
     */
    public void accountSignProbability() {
        try {
            //更新用户每日签到
            wmCurrentUserAccountService.accountSign();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户昨日收益
     *
     * @throws Exception
     */
    public void accountYesterdayIncome() {
        try {
            //更新用户昨日收益
            wmCurrentUserAccountService.accountYesterdayIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新基金类产品状态和提前短信提醒
     */
    public void accountStable() {
        try {
            //更新满标状态
            updateFullStandardState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //发送提醒短信
            sendRemindMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //更新到期状态
            updateDueToState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 稳进宝更新满标状态
     */
    private void updateFullStandardState() throws Exception {
        //如果申购结束日期为1号,那么就在2号结算,所以取前一天时间
        Long date = DateUtil.getSystemTimeDay(DateUtil.subDay(DateUtil.getCurrentDate()));
        List<WmStable> stables = wmStableAccountservice.findByEndDate(date);
        if (stables != null) {
            for (WmStable stable : stables) {
                try {
                    wmStableAccountservice.updateFullStandardState(stable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 稳进宝发送短信提醒给管理员
     */
    public void sendRemindMessage() throws Exception {
        //查询3天后即将到期并且未设置利率的的基金产品
        Long threeDaysLater = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + 24 * 3600 * 3;
        List<WmStable> stables = wmStableAccountservice.findByCloseDateAndNoApr(threeDaysLater);
        if (stables != null) {
            for (WmStable stable : stables) {
                try {
                    infoSmsTimerService.sendWmInfoSmsTimer("stable_setapr", "15246787602", "name:" + stable.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 更新到期状态
     *
     * @throws Exception
     */
    public void updateDueToState() throws Exception {
        //系统当前日期
        Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        List<WmStable> stables = wmStableAccountservice.findByCloseDate(curDate);
        if (stables != null && !stables.isEmpty()) {
            for (WmStable stable : stables) {
                if (stable != null && stable.getApr() != null) {
                    try {
                        wmStableAccountservice.updateStable(stable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public IWmCurrentUserAccountService getWmCurrentUserAccountService() {
        return wmCurrentUserAccountService;
    }

    public void setWmCurrentUserAccountService(IWmCurrentUserAccountService wmCurrentUserAccountService) {
        this.wmCurrentUserAccountService = wmCurrentUserAccountService;
    }

    public IWmStableAccountservice getWmStableAccountservice() {
        return wmStableAccountservice;
    }

    public void setWmStableAccountservice(IWmStableAccountservice wmStableAccountservice) {
        this.wmStableAccountservice = wmStableAccountservice;
    }

    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }
}
