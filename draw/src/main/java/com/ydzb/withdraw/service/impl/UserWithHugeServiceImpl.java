package com.ydzb.withdraw.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.withdraw.entity.UserWithHuge;
import com.ydzb.withdraw.repository.UserWithHugeRepository;
import com.ydzb.withdraw.service.IUserWithHugeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2016/9/19.
 */
@Service
public class UserWithHugeServiceImpl extends BaseServiceImpl<UserWithHuge, Long> implements IUserWithHugeService {

    @Autowired
    private UserWithHugeRepository userWithHugeRepository;

    /**
     *
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findWithdrawExportData(Map<String, Object> filters) {
        return userWithHugeRepository.findExportData(filters);
    }

    @Override
    public String exportExcel(List<Object[]> data, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "提现金额", "可用金额", "操作时间"};
            String[] keys = {"username", "mobile", "withMoney", "ableMoney", "optime"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, data);
            fileName = "/withhuge" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("大额提现统计", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     *
     * @param keys
     * @param withDrawNums
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> withDrawNums) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row : withDrawNums) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            dataItem.put(keys[1], row[1] == null ? "" : row[1]);
            dataItem.put(keys[2], row[2] == null ? 0 : row[2]);
            dataItem.put(keys[3], row[3] == null ? 0 : row[3]);
            dataItem.put(keys[4], row[4] == null ? "" : formatter.format(DateUtil.getSystemTimeMillisecond(((Integer) row[4]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

}
