package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.UserIncomeRecord;
import com.ydzb.user.repository.UserIncomeRecordRepository;
import com.ydzb.user.service.IUserIncomeRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收益记录service实现类
 */
@Service
public class UserIncomeRecordServiceImpl extends BaseServiceImpl<UserIncomeRecord, Long> implements IUserIncomeRecordService {

    @Autowired
    private UserIncomeRecordRepository cUserIncomeRecordRepository;

    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "收益金额", "来源", "类型", "操作时间"};
            String[] keys = {"username", "mobile", "income", "name", "ptype", "optime"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/incomerecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户收益记录", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     *
     * @param keys
     * @param records
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> records) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row : records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? "" : row[2]);
            dataItem.put(keys[3], row[3] == null ? "" : row[3]);
            dataItem.put(keys[4], row[4] == null ? "" : getType((byte) row[4]));
            dataItem.put(keys[5], row[5] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(Long.valueOf((int) row[5]))));
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 获得类型
     *
     * @param type
     * @return
     */
    private String getType(Byte type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "活期宝";
            case 2:
                return "定存宝";
            case 3:
                return "现金红包";
            case 4:
                return "砸蛋中奖";
            case 5:
                return "定存返现";
            case 6:
                return "基金收益";
            case 7:
                return "采摘返现";
            case 8:
                return "网袋还款";
            case 9:
                return "代金券红包";
            case 10:
                return "随心存";
        }
        return "";
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cUserIncomeRecordRepository.findExportData(filter);
    }

    /**
     * 根据操作时间段查询总收益
     *
     * @param startTime 操作时间-开始
     * @param endTime   操作时间-结束
     * @return
     */
    @Override
    public BigDecimal findSumIncome(String startTime, String endTime) {
        Long sTime = StringUtils.isEmpty(startTime) ? null : DateUtil.getSystemTimeDay(startTime);
        Long eTime = StringUtils.isEmpty(endTime) ? null : DateUtil.getSystemTimeDay(DateUtil.addDay(endTime));
        return cUserIncomeRecordRepository.findSumIncome(sTime, eTime);
    }

    /**
     * 查询每日收益
     *
     * @param filter
     * @param pageCurrent 当前页数
     * @param pageSize    每页显示条数
     * @return
     */
    @Override
    public Page<Object[]> findDailyIncome(Map<String, Object> filter, int pageCurrent, int pageSize) {
        return cUserIncomeRecordRepository.findDailyIncome(filter, pageCurrent, pageSize);
    }

    /**
     * 查询每日收益导出excel数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findDailyIncomeExportData(Map<String, Object> filter) {
        return cUserIncomeRecordRepository.findDailyIncomeExportData(filter);
    }

    /**
     * 每日收益导出excel
     *
     * @param records
     * @param address
     * @return
     */
    @Override
    public String dailyIncomeExportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"收益金额", "日期"};
            String[] keys = {"income", "date"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/incomedaily" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户每日收益", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}