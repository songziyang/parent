package com.ydzb.web.withdraw.condition;

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

public class WithDrawCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String realname; // 真实姓名
    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String applicationTimeStart; // 申请时间开始
    @Expose
    private String applicationTimeEnd;// 申请时间结束
    @Expose
    private Integer status; // 状态
    @Expose
    private Integer orderNumber;
    @Expose
    private String orderSort;

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("drawAuto", SearchOperator.ne, 1),
                SearchFilterHelper.newCondition("drawAuto", SearchOperator.isNull, null)));

        filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("withdrawtype", SearchOperator.ne, 1),
                SearchFilterHelper.newCondition("withdrawtype", SearchOperator.isNull, null)));

        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        } else {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 0));
        }


        if (!StringUtils.isEmpty(realname)) {
            filters.add(SearchFilterHelper.newCondition("realname", SearchOperator.eq, realname));
        }


        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }


        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(applicationTimeStart)) {
            filters.add(SearchFilterHelper.newCondition("applicationTime", SearchOperator.gte,
                    DateUtil.getSystemTimeDay(this.applicationTimeStart)));
        }

        if (!StringUtils.isEmpty(applicationTimeEnd)) {
            filters.add(SearchFilterHelper.newCondition("applicationTime", SearchOperator.lt,
                    (DateUtil.getSystemTimeDay(this.applicationTimeEnd) + 24 * 3600)));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filters = new HashMap<String, Object>();
        if (status != null) {
            filters.put("status", status);
        } else {
            filters.put("status", 0);
        }

        if (!StringUtils.isEmpty(realname)) {
            filters.put("realname", realname);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.put("mobile", mobile);
        }

        if (!StringUtils.isEmpty(username)) {
            filters.put("username", username);
        }

        if (!StringUtils.isEmpty(applicationTimeStart)) {
            filters.put("applicationTimeStart", DateUtil.getSystemTimeDay(this.applicationTimeStart));
        }

        if (!StringUtils.isEmpty(applicationTimeEnd)) {
            filters.put("applicationTimeEnd", (DateUtil.getSystemTimeDay(this.applicationTimeEnd) + 24 * 3600));
        }
        return filters;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchFilter> filters) {
        this.filters = filters;
    }

    public String getApplicationTimeStart() {
        return applicationTimeStart;
    }

    public void setApplicationTimeStart(String applicationTimeStart) {
        this.applicationTimeStart = applicationTimeStart;
    }

    public String getApplicationTimeEnd() {
        return applicationTimeEnd;
    }

    public void setApplicationTimeEnd(String applicationTimeEnd) {
        this.applicationTimeEnd = applicationTimeEnd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }
}
