package com.ydzb.web.message.condition;

import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.List;


/**
 * 短信群发记录分页/查询设置
 */
public class MassDealCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long massId;//所属群发模板id

    /**
     * 获得过滤条件
     *
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        //根据模板Id查询
        if (massId != null) {
            filters.add(SearchFilterHelper.newCondition("massId", SearchOperator.eq, massId));
        }
        return filters;
    }

    public Long getMassId() {
        return massId;
    }

    public void setMassId(Long massId) {
        this.massId = massId;
    }
}