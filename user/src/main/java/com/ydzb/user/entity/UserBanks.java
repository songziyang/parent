package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.*;

/**
 * 用户银行信息
 * @author sy
 */
@Entity
@Table(name = "wm_user_banks")
public class UserBanks extends BaseEntity<Long> {

    public static final byte NOT_DEFAULT_CARD = 0;  //非默认银行卡
    public static final byte DEFAULT_CARD = 1;  //默认银行卡

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 银行卡号
     */
    @Column(name = "card_no")
    private String cardNo;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 开户行
     */
    @Column(name = "bank_opening")
    private String bankOpening;

    /**
     * 开户行省份
     */
    @Column(name = "bank_province")
    private String bankProvince;

    /**
     * 开户行城市
     */
    @Column(name = "bank_city")
    private String bankCity;

    /**
     * 默认银行卡 0-非默认   1-默认
     */
    private Byte type;

    /**
     * 充值标志 0-未充值   1-充值
     */
    @Column(name = "is_recharge")
    private Byte isRecharge;

    /**
     * 绑定状态 0-正常    1-删除
     */
    private Byte state;

    /**
     * 修改时间
     */
    private Long updated;

    // 电子账户类型：1、电子账户 0/其他、银多
    private Integer cardtype;

    public Integer getCardtype() {
        return cardtype;
    }

    public void setCardtype(Integer cardtype) {
        this.cardtype = cardtype;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankOpening() {
        return bankOpening;
    }

    public void setBankOpening(String bankOpening) {
        this.bankOpening = bankOpening;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(Byte isRecharge) {
        this.isRecharge = isRecharge;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}
