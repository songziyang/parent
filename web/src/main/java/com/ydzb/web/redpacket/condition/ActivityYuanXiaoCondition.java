package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 红包-加息券查询设置
 *
 * @author sy
 */
public class ActivityYuanXiaoCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Integer numsStart;

    @Expose
    private Integer numsEnd;

    @Expose
    private Integer scoresStart;

    @Expose
    private Integer scoresEnd;

    /**
     * 拼查询sql
     *
     * @return
     */
    public List<SearchFilter> getAndFilters() {
        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.like, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.like, mobile));
        }

        if (numsStart != null) {
            filters.add(SearchFilterHelper.newCondition("nums", SearchOperator.gte, numsStart));
        }

        if (numsEnd != null) {
            filters.add(SearchFilterHelper.newCondition("nums", SearchOperator.lte, numsEnd));
        }

        if (scoresStart != null) {
            filters.add(SearchFilterHelper.newCondition("scores", SearchOperator.gte, scoresStart));
        }

        if (scoresEnd != null) {
            filters.add(SearchFilterHelper.newCondition("scores", SearchOperator.lte, scoresEnd));
        }


        return filters;
    }


    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchFilter> filters) {
        this.filters = filters;
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

    public Integer getNumsStart() {
        return numsStart;
    }

    public void setNumsStart(Integer numsStart) {
        this.numsStart = numsStart;
    }

    public Integer getNumsEnd() {
        return numsEnd;
    }

    public void setNumsEnd(Integer numsEnd) {
        this.numsEnd = numsEnd;
    }

    public Integer getScoresStart() {
        return scoresStart;
    }

    public void setScoresStart(Integer scoresStart) {
        this.scoresStart = scoresStart;
    }

    public Integer getScoresEnd() {
        return scoresEnd;
    }

    public void setScoresEnd(Integer scoresEnd) {
        this.scoresEnd = scoresEnd;
    }
}