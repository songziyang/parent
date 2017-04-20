package com.ydzb.web.traderecord.condition;

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


public class FreeTradeRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String startDays;
    @Expose
    private String endDays;
    @Expose
    private String startDate;
    @Expose
    private String endDate;
    @Expose
    private String type;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (StringUtils.isNotEmpty(startDays)) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.gte, startDays));
        }

        if (StringUtils.isNotEmpty(endDays)) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.lt, endDays));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        if (StringUtils.isNotEmpty(type)) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("mobile", mobile);
        map.put("startDays", startDays);
        map.put("endDays", endDays);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("type", type);
        return map;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}