package com.ydzb.web.message.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.IMassDealService;
import com.ydzb.web.message.condition.MassDealCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * 短信群发记录控制层
 */
@Controller
@RequestMapping(value = "infoMessage/massDeal")
public class MassDealController extends BaseController {

	@Autowired
	private IMassDealService iMassDealService;
	
	/**
	 * 分页查询
	 * @param pageSize
	 * @param pageCurrent
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listMassDeal/{massId}")
	@RequiresPermissions("mass_create")
	public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
			@PathVariable Long massId, MassDealCondition massDealCondition, Model model) {
		
		massDealCondition.setMassId(massId);
		//创建查询
		Searchable searchable = Searchable.newSearchable();
		//添加查询条件
		searchable.addSearchFilters(massDealCondition.getAndFilters());
		//设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		//设置排序条件
		searchable.addSort(new Sort(Direction.ASC, "created"));
		
		model.addAttribute("page", iMassDealService.findAll(searchable));
		return "infoMessage/massdeal/list";
	}

	public IMassDealService getiMassDealService() {
		return iMassDealService;
	}

	public void setiMassDealService(IMassDealService iMassDealService) {
		this.iMassDealService = iMassDealService;
	}
}