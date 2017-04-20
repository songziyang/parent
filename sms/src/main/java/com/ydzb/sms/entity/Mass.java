package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 群发短息模板实体
 */
@Entity
@Table(name = "wm_info_mass")
public class Mass extends BaseEntity<Long> implements java.io.Serializable {

	private static final long serialVersionUID = -8656889841030193675L;

	public static final byte STATE_SUCCESS = 1; // 发送状态-成功
	public static final byte STATE_FAILURE = 2; // 发送状态-失败

	@Column
	private String name; // 名称

	@Column
	private String content; // 短信内容

	@Column
	private Byte state; // 发送状态

	@Column
	private String msg; // 错误原因

	@Column
	private Byte num; // 发送次数

	@Column
	private Long created; // 创建时间毫秒数

	@Column(name = "create_user")
	private Long createUser; // 创建人

	@Column(name = "update_user")
	private Long updateUser; // 更新人

	@Transient
	public Date createDate;	//创建时间日期
	
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

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Byte getNum() {
		return num;
	}

	public void setNum(Byte num) {
		this.num = num;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDate() {
		return DateUtil.getSystemTimeMillisecond(created);
	}
}