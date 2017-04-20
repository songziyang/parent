package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.Integral;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 现金红包查询条件
 * @author sy
 */
public class IntegralCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte status = Integral.STATE_INUSE;    //状态，默认是使用中
    @Expose
    private Integer linkType;
    @Expose
    private String name;
    @Expose
    private String startDate;
    @Expose
    private String endDate;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status",
                SearchOperator.eq, status));

        if (linkType != null) {
            filters.add(SearchFilterHelper.newCondition("linkType",
                    SearchOperator.eq, linkType));
        }

        if (StringUtils.isNotEmpty(name)) {
            filters.add(SearchFilterHelper.newCondition("name",
                    SearchOperator.like, name));
        }

        if (StringUtils.isNotEmpty(startDate)) {
            filters.add(SearchFilterHelper.newCondition("created",
                    SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("created",
                    SearchOperator.lt, DateUtil.getSystemTimeDay(DateUtil.addDay(endDate))));
        }

        return filters;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
