package com.ydzb.web.recharge.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserRechargeTotalCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String startTime;

    @Expose
    private String endTime;

    //充值类型 1、电子账户充值 0/其他 、银多账户充值
    @Expose
    private Integer rechargetype;

    //充值渠道 1、线上充值 0/其他线下充值
    @Expose
    private Integer onlines;

    public List<SearchFilter> getAndFilters() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 1));
        if (!StringUtils.isEmpty(startTime)) {
            Long lg = 0L;
            try {
                lg = formatter.parse(startTime).getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.gte, lg));
        } else {
            Long lg = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.gte, lg));
        }

        if (!StringUtils.isEmpty(endTime)) {
            Long lt = 0L;
            try {
                lt = formatter.parse(endTime).getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filters.add(SearchFilterHelper.newCondition("rechargeTime",  SearchOperator.lte, lt));
        } else {
            Long lt = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + 24 * 3600;
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.lte, lt));
        }


        if (null != rechargetype) {
            if (rechargetype == 1) {
                filters.add(SearchFilterHelper.newCondition("rechargetype", SearchOperator.eq, rechargetype));
            }
            else {
                filters.add(SearchFilterHelper.newCondition("rechargetype", SearchOperator.ne, 1));
            }
        }

        if (null != onlines) {
            if (onlines == 1) {
                filters.add(SearchFilterHelper.newCondition("onlines", SearchOperator.eq, onlines));
            }
            else {
                filters.add(SearchFilterHelper.newCondition("onlines", SearchOperator.ne, 1));
            }
        }


        return filters;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getRechargetype() {
        return rechargetype;
    }

    public void setRechargetype(Integer rechargetype) {
        this.rechargetype = rechargetype;
    }

    public Integer getOnlines() {
        return onlines;
    }

    public void setOnlines(Integer onlines) {
        this.onlines = onlines;
    }
}
