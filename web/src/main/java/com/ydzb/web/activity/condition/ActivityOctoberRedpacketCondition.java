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


public class ActivityOctoberRedpacketCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String buyType;
    @Expose
    private String redpacketFund;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (StringUtils.isNotEmpty(buyType)) {
            filters.add(SearchFilterHelper.newCondition("buyType", SearchOperator.eq, buyType));
        }

        if (StringUtils.isNotEmpty(redpacketFund)) {
            filters.add(SearchFilterHelper.newCondition("redpacketFund", SearchOperator.eq, redpacketFund));
        }

        return filters;
    }


    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();
        filter.put("username", username);
        filter.put("mobile", mobile);
        filter.put("buyType", buyType);
        filter.put("redpacketFund", redpacketFund);
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

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getRedpacketFund() {
        return redpacketFund;
    }

    public void setRedpacketFund(String redpacketFund) {
        this.redpacketFund = redpacketFund;
    }
}