package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.SelfRevenue;
import com.ydzb.product.entity.SelfTradeLog;
import com.ydzb.product.repository.ISelfTradeLogRepository;
import com.ydzb.product.service.ISelfRevenueService;
import com.ydzb.product.service.ISelfTradeLogService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserInvestinfo;
import com.ydzb.user.entity.UserMoney;
import com.ydzb.user.repository.IUserMoneyRepository;
import com.ydzb.user.repository.IUserRepository;
import com.ydzb.user.service.IUserFundInLogService;
import com.ydzb.user.service.IUserFundRecordService;
import com.ydzb.user.service.IUserIncomeService;
import com.ydzb.user.service.IUserInvestinfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * 自主交易记录-service实现
 */
@Service
public class SelfTradeLogServiceImpl extends BaseServiceImpl<SelfTradeLog, Long> implements ISelfTradeLogService {

	@Autowired
	private ISelfTradeLogRepository tradeLogRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IUserMoneyRepository userMoneyRepository;
	@Autowired
	private IUserFundInLogService userFundInLogService;
	@Autowired
	private ISelfRevenueService selfRevenueService;
	@Autowired
	private IUserFundRecordService userFundRecordService;
	@Autowired
	private IUserInvestinfoService userInvestinfoService;
	@Autowired
	private IUserIncomeService userIncomeService;

	/**
	 * 保存
	 * @param tradeLog
	 * @param dTradeDate 交易日期
	 * @return
	 */
	@Override
	public String saveOne(SelfTradeLog tradeLog, String dTradeDate) {
		if (tradeLog == null) {
			return null;
		}
		//如果是编辑
		if (tradeLog.getId() != null) {
			SelfTradeLog targetTradeLog = tradeLogRepository.findOne(tradeLog.getId());
			User user = userRepository.findOne(tradeLog.getUser().getId());
			targetTradeLog.setUser(user);
			targetTradeLog.setBuyName(tradeLog.getBuyName());
			targetTradeLog.setTradeMoney(tradeLog.getTradeMoney());
			targetTradeLog.setProType(tradeLog.getProType());
			targetTradeLog.setDescription(tradeLog.getDescription());
			targetTradeLog.setFreezeMoney(tradeLog.getFreezeMoney());
			tradeLog = targetTradeLog;
		} else {
			tradeLog.setCreated(DateUtil.getSystemTimeSeconds());
		}
		tradeLog.setTradeTime(DateUtil.getSystemTimeDay(dTradeDate));
		tradeLogRepository.save(tradeLog);
		return null;
	}

	/**
	 * 购买失败
	 * @param id
	 */
	@Override
	public void buyFail(Long id) {
		SelfTradeLog selfTradeLog = tradeLogRepository.findOne(id);
		//判断自主交易是否存在以及状态是否是交易中
		if (selfTradeLog == null || selfTradeLog.getState() != SelfTradeLog.STATE_TRADING) {
			return;
		}
		//更改交易状态为失败
		selfTradeLog.setState(SelfTradeLog.STATE_TRADING_FAILURE);
		selfTradeLog.setOptime(DateUtil.getSystemTimeSeconds());
		this.update(selfTradeLog);
		Long userId = selfTradeLog.getUser().getId();
		// 修改用户资金
		UserMoney userMoney = userMoneyRepository.findUserMoney(userId);
		if (userMoney == null) {
			return;
		}
		//可用余额 = 当前可用余额 + 冻结资金
		userMoney.setUsableFund(userMoney.getUsableFund().add(selfTradeLog.getFreezeMoney()));
		userMoneyRepository.save(userMoney);
		//保存用户资金入账
		userFundInLogService.saveUserFundInLog(userId, selfTradeLog.getId(),
				Integer.valueOf(SelfTradeLog.SELF_TRADE_FAILURE),
				selfTradeLog.getFreezeMoney(), BigDecimal.ZERO, userMoney.getUsableFund(), null);
		//保存用户资金记录
		userFundRecordService.saveUserFundRecord(userId, "私人订制退款",
				selfTradeLog.getFreezeMoney(), 1, 7, userMoney.getUsableFund(), selfTradeLog.getId() );
		//修改投资金额
		UserInvestinfo investinfo = userInvestinfoService.queryByUser(userId);
		investinfo.setAllInvest(investinfo.getAllInvest().subtract(selfTradeLog.getFreezeMoney()));
		investinfo.setAllInvestSelf(investinfo.getAllInvestSelf().subtract(selfTradeLog.getFreezeMoney()));
		userInvestinfoService.save(investinfo);

	}

	/**
	 * 交易成功-保存自主交易
	 * @param fid
	 * @param tradeMoney
	 * @param investDate
	 * @return
	 */
	@Override
	public String saveOption(Long fid, BigDecimal tradeMoney, String investDate) {
		//查询自主交易
		SelfTradeLog selfTradeLog = tradeLogRepository.findOne(fid);
		//判断自主交易是否存在以及状态是否是交易中
		if (selfTradeLog == null || selfTradeLog.getState() != SelfTradeLog.STATE_TRADING) {
			return null;
		}
		if (selfTradeLog.getFreezeMoney().compareTo(tradeMoney) < 0) {
			return "投资金额应小于等于冻结金额";
		}
		selfTradeLog.setState(SelfTradeLog.STATE_TRADING_SUCCESS);
		selfTradeLog.setInvestTime(DateUtil.getSystemTimeDay(investDate));
		selfTradeLog.setTradeMoney(tradeMoney);
		selfTradeLog.setOptime(DateUtil.getSystemTimeSeconds());
		selfTradeLog.setReturnMoney(tradeMoney);
		update(selfTradeLog);
		Long userId = selfTradeLog.getUser().getId();
		//判断投资额是否等于冻结金额，如果相等，则不需要修改用户资金
		if (selfTradeLog.getFreezeMoney().compareTo(tradeMoney) == 0) {
			return null;
		}

		//修改用户资金
		UserMoney userMoney = userMoneyRepository.findUserMoney(userId);
		if (userMoney == null) {
			return null;
		}
		//可用金额= 现有可用金额 + 冻结金额 - 投资金额
		userMoney.setUsableFund(userMoney.getUsableFund().add(selfTradeLog.getFreezeMoney().subtract(tradeMoney)));
		userMoneyRepository.save(userMoney);
		//用户进账日志
		userFundInLogService.saveUserFundInLog(userId, selfTradeLog.getId(),
				Integer.valueOf(SelfTradeLog.SELF_TRADE_FORZEN_BACK),
				selfTradeLog.getFreezeMoney().subtract(tradeMoney),
				BigDecimal.ZERO, userMoney.getUsableFund(), null);
		//用户资金记录
		userFundRecordService.saveUserFundRecord(userId, "私人订制退款",
				selfTradeLog.getFreezeMoney().subtract(tradeMoney), 1, 7, userMoney.getUsableFund(), selfTradeLog.getId() );
		//用户投资信息
		//投资金额= 当前投资金额 - (冻结金额 - 投资金额)
		UserInvestinfo investinfo = selfTradeLog.getUser().getUserInvestinfo();
		investinfo.setAllInvest(investinfo.getAllInvest().subtract(selfTradeLog.getFreezeMoney().subtract(tradeMoney)));
		investinfo.setAllInvestSelf(investinfo.getAllInvestSelf().subtract(selfTradeLog.getFreezeMoney().subtract(tradeMoney)));
		userInvestinfoService.save(investinfo);
		return null;
	}

	/**
	 * 还款
	 * @param fid
	 * @param state
	 * @param endDate
	 * @param remark
	 * @param revenue
	 */
	@Override
	public void saveSales(Long fid, Byte state, String endDate,
			String remark, BigDecimal revenue) {
		//查询自主交易
		SelfTradeLog selfTradeLog = tradeLogRepository.findOne(fid);
		//判断自主交易是否存在以及状态是否是交易中
		if (selfTradeLog == null || selfTradeLog.getState() != SelfTradeLog.STATE_TRADING_SUCCESS){
			return;
		}
		//设置自主投资状态
		if (StringUtils.isNotEmpty(endDate)) {
			selfTradeLog.setEndDate(DateUtil.getSystemTimeDay(endDate));
		}
		update(selfTradeLog);
		Byte finalDeal = state == SelfTradeLog.STATE_TRADING_FINISHED? SelfRevenue.IS_FINAL_DEAL: SelfRevenue.ISNOT_FINAL_DEAL;
		selfRevenueService.saveOne(selfTradeLog, revenue, remark, finalDeal);
		if (state == SelfTradeLog.STATE_TRADING_FINISHED) {
			//设置状态为审核中
			selfTradeLog.setPrincipalStatus(SelfTradeLog.PRINCIPAL_CHECKING);
			update(selfTradeLog);
		}
	}
}
