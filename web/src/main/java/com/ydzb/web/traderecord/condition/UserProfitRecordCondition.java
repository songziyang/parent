package com.ydzb.web.traderecord.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收益记录查询设置
 */
public class UserProfitRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String startDate;

    @Expose
    private String endDate;

    public List<SearchFilter> getAndFilters() {
        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();


        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
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
}