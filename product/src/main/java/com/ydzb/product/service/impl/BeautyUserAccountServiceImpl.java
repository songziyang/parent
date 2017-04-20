package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.BeautyUserAccount;
import com.ydzb.product.repository.BeautyUserAccountRepository;
import com.ydzb.product.repository.IBeautyUserAccountRepository;
import com.ydzb.product.service.IBeautyUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BeautyUserAccountServiceImpl extends BaseServiceImpl<BeautyUserAccount, Long> implements IBeautyUserAccountService {


    @Autowired
    private IBeautyUserAccountRepository beautyUserAccountRepository;

    @Autowired
    private BeautyUserAccountRepository userAccountRepository;

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return userAccountRepository.findExportData(filter);
    }

    @Override
    public String exportExcele(List<Object[]> beautyUserAccounts, String address) {
        String fileName = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
            itemMap.put("title", "购买份数");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "buyCopies");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "apr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "加息利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "grandApr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "VIP利率");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "vipApr");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "总收益");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "revenue");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "未还收益");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "predictIncome");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "交易状态");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "status");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "购买时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "created");
            headInfoList.add(itemMap);


            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "到期日期");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "closingDate");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row : beautyUserAccounts) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null? "": row[0]);
                dataItem.put("mobile", row[1] == null? "": row[1]);
                dataItem.put("buyCopies", row[2] == null? "": row[2]);
                dataItem.put("apr", row[3] == null? "": row[3]);
                dataItem.put("grandApr", row[4] == null? "": row[4]);
                dataItem.put("revenue", row[5] == null? "": row[5]);
                dataItem.put("status", getStatus((Integer)row[6]));
                dataItem.put("created", row[7] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[7]).longValue())));
                dataItem.put("closingDate", row[8] == null? "": df.format(DateUtil.getSystemTimeMillisecond(((Integer)row[8]).longValue())));
                dataItem.put("vipApr", row[9] == null? "": row[9]);
                dataItem.put("predictIncome", row[10] == null? "": row[10]);
                dataList.add(dataItem);
            }
            fileName = "/beauty" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("美利金融已购记录", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }



    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 0: return "未到期";
            case 1: return "已到期";
            case 2: return "转让中";
            case 3: return "转让成功";
            default: return "";
        }
    }

    public IBeautyUserAccountRepository getBeautyUserAccountRepository() {
        return beautyUserAccountRepository;
    }

    public void setBeautyUserAccountRepository(IBeautyUserAccountRepository beautyUserAccountRepository) {
        this.beautyUserAccountRepository = beautyUserAccountRepository;
    }

    public BeautyUserAccountRepository getUserAccountRepository() {
        return userAccountRepository;
    }

    public void setUserAccountRepository(BeautyUserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
