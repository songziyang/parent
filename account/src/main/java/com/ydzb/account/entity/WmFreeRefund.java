package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_free_refund")
@DynamicInsert
@DynamicUpdate
public class WmFreeRefund extends BaseEntity<Long> implements IWmPeriodicProductRefund {

    private static final long serialVersionUID = 1L;

    public static final int STATE_NOTEXPIRE = 0;   //状态-未还
    public static final int STATE_EXPIRE = 1;   //状态-已还
    public static final int STATE_REFUNDING = 2;   //状态-还款中
    public static final int EXPIRE_NOTLAST = 0  ;   //非最后一期
    public static final int EXPIRE_LAST = 1;


    //用户id
    @Column(name = "user_id")
    private Long userId;

    //用户定存记录id
    @Column(name = "account_id")
    private Long accountId;

    //总期数
    private Integer stage;

    @Column(name = "cur_stage")
    private Integer curStage;

    @Column(name = "refund_time")
    private Long refundTime;

    //加息
    private BigDecimal fund;

    //加息收益
    @Column(name = "redpacket_fund")
    private BigDecimal redpacketFund;

    //0-未还 1-已还
    private Integer state;

    //0、非最后一期 1、最后一期
    @Column(name = "is_expire")
    private Integer isExpire;

    //VIP 收益
    @Column(name = "vip_fund")
    private BigDecimal vipFund;

    private Integer organization;

    //代金券收益
    @Column(name = "vouchers_fund")
    private BigDecimal vouchersFund;

    public WmFreeRefund() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getCurStage() {
        return curStage;
    }

    public void setCurStage(Integer curStage) {
        this.curStage = curStage;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getRedpacketFund() {
        return redpacketFund;
    }

    public void setRedpacketFund(BigDecimal redpacketFund) {
        this.redpacketFund = redpacketFund;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Integer isExpire) {
        this.isExpire = isExpire;
    }

    public BigDecimal getVipFund() {
        return vipFund;
    }

    public void setVipFund(BigDecimal vipFund) {
        this.vipFund = vipFund;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public BigDecimal getVouchersFund() {
        return vouchersFund;
    }

    public void setVouchersFund(BigDecimal vouchersFund) {
        this.vouchersFund = vouchersFund;
    }
}