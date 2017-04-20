package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name = "wm_info_template")
public class InfoTemplate extends BaseEntity<Long> {

    private static final long serialVersionUID = -134759425299027414L;


    private String flag;                        //标识

    private String name;                        //名称

    @Column(name = "site_content")
    private String siteContent;                //站内信模板内容

    @Column(name = "sms_content")
    private String smsContent;                  //短信模板内容

    private Byte status;                        //状态：0正常，-1删除

    private Long created;                       //创建时间

    @Column(name = "created_user")
    private String createdUser;                 //创建人

    private Long updated;                       //更新时间

    @Column(name = "updated_user")
    private String updatedUser;                 //更新人


    @Transient
    private Date createDate;


    @Transient
    private Date updatedDate;


    public InfoTemplate() {
        super();
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getUpdatedDate() {
        return DateUtil.getSystemTimeMillisecond(updated);
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSiteContent() {
        return siteContent;
    }

    public void setSiteContent(String siteContent) {
        this.siteContent = siteContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}