package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户赎回队列表
 */
@Entity
@Table(name = "cms_redeem_queue")
@DynamicInsert
@DynamicUpdate
public class WmCmsRedeemQueue extends BaseEntity<Long> {

    public static final int PRODUCTTYPE_CURRENT = 1;    //产品类型-活期宝
    public static final int PRODUCTTYPE_RAGULAR = 2;    //产品类型-定存宝
    public static final int PRODUCTTYPE_FREERAGULAR = 3;    //产品类型-随心存

    public static final int TYPE_REDEEM = 2;    //赎回
    public static final int TYPE_EXPIRE = 3;    //到期
    public static final int TYPE_TRANSFER = 4;  //转让
    public static final int TYPE_PRINCIPAL_EXPIRE = 6;  //本金复投

    public static final int STATUS_UNDERHANDLE = 0; //状态-未处理
    public static final int STATUS_HAVEHANDLED = 1; //状态-已处理
    public static final int STATUS_INTERFACEHANDLED = 2; //状态-接口处理完成

    public static final int WARN_NORMAL = 0;    //错误警告状态 正常


    /**
     * 用户id
     */
    @Column(name = "userid")
    private Long userId;

    /**
     * 交易id
     */
    @Column(name = "dealid")
    private Long dealId;

    private BigDecimal amount;  //本金金额

    /**
     * 产品类型 1 活期 2定存 3随心存
     */
    @Column(name = "product_type")
    private Integer productType;

    /**
     * 冻结ID
     */
    @Column(name = "rbid")
    private Long rbId;

    /**
     * 转让接收人ID
     */
    @Column(name = "transfer_userid")
    private Long transferUserId;

    private BigDecimal income;  //收益金额
    private Integer type;   //1赎回，2到期，3债权转让
    private Integer status; //状态
    private Integer warn;   //错误警告状态

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public Long getRbId() {
        return rbId;
    }

    public void setRbId(Long rbId) {
        this.rbId = rbId;
    }

    public Long getTransferUserId() {
        return transferUserId;
    }

    public void setTransferUserId(Long transferUserId) {
        this.transferUserId = transferUserId;
    }
}