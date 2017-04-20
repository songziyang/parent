package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台交易统计
 * @author sy
 */
@Entity
@Table(name = "wm_platform_trading")
public class PlatformTrading extends BaseEntity<Long> {

    public static final byte RECHARGE = 1;  //充值
    public static final byte WITHDRAW = 2;  //提现
    public static final byte CURRENT = 3;  //活期宝
    public static final byte REGULAR = 4;  //定存宝
    public static final byte INSTITUTION = 5;  //机构宝
    public static final byte PRIVATE_ORDERING = 6;  //私人订制
    public static final byte FREE = 7;  //随心存

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 类型：1-充值  2-提现    3-活期宝   4-定存宝   5-机构宝   6-私人订制
     */
    private Byte type;

    /**
     * 操作日期
     */
    @Column(name = "opdate")
    private Long operationTime;

    @Transient
    private Date operationDate;

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }
    public Date getOperationDate() {
        return DateUtil.getSystemTimeMillisecond(operationTime);
    }
}