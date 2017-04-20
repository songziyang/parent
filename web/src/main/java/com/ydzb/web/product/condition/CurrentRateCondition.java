package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.CurrentRate;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 活期宝利率查询条件
 * @author sy
 */
public class CurrentRateCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte status = CurrentRate.STATUS_INUSE ;
    @Expose
    private String beginDate;
    @Expose
    private String finishDate;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status",
                SearchOperator.eq, status));

        if (StringUtils.isNotEmpty(beginDate)) {
            filters.add(SearchFilterHelper.newCondition("currentDate",
                    SearchOperator.gte, DateUtil.getSystemTimeDay(beginDate)));
        }

        if (StringUtils.isNotEmpty(finishDate)) {
            filters.add(SearchFilterHelper.newCondition("currentDate",
                    SearchOperator.lte, DateUtil.getSystemTimeDay(finishDate)));
        }

        return filters;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
