package com.ydzb.web.withdraw.condition;

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
 * 用户提现次数查询设置
 */
public class WithDrawNumCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Integer countNum;

    @Expose
    private Integer orderNumber;

    @Expose
    private String orderSort;

    @Expose
    private Integer startWithDrawNum;

    @Expose
    private Integer endWithDrawNum;

    @Expose
    private Integer startRechargeNum;

    @Expose
    private Integer endRechargeNum;

    @Expose
    private Integer status;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }


        if (startWithDrawNum != null && endWithDrawNum != null) {
            filters.add(SearchFilterHelper.newCondition("countNum", SearchOperator.gte, startWithDrawNum));
            filters.add(SearchFilterHelper.newCondition("countNum", SearchOperator.lte, endWithDrawNum));
        }

        if (startRechargeNum != null && endRechargeNum != null) {
            filters.add(SearchFilterHelper.newCondition("rechargeNum", SearchOperator.gte, startRechargeNum));
            filters.add(SearchFilterHelper.newCondition("rechargeNum", SearchOperator.lte, endRechargeNum));
        }


        if (status != null) {

            if (status == 1) {
                filters.add(SearchFilterHelper.newCondition("user.remark", SearchOperator.isNotNull, ""));
            }

            if (status == 2) {
                filters.add(SearchFilterHelper.newCondition("user.remark", SearchOperator.isNull, ""));
            }
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

        if (StringUtils.isNotEmpty(orderSort)) {
            filter.put("orderSort", orderSort);
        }

        if (orderNumber != null) {
            filter.put("orderNumber", orderNumber);
        }

        if (startWithDrawNum != null && endWithDrawNum != null) {
            filter.put("startWithDrawNum", startWithDrawNum);
            filter.put("endWithDrawNum", endWithDrawNum);
        }

        if (startRechargeNum != null && endRechargeNum != null) {
            filter.put("startRechargeNum", startRechargeNum);
            filter.put("endRechargeNum", endRechargeNum);
        }

        if (status != null) {
            filter.put("redname", status);
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

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStartWithDrawNum() {
        return startWithDrawNum;
    }

    public void setStartWithDrawNum(Integer startWithDrawNum) {
        this.startWithDrawNum = startWithDrawNum;
    }

    public Integer getEndWithDrawNum() {
        return endWithDrawNum;
    }

    public void setEndWithDrawNum(Integer endWithDrawNum) {
        this.endWithDrawNum = endWithDrawNum;
    }

    public Integer getStartRechargeNum() {
        return startRechargeNum;
    }

    public void setStartRechargeNum(Integer startRechargeNum) {
        this.startRechargeNum = startRechargeNum;
    }

    public Integer getEndRechargeNum() {
        return endRechargeNum;
    }

    public void setEndRechargeNum(Integer endRechargeNum) {
        this.endRechargeNum = endRechargeNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
