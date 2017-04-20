package com.ydzb.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 利率实体
 */
public class WmAprEntity implements Serializable {

    private BigDecimal apr; //利率

    private BigDecimal dayApr;  //日加息？加息券整个天标？

    private BigDecimal monthApr;    //月加息？加息券单笔天标？

    private BigDecimal vipApr;  //vip加息


    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getDayApr() {
        return dayApr;
    }

    public void setDayApr(BigDecimal dayApr) {
        this.dayApr = dayApr;
    }

    public BigDecimal getMonthApr() {
        return monthApr;
    }

    public void setMonthApr(BigDecimal monthApr) {
        this.monthApr = monthApr;
    }

    public BigDecimal getVipApr() {
        return vipApr;
    }

    public void setVipApr(BigDecimal vipApr) {
        this.vipApr = vipApr;
    }
}
