package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户所持债权表
 */
@Entity
@Table(name = "cms_user_creditor_record")
@DynamicInsert
@DynamicUpdate
public class WmCmsUserCreditorRecord extends BaseEntity<Long> {

    public static final int PRODUCTTYPE_RAGULAR = 2;    //产品类型-定存宝
    public static final int PRODUCTTYPE_FREE = 3;    //产品类型-随心存
    public static final int PRODUCTTYPE_PRIVILEGE = 4;    //产品类型-新手特权
    public static final int STATUS_USERHOLD = 0;    //状态-用户所持

    @Column(name = "userid")
    private Long userId;

    /**
     * 交易ID 定存类交易ID 活期类 活期账户ID
     */
    @Column(name = "dealid")
    private Long dealId;

    /**
     * 产品类型1、活期 2、定存  3、随心存 4、新手标
     */
    @Column(name = "product_type")
    private Integer productType;


    private Long bid;   //购买时的冻结记录id
    private Integer status; //状态

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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}