package com.ydzb.product.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 自主交易记录
 * @author sy
 */
@Entity
@Table(name = "wm_self_tradelog")
@DynamicUpdate
public class SelfTradeLog extends BaseEntity<Long> {

	private static final long serialVersionUID = -2954842086127410974L;
	
	public static final byte STATE_TO_BE_CONFIRM = 1;		//待确认
	public static final byte STATE_TRADING = 2;				//交易中
	public static final byte STATE_TRADING_FAILURE = 3;		//交易失败
	public static final byte STATE_TRADING_SUCCESS = 4;		//交易成功
	public static final byte STATE_TRADING_FINISHED = 5;	//交易结束

	public static final byte SELF_TRADE_FAILURE = 26;	//自主交易失败
	public static final byte SELF_TRADE_FORZEN_BACK = 27;	//自主交易冻结回款
	public static final byte SELF_TRADE_BACK = 28;	//自主交易还款

	public static final byte PRINCIPAL_NOPAY = 1;	//本金未归还
	public static final byte PRINCIPAL_CHECKING = 2;	//本金归还审核中
	public static final byte PRINCIPAL_PAID = 3;	//本金已归还


	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;	//用户
	
	@Column(name = "buy_name")
	private String buyName;	//所购产品名称
	
	@Column(name = "trade_date")
	private Long tradeTime;	//交易日期 毫秒数
	
	@Column(name = "invest_date")
	private Long investTime;	//投资日期 毫秒数
	
	@Column(name = "trade_money")
	private BigDecimal tradeMoney;	//投资金额

	@Column(name = "return_money")
	private BigDecimal returnMoney;	//还款金额
	
	@Column(name = "pro_type")
	private Byte proType;	//资产类型： 1 债权投资  2股票投资  3、股权投资 4、基金投资',
	
	@Column(name = "freeze_money")
	private BigDecimal freezeMoney ; //冻结金额
	
	@Column(name = "confirm_time")
	private Long confirmTime;   //确认时间

	@Column(name = "end_date")
	private Long endDate;   //结束日期
	 
	private Byte state = STATE_TO_BE_CONFIRM; //1、待确认 2、交易中3、交易失败 4、交易成功 5、交易结束

	private String description;	//产品详情

	@Column(name = "principal_status")
	private Byte principalStatus = PRINCIPAL_NOPAY ;	//归还本金状态	1-未归还	2-审核中	3-已归还

	/**
	 * 创建时间
	 */
	private Long created;

	/**
	 * 操作时间
	 */
	private Long optime;

	@Transient
	private Date tradeDate;	//交易日期
	
	@Transient
	private Date investDate;	//投资日期
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public Long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Long getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Long investTime) {
		this.investTime = investTime;
	}

	public BigDecimal getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(BigDecimal tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Byte getProType() {
		return proType;
	}

	public void setProType(Byte proType) {
		this.proType = proType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Date getTradeDate() {
		return DateUtil.getSystemTimeMillisecond(tradeTime);
	}

	public Date getInvestDate() {
		return DateUtil.getSystemTimeMillisecond(investTime);
	}

	public BigDecimal getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getOptime() {
		return optime;
	}

	public void setOptime(Long optime) {
		this.optime = optime;
	}

	public Byte getPrincipalStatus() {
		return principalStatus;
	}

	public void setPrincipalStatus(Byte principalStatus) {
		this.principalStatus = principalStatus;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}
}