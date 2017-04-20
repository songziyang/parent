package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_redpacket_used")
public class WmRedpacketUsed extends BaseEntity<Long> {

    public static final int PRODUCTTYPE_RAGULAR = 3;   //加息券单笔定存
    public static final int PRODUCTTYPE_VOUCHER = 4;   //单笔定存代金券

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "redpacket_users_id")
    private Long redpacketUsersId;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "operation_time")
    private Long operationTime;

    @Column(name = "finish_time")
    private Long finishTime;

    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "apr")
    private BigDecimal apr;

    @Column(name = "fund")
    private BigDecimal fund;

    public WmRedpacketUsed() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRedpacketUsersId() {
        return redpacketUsersId;
    }

    public void setRedpacketUsersId(Long redpacketUsersId) {
        this.redpacketUsersId = redpacketUsersId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }
}