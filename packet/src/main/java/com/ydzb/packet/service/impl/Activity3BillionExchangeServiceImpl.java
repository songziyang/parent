package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.Activity3BillionExchange;
import com.ydzb.packet.repository.Activity3BillionExchangeRepository;
import com.ydzb.packet.repository.IActivity3BillionExchangeRepository;
import com.ydzb.packet.service.IActivity3BillionExchangeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 30亿活动兑换service实现
 */
@Service
public class Activity3BillionExchangeServiceImpl extends BaseServiceImpl<Activity3BillionExchange, Long> implements IActivity3BillionExchangeService {

    @Autowired
    private IActivity3BillionExchangeRepository activity3BillionExchangeRepository;
    @Autowired
    private Activity3BillionExchangeRepository cActivity3BillionExchangeRepository;

    /**
     * 获取当前登陆管理员
     * @return
     */
    private String getCurrentAdmin(){
        return ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
    }

    @Override
    public Activity3BillionExchange updateExchange(Long id, Integer status) {

        Activity3BillionExchange exchange = activity3BillionExchangeRepository.findOne(id);
        if (exchange != null) {
            exchange.setStatus(status);
            exchange.setOperationUser(getCurrentAdmin());
            exchange.setOperationTime(DateUtil.getSystemTimeSeconds());
            return update(exchange);
        }
        return null;
    }

    private void updateExchange(Activity3BillionExchange exchange, Integer status) {

        if (exchange != null) {
            exchange.setStatus(status);
            exchange.setOperationUser(getCurrentAdmin());
        }
    }

    @Override
    public void resetToUnderHandle(Long id) {

        Activity3BillionExchange exchange = activity3BillionExchangeRepository.findOne(id);
        if (exchange != null) {
            exchange.setStatus(Activity3BillionExchange.STATUS_UNDERHANDLE);
            exchange.setOperationTime(null);
            exchange.setOperationUser(getCurrentAdmin());
            update(exchange);
        }
    }

    /**
     * 取消订单
     * @param id
     */
    @Override
    public void auditFailure(Long id) {
        Activity3BillionExchange exchange = activity3BillionExchangeRepository.findOne(id);
        //改为订单取消状态
        updateExchange(exchange, Activity3BillionExchange.STATUS_CANCEL);
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
        return cActivity3BillionExchangeRepository.findExportData(filter);
    }


    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {

        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/3billexchange" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("30亿活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[] {"用户名", "手机号", "兑换产品", "收货人", "收货人手机号", "收货地址",
                "状态", "兑换时间", "操作时间"};
        String[] keys = new String[]{"username", "mobile", "productName", "realname", "receiveMobile", "address",
                "status", "time", "operationTime"};
        List<Map<String, Object>> headInfoList = new ArrayList<>();
        Map<String, Object> itemMap;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<>();
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
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap;
        for (Object[] row : statistics) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<>();
            rowMap.put("username", row[0] == null? "": (String)row[0]);
            rowMap.put("mobile", row[1] == null? "": (String)row[1]);
            rowMap.put("productName", getProductName((Byte) row[2]));
            rowMap.put("realname", row[3] == null? "": (String)row[3]);
            rowMap.put("receiveMobile", row[4] == null? "": row[4]);
            rowMap.put("address", row[5] == null? "": (String)row[5]);
            rowMap.put("status", getStatus((Byte) row[6]));
            rowMap.put("time", row[7] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[7]).longValue())));
            rowMap.put("operationTime", row[8] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[8]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getStatus(Byte status) {
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
                return "已撤单";
            default:
                return "";
        }
    }


    private String getProductName(Byte type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "订制U盘";
            case 2:
                return "纪念银币";
            default:
                return "";
        }
    }
}