package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_redpacket_users_log")
public class WmUserRedPacketLog extends BaseEntity<Long> {

    //用户红包ID
    @Column(name = "redpacket_users_id")
    private Long redpacketUsersId;

    //产品交易id
    @Column(name = "link_id")
    private Long linkId;

    //产品类别  0-现金 1-活期宝 2-定存宝
    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "user_id")
    private Long userId;

    //产品名
    @Column(name = "product_id")
    private Long productId;

    //红包名称
    @Column(name = "redpacket_name")
    private String redpacketName;

    //操作类型 1-使用 ，2-到期 ， 3-过期
    @Column(name = "operation_type")
    private Integer operationType;

    //红包类型： 1现金红包，2加息卷
    @Column(name = "redpacket_type")
    private Integer redpacketType;

    //红包赠送值
    @Column(name = "give_value")
    private BigDecimal giveValue;

    @Column(name = "operation_time")
    private Long operationTime;

    private String remark;

    public WmUserRedPacketLog() {

    }


    public Long getRedpacketUsersId() {
        return redpacketUsersId;
    }

    public void setRedpacketUsersId(Long redpacketUsersId) {
        this.redpacketUsersId = redpacketUsersId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Integer redpacketType) {
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
}