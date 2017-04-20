package com.ydzb.admin.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "wm_sys_admin")
public class Admin extends BaseEntity<Long> {

	private static final long serialVersionUID = -6411826768500695412L;

	private String username;

	private String realname;

	private String password;
	
	// 1 表示可用 2 表示删除
	private Integer status;

	private Long created;

	private String salt;     

	@ManyToOne
	@JoinColumn(name = "roleid")
	private Role role;

	//数字令牌
	@OneToOne
	@JoinColumn(name = "otp_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OnetimePassword onetimePassword;

	@Transient
	private String captcha;

	@Transient
	private Date createDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		if (created != null && created > 0) {
			return new Date(created);
		}
		return new Date();
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public OnetimePassword getOnetimePassword() {
		return onetimePassword;
	}

	public void setOnetimePassword(OnetimePassword onetimePassword) {
		this.onetimePassword = onetimePassword;
	}
}
