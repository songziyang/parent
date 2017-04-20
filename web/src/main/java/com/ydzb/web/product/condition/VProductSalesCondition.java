package com.ydzb.web.product.condition;

import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品售出查询设置
 */
public class VProductSalesCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long typeId;

    @Expose
    private String startDate;   //开始时间

    @Expose
    private String endDate; //结束时间

    @Expose
    private String name;    //产品名称


    /**
     * 获得查询语句过滤条件
     *
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        if (typeId != null && !"".equals(typeId)) {
            filters.add(SearchFilterHelper.newCondition("productInfo.type.id",
                    SearchOperator.eq, typeId));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("buyDate", SearchOperator.gte,
                    DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("buyDate", SearchOperator.lt,
                    DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        if (StringUtils.isNotEmpty(name)) {
            filters.add(SearchFilterHelper.newCondition("productInfo.name",
                    SearchOperator.like, name));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();
        if (typeId != null && !"".equals(typeId)) {
            filter.put("typeId", typeId);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filter.put("startDate",  DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filter.put("endDate", DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
        }

        if (StringUtils.isNotEmpty(name)) {
            filter.put("name", name);
        }

        return filter;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
