package com.ydzb.admin.entity;

import com.ydzb.admin.entity.Admin;
import com.ydzb.core.entity.BaseEntity;

import javax.persistence.*;

/**
 * 动态令牌
 */
@Entity
@Table(name = "wm_sys_otp")
public class OnetimePassword extends BaseEntity<Long> {

    /**
     * 动态令牌号
     */
    @Column(name = "otp_number")
    private Long otpNumber;

    /**
     * 密钥
     */
    private String authkey;

    /**
     * 成功值
     */
    @Column(name = "success_val")
    private Long successVal = 0l;

    /**
     * 偏移值
     */
    @Column(name = "drift_val")
    private Integer driftVal = 0;

    @OneToOne(mappedBy = "onetimePassword")
    private Admin admin;

    private Long created;

    public OnetimePassword() {}

    public Long getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Long otpNumber) {
        this.otpNumber = otpNumber;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public Long getSuccessVal() {
        return successVal;
    }

    public void setSuccessVal(Long successVal) {
        this.successVal = successVal;
    }

    public Integer getDriftVal() {
        return driftVal;
    }

    public void setDriftVal(Integer driftVal) {
        this.driftVal = driftVal;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
