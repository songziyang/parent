package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class RagularUserAccountCondition {

    private List<SearchFilter> filters = Lists.newArrayList();
    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose Integer days;


    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("user.status", SearchOperator.ne, -1));


        if (days != null) {
            filters.add(SearchFilterHelper.newCondition("days", SearchOperator.eq, days));
        }
/*
        filters.add(SearchFilterHelper.or(
                SearchFilterHelper.newCondition("allFund", SearchOperator.gt, 0),
                SearchFilterHelper.newCondition("expFund", SearchOperator.gt, 0)
        ));*/

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username",
                    SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile",
                    SearchOperator.like, mobile));
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
}
