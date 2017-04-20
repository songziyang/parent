package com.ydzb.web.redpacket.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.RedPacketInterest;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 红包-加息券查询设置
 * @author sy
 */
public class RedPacketInterestCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte status = RedPacketInterest.STATE_INUSE;    //状态，默认是使用中
    @Expose
    private String name;
    @Expose
    private String activityBeginDate;
    @Expose
    private String activityFinishDate;

    /**
     * 拼查询sql
     * @return
     */
    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status",
                SearchOperator.eq, status));

        if (StringUtils.isNotEmpty(name)) {
            filters.add(SearchFilterHelper.newCondition("name",
                SearchOperator.like, name));
        }

        if (StringUtils.isNotEmpty(activityBeginDate)) {
            filters.add(SearchFilterHelper.newCondition("activityBeginTime",
                    SearchOperator.lte, DateUtil.getSystemTimeDay(activityBeginDate)));
        }

        if (StringUtils.isNotEmpty(activityFinishDate)) {
            filters.add(SearchFilterHelper.newCondition("activityFinishTime",
                    SearchOperator.gte, DateUtil.getSystemTimeDay(activityFinishDate)));
        }

        return filters;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityBeginDate() {
        return activityBeginDate;
    }

    public void setActivityBeginDate(String activityBeginDate) {
        this.activityBeginDate = activityBeginDate;
    }

    public String getActivityFinishDate() {
        return activityFinishDate;
    }

    public void setActivityFinishDate(String activityFinishDate) {
        this.activityFinishDate = activityFinishDate;
    }
}