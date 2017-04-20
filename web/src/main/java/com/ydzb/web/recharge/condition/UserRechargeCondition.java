package com.ydzb.web.recharge.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRechargeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private String orderNumber;

    //充值类型 1、电子账户充值 0/其他 、银多账户充值
    @Expose
    private Integer rechargetype;



    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("rechargeTime", SearchOperator.isNotNull, ""));

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (!StringUtils.isEmpty(orderNumber)) {
            filters.add(SearchFilterHelper.newCondition("orderNumber", SearchOperator.like, orderNumber));
        }

        if (null != rechargetype) {
            if (rechargetype == 1) {
                filters.add(SearchFilterHelper.newCondition("rechargetype", SearchOperator.eq, rechargetype));
            }
            else {
                filters.add(SearchFilterHelper.newCondition("rechargetype", SearchOperator.ne, 1));
            }
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(username)) {
            filter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filter.put("mobile", mobile);
        }

        if (!StringUtils.isEmpty(orderNumber)) {
            filter.put("orderNumber", orderNumber);
        }

        if (null != rechargetype) {
            filter.put("rechargetype", rechargetype);
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Integer getRechargetype() {
        return rechargetype;
    }

    public void setRechargetype(Integer rechargetype) {
        this.rechargetype = rechargetype;
    }

}
