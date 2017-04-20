package com.ydzb.web.recharge.condition;

/**
 * Created by Administrator on 15-10-19.
 */

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户充值详细condition
 */
public class UserRechargeDetailCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    public List<SearchFilter> getAndFilters() {

        /*filters.add(SearchFilterHelper.newCondition("user.status", SearchOperator.eq, 0));*/

        filters.add(SearchFilterHelper.newCondition("allRecharge", SearchOperator.gt, 0));

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();

        if (StringUtils.isNotEmpty(username)) {
            filter.put("username", username);
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filter.put("mobile", mobile);
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
}
