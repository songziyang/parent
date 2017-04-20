package com.ydzb.withdraw.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.withdraw.entity.WithdrawNum;
import com.ydzb.withdraw.repository.WithdrawNumRepository;
import com.ydzb.withdraw.service.IWithdrawNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawNumServiceImpl extends BaseServiceImpl<WithdrawNum, Long> implements IWithdrawNumService {

    @Autowired
    private WithdrawNumRepository withdrawNumRepository;

    @Override
    public void initData() throws Exception {
        withdrawNumRepository.initData();
    }

    @Override
    public void deleteAllWithdrawNum() throws Exception {
        withdrawNumRepository.deleteData();
    }

    @Override
    public String exportExcel(List<Object[]> withDrawNums, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "真实姓名", "七天提现次数", "最近一次提现时间", "七天充值次数", "一个月提现次数"};
            String[] keys = {"username", "mobile", "realName", "withdrawNum", "withdrawDate", "rechargeNum", "rechargeDate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, withDrawNums);
            fileName = "/withdrawnum" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户提现统计", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return withdrawNumRepository.findExportData(filter);
    }

    /**
     * 设置数据
     *
     * @param keys
     * @param withDrawNums
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> withDrawNums) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        for (Object[] row : withDrawNums) {
            if (row == null) {
                continue;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? "" : row[2]);
            dataItem.put(keys[3], row[3] == null ? 0 : row[3]);
            dataItem.put(keys[4], row[4] == null ? "" : formatter.format(DateUtil.getSystemTimeMillisecond(((Integer) row[4]).longValue())));
            dataItem.put(keys[5], row[5] == null ? 0 : row[5]);
            dataItem.put(keys[6], row[6] == null ? 0 : row[6]);
            dataList.add(dataItem);
        }
        return dataList;
    }
}
