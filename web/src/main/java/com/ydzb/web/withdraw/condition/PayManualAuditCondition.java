package com.ydzb.web.withdraw.condition;

import com.google.common.collect.Lists;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.withdraw.entity.PayManualRecord;

import java.util.List;

/**
 * 代付手动审核查询设置
 */
public class PayManualAuditCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, PayManualRecord.UNDER_AUDIT));

        return filters;
    }
}
