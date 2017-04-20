package com.ydzb.web.withdraw.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PayBackDrawCondition {

    private List<SearchFilter> filters = Lists.newArrayList();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Expose
    private String realname; // 真实姓名
    @Expose
    private String username;
    @Expose
    private String mobile;
    @Expose
    private String applicationTimeStart; // 申请时间开始
    @Expose
    private String applicationTimeEnd;// 申请时间结束
    @Expose
    private Integer status; // 状态 显示打款中状态
    @Expose
    private Integer orderNumber;
    @Expose
    private String orderSort;
    @Expose
    private Boolean poundage;
    @Expose
    private Integer drawAuto;
    @Expose
    private Integer withdrawtype;

    public List<SearchFilter> getAndFilters() throws Exception {

        //当有状态条件时，根据状态查询
        if (status != null) {
            if (status == 3) {

                filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("drawAuto", SearchOperator.ne, 1),
                        SearchFilterHelper.newCondition("drawAuto", SearchOperator.isNull, null)));
            } else {

                if (drawAuto != null) {
                    filters.add(SearchFilterHelper.newCondition("drawAuto", SearchOperator.eq, drawAuto));
                }

                if (withdrawtype != null) {
                    filters.add(SearchFilterHelper.newCondition("withdrawtype", SearchOperator.eq, withdrawtype));
                }
            }

            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, status));

        } else if (poundage != null && poundage) { //当没选择条件并且想要查看手续费时 状态查询放款成功的
            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 4));
        } else {    //其他情况 查询待放款状态

            filters.add(SearchFilterHelper.or(SearchFilterHelper.newCondition("drawAuto", SearchOperator.ne, 1),
                    SearchFilterHelper.newCondition("drawAuto", SearchOperator.isNull, null)));

            filters.add(SearchFilterHelper.newCondition("status", SearchOperator.eq, 3));
        }

        if (!StringUtils.isEmpty(realname)) {
            filters.add(SearchFilterHelper.newCondition("realname", SearchOperator.eq, realname));
        }

        if (!StringUtils.isEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username", SearchOperator.eq, username));
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile", SearchOperator.eq, mobile));
        }

        if (!StringUtils.isEmpty(applicationTimeStart)) {
            filters.add(SearchFilterHelper.newCondition("payTime", SearchOperator.gte,
                    formatter.parse(applicationTimeStart).getTime() / 1000));

        }
        //当时间为null时，证明没通过条件筛选，默认查询今天的数据，而时间为""时，证明通过条件筛选，不查询今天的数据
        else if (applicationTimeStart == null && poundage != null && poundage) {
            filters.add(SearchFilterHelper.newCondition("payTime", SearchOperator.gte,
                    DateUtil.getSystemTimeDay(DateUtil.getCurrentDate())));
        }

        if (!StringUtils.isEmpty(applicationTimeEnd)) {
            filters.add(SearchFilterHelper.newCondition("payTime", SearchOperator.lt,
                    formatter.parse(applicationTimeEnd).getTime() / 1000 + 60));  //截止分钟需要+1分钟

        }
        //当时间为null时，证明没通过条件筛选，默认查询今天的数据，而时间为""时，证明通过条件筛选，不查询今天的数据
        else if (applicationTimeEnd == null && poundage != null && poundage) {
            filters.add(SearchFilterHelper.newCondition("payTime", SearchOperator.lt,
                    DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()))));
        }

        if (poundage != null && poundage) {
            filters.add(SearchFilterHelper.newCondition("fee", SearchOperator.gt, 0));
        }

        return filters;
    }

    /**
     * 获取导出excel的条件
     *
     * @return
     */
    public Map<String, Object> getSqlFilters() {

        Map<String, Object> filters = new HashMap<String, Object>();
        if (status != null) {
            filters.put("status", status);
        }
        //当没选择条件并且想要查看手续费时
        else if (poundage != null && poundage) {
            filters.put("status", 4);
        } else { //其他情况 查询待放款状态
            filters.put("status", 3);
        }

        if (drawAuto != null && status != null && status != 3) {
            filters.put("drawAuto", drawAuto);
        }

        if (withdrawtype != null && status != null && status != 3) {
            filters.put("withdrawtype", withdrawtype);
        }


        if (!StringUtils.isEmpty(realname)) {
            filters.put("realname", realname);
        }

        if (!StringUtils.isEmpty(username)) {
            filters.put("username", username);
        }

        if (!StringUtils.isEmpty(mobile)) {
            filters.put("mobile", mobile);
        }

        if (!StringUtils.isEmpty(applicationTimeStart)) {
            try {
                filters.put("payTimeStart", formatter.parse(applicationTimeStart).getTime() / 1000);
            } catch (Exception e) {
                e.printStackTrace();
                filters.put("payTimeStart", DateUtil.getSystemTimeDay(this.applicationTimeStart));
            }
        }
        //当时间为null时，证明没通过条件筛选，默认查询今天的数据，而时间为""时，证明通过条件筛选，不查询今天的数据
        else if (applicationTimeStart == null && poundage != null && poundage) {
            filters.put("payTimeStart", DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        }

        if (!StringUtils.isEmpty(applicationTimeEnd)) {
            try {
                filters.put("payTimeEnd", formatter.parse(applicationTimeEnd).getTime() / 1000 + 60);//截止分钟需要+1分钟
            } catch (Exception e) {
                e.printStackTrace();
                filters.put("payTimeEnd", (DateUtil.getSystemTimeDay(this.applicationTimeEnd) + 24 * 3600));
            }
        }
        //当时间为null时，证明没通过条件筛选，默认查询今天的数据，而时间为""时，证明通过条件筛选，不查询今天的数据
        else if (applicationTimeEnd == null && poundage != null && poundage) {
            filters.put("payTimeEnd", DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())));
        }

        filters.put("poundage", poundage);

        return filters;
    }

    /**
     * 获取计算提现金额/打款金额的条件
     *
     * @return
     */
    public Map<String, Object> getSumFilters() {
        //默认开始结束时间为条件查询的时间
        Long payTimeStart = null;
        Long payTimeEnd = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<String, Object> filters = new HashMap<String, Object>();
        if (status != null) {
            filters.put("status", status);
        } else {
            filters.put("status", 4); //其余默认状态是4
        }

        if (status != null && drawAuto != null) {
            filters.put("drawAuto", drawAuto);
        }

        if (status != null && withdrawtype != null) {
            filters.put("withdrawtype", withdrawtype);
        }


        filters.put("realname", realname);
        filters.put("username", username);
        filters.put("mobile", mobile);

        /**
         * 时间的4种情况
         * 1.有时间条件，且不为空 -跟时间条件走
         * 2.有时间条件，且为空  -查询全部（跟时间条件走）
         * 3.无时间条件，且查询手续费   -查询今天数据
         * 4.无时间条件，且不查询手续费  -查询今天数据
         */
        if (StringUtils.isNotEmpty(applicationTimeStart)) {
            try {
                payTimeStart = formatter.parse(applicationTimeStart).getTime() / 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (applicationTimeStart == null) {
            payTimeStart = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        }

        if (StringUtils.isNotEmpty(applicationTimeEnd)) {
            try {
                payTimeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(applicationTimeEnd).getTime() / 1000 + 60;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (applicationTimeEnd == null) {
            payTimeEnd = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()));
        }

        filters.put("payTimeStart", payTimeStart);
        filters.put("payTimeEnd", payTimeEnd);
        filters.put("poundage", poundage);
        return filters;
    }

    public String getApplicationTimeStart() {
        return applicationTimeStart;
    }

    public void setApplicationTimeStart(String applicationTimeStart) {
        this.applicationTimeStart = applicationTimeStart;
    }

    public String getApplicationTimeEnd() {
        return applicationTimeEnd;
    }

    public void setApplicationTimeEnd(String applicationTimeEnd) {
        this.applicationTimeEnd = applicationTimeEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public Boolean getPoundage() {
        return poundage;
    }

    public void setPoundage(Boolean poundage) {
        this.poundage = poundage;
    }

    public Integer getDrawAuto() {
        return drawAuto;
    }

    public void setDrawAuto(Integer drawAuto) {
        this.drawAuto = drawAuto;
    }


    public Integer getWithdrawtype() {
        return withdrawtype;
    }

    public void setWithdrawtype(Integer withdrawtype) {
        this.withdrawtype = withdrawtype;
    }
}
