package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 产品交易冻结记录
 */
@Entity
@Table(name = "jx_freeze_record")
@DynamicInsert
@DynamicUpdate
public class WmJxFreezeRecord extends BaseEntity<Long> {

    public static final int TYPE_PURCHASE = 1;  //类型-购买
    public static final int TYPE_EXPIRE = 3;    //类型-到期
    public static final int TYPE_PROFIT = 5;    //类型-复利
    public static final int TYPE_PRINCIPAL_RECAST = 6;    //类型-本金复投
    public static final int LINKTYPE_CURRENT = 1;   //交易类型-活期
    public static final int LINKTYPE_RAGULAR = 2;   //交易类型-定存
    public static final int LINKTYPE_PRIVILEGE = 4;   //交易类型-新手
    public static final int LINKTYPE_FREE = 3;   //交易类型-随心存
    public static final int STATE_UNDERHANDLE = 1;   //状态-待处理
    public static final int STATE_MATCHFINISH = 2;   //状态-匹配完成
    public static final int STATE_HANDLEFINISH = 3;   //状态-处理完成
    public static final int DEVICE_SYSTEM = 5;  //设备类型-系统
    public static final int EXPIREMODE_NONE = 0;    //复投模式-不复投

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 电子账户
     */
    @Column(name = "accountid")
    private String accountId;

    /**
     * 交易金额
     */
    @Column(name = "txamount")
    private BigDecimal txAmount;

    /**
     * 交易收益
     */
    @Column(name = "txincome")
    private BigDecimal txIncome;

    /**
     * 交易记录ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 加息券红包ID（定存）
     */
    @Column(name = "invest_redpacket_id")
    private String investRedpacketId;

    /**
     * 定存红包IDs（定存）
     */
    @Column(name = "deposit_redpacket_id")
    private String depositRedpacketId;

    /**
     * 是否复投 0 不复投 1本金复投 2本息复投（定存）
     */
    @Column(name = "expire_mode")
    private Integer expireMode;

    /**
     * 开始时间（随心存）
     */
    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "free_copies")
    private String freeCopies;
    /**
     * 交易类型
     */
    @Column(name = "link_type")
    private Integer linkType;

    private Integer type;   //类型
    private Integer state;  //状态
    private Long created;   //创建时间
    private Long updated;   //处理完成时间
    private Integer device; //1苹果2安卓3微信4网站

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public BigDecimal getTxIncome() {
        return txIncome;
    }

    public void setTxIncome(BigDecimal txIncome) {
        this.txIncome = txIncome;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getInvestRedpacketId() {
        return investRedpacketId;
    }

    public void setInvestRedpacketId(String investRedpacketId) {
        this.investRedpacketId = investRedpacketId;
    }

    public String getDepositRedpacketId() {
        return depositRedpacketId;
    }

    public void setDepositRedpacketId(String depositRedpacketId) {
        this.depositRedpacketId = depositRedpacketId;
    }

    public Integer getExpireMode() {
        return expireMode;
    }

    public void setExpireMode(Integer expireMode) {
        this.expireMode = expireMode;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getFreeCopies() {
        return freeCopies;
    }

    public void setFreeCopies(String freeCopies) {
        this.freeCopies = freeCopies;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}