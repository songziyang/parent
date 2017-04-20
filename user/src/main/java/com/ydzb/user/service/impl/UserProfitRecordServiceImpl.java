package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.UserProfitRecord;
import com.ydzb.user.repository.UserProfitRecordRepository;
import com.ydzb.user.service.IUserProfitRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每日用户额外收益实现
 */
@Service
public class UserProfitRecordServiceImpl extends BaseServiceImpl<UserProfitRecord, Long>
        implements IUserProfitRecordService {

    @Autowired
    private UserProfitRecordRepository userProfitRecordRepository;

    /**
     * 查询每日
     * @param filter
     * @param pageCurrent 当前页数(从0开始)
     * @param pageSize 每页显示条数
     * @return
     */
    @Override
    public Page<Object[]> findDailyFund(Map<String, Object> filter, int pageCurrent, int pageSize) {
        return userProfitRecordRepository.findDailyFund(filter, pageCurrent, pageSize);
    }

    /**
     * 根据记录日期段查询总收益
     * @param startTime 操作时间-开始
     * @param endTime 操作时间-结束
     * @return
     */
    @Override
    public BigDecimal findSumIncome(String startTime, String endTime) {
        Long sTime = StringUtils.isEmpty(startTime)? null: DateUtil.getSystemTimeDay(startTime);
        Long eTime = StringUtils.isEmpty(endTime)? null: DateUtil.getSystemTimeDay(DateUtil.addDay(endTime));
        return userProfitRecordRepository.findSumIncome(sTime, eTime);
    }

    /**
     * 查询每日额外收益导出excel数据
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findDailyIncomeExportData(Map<String, Object> filter) {
        return userProfitRecordRepository.findDailyProfitRecordExportData(filter);
    }

    /**
     * 每日收益导出excel
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
            fileName = "/profitincomedaily" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户每日额外收益", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     * @param keys
     * @param records
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> records) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row: records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataList.add(dataItem);
        }
        return dataList;
    }
}