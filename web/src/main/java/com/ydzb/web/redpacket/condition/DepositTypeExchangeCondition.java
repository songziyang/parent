package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DepositTypeExchangeCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Integer actype;

    @Expose
    private Integer ptype;

    @Expose
    private Integer type;

    @Expose
    private Integer status;

    @Expose
    private String startDate;

    @Expose
    private String endDate;

    @Expose
    private BigDecimal startFund;

    @Expose
    private BigDecimal endFund;


    public List<SearchFilter> getAndFilters() {


        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        } else {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 1));
        }


        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (actype != null) {
            filters.add(SearchFilterHelper.newCondition("actype", SearchOperator.eq, actype));
        }

        if (ptype != null) {
            filters.add(SearchFilterHelper.newCondition("ptype", SearchOperator.eq, ptype));
        }

        if (type != null) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }


        if (startFund != null) {
            filters.add(SearchFilterHelper.newCondition("fund", SearchOperator.gte, startFund));
        }

        if (endFund != null) {
            filters.add(SearchFilterHelper.newCondition("fund", SearchOperator.lte, endFund));
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

        if (status != null) {
            filter.put("status", status);
        } else {
            filter.put("status", 1);
        }

        if (actype != null) {
            filter.put("actype", actype);
        }

        if (ptype != null) {
            filter.put("ptype", ptype);
        }

        if (type != null) {
            filter.put("type", type);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", startDate);
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", endDate);
        }


        if (startFund != null) {
            filter.put("startFund", startFund);
        }

        if (endFund != null) {
            filter.put("endFund", endFund);
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

    public Integer getActype() {
        return actype;
    }

    public void setActype(Integer actype) {
        this.actype = actype;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public BigDecimal getStartFund() {
        return startFund;
    }

    public void setStartFund(BigDecimal startFund) {
        this.startFund = startFund;
    }

    public BigDecimal getEndFund() {
        return endFund;
    }

    public void setEndFund(BigDecimal endFund) {
        this.endFund = endFund;
    }
}