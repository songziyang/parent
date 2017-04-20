package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "pay_manual_request")
public class WmPayManualRequest extends BaseEntity<Long> {

    private static final long serialVersionUID = 7246050197014758728L;

    // 银行名称
    @Column(name = "bank_name")
    private String bankName;

    // 账号
    @Column(name = "account_no")
    private String accountNo;

    // 账户名称
    @Column(name = "account_name")
    private String accountName;

    // 开户行所在省
    private String province;

    // 开户行所在市
    private String city;

    // 开户行名称
    @Column(name = "open_name")
    private String openName;

    // 交易金额
    private BigDecimal tranamt;

    // 交易日期
    private String trandate;

    // 交易时间
    private String trantime;

    // 交易流水号
    private String bussflowno;

    @Column(name = "user_id")
    private Long userId;

    // 返回码 C000000000 成功
    private String respcode;

    // 中文描述
    private String respmsg;

    // 交易状态
    @Column(name = "tran_state")
    private String tranState;

    //打款类型：1、账户打款2、手动打款
    private Integer type;

    @Column(name = "withdraw_id")
    private Long linkId;

    private Long created;

    @Column(name = "acc_state")
    private Integer accState;

    @Column(name = "trans_orderid")
    private String transOrderid;

    @Column(name = "trans_batchid")
    private String transBatchid;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public BigDecimal getTranamt() {
        return tranamt;
    }

    public void setTranamt(BigDecimal tranamt) {
        this.tranamt = tranamt;
    }

    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public String getBussflowno() {
        return bussflowno;
    }

    public void setBussflowno(String bussflowno) {
        this.bussflowno = bussflowno;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }

    public String getTranState() {
        return tranState;
    }

    public void setTranState(String tranState) {
        this.tranState = tranState;
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


    public Integer getAccState() {
        return accState;
    }

    public void setAccState(Integer accState) {
        this.accState = accState;
    }

    public String getTransOrderid() {
        return transOrderid;
    }

    public void setTransOrderid(String transOrderid) {
        this.transOrderid = transOrderid;
    }

    public String getTransBatchid() {
        return transBatchid;
    }

    public void setTransBatchid(String transBatchid) {
        this.transBatchid = transBatchid;
    }
}
