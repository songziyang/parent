package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 产品表
 *
 * @author sy
 */
@Entity
@Table(name = "wm_product_info")
@DynamicUpdate
@DynamicInsert
public class ProductInfo extends BaseEntity<Long> {

    public static final byte STATUS_INUSE = 0;  //状态-使用中
    public static final byte STATUS_NOTUSE = 1; //状态-停用
    public static final byte MANUAL = 0;    //发布方式-手动
    public static final byte AUTOMATIC = 1; //发布方式-自动
    public static final int INIT_STAGE = 1; //默认期数
    public static final byte YINDUO = 1;    //银多类别
    public static final byte JIGOU = 2;     //机构类别


    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType type;

    /**
     * 产品类别：1-银多    2-机构
     */
    @Column(name = "product_clas")
    private Byte productClas;

    /**
     * 期数：起始是1，如有新产品，则新产品的stage + 1；
     */
    private Integer stage;

    /**
     * 年利率
     */
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    /**
     * 发布金额
     */
    private Long funds = 0l;

    /**
     * 发布份数
     */
    private Long copies = 0l;

    /**
     * 每份金额
     */
    private Long unit = 1l;

    /**
     * 剩余金额
     */
    private Long surplus = 0l;

    /**
     * 剩余份数
     */
    private Long residue = 0l;

    /**
     * 天数
     */
    @Column(name = "cylcle_days")
    private Integer cylcleDays;

    /**
     * 状态：0-使用中（默认）     1-停用
     */
    private Byte status = STATUS_INUSE;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建日期
     */
    @Transient
    private Date createdDate;

    /**
     * 创建人真实姓名
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 修改人真实姓名
     */
    @Column(name = "updated_user")
    private String updatedUser;

    /**
     * 发布方式：0-手动（默认）    1-自动
     */
    @Column(name = "create_way")
    private Byte createWay = MANUAL;


    /**
     * 活动利率
     */
    @Column(name = "activity_rate")
    private BigDecimal activityRate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Byte getProductClas() {
        return productClas;
    }

    public void setProductClas(Byte productClas) {
        this.productClas = productClas;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Long getFunds() {
        return funds;
    }

    public void setFunds(Long funds) {
        this.funds = funds;
    }

    public Long getCopies() {
        return copies;
    }

    public void setCopies(Long copies) {
        this.copies = copies;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Integer getCylcleDays() {
        return cylcleDays;
    }

    public void setCylcleDays(Integer cylcleDays) {
        this.cylcleDays = cylcleDays;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Byte getCreateWay() {
        return createWay;
    }

    public void setCreateWay(Byte createWay) {
        this.createWay = createWay;
    }

    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(Long surplus) {
        this.surplus = surplus;
    }

    public Long getResidue() {
        return residue;
    }

    public void setResidue(Long residue) {
        this.residue = residue;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public BigDecimal getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(BigDecimal activityRate) {
        this.activityRate = activityRate;
    }
}