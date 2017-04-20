package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 债券总数
 */
@Entity
@Table(name = "cms_creditor_canmatch_total")
@DynamicInsert
@DynamicUpdate
public class WmCmsCreditorCanmatchTotal extends BaseEntity<Long> {

    /**
     * 可匹配总数
     */
    @Column(name = "canmatch")
    private BigDecimal canMatch;
    private BigDecimal buy; //购买总数
    private BigDecimal redeem;  //赎回总数

    public BigDecimal getCanMatch() {
        return canMatch;
    }

    public void setCanMatch(BigDecimal canMatch) {
        this.canMatch = canMatch;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getRedeem() {
        return redeem;
    }

    public void setRedeem(BigDecimal redeem) {
        this.redeem = redeem;
    }
}
