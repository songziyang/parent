package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReferralCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    //用户名
    @Expose
    private String username;
    //手机号
    @Expose
    private String mobile;

    @Expose
    private Integer userType;


    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.ne, -1));

        filters.add(SearchFilterHelper.newCondition("referralNum", SearchOperator.gt, 0));


        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("username", SearchOperator.like, username));
        }


        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("mobile", SearchOperator.like, mobile));
        }

        if (userType != null ) {
            filters.add(SearchFilterHelper.newCondition("userType", SearchOperator.eq, userType));
        }

        return filters;
    }
    public Map<String, Object> getMysqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (userType != null ) {
            filter.put("userType", userType);
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
