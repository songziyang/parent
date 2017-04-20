package com.ydzb.web.traderecord.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CurrentOverLogCondition {

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
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (!StringUtils.isEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("redemptionTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("redemptionTime", SearchOperator.lte, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }

        return filters;
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

    @Override
    public String toString() {

        return "condtion";
    }
}
