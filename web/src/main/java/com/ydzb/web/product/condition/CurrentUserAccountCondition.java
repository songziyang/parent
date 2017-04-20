package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CurrentUserAccountCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    public List<SearchFilter> getAndFilters() {


        filters.add(SearchFilterHelper.newCondition("user.status", SearchOperator.ne, -1));


        filters.add(SearchFilterHelper.or(
                SearchFilterHelper.newCondition("allFund", SearchOperator.gt, 0),
                SearchFilterHelper.newCondition("expFund", SearchOperator.gt, 0)
        ));

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

    /**
     * 获得sql过滤
     * @return
     */
    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filters = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(username)) {
            filters.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.put("mobile", mobile);
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
