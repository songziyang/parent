package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "wm_info_message")
public class Message extends BaseEntity<Long> {

    //收件人id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;
    //标题
    private String title;
    //内容
    private String content;

    //状态。默认为0：未读；1：已读
    private Integer status;

    //默认为0：正常；1：删除
    private Integer deleted;

    //创建时间
    private Long created;

    // 0 系统 1 通知
    private Integer type;

    @Transient
    private Date createDate;

    @Transient
    private String username;

    public Message() {
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

}