package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IOtherPlatformService;
import com.ydzb.user.service.IOtherPlatformUserService;
import com.ydzb.web.user.condition.OtherPlatformUserCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 其他平台用户controller
 */
@Controller
@RequestMapping(value = "userinfo/otherplatform")
public class OtherPlatformUserController extends BaseController {

    @Autowired
    private IOtherPlatformUserService otherPlatformUserService;
    @Autowired
    private IOtherPlatformService otherPlatformService;

    private static final int LINE_PER_THREAD = 2000;    //每个线程负责的行数
    private ReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize    每页显示数量
     * @param pageCurrent 当前页码
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("oplatformuser_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute(value = "condition") String condition,
                            @ModelAttribute OtherPlatformUserCondition otherPlatformUserCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            otherPlatformUserCondition = gson.fromJson(condition, OtherPlatformUserCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(otherPlatformUserCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(otherPlatformUserCondition));
        model.addAttribute("page", otherPlatformUserService.findAll(searchable));
        model.addAttribute("otherPlatforms", otherPlatformService.findAll());
        return "userinfo/otherplatform/list";
    }

    /**
     * 跳转至导入excel页面
     *
     * @return
     */
    @RequestMapping(value = "toUpload", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("oplatformuser_list")
    public String toUpload(Model model) {
        model.addAttribute("otherPlatforms", otherPlatformService.findAll());
        return "userinfo/otherplatform/upload";
    }

    /**
     * 上传
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "upload", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("oplatformuser_list")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpServletRequest request) throws Exception {
        List<Future> futures = new ArrayList<>() ;
        try {
            //平台id
            final Long platformId = Long.parseLong(request.getParameter("platformId"));
            Workbook workbook = new HSSFWorkbook(new POIFSFileSystem(multipartFile.getInputStream()));
            final Sheet sheet = workbook.getSheetAt(0);
            //最后一行的行数
            final int lastRowNum = sheet.getLastRowNum();
            //线程数
            final int threadNum = (int) Math.ceil(Double.valueOf(lastRowNum) / LINE_PER_THREAD);
            //创建线程池
            final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);

            final Row row = sheet.getRow(0);
            if (row != null) {
                Cell nameCell = row.getCell(0);
                Cell mobileCell = row.getCell(1);
                if (nameCell != null && mobileCell != null) {
                    if (!("姓名".equals(nameCell.getStringCellValue()) && "电话".equals(mobileCell.getStringCellValue()))) {
                        return "Excel首列名错误";
                    }
                }
            }
            //执行线程方法
            for (int i = 0; i < threadNum; i++) {
                final int thread = i;
                Future future = fixedThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //每个线程操作excel的最小行数以及最大行数
                            int minRow = thread * LINE_PER_THREAD, maxRow = (thread + 1) * LINE_PER_THREAD - 1;
                            if (thread == 0) minRow = 1;
                            if (thread == threadNum - 1) maxRow = lastRowNum;
                            Row row;
                            Cell nameCell, mobileCell;
                            String realname = null, mobile = null;
                            for (int i = minRow; i <= maxRow; i ++) {
                                try {
                                    //需枷锁，否则变量线程不安全，mobile会变
                                    lock.writeLock().lock();
                                    try {
                                        row = sheet.getRow(i);
                                        nameCell = row.getCell(0);
                                        mobileCell = row.getCell(1);
                                        if (nameCell != null && mobileCell != null) {
                                            nameCell.setCellType(Cell.CELL_TYPE_STRING);
                                            mobileCell.setCellType(Cell.CELL_TYPE_STRING);
                                            realname = nameCell.getStringCellValue();
                                            mobile = mobileCell.getStringCellValue();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        lock.writeLock().unlock();
                                    }
                                    if (StringUtils.isNotEmpty(mobile)) {
                                        otherPlatformUserService.createOne(mobile, realname, platformId);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true);
                futures.add(future);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //所有线程执行完才进行return，如果不进行此操作方法直接return
        for (Future future: futures) {
            future.get();
        }
        return "true";
    }
}
