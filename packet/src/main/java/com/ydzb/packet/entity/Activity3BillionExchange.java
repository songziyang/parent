package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 30亿活动兑换
 */
@Entity
@Table(name = "wm_activity_thirty_exchange")
@DynamicInsert
@DynamicUpdate
public class Activity3BillionExchange extends BaseEntity<Long> {

    public static final int STATUS_UNDERHANDLE = 1; //状态-待处理
    public static final int STATUS_ENSURESEND = 2;  //状态-已发货
    public static final int STATUS_ENSURERECEIVE = 3;   //状态-已收货
    public static final int STATUS_CANCEL = 4;  //状态-取消订单


    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 真实姓名
     */
    @Column(name = "realname")
    private String realName;

    private String mobile;  //手机
    private String addr;    //地址
    private Long created;   //创建时间

    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private Long operationTime;

    /**
     * 操作人
     */
    @Column(name = "operation_user")
    private String operationUser;
    private Integer type;   //兑换类型
    private Integer status; //状态

    @Transient
    private Date createdDate;
    @Transient
    private Date operationDate;

    /**
     * 转换产品名称
     * @return
     */
    public String convertProductName() {

        if (type == null) return "";
        switch (type) {
            case 1: return "订制U盘";
            case 2: return "纪念银币";
            default: return "";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getOperationDate() {
        return DateUtil.getSystemTimeMillisecond(operationTime);
    }
}