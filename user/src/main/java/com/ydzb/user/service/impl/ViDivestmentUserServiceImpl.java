package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.ViDivestmentUser;
import com.ydzb.user.repository.IViDivestmentUserRepository;
import com.ydzb.user.repository.ViDivestmentUserRepository;
import com.ydzb.user.service.IViDivestmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ViDivestmentUserServiceImpl extends BaseServiceImpl<ViDivestmentUser, Long> implements IViDivestmentUserService {


    @Autowired
    private IViDivestmentUserRepository divestmentUserRepository;

    @Autowired
    private ViDivestmentUserRepository viDivestmentUserRepository;

    /**
     * 查询导出excel数据
     *
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return viDivestmentUserRepository.findExportData(filters);
    }


    /**
     * 导出excel
     *
     * @param records
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"用户ID", "用户名", "手机号", "充值总额", "账户总额", "最后提现时间"};
            String[] keys = {"userId", "username", "mobile", "allRecharge", "totalFund", "applicationDate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/divestment" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("撤资用户信息", filePath, headInfoList, dataList);
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
            dataItem.put(keys[5], row[5] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((BigInteger) row[5]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }


    public IViDivestmentUserRepository getDivestmentUserRepository() {
        return divestmentUserRepository;
    }

    public void setDivestmentUserRepository(IViDivestmentUserRepository divestmentUserRepository) {
        this.divestmentUserRepository = divestmentUserRepository;
    }

    public ViDivestmentUserRepository getViDivestmentUserRepository() {
        return viDivestmentUserRepository;
    }

    public void setViDivestmentUserRepository(ViDivestmentUserRepository viDivestmentUserRepository) {
        this.viDivestmentUserRepository = viDivestmentUserRepository;
    }
}