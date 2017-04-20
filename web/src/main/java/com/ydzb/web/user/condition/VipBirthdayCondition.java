package com.ydzb.web.user.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VipBirthdayCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String birthdayDate;


    public static String getTimeByCalendar() {
        DateFormat dateFormat = new SimpleDateFormat("MMdd");
        return dateFormat.format(new Date());
    }

    public List<SearchFilter> getAndFilters() {

        filters.add(SearchFilterHelper.newCondition("status", SearchOperator.ne, -1));

        filters.add(SearchFilterHelper.newCondition("userLeve.gradeNum", SearchOperator.gt, 0));

        if (!StringUtils.isEmpty(birthdayDate)) {
            filters.add(SearchFilterHelper.newCondition("idCard", SearchOperator.like, birthdayDate));
        } else {
            filters.add(SearchFilterHelper.newCondition("idCard", SearchOperator.like, getTimeByCalendar()));
        }

        return filters;
    }

    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filter = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(birthdayDate)) {
            filter.put("birthdayDate", birthdayDate);
        } else {
            filter.put("birthdayDate", getTimeByCalendar());
        }
        return filter;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
}
