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
 * 现金红包查询条件
 * @author sy
 */
public class RedPacketCashCondition {
    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private Byte status = RedPacketInterest.STATE_INUSE;    //状态，默认是使用中
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
            filters.add(SearchFilterHelper.newCondition("beginTime",
                    SearchOperator.lte, DateUtil.getSystemTimeDay(beginDate)));
        }

        if (StringUtils.isNotEmpty(finishDate)) {
            filters.add(SearchFilterHelper.newCondition("finishTime",
                    SearchOperator.gte, DateUtil.getSystemTimeDay(finishDate)));
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
