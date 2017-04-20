package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 自主交易还款
 * @author sy
 */
@Entity
@Table(name = "wm_self_revenue")
public class SelfRevenue extends BaseEntity<Long> {

	private static final long serialVersionUID = 1687514429467497864L;

	public static final byte CHECKING = 0;	//审核中
	public static final byte CHECKING_SUCCESS = 1;	//审核成功
	public static final byte CHECKING_FAILURE = 2;	//审核失败
	public static final byte IS_FINAL_DEAL = 0;	//是最后一笔还款
	public static final byte ISNOT_FINAL_DEAL = 1;	//不是最后一笔还款

	@ManyToOne
	@JoinColumn(name = "self_id")
	private SelfTradeLog selfTradeLog;

	private BigDecimal revenue;

	private Long created;

	@Column(name = "create_user")
	private String createUser;

	private String remark;

	/**
	 * 审核状态：0-审核中	1-审核成功	2-审核失败
	 */
	private Byte status = CHECKING;

	/**
	 * 审核人
	 */
	@Column(name = "check_user")
	private String checkUser;

	/**
	 * 审核时间
	 */
	@Column(name = "check_time")
	private Long checkTime;

	/**
	 * 是否是最后一笔交易
	 */
	@Column(name = "final_deal" )
	private Byte finalDeal = ISNOT_FINAL_DEAL;

	@Transient
	private Date createDate;
	
	public SelfTradeLog getSelfTradeLog() {
		return selfTradeLog;
	}

	public void setSelfTradeLog(SelfTradeLog selfTradeLog) {
		this.selfTradeLog = selfTradeLog;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return DateUtil.getSystemTimeMillisecond(created);
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Byte getFinalDeal() {
		return finalDeal;
	}

	public void setFinalDeal(Byte finalDeal) {
		this.finalDeal = finalDeal;
	}

	public Long getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
	}
}