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

/**
 * vip变更查询条件
 */
public class UserVipChangeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;    //用户名
    @Expose
    private String mobile;  //手机号
    @Expose
    private String cType;   //改变类型
    @Expose
    private String oType;   //操作类型
    @Expose
    private String startDate;
    @Expose
    private String endDate;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (StringUtils.isNotEmpty(cType)) {
            filters.add(SearchFilterHelper.newCondition("cType", SearchOperator.eq, Integer.valueOf(cType)));
        }

        if (StringUtils.isNotEmpty(oType)) {
            filters.add(SearchFilterHelper.newCondition("oType", SearchOperator.eq, Integer.valueOf(oType)));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }
        return filters;
    }

    public Map<String, Object> getSqlFilters() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("username", username);
        filters.put("mobile", mobile);
        filters.put("cType", cType);
        filters.put("oType", oType);
        filters.put("startDate", StringUtils.isEmpty(startDate)? null: DateUtil.getSystemTimeDay(startDate));
        filters.put("endDate", StringUtils.isEmpty(endDate)? null: DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
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

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
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

    public String getoType() {
        return oType;
    }

    public void setoType(String oType) {
        this.oType = oType;
    }
}