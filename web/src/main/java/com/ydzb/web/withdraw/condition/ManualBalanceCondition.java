package com.ydzb.web.withdraw.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.filter.SearchFilter;

import java.util.List;

public class ManualBalanceCondition {

    private List<SearchFilter> filters = Lists.newArrayList();


    public List<SearchFilter> getAndFilters() {

        return filters;
    }


}
