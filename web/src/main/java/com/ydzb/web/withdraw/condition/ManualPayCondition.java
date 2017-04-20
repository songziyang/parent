package com.ydzb.web.withdraw.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;

import java.util.List;

public class ManualPayCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long linkId;

    @Expose
    private Integer type;

    public List<SearchFilter> getAndFilters() {

        if (linkId != null) {
            filters.add(SearchFilterHelper.newCondition("linkId", SearchOperator.eq, linkId));
        }

        if (type != null) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }


        return filters;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
