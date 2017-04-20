package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.FreeTradeRecord;
import com.ydzb.product.repository.FreeTradeRecordRepository;
import com.ydzb.product.service.IFreeTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随心存交易记录service实现
 */
@Service
public class FreeTradeRecordServiceImpl extends BaseServiceImpl<FreeTradeRecord, Long> implements IFreeTradeRecordService {

    @Autowired
    private FreeTradeRecordRepository freeTradeRecordRepository;

    /**
     * 查询导出excel所需数据
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return freeTradeRecordRepository.findExportData(filter);
    }

    /**
     * 导出excel
     * @param datas
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> datas, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "产品天数", "交易名称", "交易类型", "交易金额", "交易时间"};
            String[] keys = {"username", "mobile", "days", "names", "type", "fund", "time"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, datas);
            fileName = "/freerecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("随心存交易记录", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     * @param keys
     * @param userAccounts
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> userAccounts) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row: userAccounts) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? "": row[2]);
            dataItem.put(keys[3], row[3] == null? "": row[3]);
            dataItem.put(keys[4], row[4] == null? "": convertType((Integer) row[4]));
            dataItem.put(keys[5], row[5] == null? "": row[5]);
            dataItem.put(keys[6], row[6] == null? "": convertDate(new Long((Integer) row[6])));
            dataList.add(dataItem);
        }
        return dataList;
    }

    private String convertType(Integer type) {
        if (type == null) return "";
        switch (type) {
            case 1: return "购买";
            case 2: return "到期";
            default: return "";
        }
    }
    private String convertDate(Long time) {
        if (time != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:ss").format( DateUtil.getSystemTimeMillisecond(time));
        }
        return "";
    }

}