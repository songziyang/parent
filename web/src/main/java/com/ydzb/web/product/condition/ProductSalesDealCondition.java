package com.ydzb.web.product.condition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 产品售出详细查询设置
 */
public class ProductSalesDealCondition {

    private List<SearchFilter> filters = Lists.newArrayList();

    @Expose
    private String username;

    @Expose
    private String mobile;

    @Expose
    private Long buyTime;

    @Expose
    private Long productId;

    public List<SearchFilter> getAndFilters() {

        Date date = DateUtil.getSystemTimeMillisecond(buyTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        filters.add(SearchFilterHelper.newCondition("buyCopies", SearchOperator.gt,
                0));

        if (buyTime != null) {
            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.gte,
                    buyTime));

            filters.add(SearchFilterHelper.newCondition("buyTime", SearchOperator.lt,
                   buyTime + 24 * 3600));
        }

        if (productId != null) {
            filters.add(SearchFilterHelper.newCondition("productInfo.id",
                    SearchOperator.eq, productId));
        }

        if (StringUtils.isNotEmpty(username)) {
            filters.add(SearchFilterHelper.newCondition("user.username",
                    SearchOperator.like, username));
        }

        if (StringUtils.isNotEmpty(mobile)) {
            filters.add(SearchFilterHelper.newCondition("user.mobile",
                    SearchOperator.like, mobile));
        }

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

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
