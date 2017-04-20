package com.ydzb.web.message.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.IMassDealService;
import com.ydzb.message.service.IMassService;
import com.ydzb.sms.entity.Mass;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.web.message.condition.MassCondition;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;


/**
 * 群发短信模板控制层
 */
@Controller
@RequestMapping(value = "infoMessage/mass")
public class MassController extends BaseController {

    @Autowired
    private IMassService iMassService;
    @Autowired
    private IMassDealService iMassDealService;
    @Autowired
    private ISmsHandleService iSmsHandleService;

    /**
     * 分页查询
     *
     * @param pageSize      每页显示几条
     * @param pageCurrent   当前页-从0开始
     * @param condition
     * @param massCondition
     * @return
     */
    @RequestMapping(value = "listMass", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            String condition, MassCondition massCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            massCondition = gson.fromJson(condition, MassCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(massCondition.getAndFilters());
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));

        model.addAttribute("condition", gson.toJson(massCondition));
        model.addAttribute("page", iMassService.findAll(searchable));
        return "infoMessage/mass/list";
    }

    /**
     * 添加群发模板
     *
     * @return
     */
    @RequestMapping(value = "createMass", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_create")
    public String create() {
        return "infoMessage/mass/edit";
    }

    /**
     * 保存群发模板
     *
     * @param session
     * @param model
     * @param mass    群发模板实体
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveMass", method = RequestMethod.POST)
    @RequiresPermissions(value = {"mass_create", "mass_edit"}, logical = Logical.OR)
    public String save(HttpSession session, Model model, Mass mass) throws Exception {
        String result = iMassService.saveMass(mass);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/infoMessage/mass/listMass";
        }
        session.setAttribute("error", result);
        model.addAttribute("mass", mass);
        return "infoMessage/mass/edit";
    }

    /**
     * 编辑群发模板
     *
     * @param id    模板Id
     * @param model
     * @return
     */
    @RequestMapping(value = "editMass/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("mass", iMassService.findOne(id));
        return "infoMessage/mass/edit";
    }

    /**
     * 删除单个群发模板
     *
     * @param id 群发模板id
     * @return
     */
    @RequestMapping(value = "deleteMass/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_del")
    public String delete(@PathVariable Long id, HttpSession session) {
        iMassService.delete(id);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/infoMessage/mass/listMass";
    }

    /**
     * 删除一个/多个群发模板
     *
     * @param ids 群发模板id
     * @return
     */
    @RequestMapping(value = "deleteMasses", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_del")
    public String delete(Long[] ids, HttpSession session) {
        iMassService.delete(ids);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/infoMessage/mass/listMass";
    }

    /**
     * 跳转至导入excel页面
     *
     * @param id    模板id
     * @param model
     * @return
     */
    @RequestMapping(value = "uploadFile/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_create")
    public String upload(@PathVariable Long id, Model model) {
        model.addAttribute("massId", id);
        return "infoMessage/mass/upload";
    }

    /**
     * 导入excel模板
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "upload", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("mass_create")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                         HttpServletRequest request) {
        String result = null;
        Workbook workbook = null;
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            Long massId = Long.parseLong(request.getParameter("massId"));
            workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0); // 获取第一张Sheet表
            String column = "手机号码";
            result = iMassDealService.sheetProcess(sheet, column, massId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                workbook.close();
            }
        }
        return StringUtils.isEmpty(result) ? "true" : result;
    }

    /**
     * 群发短信
     *
     * @param massId 群发模板id
     * @return
     */
    @RequestMapping(value = "sendSms/{massId}")
    @RequiresPermissions("mass_create")
    @ResponseBody
    public String sendSms(@PathVariable Long massId) throws Exception {
        Mass mass = iMassService.findOne(massId);
        if (mass == null) {
            return "发送失败";
        }
        String[] mobile = iMassDealService.findMobile(massId);
        String result = iSmsHandleService.sendUserMassSms(mobile, mass.getContent());
        iMassService.saveMass(mass, result);
        return "ok".equals(result) ? "发送成功" : "发送失败";
    }

    public IMassService getiMassService() {
        return iMassService;
    }

    public void setiMassService(IMassService iMassService) {
        this.iMassService = iMassService;
    }

    public IMassDealService getiMassDealService() {
        return iMassDealService;
    }

    public void setiMassDealService(IMassDealService iMassDealService) {
        this.iMassDealService = iMassDealService;
    }

    public ISmsHandleService getiSmsHandleService() {
        return iSmsHandleService;
    }

    public void setiSmsHandleService(ISmsHandleService iSmsHandleService) {
        this.iSmsHandleService = iSmsHandleService;
    }
}