package com.ydzb.withdraw.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_user_withdraw")
public class UserWithDraw extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "application_time")
    private Long applicationTime; // 申请时间

    @Column(name = "advance_money")
    private BigDecimal advanceMoney; // 放款金额

    @Column(name = "withdraw_money")
    private BigDecimal withdrawMoney; // 提现金额

    @Column(name = "status")
    private Integer status; // 状态

    @Column(name = "realname")
    private String realname; // 真实姓名

    @Column(name = "id_card")
    private String idCard; // 身份证号

    @Column(name = "bank_name")
    private String bankName; // 银行姓名

    @Column(name = "bank_card_number")
    private String bankCardNumber;// 银行卡号

    @Column(name = "opening_bank")
    private String openingBank; // 开户行

    @Column(name = "open_province")
    private String openProvince;

    @Column(name = "open_city")
    private String openCity;

    @Column(name = "audit_time")
    private Long auditTime;// 审核时间

    @Column(name = "audit_user")
    private String auditUser; // 审核人真实姓名

    @Column(name = "transfer_time")
    private Long transferTime; // 转账时间

    @Column(name = "transfer_user")
    private String transferUser; // 批准人真实姓名

    @Column(name = "problem_description")
    private String probleDescription;// 问题描述

    @Column(name = "pay_time")
    private Long payTime;

    @Column(name = "suc_amt")
    private BigDecimal sucAmt;


    @Transient
    private Date sqDate;

    @Transient
    private Date auditDate;

    @Transient
    private Date transferDate;

    @Transient
    private Date payDate;

    @Formula("(select withdraw.withdraw_money - withdraw.advance_money from wm_user_withdraw as withdraw where withdraw.id = id)")
    private BigDecimal fee;

    @Column(name = "times_sorurce")
    private Integer timesSorurce;

    @Column(name = "draw_auto")
    private Integer drawAuto;

    @Column(name = "trans_mobile")
    private String transMobile;

    //提现类型 1、电子账户提现 0/其他 、银多账户提现
    private Integer withdrawtype;


    public Date getSqDate() {
        return DateUtil.getSystemTimeMillisecond(applicationTime);
    }

    public Date getAuditDate() {
        return DateUtil.getSystemTimeMillisecond(auditTime);
    }

    public Date getTransferDate() {
        return DateUtil.getSystemTimeMillisecond(transferTime);
    }

    public Date getPayDate() {
        return DateUtil.getSystemTimeMillisecond(payTime);
    }

    public String getProbleDescription() {
        return probleDescription;
    }

    public void setProbleDescription(String probleDescription) {
        this.probleDescription = probleDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Long applicationTime) {
        this.applicationTime = applicationTime;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Long getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Long transferTime) {
        this.transferTime = transferTime;
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser;
    }


    public String getOpenProvince() {
        return openProvince;
    }

    public void setOpenProvince(String openProvince) {
        this.openProvince = openProvince;
    }

    public String getOpenCity() {
        return openCity;
    }

    public void setOpenCity(String openCity) {
        this.openCity = openCity;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getSucAmt() {
        return sucAmt;
    }

    public void setSucAmt(BigDecimal sucAmt) {
        this.sucAmt = sucAmt;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getTimesSorurce() {
        return timesSorurce;
    }

    public void setTimesSorurce(Integer timesSorurce) {
        this.timesSorurce = timesSorurce;
    }

    public Integer getDrawAuto() {
        return drawAuto;
    }

    public void setDrawAuto(Integer drawAuto) {
        this.drawAuto = drawAuto;
    }

    public String getTransMobile() {
        return transMobile;
    }

    public void setTransMobile(String transMobile) {
        this.transMobile = transMobile;
    }

    public Integer getWithdrawtype() {
        return withdrawtype;
    }

    public void setWithdrawtype(Integer withdrawtype) {
        this.withdrawtype = withdrawtype;
    }
}
