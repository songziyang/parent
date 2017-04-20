package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.ViOftenRechargeWithdraw;
import com.ydzb.user.repository.ViOftenRechargeWithdrawRepository;
import com.ydzb.user.service.IViOftenRechargeWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2016/9/30.
 */
@Service
public class ViOftenRechargeWithdrawServiceImpl extends BaseServiceImpl<ViOftenRechargeWithdraw, String> implements IViOftenRechargeWithdrawService {

    @Autowired
    private ViOftenRechargeWithdrawRepository viOftenRechargeWithdrawRepository;

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return viOftenRechargeWithdrawRepository.findExportData(filters);
    }


    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "真实姓名", "充值金额", "充值时间", "提现金额", "提现时间"};
            String[] keys = {"username", "mobile", "realName", "rechargeMoney", "rechargeTime", "withdrawMoney", "withdrawTime"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/oftenrecharge" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("当.次日提现", filePath, headInfoList, dataList);
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
            dataItem.put(keys[4], row[4] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((Integer) row[4]).longValue())));
            dataItem.put(keys[5], row[5] == null ? "" : row[5]);
            dataItem.put(keys[6], row[6] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((BigInteger) row[6]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }
}