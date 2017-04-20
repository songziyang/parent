package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;

import java.util.List;


public class UserFundRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long userId;
    @Expose
    private Byte fundType;


    public List<SearchFilter> getAndFilters() {

        if (null != userId && userId > 0) {
            filters.add(SearchFilterHelper.newCondition("userId", SearchOperator.eq, userId));
        }

        if (fundType != null) {
            //充值或者提现
            if (fundType == -1) {
                filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("fundType", SearchOperator.eq, 0),
                        new SearchFilter[]{SearchFilterHelper.newCondition("fundType", SearchOperator.eq, 1)}));
            } else {
                filters.add(SearchFilterHelper.newCondition("fundType", SearchOperator.eq, fundType));
            }
        }

        return filters;
    }

    public void setFilters(List<SearchFilter> filters) {
        this.filters = filters;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getFundType() {
        return fundType;
    }

    public void setFundType(Byte fundType) {
        this.fundType = fundType;
    }
}
