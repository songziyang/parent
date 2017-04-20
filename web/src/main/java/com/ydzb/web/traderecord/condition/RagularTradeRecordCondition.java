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


public class RagularTradeRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String startDate;
    @Expose
    private String endDate;
    @Expose
    private Integer type;
    @Expose
    private Integer days;

    public List<SearchFilter> getAndFilters() {


        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (!StringUtils.isEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.lt, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }

        if (type != null) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (days != null) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.eq, days));
        }


        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (!StringUtils.isEmpty(startDate)) {
            filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(endDate) + 24 * 3600);
        }

        if (type != null) {
            filter.put("type", type);
        }

        if (days != null) {
            filter.put("days", days);
        }
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
