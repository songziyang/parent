package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;




@Entity
@Table(name = "wm_info_sms_log")
public class SmsLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 2991108128715920728L;
	

	//手机号码
	private String phone;
	//内容
	private String content;
	//描述
	private String description;
	//创建时间
	private Long created;

	@Transient
	private Date createDate;
	

	public SmsLog() {
	}
	
	public Date getCreateDate() {
		return DateUtil.getSystemTimeMillisecond(created);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}
}