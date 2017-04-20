package com.ydzb.user.service.impl;

import com.ydzb.admin.repository.IAdminRepository;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserManualRecharge;
import com.ydzb.user.repository.IUserManualRechargeRepository;
import com.ydzb.user.repository.UserManualRechargeRepository;
import com.ydzb.user.service.IUserManualRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserManualRechargeInfoServiceImpl extends BaseServiceImpl<UserManualRecharge, Long> implements IUserManualRechargeService {

    @Autowired
    private IUserManualRechargeRepository userManualRechargeRepository;

    @Autowired
    private IAdminRepository adminRepository;

    @Autowired
    private UserManualRechargeRepository cUserManualRechargeRepository;

    /**
     * @param user
     * @param fund
     * @param adminId
     * @param remark
     */
    public Long saveUserManualRecharge(User user, BigDecimal fund, Long adminId, String remark) {
        UserManualRecharge userManualRecharge = new UserManualRecharge();
        userManualRecharge.setUser(user);
        userManualRecharge.setFund(fund);
        userManualRecharge.setAdmin(adminRepository.findOne(adminId));
        userManualRecharge.setUsableFund(user.getUserMoney().getUsableFund());
        userManualRecharge.setRemark(remark);
        userManualRecharge.setOperateTime(DateUtil.getSystemTimeSeconds());
        userManualRechargeRepository.save(userManualRecharge);
        return userManualRecharge.getId();
    }


    @Override
    public String exportExcele(List<Object[]> userManualRecharges, String address) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

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
            itemMap.put("title", "充值金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "fund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "账户余额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "usableFund");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "操作人");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "admin");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "操作时间");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "createDate");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "操作说明");
            itemMap.put("columnWidth", 50);
            itemMap.put("dataKey", "remark");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;
            for (Object[] row: userManualRecharges) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null? "": row[0]);
                dataItem.put("mobile", row[1] == null? "": row[1]);
                dataItem.put("fund", row[2] == null? "": row[2]);
                dataItem.put("usableFund", row[3] == null? "": row[3]);
                dataItem.put("admin", row[4] == null? "": row[4]);
                dataItem.put("createDate", row[5] == null? "": format.format(DateUtil.getSystemTimeMillisecond(((Integer)row[5]).longValue())));
                dataItem.put("remark", row[6] == null? "": row[6]);
                dataList.add(dataItem);
            }

            fileName = "/recharge" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("手动充值记录", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public List<Object[]> findExportData(Map<String, Object> filter) {
        return cUserManualRechargeRepository.findExportData(filter);
    }


    public IUserManualRechargeRepository getUserManualRechargeRepository() {
        return userManualRechargeRepository;
    }

    public void setUserManualRechargeRepository(IUserManualRechargeRepository userManualRechargeRepository) {
        this.userManualRechargeRepository = userManualRechargeRepository;
    }

    public IAdminRepository getAdminRepository() {
        return adminRepository;
    }

    public void setAdminRepository(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}
