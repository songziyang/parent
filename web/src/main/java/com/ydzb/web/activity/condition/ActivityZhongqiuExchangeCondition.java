package com.ydzb.web.activity.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityZhongqiuExchangeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Integer type;

    @Expose
    private Integer status;

    public List<SearchFilter> getAndFilters() {

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (type != null) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        } else {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 1));
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

        if (status != null) {
            filter.put("status", status);
        } else {
            filter.put("status", 1);
        }

        if (type != null) {
            filter.put("type", type);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}