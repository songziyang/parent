package com.ydzb.sms.entity;

import com.ydzb.core.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 短信群发记录实体
 */
@Entity
@Table(name = "wm_info_mass_deal")
public class MassDeal extends BaseEntity<Long> implements java.io.Serializable {

	private static final long serialVersionUID = -2084928513938253543L;

	@Column(name = "mass_id")
	private Long massId;	//群发模板id

	@Column
	private String mobile;	//手机号

	@Column
	private Date created;	//创建时间

	/**
	 * 构造方法
	 */
	public MassDeal() {
		
	}
	
	/**
	 * 构造方法
	 * @param massId
	 * @param mobile
	 */
	public MassDeal(Long massId, String mobile) {
		this.massId = massId;
		this.mobile = mobile;
	}
	

	public Long getMassId() {
		return massId;
	}

	public void setMassId(Long massId) {
		this.massId = massId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}