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

/**
 * 随心存产品记录查询条件
 */
public class FreeUserAccountCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String status;
    @Expose
    private String startDate;
    @Expose
    private String endDate;
    @Expose
    private String startDays;
    @Expose
    private String endDays;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(status)) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        }

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("expireTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("expireTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        if (StringUtils.isNotEmpty(startDays)) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.gte, startDays));
        }

        if (StringUtils.isNotEmpty(endDays)) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.lt, endDays));
        }
        return filters;
    }


    public Map<String, Object> getSqlFilter() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("username", username);
        filter.put("mobile", mobile);
        filter.put("status", status);
        filter.put("startDate", startDate);
        filter.put("endDate", endDate);
        filter.put("startDays", startDays);
        filter.put("endDays", endDays);
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

    public Long getStartTimeLong() {
        if (!StringUtils.isEmpty(startDate)) {
            Long start = DateUtil.getSystemTimeDay(startDate);
            return start;
        }
        return 0L;
    }

    public Long getEndTimeLong() {
        if (!StringUtils.isEmpty(endDate)) {
            Long end = DateUtil.getSystemTimeDay(endDate) + 24 * 3600;
            return end;
        }
        return 0L;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
