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
public class ViOftenRechargeWithdrawCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String realName;
    @Expose
    private String startRechargeDays;
    @Expose
    private String endRechargeDays;
    @Expose
    private String startWithdrawDays;
    @Expose
    private String endWithdrawDays;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (StringUtils.isNotEmpty(startRechargeDays)) {
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startRechargeDays)));
        }

        if (StringUtils.isNotEmpty(endRechargeDays)) {
            filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endRechargeDays))));
        }

        if (StringUtils.isNotEmpty(startWithdrawDays)) {
            filters.add(SearchFilterHelper.newCondition("withdrawTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startWithdrawDays)));
        }

        if (StringUtils.isNotEmpty(endWithdrawDays)) {
            filters.add(SearchFilterHelper.newCondition("withdrawTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endWithdrawDays))));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();
        filter.put("username", username);
        filter.put("mobile", mobile);
        filter.put("realName", realName);
        filter.put("startRechargeDays", startRechargeDays);
        filter.put("endRechargeDays", endRechargeDays);
        filter.put("startWithdrawDays", startWithdrawDays);
        filter.put("endWithdrawDays", endWithdrawDays);
        return filter;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStartRechargeDays() {
        return startRechargeDays;
    }

    public void setStartRechargeDays(String startRechargeDays) {
        this.startRechargeDays = startRechargeDays;
    }

    public String getEndRechargeDays() {
        return endRechargeDays;
    }

    public void setEndRechargeDays(String endRechargeDays) {
        this.endRechargeDays = endRechargeDays;
    }

    public String getStartWithdrawDays() {
        return startWithdrawDays;
    }

    public void setStartWithdrawDays(String startWithdrawDays) {
        this.startWithdrawDays = startWithdrawDays;
    }

    public String getEndWithdrawDays() {
        return endWithdrawDays;
    }

    public void setEndWithdrawDays(String endWithdrawDays) {
        this.endWithdrawDays = endWithdrawDays;
    }
}
