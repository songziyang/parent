package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台支出统计
 * @author sy
 */
@Entity
@Table(name = "wm_platform_pay")
public class WmPlatformPay extends BaseEntity<Long> {

    public static final byte CURRENT = 1;   //活期宝收益
    public static final byte REGULAR = 2;   //定存宝收益
    public static final byte INSTITUTION = 3;   //机构宝收益
    public static final byte PRIVATE_ORDERING = 4;  //私人订制收益
    public static final byte REFEREE = 5;   //推荐收益返现

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 类型：1-活期宝收益   2-定存宝收益 3-机构宝收益 4-私人订制收益    5-推荐收益返现
     */
    private Byte type;

    /**
     * 操作时间
     */
    @Column(name = "opdate")
    private Long operationTime;

    /**
     * 操作日期
     */
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