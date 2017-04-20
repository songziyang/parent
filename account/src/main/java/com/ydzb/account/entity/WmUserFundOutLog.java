package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_user_fund_outlog")
public class WmUserFundOutLog extends BaseEntity<Long> {

    private static final long serialVersionUID = -2111962716857476963L;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 外链ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 类型
     * 类型：1提现,2天标购买,3定存购买，4定存复投,5提现扣费,6M提前赎回扣费,7（2.0+购买）,
     * 8放款、9聚幕购买 10股票配资11 投资金到期 12 稳进宝购买 13 网贷定存 14 自主投资
     */
    private Integer type;

    /**
     * 出帐时间
     */
    @Column(name = "out_time")
    private Long outTime;

    /**
     * 本金
     */
    @Column(name = "out_fund")
    private BigDecimal outFund;


    @Column(name = "balance")
    private BigDecimal balance;

    private  String remark;


    public WmUserFundOutLog() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOutTime() {
        return outTime;
    }

    public void setOutTime(Long outTime) {
        this.outTime = outTime;
    }

    public BigDecimal getOutFund() {
        return outFund;
    }

    public void setOutFund(BigDecimal outFund) {
        this.outFund = outFund;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}