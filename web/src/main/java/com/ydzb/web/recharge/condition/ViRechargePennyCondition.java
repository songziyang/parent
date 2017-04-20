package com.ydzb.web.recharge.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小额充值查询条件
 */
public class ViRechargePennyCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String startMoney;
    @Expose
    private String endMoney;
    @Expose
    private String startDays;
    @Expose
    private String endDays;

    @Expose
    private Integer rechargetype;
    @Expose
    private Integer onlines;

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

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (StringUtils.isNotEmpty(startMoney)) {
            filters.add(SearchFilterHelper.newCondition("money", SearchOperator.gte, startMoney));
        }

        if (StringUtils.isNotEmpty(endMoney)) {
            filters.add(SearchFilterHelper.newCondition("money", SearchOperator.lte, endMoney));
        }

        if (StringUtils.isNotEmpty(startDays)) {
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDays)));
        }

        if (StringUtils.isNotEmpty(endDays)) {
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDays))));
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

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();
        filter.put("username", username);
        filter.put("mobile", mobile);
        filter.put("startMoney", startMoney);
        filter.put("endMoney", endMoney);
        filter.put("startDays", startDays);
        filter.put("endDays", endDays);
        filter.put("rechargetype", rechargetype);
        filter.put("onlines", onlines);
        return filter;
    }

    public Long getStartTime() {
        return StringUtils.isEmpty(startDays)? null: DateUtil.getSystemTimeDay(startDays);
    }

    public Long getEndTime() {
        return StringUtils.isEmpty(endDays)? null: DateUtil.getSystemTimeDay(DateUtil.addDay(endDays));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(String startMoney) {
        this.startMoney = startMoney;
    }

    public String getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(String endMoney) {
        this.endMoney = endMoney;
    }

    public String getStartDays() {
        return startDays;
    }

    public void setStartDays(String startDays) {
        this.startDays = startDays;
    }

    public String getEndDays() {
        return endDays;
    }

    public void setEndDays(String endDays) {
        this.endDays = endDays;
    }
}
