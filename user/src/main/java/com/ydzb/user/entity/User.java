package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "wm_user_users")
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    //用户名
    private String username;

    //手机号
    private String mobile;

    // 身份证验证次数（默认一个用户只能验证三次）
    @Column(name = "card_verify_num")
    private Integer cardVerifyNum;

    // 已经登陆错误次数
    @Column(name = "login_num")
    private Integer loginNum;

    // 锁定时间
    @Column(name = "lock_time")
    private Long lockTime;

    // 是否允许登录 0默认允许，-1不允许
    @Column(name = "is_login")
    private Integer isLogin;

    // 用户状态，0默认，-1删除
    private Integer status;

    // 登录时间
    @Column(name = "login_time")
    private Long longTime;

    // 推荐人手机号
    @Column(name = "referral_mobile")
    private String referralMobile;

    // 推广数量
    @Column(name = "referral_num")
    private Integer referralNum;

    /**
     * 推荐码
     */
    @Column(name = "referral_code")
    private String referralCode;

    /**
     * 用户等级
     */
    @ManyToOne
    @JoinColumn(name = "user_leve")
    private VipGrade userLeve;

    //用户类型   0、普通用户 1、老用户 2、股东用户 3、业务员用户 4、新鲜说用户 5、麦罗用户
    @Column(name = "user_type")
    private Integer userType;

    //真实姓名
    @Column(name = "real_name")
    private String realName;

    //身份证号
    @Column(name = "id_card")
    private String idCard;

    //注册时间
    private Long created;


    //用户资金
    @OneToOne(mappedBy = "user")
    private UserMoney userMoney;

    //用户投资
    @OneToOne(mappedBy = "user")
    private UserInvestinfo userInvestinfo;

    //用户体验金
    @OneToOne(mappedBy = "user")
    private UserExMoney userExMoney;

    //用户充值提现
    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;

    //用户收益
    @OneToOne(mappedBy = "user")
    private UserIncome userIncome;

    //用户积分
    @OneToOne(mappedBy = "user")
    private UserIntegral userIntegral;

    @OneToOne(mappedBy = "user")
    private UserWithDrawNum userWithDrawNum;


    @OneToMany(mappedBy = "user")
    private List<UserReferral> userReferralList;


    private String remark;

    //等级变更时间
    @Column(name = "leve_time")
    private Long leveTime;

    /**
     * 微信号
     */
    @Column(name = "wechat_number")
    private String wechatNumber;

    /**
     * qq号
     */
    @Column(name = "qq_number")
    private String qqNumber;

    //创建时间
    @Transient
    private Date createDate;

    //锁定时间
    @Transient
    private Date lockTimeDate;


    //推荐充值总额
    @Transient
    private BigDecimal referralAllRecharge;

    //推荐投资总额
    @Transient
    private BigDecimal referralAllInvest;

    //推荐提现总额
    @Transient
    private BigDecimal referralAllWithdraw;

    //推荐收益总额
    @Transient
    private BigDecimal referralAllIncome;

    //推荐活期宝总额
    @Transient
    private BigDecimal referralAllDayloan;

    //推荐定存宝总额
    @Transient
    private BigDecimal referralAllDeposit;

    @Transient
    private String birthday;

    @Transient
    private String gender;

    @Transient
    private Integer age;

    /**
     * ADD BY CRF
     * 2017.03.06
     */
    //用户来源
    @Column(name = "user_source")
    private Integer userSource;
    //电子账户手机号
    @Column(name = "account_mobile")
    private String accountMobile;
    //电子账号
    private String accountid;
    //电子账户类型 1、普通用户 2、债权用户 3、美利用户 4、红包账户 5、手续费账户
    @Column(name = "account_type")
    private Integer accountType;
    //电子账户用途 00000 普通用户  10000 红包账户 01000 手续费账户 00100 担保账户
    private String acctuse;
    //密码设置状态 1、已设置 0/其他 、未设置
    @Column(name = "pdset_status")
    private Integer pdsetStatus;
    //投标签约 1、已签约  0/其他、未签约
    private Integer bidauth;
    //债权签约 1、已签约 0/其他 、未签约
    private Integer investauth;
    //投标签约订单号
    private String bidorderid;
    //债权签约订单号
    private String investorderid;
    /**
     * END
     */



    public User() {
    }

    public User(Long userId) {
        super.setId(userId);
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getLockTimeDate() {
        return DateUtil.getSystemTimeMillisecond(lockTime);
    }

    public BigDecimal getReferralAllRecharge() {
        referralAllRecharge = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllRecharge = referralAllRecharge.add(userReferral.getReferralUser().getUserInfo().getAllRecharge());
                }
            }
        }
        return referralAllRecharge;
    }

    public BigDecimal getReferralAllInvest() {
        referralAllInvest = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllInvest = referralAllInvest.add(userReferral.getReferralUser().getUserInvestinfo().getAllInvest());
                }
            }
        }
        return referralAllInvest;
    }

    public BigDecimal getReferralAllIncome() {
        referralAllIncome = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllIncome = referralAllIncome.add(userReferral.getReferralUser().getUserIncome().getAllIncome());
                }
            }
        }
        return referralAllIncome;
    }

    public BigDecimal getReferralAllWithdraw() {
        referralAllWithdraw = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllWithdraw = referralAllWithdraw.add(userReferral.getReferralUser().getUserInfo().getAlliWthdraw());
                }
            }
        }
        return referralAllWithdraw;
    }

    public BigDecimal getReferralAllDayloan() {
        referralAllDayloan = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllDayloan = referralAllDayloan.add(userReferral.getReferralUser().getUserInvestinfo().getAllInvestDayloan());
                }
            }
        }
        return referralAllDayloan;
    }

    public BigDecimal getReferralAllDeposit() {
        referralAllDeposit = BigDecimal.ZERO;
        if (userReferralList != null) {
            for (UserReferral userReferral : userReferralList) {
                if (userReferral != null) {
                    referralAllDeposit = referralAllDeposit.add(userReferral.getReferralUser().getUserInvestinfo().getAllInvestDeposit());
                }
            }
        }
        return referralAllDeposit;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public VipGrade getUserLeve() {
        return userLeve;
    }

    public void setUserLeve(VipGrade userLeve) {
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

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getCardVerifyNum() {
        return cardVerifyNum;
    }

    public void setCardVerifyNum(Integer cardVerifyNum) {
        this.cardVerifyNum = cardVerifyNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserMoney getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(UserMoney userMoney) {
        this.userMoney = userMoney;
    }

    public UserInvestinfo getUserInvestinfo() {
        return userInvestinfo;
    }

    public void setUserInvestinfo(UserInvestinfo userInvestinfo) {
        this.userInvestinfo = userInvestinfo;
    }

    public UserExMoney getUserExMoney() {
        return userExMoney;
    }

    public void setUserExMoney(UserExMoney userExMoney) {
        this.userExMoney = userExMoney;
    }

    public List<UserReferral> getUserReferralList() {
        return userReferralList;
    }

    public void setUserReferralList(List<UserReferral> userReferralList) {
        this.userReferralList = userReferralList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserIncome getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(UserIncome userIncome) {
        this.userIncome = userIncome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getLeveTime() {
        return leveTime;
    }

    public void setLeveTime(Long leveTime) {
        this.leveTime = leveTime;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public UserWithDrawNum getUserWithDrawNum() {
        return userWithDrawNum;
    }

    public void setUserWithDrawNum(UserWithDrawNum userWithDrawNum) {
        this.userWithDrawNum = userWithDrawNum;
    }

    public String getBirthday() {
        if (!StringUtils.isEmpty(idCard)) {
            return idCard.substring(6, 10) + "年" + idCard.substring(10, 12) + "月" + idCard.substring(12, 14) + "日";
        }
        return birthday;
    }

    public Integer getAge() {
        try {
            return StringUtils.isEmpty(idCard)? null: Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(idCard.substring(6, 10));
        } catch (Exception e) {
            return null;
        }
    }

    public String getGender() {
        try {
            if (StringUtils.isEmpty(idCard)) return null;
            int sex = Integer.valueOf(idCard.substring(16, 17));
            if (sex % 2 == 0) {
                return "女";
            }
            return "男";
        } catch (Exception e) {
            return null;
        }
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public UserIntegral getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(UserIntegral userIntegral) {
        this.userIntegral = userIntegral;
    }

    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAcctuse() {
        return acctuse;
    }

    public void setAcctuse(String acctuse) {
        this.acctuse = acctuse;
    }

    public Integer getPdsetStatus() {
        return pdsetStatus;
    }

    public void setPdsetStatus(Integer pdsetStatus) {
        this.pdsetStatus = pdsetStatus;
    }

    public Integer getBidauth() {
        return bidauth;
    }

    public void setBidauth(Integer bidauth) {
        this.bidauth = bidauth;
    }

    public Integer getInvestauth() {
        return investauth;
    }

    public void setInvestauth(Integer investauth) {
        this.investauth = investauth;
    }

    public String getBidorderid() {
        return bidorderid;
    }

    public void setBidorderid(String bidorderid) {
        this.bidorderid = bidorderid;
    }

    public String getInvestorderid() {
        return investorderid;
    }

    public void setInvestorderid(String investorderid) {
        this.investorderid = investorderid;
    }
}
