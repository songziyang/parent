package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 体验金
 * @author sy
 */
@Entity
@Table(name = "wm_expmoney_send")
public class ExpMoney extends BaseEntity<Long> {

    public static final byte SEND = 0;  //发送短信
    public static final byte NOTSEND = 1;   //不发送短信
    public static final byte STATE_INUSE = 0;  //使用中
    public static final byte STATE_DISABLED = 1;   //已停用

    /**
     * 份数
     */
    private Integer copies;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 是否发短信    0-发送    1-不发送
     */
    @Column(name = "issend")
    private Byte isSend;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 状态   0-使用中   1-已停用
     */
    private Byte state = STATE_INUSE;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 短信内容
     */
    private String content;

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Byte getIsSend() {
        return isSend;
    }

    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}