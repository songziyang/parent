package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.repository.IUserInfoRepository;
import com.ydzb.user.repository.UserInfoRepository;
import com.ydzb.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long> implements IUserInfoService {


    @Autowired
    private IUserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoRepository infoRepository;

    @Override
    public Integer findRechargePersons() {
        return userInfoRepository.findRechargePersons();
    }

    @Override
    public BigDecimal findRechargeAllFund() {
        return userInfoRepository.findRechargeAllFund();
    }

    @Override
    public UserInfo findByUserId(Long userId) {
        return userId == null? null: userInfoRepository.findByUserId(userId);
    }


    /**
     * 导出到Excel
     * @param userInfoList
     * @param address
     * @return
     */
    @Override
    public String exportUser(List<UserInfo> userInfoList, String address) {

        String fileName = "";

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            //表头集合
            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            //表头项
            Map<String, Object> itemMap = new HashMap<String, Object>();

            itemMap = new HashMap<String, Object>();
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
            itemMap.put("title", "分数");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "signn");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "风险等级类型");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "signType");
            headInfoList.add(itemMap);


            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (UserInfo userInfo : userInfoList) {
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", userInfo.getUser().getUsername());
                dataItem.put("mobile", userInfo.getUser().getMobile());
                dataItem.put("signn", userInfo.getSignn());
                dataItem.put("signType", getSignnType(userInfo.getSignn()));
                dataList.add(dataItem);
            }

            fileName = "/user" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;

            POIUtil.exportExcel2FilePath("用户风险测评", filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * 根据测评分数获取测评类型
     * @param signn
     * @return
     */
    private String getSignnType(Integer signn) {
        String ret = "";
        if (signn == null) {
            return "";
        }
        if (signn >= 8 && signn <= 15) {
            ret = "保守型";
        }
        else if (signn >= 16 && signn <= 30) {
            ret = "稳健型";
        }
        else if (signn >= 31 && signn <= 36) {
            ret = "激进型";
        }
        return ret;
    }

    public IUserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }

    public void setUserInfoRepository(IUserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoRepository getInfoRepository() {
        return infoRepository;
    }

    public void setInfoRepository(UserInfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

}
