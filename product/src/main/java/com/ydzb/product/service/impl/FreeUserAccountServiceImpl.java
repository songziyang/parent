package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.FreeUserAccount;
import com.ydzb.product.repository.FreeUserAccountRepository;
import com.ydzb.product.repository.IFreeUserAccountRepository;
import com.ydzb.product.service.IFreeUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随心存产品记录service实现
 */
@Service
public class FreeUserAccountServiceImpl extends BaseServiceImpl<FreeUserAccount, Long> implements IFreeUserAccountService  {

    @Autowired
    private IFreeUserAccountRepository freeUserAccountRepository;
    @Autowired
    private FreeUserAccountRepository cFreeUserAccountRepository;

    /**
     * 根据起始时间查询总购买金额
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public BigDecimal findTotalFund(Long startDate, Long endDate) {
        return freeUserAccountRepository.findTotalFund(startDate, endDate);
    }

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cFreeUserAccountRepository.findExportData(filter);
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
            String[] names = {"用户名", "手机号", "产品名称", "产品天数", "购买份数", "购买利率", "加息利率", "VIP利率", "总收益", "未还收益", "交易状态", "购买时间", "到期日期"};
            String[] keys = {"username", "mobile", "productName", "productDays", "buyCopies", "apr", "grandApr", "vipApr", "revenue", "predictIncome", "status", "created", "closingDate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, datas);
            fileName = "/freebuyrecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("随心存已购记录", filePath, headInfoList, dataList);
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
            dataItem.put(keys[4], row[4] == null? "": row[4]);
            dataItem.put(keys[5], row[5] == null? "": row[5]);
            dataItem.put(keys[6], row[6] == null? "": row[6]);
            dataItem.put(keys[7], row[7] == null? "": row[7]);
            dataItem.put(keys[8], row[8] == null? "": row[8]);
            dataItem.put(keys[9], row[9] == null? "": row[9]);
            dataItem.put(keys[10], row[10] == null? "": convertStatus((Integer) row[10]));
            dataItem.put(keys[11], row[11] == null? "": convertTime(new Long((Integer)row[11])));
            dataItem.put(keys[12], row[12] == null? "": convertTime(new Long((Integer)row[12])));
            dataList.add(dataItem);
        }
        return dataList;
    }

    private String convertStatus(Integer status) {
        if (status == null) return "";
        switch (status) {
            case 0: return "未到期";
            case 1: return "已到期";
            default: return "";
        }
    }

    private String convertTime(Long time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(DateUtil.getSystemTimeMillisecond(time));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}