package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name = "wm_info_auth_code")
public class AuthCode extends BaseEntity<Long> {

    private static final long serialVersionUID = 2991108128715920728L;

    // 手机号码或者邮箱值
    private String name;

    // 内容
    private String content;

    // 发送类型。0默认，0手机，1邮箱
    private Byte type;

    // 是否已验证，默认0，0没验证，1已验证
    private Byte status;

    // 有效期，默认0，0不过期，有效期为创建时间的十分钟。
    private Long expire;

    // 创建时间
    private Long created;

    private String ips;

    @Transient
    private Date expireDate;

    @Transient
    private Date createDate;

    public AuthCode() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getExpireDate() {
        return DateUtil.getSystemTimeMillisecond(expire);
    }

}