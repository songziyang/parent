package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.filter.SearchFilter;

import java.util.List;

/**
 * Created by sy on 2016/6/6.
 */
public class UserIntegralCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long integralId;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {
        return filters;
    }

    public Long getIntegralId() {
        return integralId;
    }

    public void setIntegralId(Long integralId) {
        this.integralId = integralId;
    }
}
