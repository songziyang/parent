package com.ydzb.packet.entity;

import com.ydzb.admin.entity.Admin;
import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定存类活动 中奖/兑换记录
 */
@Entity
@Table(name = "wm_deposit_type_exchange")
public class DepositTypeExchange extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //活动类型1、16双旦活动
    private Integer actype;

    //奖品类型 1、实物  2、活期加息券 3、定存加息券  4、现金红包 5、定存代金券 6、体验金
    private Integer ptype;

    //获得类型1、兑换 2、投资 3、抽奖类
    private Integer type;

    //产品名称(红包名称)
    @Column(name = "product_name")
    private String productName;

    //兑换数量
    private Integer number;

    //使用数量/投资金额
    private BigDecimal usenum;

    //奖品金额
    private BigDecimal fund;

    //产品周期 1、一 2、三 3、六 4、十二
    private Integer procycle;

    //收货人姓名
    private String realname;

    //收货人地址
    private String address;

    //收货人手机号
    private String mobile;

    //订单状态 1、已受理 2、已发货3、确认收货4、取消
    private Integer status;

    //创建时间
    private Long created;

    //发货时间
    private Long sended;

    //备注说明
    private String remark;

    //操作用户
    @Column(name = "operation_user")
    private String operationUser;

    @Version
    private Integer version;

    @Transient
    private Date createdDate;

    @Transient
    private Date sendedDate;

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getSendedDate() {
        return DateUtil.getSystemTimeMillisecond(sended);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getActype() {
        return actype;
    }

    public void setActype(Integer actype) {
        this.actype = actype;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getUsenum() {
        return usenum;
    }

    public void setUsenum(BigDecimal usenum) {
        this.usenum = usenum;
    }

    public Integer getProcycle() {
        return procycle;
    }

    public void setProcycle(Integer procycle) {
        this.procycle = procycle;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getSended() {
        return sended;
    }

    public void setSended(Long sended) {
        this.sended = sended;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }
}