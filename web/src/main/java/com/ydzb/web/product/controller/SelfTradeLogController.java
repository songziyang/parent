package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.SelfTradeLog;
import com.ydzb.product.service.ISelfRevenueService;
import com.ydzb.product.service.ISelfTradeLogService;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.product.condition.SelfRevenueCondition;
import com.ydzb.web.product.condition.SelfTradeLogCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 自主交易记录-控制层
 */
@Controller
@RequestMapping("product/selfTradeLog")
public class SelfTradeLogController extends BaseController {

	@Autowired
	private ISelfTradeLogService tradeLogService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISelfRevenueService selfRevenueService;

	/**
	 * 分页查询
	 * @param pageSize 每页显示几条
	 * @param pageCurrent 当前页数-从0开始
	 * @param condition
	 * @param tradeLogCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("selftradelog_list")
	public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute("condition") String condition,
			@ModelAttribute("tradeLogCondition") SelfTradeLogCondition tradeLogCondition,
			Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (StringUtils.isNotEmpty(condition)) {
			tradeLogCondition = gson.fromJson(condition, SelfTradeLogCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(tradeLogCondition.getAndFilters());
		searchable.addSort(new Sort(Direction.DESC, "id"));
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		model.addAttribute("condition", gson.toJson(tradeLogCondition));
		model.addAttribute("page", tradeLogService.findAll(searchable));
		return "product/selftradelog/list";
	}

	/**
	 * 创建
	 * 
	 * @return
	 */
	@RequestMapping(value = "create", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_create")
	public String create(Model model) {
		model.addAttribute("curDate", DateUtil.getCurrentDate());
		return "product/selftradelog/edit";
	}

	/**
	 * 编辑
	 * @param id 自主交易id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_edit")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("tradeLog", tradeLogService.findOne(id));
		return "product/selftradelog/edit";
	}

	/**
	 * 保存
	 * @param session
	 * @param model
	 * @param tradeLog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@RequiresPermissions(value = { "selftradelog_create", "selftradelog_edit" }, logical = Logical.OR)
	public String save(HttpSession session, Model model, SelfTradeLog tradeLog,
			String dTradeDate) throws Exception {
		String result = tradeLogService.saveOne(tradeLog, dTradeDate);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", SAVE_SUCCESS);
			return "redirect:/product/selfTradeLog/list";
		}
		session.setAttribute("error", result);
		model.addAttribute("tradeLog", tradeLog);
		return "product/selftradelog/edit";
	}

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_del")
	public String delete(@PathVariable Long id, HttpSession session)
			throws Exception {
		tradeLogService.delete(id);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/product/selfTradeLog/list";
	}

	/**
	 * 删除多个
	 * 
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "deleteOnes", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_del")
	public String delete(Long[] ids, HttpSession session) {
		tradeLogService.delete(ids);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/product/selfTradeLog/list";
	}

	/**
	 * 查询自我交易用户
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "findSelfTradeUser", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	@RequiresPermissions(value = { "selftradelog_create", "selftradelog_edit" }, logical = Logical.OR)
	@ResponseBody
	public String findUser(String condition) {
		return userService.findByUsernameOrMobile(condition, condition);
	}

	/**
	 * 买入失败
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "buyFail/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_buy")
	public String buyTradeFail(@PathVariable("id") Long id, Model model,
			HttpSession session) {
		tradeLogService.buyFail(id);
		session.setAttribute("message", "操作成功");
		return "redirect:/product/selfTradeLog/list";
	}


	/**
	 * 买入成功-跳转到买入页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("selftradelog_buy")
	public String buy(@PathVariable("id") Long id, Model model) {
		model.addAttribute("tradeLog", tradeLogService.findOne(id));
		model.addAttribute("optionDate", new Date());
		return "product/selftradelog/buy";
	}

	/**
	 * 买入
	 * @param fid 自主交易id
	 * @param tradeMoney 投资金额
	 * @param investDate 投资日期
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "buy", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("selftradelog_buy")
	public String buy(Long fid, BigDecimal tradeMoney, String investDate,
			Model model, HttpSession session) {
		String result = tradeLogService.saveOption(fid, tradeMoney, investDate);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", "操作成功");
			return "redirect:/product/selfTradeLog/list";
		}
		session.setAttribute("error", result);
		return "redirect:/product/selfTradeLog/buy/" + fid;
	}

	/**
	 * 跳转到还款（卖出）页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sales/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("selftradelog_back")
	public String salesTrade(@PathVariable("id") Long id, Model model) {
		model.addAttribute("tradeLog", tradeLogService.findOne(id));
		model.addAttribute("optionDate", new Date());
		return "product/selftradelog/sales";
	}

	/**
	 * 还款（卖出）
	 * @param fid
	 * @param state
	 * @param endDate
	 * @param remark
	 * @param revenue
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "sales", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("selftradelog_back")
	public String sales(Long fid, Byte state, String endDate, String remark,
			BigDecimal revenue, Model model, HttpSession session) {
		SelfTradeLog tradeLog = tradeLogService.findOne(fid);
		//如果还款金额为负数，并且负收益大于投资金额
		if (revenue.compareTo(BigDecimal.ZERO) < 0 && (tradeLog.getTradeMoney().add(revenue)).compareTo(BigDecimal.ZERO) < 0) {
			session.setAttribute("message", "负收益不能大于投资金额");
			return "redirect:/product/selfTradeLog/sales/" + fid;
		}
		tradeLogService.saveSales(fid, state, endDate, remark, revenue);
		session.setAttribute("message", "操作成功");
		return "redirect:/product/selfTradeLog/list";
	}

	/**
	 * 还款记录
	 * @param pageSize
	 * @param pageCurrent
	 * @param condition
	 * @param selfRevenueCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listSelfRevenue", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("selftradelog_list")
	public String listSelfRevenue(
			@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
			String condition, SelfRevenueCondition selfRevenueCondition,
			Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (StringUtils.isNotEmpty(condition)) {
			selfRevenueCondition = gson.fromJson(condition,SelfRevenueCondition.class);
		}
		selfRevenueCondition.setStatus(null);	//查询所有状态
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(selfRevenueCondition.getAndFilters());
		searchable.addSort(new Sort(Direction.DESC, "id"));
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);

		model.addAttribute("condition", gson.toJson(selfRevenueCondition));
		model.addAttribute("page", selfRevenueService.findAll(searchable));
		return "product/selftradelog/deail";
	}
}