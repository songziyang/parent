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


/**
 * 产品销售统计数据
 */
public class ProductSalesRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Integer status;

    @Expose
    private String startDate;

    @Expose
    private String endDate;


    public List<SearchFilter> getAndFilters() {

        if (status != null) {
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("releaseTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("releaseTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        return filters;
    }


    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();

        if (status != null) {
            filter.put("status", status);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", startDate);
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", endDate);
        }

        return filter;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}