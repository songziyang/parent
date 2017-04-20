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


public class BeautyUserAccountCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private Integer status = 0; //默认未到期
    @Expose
    private String startDate;
    @Expose
    private String endDate;

    public List<SearchFilter> getAndFilters() {

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (status != null && status >= 0) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        }

        if (!StringUtils.isEmpty(startDate)) {

            filters.add(SearchFilterHelper.newCondition("expireTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("expireTime", SearchOperator.lt, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }


        return filters;
    }

    public Map<String, Object> getSqlFilter() {

        Map<String, Object> filter = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (status != null) {
            filter.put("status", status);
        }

        if (!StringUtils.isEmpty(startDate)) {
            filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(endDate) + 24 * 3600);
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}
