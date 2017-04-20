package com.ydzb.web.platform.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformInvestMapService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("platform/platforminvestmap")
public class PlatformInvestMapController extends BaseController {

    @Autowired
    private IPlatformInvestMapService platformInvestMapService;

    /**
     * 在投分布图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "listInvestMap", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platforminvestmap_list")
    public String listInvestMap(Model model) {
        model.addAttribute("investFunds", platformInvestMapService.findInvestFunds());
        model.addAttribute("investPersons", platformInvestMapService.findInvestPersons());
        return "product/platforminvestmap/list";
    }

    /**
     * 在投省份分布图
     *
     * @param pname
     * @param model
     * @return
     */
    @RequestMapping(value = "investProvince", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platforminvestmap_list")
    public String investProvince(String pname, Model model) throws Exception {
        if (StringUtils.isNotEmpty(pname)) {
            pname = new String(URLDecoder.decode(pname,"UTF-8"));
        }
        model.addAttribute("pname", pname);
        model.addAttribute("investFunds", platformInvestMapService.findInvestFundsByPName(pname));
        model.addAttribute("investPersons", platformInvestMapService.findInvestPersonsByPName(pname));
        return "product/platforminvestmap/province";
    }

    /**
     * 累计充值分布图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "listRechargeMap", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformrechagemap_list")
    public String listRechargeMap(Model model) {
        model.addAttribute("rechargeFunds", platformInvestMapService.findRechargeFunds());
        model.addAttribute("rechargePersons", platformInvestMapService.findRechargePersons());
        return "product/platformrechagemap/list";
    }


    /**
     * 累计充值分布图
     *
     * @param pname
     * @param model
     * @return
     */
    @RequestMapping(value = "rechargeProvince", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformrechagemap_list")
    public String rechargeProvince(String pname, Model model) throws Exception {
        if (StringUtils.isNotEmpty(pname)) {
            pname = new String(pname.getBytes("ISO8859-1"), "UTF-8");
        }
        model.addAttribute("rechargeFunds", platformInvestMapService.findRechargeFundsByPName(pname));
        model.addAttribute("rechargePersons", platformInvestMapService.findRechargePersonsByPName(pname));
        model.addAttribute("pname", pname);
        return "product/platformrechagemap/province";
    }

    /**
     * 性别和年龄比例
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "listSex", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformsex_list")
    public String listSex(Model model) {
        model.addAttribute("sexnum", platformInvestMapService.findSexNum());
        model.addAttribute("agenum", platformInvestMapService.findAgeNum());
        return "product/platformsex/list";
    }



    /**
     * 根据条件分页查询在投统计信息
     * @param pageSize
     * @param pageCurrent
     * @param model
     * @return
     */
    @RequestMapping(value = "listInvest/{province}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platforminvestmap_list")
    public String pageQuery(
            @ModelAttribute(value = "province") String province,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            Model model) {

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("page", platformInvestMapService.getInvestList(searchable, province));
        model.addAttribute("province", province);
        return "product/platforminvestmap/provincelist";
    }


    /**
     * 导出在投统计数据
     * @param province
     * @param city
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{province}/{city}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platforminvestmap_list")
    public String exportExcel(
            @ModelAttribute(value = "province") String province,
            @ModelAttribute(value = "city") String city,
            HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        province = URLDecoder.decode(province, "UTF-8");
        city = URLDecoder.decode(city, "UTF-8");

        String path = request.getSession().getServletContext().getRealPath("/static/download");
        List<Object[]> list = platformInvestMapService.getExportData(province, city);
        String fileName = platformInvestMapService.exportExcel(list, path, province, city);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/platform/platforminvestmap/listInvest/" + URLEncoder.encode(province, "utf-8");
    }


    public IPlatformInvestMapService getPlatformInvestMapService() {
        return platformInvestMapService;
    }

    public void setPlatformInvestMapService(IPlatformInvestMapService platformInvestMapService) {
        this.platformInvestMapService = platformInvestMapService;
    }
}