package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_product_info")
@DynamicInsert
@DynamicUpdate
public class WmProductInfo extends BaseEntity<Long> {

    public static final byte YINDUO = 1;    //银多
    public static final byte INSTITUTION = 2;   //机构
    public static final int STATUS_USING = 0; //状态-使用中
    public static final int STATUS_ABANDON = 1;    //状态-停用
    public static final int PRODUCTCLAS_YINDUO = 1; //产品类别—银多

    public static final Integer TYPE_CURRENT = 1;    //活期宝
    public static final Integer TYPE_REGULAR = 2;    //定存宝
    public static final int TYPE_PRIVILEGE = 9; //类型-新手特权
    public static final int STATUS_USE = 0; //状态-使用中
    /*public static final Integer[] TYPE_INSTITUTION = {3, 4, 5, 6}; //机构宝，id是3和4,5涌金门 6,银多-金贸街特供*/

    /**放
     * 产品名称
     */
    private String name;

    /**
     * 产品类型
     */
    @Column(name = "type_id")
    private Integer type;

    /**
     * 产品类别：1-银多    2-机构
     */
    @Column(name = "product_clas")
    private Integer productClas;

    /**
     * 期数
     */
    private Integer stage;

    /**
     * 年利率
     */
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    /**
     * 天数
     */
    @Column(name = "cylcle_days")
    private Integer cylcleDays;

    /**
     * 状态：0-使用中（默认） 1-停用
     */
    private Integer status;

    /**
     * 发布方式：0-手动 1-自动
     */
    @Column(name = "create_way")
    private Integer createWay;

    /**
     * 发布金额
     */
    private Integer funds;

    /**
     * 发布份数
     */
    private Integer copies;

    /**
     * 剩余金额
     */
    private Integer surplus;

    /**
     * 剩余份数
     */
    private Integer residue;

    /**
     * 每份金额
     */
    private Integer unit;

    /**
     * 创建人真实姓名
     */
    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "updated_user")
    private String updatedUser;


    /**
     * 活动利率
     */
    @Column(name = "activity_rate")
    private BigDecimal activityRate;


    /**
     * 创建时间
     */
    private Long created;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProductClas() {
        return productClas;
    }

    public void setProductClas(Integer productClas) {
        this.productClas = productClas;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getCylcleDays() {
        return cylcleDays;
    }

    public void setCylcleDays(Integer cylcleDays) {
        this.cylcleDays = cylcleDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateWay() {
        return createWay;
    }

    public void setCreateWay(Integer createWay) {
        this.createWay = createWay;
    }


    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getFunds() {
        return funds;
    }

    public void setFunds(Integer funds) {
        this.funds = funds;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Integer getResidue() {
        return residue;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public BigDecimal getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(BigDecimal activityRate) {
        this.activityRate = activityRate;
    }


}