package com.ydzb.web.user.condition;

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

public class ViDivestmentUserCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String startDate;
    @Expose
    private String endDate;


    public List<SearchFilter> getAndFilters() {

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("applicationTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("applicationTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }


        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();

        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", startDate);
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", endDate);
        }

        return filter;
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


}
