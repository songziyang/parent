package com.ydzb.product.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.SelfRevenue;
import com.ydzb.product.entity.SelfTradeLog;
import com.ydzb.product.repository.ISelfRevenueRepository;
import com.ydzb.product.repository.ISelfTradeLogRepository;
import com.ydzb.product.service.ISelfRevenueService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserIncome;
import com.ydzb.user.entity.UserInvestinfo;
import com.ydzb.user.entity.UserMoney;
import com.ydzb.user.repository.IUserMoneyRepository;
import com.ydzb.user.service.IUserFundInLogService;
import com.ydzb.user.service.IUserFundRecordService;
import com.ydzb.user.service.IUserIncomeService;
import com.ydzb.user.service.IUserInvestinfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * 自主交易还款service实体类
 */
@Service
public class SelfRevenueServiceImpl extends BaseServiceImpl<SelfRevenue, Long>
		implements ISelfRevenueService {

	@Autowired
	private ISelfRevenueRepository selfRevenueRepository;
	@Autowired
	private ISelfTradeLogRepository selfTradeLogRepository;
	@Autowired
	private IUserFundInLogService userFundInLogService;
	@Autowired
	private IUserIncomeService userIncomeService;
	@Autowired
	private IUserFundRecordService userFundRecordService;
	@Autowired
	private IUserInvestinfoService userInvestinfoService;
	@Autowired
	private IUserMoneyRepository userMoneyRepository;

	/**
	 * 保存自主交易还款
	 * @param selfTradeLog 自主交易
	 * @param revenue 收益
	 * @param remark 描述
	 */
	@Override
	public void saveOne(SelfTradeLog selfTradeLog, BigDecimal revenue, String remark, Byte finalDeal) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		SelfRevenue selfRevenue = new SelfRevenue();
		selfRevenue.setSelfTradeLog(selfTradeLog);
		selfRevenue.setRevenue(revenue);
		selfRevenue.setCreated(DateUtil.getSystemTimeSeconds());
		selfRevenue.setCreateUser(user.getUsername());
		selfRevenue.setRemark(remark);
		selfRevenue.setFinalDeal(finalDeal);
		selfRevenueRepository.save(selfRevenue);
	}

	/**
	 * 保存自主交易还款
	 * @param selfTradeId 自主交易id
	 * @param revenue 收益
	 * @param remark 描述
	 */
	@Override
	public void saveOne(Long selfTradeId, BigDecimal revenue, String remark, Byte finalDeal) {
		SelfTradeLog selfTradeLog = selfTradeLogRepository.findOne(selfTradeId);
		saveOne(selfTradeLog, revenue, remark, finalDeal);
	}

	/**
	 * 审核
	 * @param revenueId 私人订制还款id
	 * @param status 审核状态
	 */
	@Override
	public void approve(Long revenueId, Byte status){
		SelfRevenue selfRevenue = updateSelfRevenue(revenueId, status);
		//如果审核成功
		if (status == SelfRevenue.CHECKING_SUCCESS) {
			checkSuccess(selfRevenue);
		} else if (status == SelfRevenue.CHECKING_FAILURE){
			checkFail(selfRevenue);
		}
	}

	/**
	 * 审核成功
	 * @param selfRevenue 私人订制还款
	 */
	private void checkSuccess(SelfRevenue selfRevenue) {
		if (selfRevenue == null) {
			return;
		}
		SelfTradeLog selfTradeLog = selfRevenue.getSelfTradeLog();
		if (selfTradeLog == null) {
			return;
		}
		User destUser = selfTradeLog.getUser();
		if (destUser == null) {
			return;
		}
		UserMoney userMoney = destUser.getUserMoney();
		UserIncome userIncome = destUser.getUserIncome();
		if (userMoney == null || userIncome == null) {
			return;
		}
		returnRevenue(selfRevenue.getRevenue(), userMoney,selfTradeLog, destUser, userIncome);
		returnPrincipal(selfRevenue, selfTradeLog, userMoney, destUser);
	}

	/**
	 * 归还收益
	 * @param revenue 还款金额
	 * @param userMoney 用户资金
	 * @param selfTradeLog 私人订制
	 * @param destUser 目标用户
	 * @param userIncome 用户收益
	 */
	private void returnRevenue(BigDecimal revenue, UserMoney userMoney, SelfTradeLog selfTradeLog,
			User destUser, UserIncome userIncome) {
		//如果还款金额不等于0
		if (revenue.compareTo(BigDecimal.ZERO) != 0) {
			//如果还款金额大于0，则修改用户余额
			if (revenue.compareTo(BigDecimal.ZERO) > 0) {
				userMoney.setUsableFund(userMoney.getUsableFund().add(revenue));
			}//如果还款金额小于0，则从还款金额中扣除负收益
			else if (revenue.compareTo(BigDecimal.ZERO) < 0) {
				selfTradeLog.setReturnMoney(selfTradeLog.getReturnMoney().add(revenue));
			}
			//修改用户总资产
			userMoney.setTotalFund(userMoney.getTotalFund().add(revenue));
			userMoneyRepository.save(userMoney);
			//添加用户进账日志
			userFundInLogService.saveUserFundInLog(destUser.getId(), selfTradeLog.getId(),
					Integer.valueOf(SelfTradeLog.SELF_TRADE_BACK),
					revenue, BigDecimal.ZERO, userMoney.getUsableFund(), null);
			//修改用户收益（只更改私人订制收益，不更改总收益）
			userIncome.setAllIncomeSelf(userIncome.getAllIncomeSelf().add(revenue));
			userIncomeService.save(userIncome);
			//添加资金记录
			userFundRecordService.saveUserFundRecord(destUser.getId(), "私人订制收益", revenue, 1, 3,
					userMoney.getUsableFund(), selfTradeLog.getId());
		}

	}

	/**
	 * 返还本金
	 * @param selfRevenue 私人订制还款
	 * @param selfTradeLog 私人订制
	 * @param userMoney 用户资金
	 * @param destUser 目标用户
	 */
	private void returnPrincipal(SelfRevenue selfRevenue, SelfTradeLog selfTradeLog, UserMoney userMoney, User destUser) {
		//如果还款结束，则返还本金
		if (selfRevenue.getFinalDeal() == SelfRevenue.IS_FINAL_DEAL) {
			//修改用户余额
			userMoney.setUsableFund(userMoney.getUsableFund().add(selfTradeLog.getReturnMoney()));
			userMoneyRepository.save(userMoney);
			//添加用户进账日志
			userFundInLogService.saveUserFundInLog(destUser.getId(), selfTradeLog.getId(),
					Integer.valueOf(SelfTradeLog.SELF_TRADE_BACK),
					selfTradeLog.getReturnMoney(), BigDecimal.ZERO, userMoney.getUsableFund(), null);
			//添加资金记录
			userFundRecordService.saveUserFundRecord(destUser.getId(), "私人订制到期", selfTradeLog.getReturnMoney(),
					1, 7, userMoney.getUsableFund(), selfTradeLog.getId());
			//修改投资信息
			UserInvestinfo investinfo = destUser.getUserInvestinfo();
			investinfo.setAllInvest(investinfo.getAllInvest().subtract(selfTradeLog.getTradeMoney()));
			investinfo.setAllInvestSelf(investinfo.getAllInvestSelf().subtract(selfTradeLog.getTradeMoney()));
			userInvestinfoService.save(investinfo);
			//改变自主投资状态
			selfTradeLog.setPrincipalStatus(SelfTradeLog.PRINCIPAL_PAID);
			selfTradeLog.setState(SelfTradeLog.STATE_TRADING_FINISHED);
			selfTradeLogRepository.save(selfTradeLog);
		}
	}


	/**
	 * 审核失败
	 * @param selfRevenue 私人订制还款
	 */
	private void checkFail(SelfRevenue selfRevenue) {
		if (selfRevenue == null) {
			return;
		}
		/**
		 * 如果归还本金，则重置私人订制还本金状态为未归还
		 * 如果不还本金，则不用操作
		 */
		if (selfRevenue.getFinalDeal() == SelfRevenue.IS_FINAL_DEAL) {
			//更改自主交易状态
			SelfTradeLog selfTradeLog = selfRevenue.getSelfTradeLog();
			if (selfTradeLog == null) {
				return;
			}
			selfTradeLog.setPrincipalStatus(SelfTradeLog.PRINCIPAL_NOPAY);
			selfTradeLogRepository.save(selfTradeLog);
		}

	}

	/**
	 * 更新私人订制还款
	 * @param revenueId 私人订制还款Id
	 * @param status 还款状态
	 * @return
	 */
	private SelfRevenue updateSelfRevenue(Long revenueId, Byte status) {
		if (revenueId == null) {
			return null;
		}
		//保存私人订制还款记录
		SelfRevenue selfRevenue = selfRevenueRepository.findOne(revenueId);
		if (selfRevenue == null) {
			return null;
		}
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		selfRevenue.setCheckUser(user == null? "未知": user.getUsername());
		selfRevenue.setCheckTime(DateUtil.getSystemTimeSeconds());
		selfRevenue.setStatus(status);
		selfRevenueRepository.save(selfRevenue);
		return selfRevenue;
	}
}