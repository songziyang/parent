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
 * 稳进宝我交易记录分页配置
 */
public class StructureDealCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long structureId;        //稳进宝id
    @Expose
    private String username;    //用户名
    @Expose
    private String mobile;        //用户手机号
    @Expose
    private String startDate;
    @Expose
    private String endDate;

    /**
     * 获得查询语句过滤条件
     *
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        //根据稳进宝id过滤
        if (structureId != null && !"".equals(structureId)) {
            filters.add(SearchFilterHelper.newCondition("structure.id", SearchOperator.eq, structureId));
        }

        //根据用户名过滤
        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        //根据手机号
        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (!StringUtils.isEmpty(startDate)) {

            filters.add(SearchFilterHelper.newCondition("closeDate", SearchOperator.gte, DateUtil.getSystemTimeDay(startDate)));
        }

        if (!StringUtils.isEmpty(endDate)) {
            filters.add(SearchFilterHelper.newCondition("closeDate", SearchOperator.lt, DateUtil.getSystemTimeDay(endDate) + 24 * 3600));
        }


        return filters;
    }

    /**
     * 获得查询语句过滤条件
     *
     * @return
     */
    public Map<String, Object> getSqlFilters() {
        Map<String, Object> sqlFilter = new HashMap<>();
        sqlFilter.put("structureId", structureId);

        if (!StringUtils.isEmpty(username)) {
            sqlFilter.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            sqlFilter.put("mobile", mobile);
        }

        if (!StringUtils.isEmpty(startDate)) {
            sqlFilter.put("startDate", DateUtil.getSystemTimeDay(startDate));
        }

        if (!StringUtils.isEmpty(endDate)) {
            sqlFilter.put("endDate", DateUtil.getSystemTimeDay(endDate) + 24 * 3600);
        }

        return sqlFilter;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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