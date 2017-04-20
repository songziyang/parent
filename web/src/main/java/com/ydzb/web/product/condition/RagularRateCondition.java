package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.product.entity.RagularRate;

import java.util.List;

/**
 * 定存宝利率查询条件
 */
public class RagularRateCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status",
                SearchOperator.eq, RagularRate.STATUS_INUSE));

        return filters;
    }
}