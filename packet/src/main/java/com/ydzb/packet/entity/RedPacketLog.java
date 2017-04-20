package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.ProductInfo;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包操作日志
 */
@Entity
@Table(name = "wm_redpacket_users_log")
public class RedPacketLog extends BaseEntity<Long> {

    /**
     * 用户红包
     */
    @ManyToOne
    @JoinColumn(name = "redpacket_users_id")
    private UserRedPacket userRedPacket;

    /**
     * 产品交易id
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 产品类型 0-现金    1-活期宝   2-定存宝
     */
    @Column(name = "product_type")
    private Byte productType;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductInfo productInfo;

    /**
     * 红包名称
     */
    @Column(name = "redpacket_name")
    private String redpacketName;

    /**
     * 操作类型：1-使用    2-到期    3-过期
     */
    @Column(name = "operation_type")
    private Byte operationType;

    /**
     * 红包类型：1-现金红包  2-加息券
     */
    @Column(name = "redpacket_type")
    private Byte redpacketType;

    /**
     * 红包赠送值
     */
    @Column(name = "give_value")
    private BigDecimal giveValue;

    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private Long operationTime;

    /**
     * 备注，到期或过期原因
     */
    private String remark;

    /**
     * 操作日期
     */
    @Transient
    private Date operationDate;

    public RedPacketLog() {

    }

    public UserRedPacket getUserRedPacket() {
        return userRedPacket;
    }

    public void setUserRedPacket(UserRedPacket userRedPacket) {
        this.userRedPacket = userRedPacket;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }

    public Byte getOperationType() {
        return operationType;
    }

    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }

    public Byte getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Byte redpacketType) {
        this.redpacketType = redpacketType;
    }

    public BigDecimal getGiveValue() {
        return giveValue;
    }

    public void setGiveValue(BigDecimal giveValue) {
        this.giveValue = giveValue;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOperationDate() {
        return DateUtil.getSystemTimeMillisecond(operationTime);
    }
}