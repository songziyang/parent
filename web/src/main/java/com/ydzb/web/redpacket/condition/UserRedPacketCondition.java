package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 用户红包查询设置
 */
public class UserRedPacketCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Long redpacketId;   //红包id
    @Expose
    private String activityStartDate;
    @Expose
    private String activityFinishDate;
    @Expose
    private Byte redpacketType = 1; //默认是现金红包

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("redpacketType",
                SearchOperator.eq, redpacketType));

        if (redpacketId != null && redpacketId != 0l) {
            filters.add(SearchFilterHelper.newCondition("redpacketId",
                    SearchOperator.eq, redpacketId));
        }

        if (StringUtils.isNotEmpty(activityStartDate)) {
            filters.add(SearchFilterHelper.newCondition("startTime",
                    SearchOperator.gte, DateUtil.getSystemTimeDay(activityStartDate)));
        }

        if (StringUtils.isNotEmpty(activityFinishDate)) {
            filters.add(SearchFilterHelper.newCondition("finishTime",
                    SearchOperator.lte, DateUtil.getSystemTimeDay(activityFinishDate)));
        }

        return filters;
    }

    public Long getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Long redpacketId) {
        this.redpacketId = redpacketId;
    }

    public String getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(String activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public String getActivityFinishDate() {
        return activityFinishDate;
    }

    public void setActivityFinishDate(String activityFinishDate) {
        this.activityFinishDate = activityFinishDate;
    }

    public Byte getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Byte redpacketType) {
        this.redpacketType = redpacketType;
    }
}