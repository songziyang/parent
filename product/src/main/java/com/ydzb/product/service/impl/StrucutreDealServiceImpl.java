package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.StructureDeal;
import com.ydzb.product.repository.StructureDealRepository;
import com.ydzb.product.service.IStructureDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StrucutreDealServiceImpl extends BaseServiceImpl<StructureDeal, Long> implements IStructureDealService {

    @Autowired
    private StructureDealRepository structureDealRepository;

    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return structureDealRepository.findExportData(filters);
    }

    @Override
    public String exportExcel(List<Object[]> data, String address) {
        String fileName = "";
        try {
            String[] names = {"转转赚产品名称", "用户名", "手机号", "购买份数", "购买时间", "到期日期", "利率(%)", "结算状态"};
            String[] keys = {"name", "username", "mobile", "buyCopies", "buyTime", "closeDate", "rate", "status"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, data);
            fileName = "/structuredeal" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("转转赚产品交易记录", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     * @param keys
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> data) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        SimpleDateFormat secondFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] row: data) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();

            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? "": row[2]);
            dataItem.put(keys[3], row[3] == null? "": row[3]);
            dataItem.put(keys[4], row[4] == null? "": secondFormatter.format(DateUtil.getSystemTimeMillisecond((new Long((Integer) row[4])))));
            dataItem.put(keys[5], row[5] == null? "": dayFormatter.format(DateUtil.getSystemTimeMillisecond(new Long((Integer) row[5]))));
            dataItem.put(keys[6], row[6] == null? "": row[6]);
            dataItem.put(keys[7], convertStatus((Integer) row[7]));

            dataList.add(dataItem);
        }
        return dataList;
    }

    private String convertStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 0: return "未结算";
            case 1: return "已结算";
            default: return "";
        }
    }
}
