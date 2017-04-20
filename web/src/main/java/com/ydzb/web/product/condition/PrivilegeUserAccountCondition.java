package com.ydzb.web.product.condition;

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


public class PrivilegeUserAccountCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;
    @Expose
    private String mobile;
   /* @Expose
    private String startDate;
    @Expose
    private String endDate;*/


    public List<SearchFilter> getAndFilters() {


        filters.add(SearchFilterHelper.newCondition("user.status", SearchOperator.ne, -1));

        filters.add(SearchFilterHelper.newCondition("allFund", SearchOperator.gt, 0));

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username",
                    SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile",
                    SearchOperator.like, mobile));
        }

        /*if (!StringUtils.isEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.lte, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }*/

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

        /*if (!StringUtils.isEmpty(startDate)) {
            filters.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.put("endDate", DateUtil.getSystemTimeDay(endDate) + 24 * 3600);
        }*/
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

   /* public String getStartDate() {
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
    }*/
}
