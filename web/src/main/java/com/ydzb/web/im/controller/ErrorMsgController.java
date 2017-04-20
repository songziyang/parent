package com.ydzb.web.im.controller;

import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.controller.BaseController;
import com.ydzb.packet.service.IIMservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * Created by CRF on 2017/3/10 0010.
 */
@Controller
@RequestMapping(value = "/im/errormsg")
public class ErrorMsgController extends BaseController {

    @Autowired
    private IIMservice imservice;

    public IIMservice getImservice() {
        return imservice;
    }

    public void setImservice(IIMservice imservice) {
        this.imservice = imservice;
    }


    @RequestMapping(value = "errorMsg/{para}", method = {RequestMethod.GET, RequestMethod.POST})
    public String statError(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            Model model, @PathVariable(value = "para") Integer para) {

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        //异常统计结果集
        List<Object> statList = imservice.getIMErrorCount();
        //详细列表结果集
        Page<Object[]> resultList = imservice.getIMErrorDetailList(searchable, para);

        model.addAttribute("statList", statList);
        model.addAttribute("page", resultList);
        return "im/errormsg/list";
    }
}


