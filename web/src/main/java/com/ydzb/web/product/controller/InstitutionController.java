package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.ProductType;
import com.ydzb.product.entity.RagularRefund;
import com.ydzb.product.entity.RagularUserAccount;
import com.ydzb.product.service.IRagularRefundService;
import com.ydzb.product.service.IRagularUserAccountService;
import com.ydzb.web.product.condition.InstitutionCondition;
import com.ydzb.web.product.condition.YongjinmenCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.math.BigDecimal;
import java.util.Map;


/**
 * 机构控制层
 * @author sy
 */
@Controller
@RequestMapping(value = "/product/institution")
public class InstitutionController extends BaseController {
	
	@Autowired
	private IRagularUserAccountService ragularUserAccountService;
	@Autowired
	private IRagularRefundService ragularRefundService;
/*	@Autowired
	private IDepositDealSettlementService depositDealSettlementService;
	@Autowired
	DepositAccountRepository depositAccountRepository;*/
	
	//利多宝webservice地址
	private static final String SERVERURI = "http://218.244.133.47:18250/webservice";
	
	private static final boolean MUST_EXIST_TIME = true;	//必须存在起始时间
	private static final boolean SHOULD_EXIST_TIME = false;	//可以存在起始时间（不必须存在）
	
	/**
	 * 麦罗产品
	 * @param pageSize
	 * @param pageCurrent
	 * @param condition
	 * @param institutionCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listMailuo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("institutionml_list")
	public String mlPageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute InstitutionCondition institutionCondition, Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			institutionCondition = gson.fromJson(condition, InstitutionCondition.class);
		}
		
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(institutionCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "x.created"));
		model.addAttribute("condition", gson.toJson(institutionCondition));
		model.addAttribute("page", ragularUserAccountService.findAll(searchable));

		//当日投资金额
		model.addAttribute("todayBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.MAILUO_PRODUCTS, null,
				DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()),
				DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())), SHOULD_EXIST_TIME));
		//累计投资金额
		model.addAttribute("totalBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.MAILUO_PRODUCTS));
		//累计交易笔数
		model.addAttribute("totalTradeNum", ragularUserAccountService.getTotalTradeNum(ProductType.MAILUO_PRODUCTS));
		//已支出收益
		model.addAttribute("paidRevenue", ragularRefundService.getTotalRevenue(ProductType.MAILUO_PRODUCTS, RagularRefund.REPAYMENT));
		//预支出收益
		model.addAttribute("prepaidRevenue", ragularRefundService.getTotalRevenue(ProductType.MAILUO_PRODUCTS, RagularRefund.NOT_REPAYMENT));
		getTotalBuyFund(model, institutionCondition);
		getClosingFund(model, institutionCondition);
		getClosingRevenue(model, institutionCondition);
		return "product/institution/ml_list";
	}
	
	/**
	 * 利多宝管理
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listLiduobao", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("institutionldb_list")
	public String ldbPageQuery(Model model) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SERVERURI + "/product/query");
		String jsonstr = target.request().get().readEntity(String.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = new Gson().fromJson(jsonstr, Map.class);
		model.addAttribute("totalBuyCopies", data.get("totalBuyCopies"));
		model.addAttribute("yesterdayBuyCopies", data.get("yesterdayBuyCopies"));
		model.addAttribute("closingBuyCopies", data.get("closingBuyCopies"));
		model.addAttribute("yesterdayClosingCopies", data.get("yesterdayClosingCopies"));
		model.addAttribute("expireRevenue", data.get("expireRevenue"));
		model.addAttribute("yesterdayRevenue", data.get("yesterdayRevenue"));
		return "product/institution/ldb_list";
	}

	/**
	 * 涌金门产品
	 * @param pageSize
	 * @param pageCurrent
	 * @param condition
	 * @param yongjinmenCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listYongjinmen", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("institutionyjm_list")
	public String yjmPageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute YongjinmenCondition yongjinmenCondition, Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			yongjinmenCondition = gson.fromJson(condition, YongjinmenCondition.class);
		}

		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(yongjinmenCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "x.created"));
		model.addAttribute("condition", gson.toJson(yongjinmenCondition));
		model.addAttribute("page", ragularUserAccountService.findAll(searchable));

		//当日投资金额
		model.addAttribute("todayBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.YJM, null,
				DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()),
				DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())), SHOULD_EXIST_TIME));
		//累计投资金额
		model.addAttribute("totalBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.YJM));
		//累计交易笔数
		model.addAttribute("totalTradeNum", ragularUserAccountService.getTotalTradeNum(ProductType.YJM));
		//已支出收益
		model.addAttribute("paidRevenue", ragularRefundService.getTotalRevenue(ProductType.YJM, RagularRefund.REPAYMENT));
		//预支出收益
		model.addAttribute("prepaidRevenue", ragularRefundService.getTotalRevenue(ProductType.YJM, RagularRefund.NOT_REPAYMENT));
		getTotalBuyFund(model, yongjinmenCondition, ProductType.YJM);
		getClosingFund(model, yongjinmenCondition, ProductType.YJM);
		getClosingRevenue(model, yongjinmenCondition, ProductType.YJM);
		return "product/institution/yjm_list";
	}

	/**
	 * 银多金贸街特供
	 * @param pageSize
	 * @param pageCurrent
	 * @param condition
	 * @param yongjinmenCondition
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listJmjtg", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("institutionjmjtg_list")
	public String jmjtgPageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute YongjinmenCondition yongjinmenCondition, Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			yongjinmenCondition = gson.fromJson(condition, YongjinmenCondition.class);
		}
		yongjinmenCondition.setProductType(ProductType.JMJ);
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(yongjinmenCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "x.created"));
		model.addAttribute("condition", gson.toJson(yongjinmenCondition));
		model.addAttribute("page", ragularUserAccountService.findAll(searchable));

		//当日投资金额
		model.addAttribute("todayBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.JMJ, null,
				DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()),
				DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())), SHOULD_EXIST_TIME));
		//累计投资金额
		model.addAttribute("totalBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.JMJ));
		//累计交易笔数
		model.addAttribute("totalTradeNum", ragularUserAccountService.getTotalTradeNum(ProductType.JMJ));
		//已支出收益
		model.addAttribute("paidRevenue", ragularRefundService.getTotalRevenue(ProductType.JMJ, RagularRefund.REPAYMENT));
		//预支出收益
		model.addAttribute("prepaidRevenue", ragularRefundService.getTotalRevenue(ProductType.JMJ, RagularRefund.NOT_REPAYMENT));
		getTotalBuyFund(model, yongjinmenCondition, ProductType.JMJ);
		getClosingFund(model, yongjinmenCondition, ProductType.JMJ);
		getClosingRevenue(model, yongjinmenCondition, ProductType.JMJ);
		return "product/institution/jmjtg_list";
	}


	/**
	 * 根据购买起始时间获得总购买金额
	 * @param model
	 * @param institutionCondition
	 */
	private void getTotalBuyFund(Model model, InstitutionCondition institutionCondition) {
		String startTime = institutionCondition.getStartTime();
		String endTime = institutionCondition.getEndTime();
		model.addAttribute("timeBuyFund", ragularUserAccountService.findTotalBuyFund(ProductType.MAILUO_PRODUCTS, null,
				StringUtils.isEmpty(startTime) ? 0L : DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime) ? 0L : DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}

	/**
	 * 根据购买起始时间获得总购买金额
	 * @param model
	 * @param yongjinmenCondition
	 */
	private void getTotalBuyFund(Model model, YongjinmenCondition yongjinmenCondition, Long type) {
		String startTime = yongjinmenCondition.getStartTime();
		String endTime = yongjinmenCondition.getEndTime();
		model.addAttribute("timeBuyFund", ragularUserAccountService.findTotalBuyFund(type, null,
				StringUtils.isEmpty(startTime) ? 0L : DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime) ? 0L :  DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}

	/**
	 * 根据到期起始时间获得总到期金额
	 * @param model
	 * @param institutionCondition
	 */
	private void getClosingFund(Model model, InstitutionCondition institutionCondition) {
		String startTime = institutionCondition.getClosingStartTime();
		String endTime = institutionCondition.getClosingEndTime();
		model.addAttribute("timeClosingFund", ragularUserAccountService.getClosingFund(ProductType.MAILUO_PRODUCTS, RagularUserAccount.NOT_EXPIRE,
				StringUtils.isEmpty(startTime) ? 0L : DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime) ? 0L : DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}

	/**
	 * 根据到期起始时间获得总到期金额
	 * @param model
	 * @param yongjinmenCondition
	 */
	private void getClosingFund(Model model, YongjinmenCondition yongjinmenCondition, Long type) {
		String startTime = yongjinmenCondition.getClosingStartTime();
		String endTime = yongjinmenCondition.getClosingEndTime();
		model.addAttribute("timeClosingFund", ragularUserAccountService.getClosingFund(type, RagularUserAccount.NOT_EXPIRE,
				StringUtils.isEmpty(startTime)? 0L: DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime)? 0L: DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}

	/**
	 * 根据到期起始时间获得总到期收益
	 * @param model
	 * @param institutionCondition
	 */
	private void getClosingRevenue(Model model, InstitutionCondition institutionCondition) {
		String startTime = institutionCondition.getClosingStartTime();
		String endTime = institutionCondition.getClosingEndTime();
		model.addAttribute("timeClosingRevenue", ragularRefundService.getClosingRevenue(ProductType.MAILUO_PRODUCTS, RagularUserAccount.EXPIRE,
				StringUtils.isEmpty(startTime) ? 0L: DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime) ? 0L: DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}

	/**
	 * 根据到期起始时间获得总到期收益
	 * @param model
	 * @param yongjinmenCondition
	 */
	private void getClosingRevenue(Model model, YongjinmenCondition yongjinmenCondition, Long type) {
		String startTime = yongjinmenCondition.getClosingStartTime();
		String endTime = yongjinmenCondition.getClosingEndTime();
		model.addAttribute("timeClosingRevenue", ragularRefundService.getClosingRevenue(type, RagularUserAccount.EXPIRE,
				StringUtils.isEmpty(startTime)? 0L:  DateUtil.getSystemTimeDay(startTime),
				StringUtils.isEmpty(endTime)? 0L:  DateUtil.getSystemTimeDay(DateUtil.addDay(endTime)), MUST_EXIST_TIME));
	}
}