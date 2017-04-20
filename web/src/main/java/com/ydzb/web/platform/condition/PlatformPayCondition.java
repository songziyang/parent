package com.ydzb.web.platform.condition;

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
 * 平台支出统计查询设置
 * @author sy
 */
public class PlatformPayCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte type;

    @Expose
    private String startTime;

    @Expose
    private String endTime;

    public List<SearchFilter> getAndFilters() {

        if (type != null && type > 0) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(SearchFilterHelper.newCondition("operationTime", SearchOperator.gte, DateUtil.getSystemTimeDay(startTime)));
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(SearchFilterHelper.newCondition("operationTime", SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endTime))));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filters = new HashMap<String, Object>();

        if (type != null && type > 0) {
            filters.put("type", type);
        }

        if (StringUtils.isNotEmpty(startTime)) {
            filters.put("startTime", DateUtil.getSystemTimeDay(startTime));
        }

        if (StringUtils.isNotEmpty(endTime)) {
            filters.put("endTime",  DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)));
        }

        return filters;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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