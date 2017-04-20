package com.ydzb.web.activity.condition;

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


public class SilverExchangeThirtyStatisticsCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String startTime;
    @Expose
    private String endTime;

    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("statisticsDate", SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("statisticsDate", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));

        }
        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<>();

        filter.put("startTime", startTime);
        filter.put("endTime", endTime);
        return filter;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}