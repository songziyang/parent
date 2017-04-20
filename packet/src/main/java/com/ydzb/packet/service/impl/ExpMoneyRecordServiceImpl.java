package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ExpMoneyRecord;
import com.ydzb.packet.repository.ExpMoneyRecordRepository;
import com.ydzb.packet.service.IExpMoneyRecordService;
import com.ydzb.product.entity.RagularTradeRecored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体验金记录service实现类
 * @author sy
 */
@Service
public class ExpMoneyRecordServiceImpl extends BaseServiceImpl<ExpMoneyRecord, Long> implements IExpMoneyRecordService {

    @Autowired
    private ExpMoneyRecordRepository expMoneyRecordRepository;

    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "来源去向", "类型", "体验金金额", "用户体验金余额", "交易时间"};
            String[] keys = {"username", "mobile", "fundFlow", "type", "fund", "userExpFund", "tradeTime"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/expmoneytraderecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("体验金交易", filePath, headInfoList, dataList);
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
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row: records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? "": row[2]);
            dataItem.put(keys[3], getType((Byte)row[3]));
            dataItem.put(keys[4], row[4] == null? BigDecimal.ZERO: row[4]);
            dataItem.put(keys[5], row[5] == null? BigDecimal.ZERO: row[5]);
            dataItem.put(keys[6], row[6] == null? "": new SimpleDateFormat("yyyy-MM-dd HH:mm").format(DateUtil.getSystemTimeMillisecond(((Integer)row[6]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 获得交易类型
     * @param type
     * @return
     */
    private String getType(Byte type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 0: return "出账";
            case 1: return "进账";
            case 2: return "冻结";
            case 3: return "解冻";
        }
        return "";
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return expMoneyRecordRepository.findExportData(filter);
    }
}
