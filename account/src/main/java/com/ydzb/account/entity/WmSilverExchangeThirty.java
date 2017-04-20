package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;

/**
 * 30亿活动兑换
 */
@Entity
@Table(name = "wm_silver_exchange_thirty")
@DynamicInsert
@DynamicUpdate
public class WmSilverExchangeThirty extends BaseEntity<Long> {

    public static final int LEVEL_GOODSMIN = 1; //实物最小level
    public static final int LEVEL_GOODSMAX = 7; //实物最大level

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_name")
    private String productName;

    private String realname;
    private String address;
    private String mobile;
    private Integer status;
    private Long created;
    private Long winning;   //中奖时间
    private Long sended;    //发货时间
    private String remark;
    private Integer level;

    @Version
    private Integer version;

    private Integer type;

    private BigDecimal fund;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getWinning() {
        return winning;
    }

    public void setWinning(Long winning) {
        this.winning = winning;
    }

    public Long getSended() {
        return sended;
    }

    public void setSended(Long sended) {
        this.sended = sended;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
