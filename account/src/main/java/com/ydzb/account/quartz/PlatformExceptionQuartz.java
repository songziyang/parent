package com.ydzb.account.quartz;


import com.ydzb.account.entity.WmUserFundBlokedLog;
import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserFundOutLog;
import com.ydzb.account.service.IWmPlatformExceptionService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 平台交易记录异常检测
 */
public class PlatformExceptionQuartz {

    //入账类型
    private List<Integer> inLst = Arrays.asList(new Integer[]{1, 9, 12, 17, 33});

    //出账类型
    private List<Integer> outLst = Arrays.asList(new Integer[]{1, 2, 3, 8, 12});

    //冻结类型
    private List<Integer> blokedLst = Arrays.asList(new Integer[]{1, 2, 3});


    @Autowired
    private IWmPlatformExceptionService platformExceptionService;

    public void accountJob() {

        try {
            //入账类型异常检测
            accountWmUserFundInLogJob();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //出账类型异常检测
            accountWmUserFundOutLogJob();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //冻结类型异常检测
            accountWmUserFundBlokedLogJob();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 入账类型异常检测
     *
     * @throws Exception
     */
    private void accountWmUserFundInLogJob() throws Exception {
        //入账类型
        for (Integer inType : inLst) {
            List<WmUserFundInLog> userFundInLogList = platformExceptionService.findWmUserFundInLogByType(inType);
            if (userFundInLogList != null && !userFundInLogList.isEmpty()) {
                for (WmUserFundInLog wmUserFundInLog : userFundInLogList) {
                    try {
                        platformExceptionService.account(wmUserFundInLog.getUserId(), wmUserFundInLog.getLinkId(), "i" + wmUserFundInLog.getType(), DateUtil.getSystemTimeSeconds());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 出账类型异常检测
     *
     * @throws Exception
     */
    private void accountWmUserFundOutLogJob() throws Exception {
        //出账类型
        for (Integer outType : outLst) {
            List<WmUserFundOutLog> userFundOutLogList = platformExceptionService.findWmUserFundOutLogByType(outType);
            if (userFundOutLogList != null && !userFundOutLogList.isEmpty()) {
                for (WmUserFundOutLog wmUserFundOutLog : userFundOutLogList) {
                    try {
                        platformExceptionService.account(wmUserFundOutLog.getUserId(), wmUserFundOutLog.getLinkId(), "o" + wmUserFundOutLog.getType(), DateUtil.getSystemTimeSeconds());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * 冻结类型异常检测
     */
    private void accountWmUserFundBlokedLogJob() throws Exception {
        //冻结类型
        for (Integer blokedType : blokedLst) {
            List<WmUserFundBlokedLog> wmUserFundBlokedLogList = platformExceptionService.findWmUserFundBlokedLogByType(blokedType);
            if (wmUserFundBlokedLogList != null && !wmUserFundBlokedLogList.isEmpty()) {
                for (WmUserFundBlokedLog wmUserFundBlokedLog : wmUserFundBlokedLogList) {
                    try {
                        platformExceptionService.account(wmUserFundBlokedLog.getUserId(), wmUserFundBlokedLog.getLinkId(), "b" + wmUserFundBlokedLog.getLinkType(), DateUtil.getSystemTimeSeconds());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public IWmPlatformExceptionService getPlatformExceptionService() {
        return platformExceptionService;
    }

    public void setPlatformExceptionService(IWmPlatformExceptionService platformExceptionService) {
        this.platformExceptionService = platformExceptionService;
    }

}
