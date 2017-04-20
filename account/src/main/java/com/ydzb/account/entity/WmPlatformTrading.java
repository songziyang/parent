package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_platform_trading")
public class WmPlatformTrading extends BaseEntity<Long> {

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 1、充值 2、提现 3、活期宝 4、定存宝 5、机构宝 6、私人定制
     */
    private Integer type;

    /**
     * 操作日期
     */
    private Long opdate;


    public WmPlatformTrading() {

    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOpdate() {
        return opdate;
    }

    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }
}
