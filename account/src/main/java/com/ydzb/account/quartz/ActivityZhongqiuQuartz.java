package com.ydzb.account.quartz;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.service.IWmActivityZhongqiuService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 桂花兑换积分
 */
public class ActivityZhongqiuQuartz {

    @Autowired
    private IWmActivityZhongqiuService activityZhongqiuService;

    /**
     * 结算任务
     */
    public void accountJob() {
        // 桂花最大ID和最小ID
        IDRange idRange = activityZhongqiuService.findMaxIdAndMinId();
        // 判断是否有桂花兑换
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            // 根据ID遍历用户的桂花
            for (long id = idRange.getMinId(); id <= idRange.getMaxId(); id++) {
                try {
                    activityZhongqiuService.sendIntegral(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public IWmActivityZhongqiuService getActivityZhongqiuService() {
        return activityZhongqiuService;
    }

    public void setActivityZhongqiuService(IWmActivityZhongqiuService activityZhongqiuService) {
        this.activityZhongqiuService = activityZhongqiuService;
    }
}