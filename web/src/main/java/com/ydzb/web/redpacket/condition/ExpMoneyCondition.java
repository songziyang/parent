package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.packet.entity.ExpMoney;

import java.util.List;

/**
 * 体验金查询设置
 * @author sy
 */
public class ExpMoneyCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte state = ExpMoney.STATE_INUSE;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("state",
                SearchOperator.eq, state));

        return filters;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}