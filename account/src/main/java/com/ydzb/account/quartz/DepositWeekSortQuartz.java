package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmDepositWeekSortService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 结构化产品排行榜
 */
public class DepositWeekSortQuartz {

    @Autowired
    private IWmDepositWeekSortService depositWeekSortService;


    public void accountJob() {
        try {
            depositWeekSortService.accountDepositWeekSort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IWmDepositWeekSortService getDepositWeekSortService() {
        return depositWeekSortService;
    }

    public void setDepositWeekSortService(IWmDepositWeekSortService depositWeekSortService) {
        this.depositWeekSortService = depositWeekSortService;
    }
}