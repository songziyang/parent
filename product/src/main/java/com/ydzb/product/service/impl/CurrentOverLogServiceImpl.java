package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;

import com.ydzb.product.entity.CurrentOverLog;
import com.ydzb.product.repository.ICurrentOverLogRepository;
import com.ydzb.product.service.ICurrentOverLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CurrentOverLogServiceImpl extends BaseServiceImpl<CurrentOverLog, Long> implements ICurrentOverLogService {


    @Autowired
    private ICurrentOverLogRepository currentOverLogRepository;


    @Override
    public String exportExcele(List<CurrentOverLog> currentOverLogs, String address) {
        String fileName = "";
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "username");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "mobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "赎回类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "type");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "赎回金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "redemPtionFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "赎回体验金");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "expFund");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "赎回日期");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "redmptionCreateStr");
            headInfoList.add(itemMap);


            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (CurrentOverLog currentOverLog : currentOverLogs) {
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", currentOverLog.getUser().getUsername());
                dataItem.put("mobile", currentOverLog.getUser().getMobile());

                if (currentOverLog.getType() == 0) {
                    dataItem.put("type", "投资赎回");
                } else if (currentOverLog.getType() == 1) {
                    dataItem.put("type", "体验金到期");
                }

                dataItem.put("redemPtionFund", currentOverLog.getRedemptionFund());
                dataItem.put("expFund", currentOverLog.getExpFund());
                dataItem.put("redmptionCreateStr", format.format(currentOverLog.getRedemptionDate()));

                dataList.add(dataItem);
            }

            fileName = "/dayloan" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("活期宝赎回记录", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public ICurrentOverLogRepository getCurrentOverLogRepository() {
        return currentOverLogRepository;
    }

    public void setCurrentOverLogRepository(ICurrentOverLogRepository currentOverLogRepository) {
        this.currentOverLogRepository = currentOverLogRepository;
    }


}
