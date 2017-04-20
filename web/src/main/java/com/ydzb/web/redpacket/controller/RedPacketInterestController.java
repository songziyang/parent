package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.service.IRedPacketInterestService;
import com.ydzb.product.service.IProductTypeService;
import com.ydzb.web.redpacket.condition.RedPacketInterestCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 红包-加息券 控制层
 *
 * @author sy
 */
@Controller
@RequestMapping("redpacket/redpacketInterest")
public class RedPacketInterestController extends BaseController {

    @Autowired
    private IRedPacketInterestService interestService;
    @Autowired
    private IProductTypeService productTypeService;

    /**
     * 列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param interestCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpinterest_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
                            @RequestParam(defaultValue = "0") int pageCurrent,
                            @ModelAttribute("condition") String condition,
                            @ModelAttribute("redPacketInterestCondition") RedPacketInterestCondition interestCondition,
                            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            interestCondition = gson.fromJson(condition, RedPacketInterestCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(interestCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(interestCondition));
        model.addAttribute("page", interestService.findAll(searchable));
        return "redpacket/redpacketinterest/list";
    }

    /**
     * 创建
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpinterest_create")
    public String create(Model model) {
        //产品类别列表
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.ASC, "id"));
        // 添加查询条件
        searchable.addSearchFilter(SearchFilterHelper.newCondition("id", SearchOperator.lt, 3));
        model.addAttribute("productTypes", productTypeService.findAll(searchable).getContent());
        return "redpacket/redpacketinterest/edit";
    }

    /**
     * 保存
     *
     * @param interest
     * @param aBeginTime  活动开始时间字符串
     * @param aFinishTime 活动结束时间字符串
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"rpinterest_create", "rpinterest_edit"}, logical = Logical.OR)
    public String save(RedPacketInterest interest, String aBeginTime, String aFinishTime,
                       HttpSession session, Model model) throws Exception {
        String result = interestService.saveOne(interest, aBeginTime, aFinishTime);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/redpacket/redpacketInterest/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("redpacketInterest", interest);
            model.addAttribute("productTypes", productTypeService.findAll(new Sort(Sort.Direction.ASC, "id")));
            return "redpacket/redpacketInterest/edit";
        }
    }

    /**
     * 编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpinterest_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("redpacketInterest", interestService.findOne(id));
        model.addAttribute("productTypes", productTypeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        return "redpacket/redpacketinterest/edit";
    }

    /**
     * 停用
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpinterest_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        interestService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/redpacket/redpacketInterest/list";
    }

    /**
     * 启用
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpinterest_del")
    public String enable(@PathVariable Long id, HttpSession session) {
        interestService.enable(id);
        session.setAttribute("message", "启用成功");
        return "redirect:/redpacket/redpacketInterest/list";
    }
}