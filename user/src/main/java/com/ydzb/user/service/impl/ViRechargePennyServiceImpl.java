package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.ViRechargePenny;
import com.ydzb.user.repository.IViRechargePennyRepository;
import com.ydzb.user.repository.ViRechargePennyRepository;
import com.ydzb.user.service.IViRechargePennyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小额充值service实现
 */
@Service
public class ViRechargePennyServiceImpl extends BaseServiceImpl<ViRechargePenny, Long> implements IViRechargePennyService {

    @Autowired
    private IViRechargePennyRepository viRechargePennyRepository;
    @Autowired
    private ViRechargePennyRepository cViRechargePennyRepository;

    @Override
    public Integer queryUsersCount(Long startTime, Long endTime) {
        if (startTime == null && endTime == null) return viRechargePennyRepository.queryUserCount();
        return viRechargePennyRepository.queryUserCount(startTime, endTime);
    }


    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return cViRechargePennyRepository.findExportData(filters);
    }


    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "真实姓名", "请求流水", "充值金额", "充值时间", "充值类型", "充值渠道"};
            String[] keys = {"username", "mobile", "realName", "serialNumber", "money", "time", "rechargetype", "onlines"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/rechargepenny" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("小额充值记录", filePath, headInfoList, dataList);
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row : records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? "" : row[2]);
            dataItem.put(keys[3], row[3] == null ? "" : row[3]);
            dataItem.put(keys[4], row[4] == null ? "" : row[4]);
            dataItem.put(keys[5], row[5] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(Long.valueOf((int) row[5]))));

            String rechargetypeStr = "银多账户充值";
            if (row[6] != null) {
                if((Integer)row[6] == 1) {
                    rechargetypeStr = "电子账户充值";
                }
            }
            dataItem.put(keys[6], rechargetypeStr);

            String onlinesStr = "线下充值";
            if (row[7] != null) {
                if ((Integer)row[7] == 1) {
                    onlinesStr = "线上充值";
                }
            }
            dataItem.put(keys[7], onlinesStr);

            dataList.add(dataItem);
        }
        return dataList;
    }
}