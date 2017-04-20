package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.UserInvestinfo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "wm_user_users")
public class WmUser extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final long PL_ID = 84033;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户VIP等级ID
     */
    @Column(name = "user_leve")
    private Long userLeve;

    /**
     * 推荐人数
     */
    @Column(name = "referral_num")
    private Integer referralNum;

    /**
     * 推荐人手机号
     */
    @Column(name = "referral_mobile")
    private String referralMobile;

    /**
     * 用户状态，0默认，-1删除
     */
    private Integer status;

    @Column(name = "id_card")
    private String idCard;

    /**
     * 等级变更时间
     */
    @Column(name = "leve_time")
    private Long leveTime;

    /**
     * 电子账号
     */
    @Column(name = "accountid")
    private String accountId;


    /**
     * 用户投资
     */
    @OneToOne(mappedBy = "user")
    private UserInvestinfo userInvestinfo;


    public WmUser() {
    }

    /**
     * 是否是存管用户
     * @return
     */
    public boolean isDepositoryUser() {
        return StringUtils.isNotEmpty(accountId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUserLeve() {
        return userLeve;
    }

    public void setUserLeve(Long userLeve) {
        this.userLeve = userLeve;
    }

    public Integer getReferralNum() {
        return referralNum;
    }

    public void setReferralNum(Integer referralNum) {
        this.referralNum = referralNum;
    }

    public String getReferralMobile() {
        return referralMobile;
    }

    public void setReferralMobile(String referralMobile) {
        this.referralMobile = referralMobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getLeveTime() {
        return leveTime;
    }

    public void setLeveTime(Long leveTime) {
        this.leveTime = leveTime;
    }

    public UserInvestinfo getUserInvestinfo() {
        return userInvestinfo;
    }

    public void setUserInvestinfo(UserInvestinfo userInvestinfo) {
        this.userInvestinfo = userInvestinfo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
