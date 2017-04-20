package com.ydzb.web.platform.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 平台支出统计查询设置
 *
 * @author sy
 */
public class PlatformAprCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String startTime;    //开始时间
    @Expose
    private String endTime;    //结束时间


    public List<SearchFilter> getAndFilters() {

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("created", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));
        }

        return filters;
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