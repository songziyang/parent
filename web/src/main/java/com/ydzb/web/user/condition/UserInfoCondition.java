package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import java.util.List;

/**
 * Created by CRF on 2017/3/7 0007.
 */
public class UserInfoCondition {
    private List<SearchFilter> filters = Lists.newArrayList();

    //风险类型
    @Expose
    private Integer signnType;

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("signn", SearchOperator.gt, 0));

        if (signnType != null) {
            if (signnType == 1) {
                SearchFilter filter1 = SearchFilterHelper.newCondition("signn", SearchOperator.gte, 8);
                SearchFilter filter2 = SearchFilterHelper.newCondition("signn", SearchOperator.lte, 15);
                filters.add(SearchFilterHelper.and(filter1, filter2));
            }
            if (signnType == 2) {
                SearchFilter filter1 = SearchFilterHelper.newCondition("signn", SearchOperator.gte, 16);
                SearchFilter filter2 = SearchFilterHelper.newCondition("signn", SearchOperator.lte, 30);
                filters.add(SearchFilterHelper.and(filter1, filter2));
            }
            if (signnType == 3) {
                SearchFilter filter1 = SearchFilterHelper.newCondition("signn", SearchOperator.gte, 31);
                SearchFilter filter2 = SearchFilterHelper.newCondition("signn", SearchOperator.lte, 36);
                filters.add(SearchFilterHelper.and(filter1, filter2));
            }
        }

        return filters;
    }

    public Integer getSignnType() {
        return signnType;
    }

    public void setSignnType(Integer signnType) {
        this.signnType = signnType;
    }
}
