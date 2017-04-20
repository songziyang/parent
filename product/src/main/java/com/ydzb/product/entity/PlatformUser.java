package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 平台用户
 */
@Entity
@Table(name = "wm_platform_user")
@DynamicInsert
@DynamicUpdate
public class PlatformUser extends BaseEntity<Long> {

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 资金余额
     */
    @Column(name = "usable_fund")
    private BigDecimal usableFund;

    /**
     * 债权总额
     */
    @Column(name = "all_invest")
    private BigDecimal allInvest;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public BigDecimal getAllInvest() {
        return allInvest;
    }

    public void setAllInvest(BigDecimal allInvest) {
        this.allInvest = allInvest;
    }
}