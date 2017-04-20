package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.DepositTypeExchange;
import com.ydzb.packet.repository.DepositTypeExchangeRepository;
import com.ydzb.packet.repository.IDepositTypeExchangeRepository;
import com.ydzb.packet.service.IDepositTypeExchangeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中秋活动
 */
@Service
public class DepositTypeExchangeServiceImpl extends BaseServiceImpl<DepositTypeExchange, Long> implements IDepositTypeExchangeService {

    @Autowired
    private IDepositTypeExchangeRepository depositTypeExchangeRepository;

    @Autowired
    private DepositTypeExchangeRepository exchangeRepository;

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateExchange(Long id, Integer status) {
        if (id != null && status != null) {
            ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            DepositTypeExchange depositTypeExchange = depositTypeExchangeRepository.findOne(id);
            if (depositTypeExchange != null) {
                depositTypeExchange.setStatus(status);
                depositTypeExchange.setOperationUser(user.getUsername());
                depositTypeExchange.setSended(DateUtil.getSystemTimeSeconds());
                this.update(depositTypeExchange);
            }
        }
    }

    /**
     * 跟新订单状态
     *
     * @param ids
     * @param status
     */
    @Override
    public void updateExchange(Long[] ids, Integer status) {
        if (ids != null && status != null) {
            for (Long id : ids) {
                updateExchange(id, status);
            }
        }
    }


    /**
     * 查询数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return exchangeRepository.findExportData(filter);
    }

    /**
     * 导出Excel
     *
     * @param exchanges
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);
            fileName = "/depositexchange" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("定存活动兑换", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "活动类型", "订单状态", "奖品类型", "方式", "奖品", "金额", "兑换数量", "收货人", "收货手机号", "收货地址", "备注", "兑换时间"};
        String[] keys = new String[]{"username", "mobile", "actype", "status", "ptype", "type", "productName", "fund", "number", "realname", "mob", "address", "remark", "created"};
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
            rowMap.put("username", row[0] == null ? "" : row[0]);
            rowMap.put("mobile", row[1] == null ? "" : row[1]);
            rowMap.put("actype", getAcType((Integer) row[2]));
            rowMap.put("status", getStatus((Integer) row[3]));
            rowMap.put("ptype", getPType((Integer) row[4]));
            rowMap.put("type", getType((Integer) row[5]));
            rowMap.put("productName", row[6] == null ? "" : row[6]);
            rowMap.put("fund", row[7] == null ? "" : row[7]);
            rowMap.put("number", row[8] == null ? "" : row[8]);
            rowMap.put("realname", row[9] == null ? "" : row[9]);
            rowMap.put("mob", row[10] == null ? "" : row[10]);
            rowMap.put("address", row[11] == null ? "" : row[11]);
            rowMap.put("remark", row[12] == null ? "" : row[12]);
            rowMap.put("created", row[13] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[13]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }

    private String getStatus(Integer status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case 0:
                return "未兑换";
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
                return "兑换";
            case 2:
                return "投资";
            case 3:
                return "抽奖";
            default:
                return "";
        }
    }

    private String getPType(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "实物";
            case 2:
                return "活期加息券";
            case 3:
                return "定存加息券";
            case 4:
                return "现金红包";
            case 5:
                return "定存代金券";
            case 6:
                return "体验金";
            default:
                return "";
        }
    }

    private String getAcType(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "16双旦活动";
            default:
                return "";
        }
    }


    public IDepositTypeExchangeRepository getDepositTypeExchangeRepository() {
        return depositTypeExchangeRepository;
    }

    public void setDepositTypeExchangeRepository(IDepositTypeExchangeRepository depositTypeExchangeRepository) {
        this.depositTypeExchangeRepository = depositTypeExchangeRepository;
    }

    public DepositTypeExchangeRepository getExchangeRepository() {
        return exchangeRepository;
    }

    public void setExchangeRepository(DepositTypeExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }
}
