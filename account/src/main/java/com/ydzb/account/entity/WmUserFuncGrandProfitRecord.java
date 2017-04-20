package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_user_func_grand_profit_record")
@DynamicInsert
@DynamicUpdate
public class WmUserFuncGrandProfitRecord extends BaseEntity<Long> {

    public static final int TYPE_INTEREST = 1;  //红包类别-加息券
    public static final int TYPE_EXPERIENCE = 2;    //红包类别-体验金
    public static final int TYPE_VIP = 3;   //红包类别-VIP加息
    public static final int TYPE_VOUCHER = 4;   //红包类别-定存代金券
    public static final int PTYPE_CURRENT = 1;  //产品类型-活期宝
    public static final int PTYPE_RAGULAR = 2;  //产品类型-定存宝
    public static final int PTYPE_FREE = 3; //产品类型-自由定存

    //用户ID
    @Column(name = "user_id")
    private Long userId;

    //产品ID
    @Column(name = "product_id")
    private Long productId;

    //金额
    private BigDecimal fund;

    //1、活期宝 2、定存宝
    private Integer ptype;

    //1-加息劵 2-体验金
    private Integer type;

    @Column(name = "record_date")
    private Long recordDate;

    public WmUserFuncGrandProfitRecord() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Long recordDate) {
        this.recordDate = recordDate;
    }
}
