package com.ydzb.packet.service.impl;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.UserIntegralExchange;
import com.ydzb.packet.entity.UserIntegralRecord;
import com.ydzb.packet.repository.IUserIntegralExchangeRepository;
import com.ydzb.packet.repository.IUserIntegralRecordRepository;
import com.ydzb.packet.repository.IUserIntegralRepository;
import com.ydzb.packet.repository.UserIntegralExchangeRepository;
import com.ydzb.packet.service.IUserIntegralExchangeService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserIntegral;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 积分兑换活动
 */
@Service
public class UserIntegralExchangeServiceImpl extends BaseServiceImpl<UserIntegralExchange, Long> implements IUserIntegralExchangeService {

    @Autowired
    private IUserIntegralExchangeRepository userIntegralExchangeRepository;
    @Autowired
    private IUserIntegralRecordRepository userIntegralRecordRepository;
    @Autowired
    private IUserIntegralRepository userIntegralRepository;

    @Autowired
    private UserIntegralExchangeRepository exchangeRepository;
    @Autowired
    private IAdminService adminService;

    /**
     * 获取当前登陆管理员
     * @return
     */
    private Admin getCurrentAdmin(){
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return adminService.findOne(user.getId());
    }

    @Override
    public void updateExchange(Long id, Integer status) {
        UserIntegralExchange userIntegralExchange = userIntegralExchangeRepository.findOne(id);
        if (userIntegralExchange != null) {
            userIntegralExchange.setStatus(status);
            userIntegralExchange.setOperationUser(getCurrentAdmin());
            userIntegralExchange.setSended(DateUtil.getSystemTimeSeconds());
            this.update(userIntegralExchange);
        }
    }

    public void updateExchange(UserIntegralExchange userIntegralExchange, Integer status) {
        if (userIntegralExchange != null) {
            userIntegralExchange.setStatus(status);
            userIntegralExchange.setOperationUser(getCurrentAdmin());
        }
    }

    @Override
    public void resetToUnderHandle(Long id) {
        UserIntegralExchange userIntegralExchange = userIntegralExchangeRepository.findOne(id);
        if (userIntegralExchange != null) {
            userIntegralExchange.setStatus(1);
            userIntegralExchange.setSended(null);
            userIntegralExchange.setOperationUser(getCurrentAdmin());
            this.update(userIntegralExchange);
        }
    }

    /**
     * 取消订单
     * @param id
     */
    @Override
    public void auditFailure(Long id) {
        UserIntegralExchange userIntegralExchange = userIntegralExchangeRepository.findOne(id);
        //改为订单取消状态
        updateExchange(userIntegralExchange, 4);
        //返还用户积分
        giveBackUserIntegral(userIntegralExchange);
    }

    /**
     * 返还用户积分
     * @param userIntegralExchange
     */
    private void giveBackUserIntegral(UserIntegralExchange userIntegralExchange) {
        BigDecimal integral = userIntegralExchange.getIntegral();
        User user = userIntegralExchange.getUser();
        UserIntegral userIntegral = user.getUserIntegral();
        //修改用户积分
        updateUserIntegral(userIntegral, integral);
        //生成用户积分记录
        generateUserIntegralRecord(user, integral, userIntegral, userIntegralExchange.getId());
    }

    /**
     * 修改用户积分
     * @param userIntegral
     * @param integral
     */
    private void updateUserIntegral(UserIntegral userIntegral, BigDecimal integral) {
        userIntegral.setIntegral(userIntegral.getIntegral().add(integral));
        userIntegralRepository.save(userIntegral);
    }

    /**
     * 生成用户积分记录
     * @param user
     * @param integral
     * @param userIntegral
     */
    private void generateUserIntegralRecord(User user, BigDecimal integral, UserIntegral userIntegral, Long exchangeId) {
        UserIntegralRecord record = new UserIntegralRecord();
        record.setUser(user);
        record.setFundflow("订单取消");
        record.setIntegral(integral);
        record.setBalance(userIntegral.getIntegral());
        record.setType(1);
        record.setLinkType(23);
        record.setLinkId(exchangeId);
        record.setCreated(DateUtil.getSystemTimeSeconds());
        userIntegralRecordRepository.save(record);
    }

    /**
     * 更新
     * @param ids
     * @param status
     */
    @Override
    public void updateExchange(Long[] ids, Integer status) {
        if (ids != null) {
            for (Long id: ids) {
                updateExchange(id, status);
            }
        }
    }

    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return exchangeRepository.findExportData(filter);
    }


    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/integralexchange" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("积分兑换", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     *
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = new String[]{"用户名", "手机号", "类型", "状态", "产品", "兑换数量",
                "使用积分", "收货人", "收货手机号", "收货地址", "备注", "兑换时间"};
        String[] keys = new String[]{"username", "mobile", "type", "status", "productName", "number",
                "integral", "realname", "receiveMobile", "address", "remark", "time"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<String, Object>();
            itemMap.put("title", titles[i]);
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", keys[i]);
            headInfoList.add(itemMap);
        }
        return headInfoList;
    }

    /**
     * 创建行信息
     *
     * @param statistics
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> statistics) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("username", row[0] == null ? "" : row[0]);
            rowMap.put("mobile", row[1] == null ? "" : row[1]);
            rowMap.put("type", getType((Integer) row[2]));
            rowMap.put("status", getStatus((Integer) row[3]));
            rowMap.put("productName", row[4] == null ? "" : row[4]);
            rowMap.put("realname", row[5] == null ? "" : row[5]);
            rowMap.put("receiveMobile", row[6] == null ? "" : row[6] + "%");
            rowMap.put("address", row[7] == null ? "" : row[7]);
            rowMap.put("remark", row[8] == null ? "" : row[8]);
            rowMap.put("time", row[9] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[9]).longValue())));
            rowMap.put("number", row[10] == null? 1: row[10]);
            rowMap.put("integral", row[11] == null? 0.0: row[11]);
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 1:
                return "待处理";
            case 2:
                return "已发货";
            case 3:
                return "已收货";
            case 4:
                return "已取消";
            default:
                return "";
        }
    }


    private String getType(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "商品";
            case 2:
                return "活期加息券";
            case 3:
                return "定存加息券";
            default:
                return "";
        }
    }


    public IUserIntegralExchangeRepository getUserIntegralExchangeRepository() {
        return userIntegralExchangeRepository;
    }

    public void setUserIntegralExchangeRepository(IUserIntegralExchangeRepository userIntegralExchangeRepository) {
        this.userIntegralExchangeRepository = userIntegralExchangeRepository;
    }

    public UserIntegralExchangeRepository getExchangeRepository() {
        return exchangeRepository;
    }

    public void setExchangeRepository(UserIntegralExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }
}
