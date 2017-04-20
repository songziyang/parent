package com.ydzb.web.platform.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by sy on 2016/9/20.
 */
public class PlatformFundRecordCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Integer type;
    @Expose
    private Integer linkType;
    @Expose
    private String startTime;
    @Expose
    private String endTime;

    public List<SearchFilter> getAndFilters() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (type != null && type.compareTo(-1) != 0) {
            filters.add(SearchFilterHelper.newCondition("type", SearchOperator.eq, type));
        }

        if (linkType != null && linkType.compareTo(-1) != 0) {
            filters.add(SearchFilterHelper.newCondition("linkType", SearchOperator.eq, linkType));
        }

        if (StringUtils.isNotEmpty(startTime)) {
            try {
                filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.gte, formatter.parse(startTime).getTime() / 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotEmpty(endTime)) {
            try {
                filters.add(SearchFilterHelper.newCondition("optime", SearchOperator.lt, formatter.parse(endTime).getTime() / 1000 + 60));//下一分钟为 endTime + 60秒
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filters;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
