package com.ydzb.withdraw.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 民生代付后台手动打款
 */
@Entity
@Table(name = "pay_manual_record")
public class PayManualRecord extends BaseEntity<Long> {

    public static final String TO_PRIVATE = "00";
    public static final String TO_PUBLIC = "01";

    public static final byte UNDER_AUDIT = 1;   //审核中
    public static final byte AUDIT_SUCCESS = 2; //审核成功
    public static final byte AUDIT_FAILURE = 3; //审核失败
    public static final byte PAY_SUCCESS = 4;   //打款成功
    public static final byte PAY_FAILURE = 5;   //打款失败

    /**
     * 收款银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 收款人账号
     */
    @Column(name = "account_no")
    private String accountNo;

    /**
     * 收款人
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 账号类型 00-对私   01-对公
     */
    @Column(name = "account_type")
    private String accountType;

    /**
     * 开户行所在省
     */
    @Column(name = "open_province")
    private String openProvince;

    /**
     * 开户行所在市
     */
    @Column(name = "open_city")
    private String openCity;

    /**
     * 开户行名称
     */
    @Column(name = "open_name")
    private String openName;

    /**
     * 交易金额
     */
    @Column(name = "tran_amt")
    private BigDecimal tranAmt;

    /**
     * 实际交易金额
     */
    @Column(name = "suc_amt")
    private BigDecimal sucAmt;

    /**
     * 创建时间
     */
    private Long created = DateUtil.getSystemTimeSeconds();

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 状态   1-审核中   2-审核成功  3-审核失败  4-打款成功  5-打款失败
     */
    private Byte status = UNDER_AUDIT;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Long auditTime;

    /**
     * 审核人
     */
    @Column(name = "audit_user")
    private String auditUser;

    /**
     * 版本号
     */
    @Version
    private Integer version;


    @Transient
    private Date createdDate;


    @Column(name = "trans_card_id")
    private String transCardId;

    @Column(name = "trans_mobile")
    private String transMobile;


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }


    public BigDecimal getSucAmt() {
        return sucAmt;
    }

    public void setSucAmt(BigDecimal sucAmt) {
        this.sucAmt = sucAmt;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getTransCardId() {
        return transCardId;
    }

    public void setTransCardId(String transCardId) {
        this.transCardId = transCardId;
    }

    public String getTransMobile() {
        return transMobile;
    }

    public void setTransMobile(String transMobile) {
        this.transMobile = transMobile;
    }
}