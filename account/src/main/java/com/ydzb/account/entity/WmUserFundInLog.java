package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_user_fund_inlog")
public class WmUserFundInLog extends BaseEntity<Long> {

	private static final long serialVersionUID = -2111962716857476963L;

	public static final int TYPE_ACTIVITY_3BILLION = 43;	//30亿活动
	public static final int TYPE_RAGULAR_INCOME = 5;	//定存收益
	public static final int TYPE_RAGULAR_EXPIRE = 3;	//定存到期
	public static final int TYPE_FREE_EXPIRE = 47;	//随心存到期
	public static final int TYPE_FREE_MONTHINCOME = 48;	//随心存月收益

	@Column(name = "user_id")
	private Long userId;

	/**
	 * 类型
	 *
	 * 1充值/2现金红包领取/3定存到期/4定存赎回/5定存月收益/6活动收益/7定存红包收益/8天标天收益/9天标赎回/10白条/11天标红包收益/12提现失败/
	 * 13推荐2.0M购买返现金/14手动充值/15佷多发工资/16银多发红包/17债权转让/18聚幕购买失败、19配资交易成功，20.配置交易失败21、体验金活动
	 * 22 稳进宝 23 网贷定存交易失败 24 网贷定存交易还款 25 网贷定存交易冻结回款  26 自主交易失败 27 自主交易冻结回款 28自主交易还款
	 *
	 */
	private Integer type;

	/**
	 * 到帐时间
	 */
	@Column(name = "receipts_time")
	private Long receiptsTime;

	/**
	 * 本金
	 */
	@Column(name = "fund")
	private BigDecimal incomeFund;
	/**
	 * 收益
	 */
	@Column(name = "income_interest")
	private BigDecimal incomeInterest;

	/**
	 * 外链ID
	 */
	@Column(name = "link_id")
	private Long linkId;



	@Column(name ="usable_fund")
	private BigDecimal usableFund;

	private String remark;


	public WmUserFundInLog() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getReceiptsTime() {
		return receiptsTime;
	}

	public void setReceiptsTime(Long receiptsTime) {
		this.receiptsTime = receiptsTime;
	}

	public BigDecimal getIncomeFund() {
		return incomeFund;
	}

	public void setIncomeFund(BigDecimal incomeFund) {
		this.incomeFund = incomeFund;
	}

	public BigDecimal getIncomeInterest() {
		return incomeInterest;
	}

	public void setIncomeInterest(BigDecimal incomeInterest) {
		this.incomeInterest = incomeInterest;
	}



	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public BigDecimal getUsableFund() {
		return usableFund;
	}

	public void setUsableFund(BigDecimal usableFund) {
		this.usableFund = usableFund;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}