package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.UserIntegralRecord;
import com.ydzb.packet.repository.IUserIntegralRecordRepository;
import com.ydzb.packet.repository.UserIntegralRecordRepository;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserIntegral;
import com.ydzb.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 积分记录
 */
@Service
public class UserIntegralRecordServiceImpl extends BaseServiceImpl<UserIntegralRecord, Long> implements IUserIntegralRecordService {

    public static final BigDecimal B = new BigDecimal(5);

    @Autowired
    private IUserIntegralRecordRepository userIntegralRecordRepository;

    @Autowired
    private UserIntegralRecordRepository recordRepository;

    @Autowired
    private IUserService userService;


    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return recordRepository.findExportData(filter);
    }


    @Override
    public String exportExcel(List<Object[]> exchanges, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(exchanges);

            fileName = "/integralrecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("积分交易记录", filePath, headInfo, rowInfo);
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
        String[] titles = new String[]{"用户名", "手机号", "来源去向", "类型", "积分", "积分余额", "操作时间"};
        String[] keys = new String[]{"username", "mobile", "fundflow", "type", "integral", "balance", "created"};
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
            rowMap.put("fundflow", row[2] == null ? "" : row[2]);
            rowMap.put("type", getType((Integer) row[3]));
            rowMap.put("integral", row[4] == null ? "" : row[4]);
            rowMap.put("balance", row[5] == null ? "" : row[5]);
            rowMap.put("created", row[6] == null ? "" : format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[6]).longValue())));
            rowList.add(rowMap);
        }
        return rowList;
    }


    private String getType(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "获取";
            case 2:
                return "支出";
            default:
                return "";
        }
    }


    @Override
    public void authentication(Long userId) {
        if (userId != null) {
            UserIntegralRecord userIntegralRecord = userIntegralRecordRepository.findUserIntegralRecordByUserIdAndLinkType(userId, 1);
            if (userIntegralRecord == null) {
                User user = userService.findOne(userId);
                if (user != null) {
                    UserIntegral userIntegral = user.getUserIntegral();
                    if (userIntegral != null && userIntegral.getId() != null) {
                        if (userIntegral.getIntegral() == null) userIntegral.setIntegral(BigDecimal.ZERO);
                        userIntegral.setIntegral(userIntegral.getIntegral().add(B));
                        if (userIntegral.getTotalIntegral() == null) userIntegral.setTotalIntegral(BigDecimal.ZERO);
                        userIntegral.setTotalIntegral(userIntegral.getTotalIntegral().add(B));
                        recordRepository.updateUserIntegral(userIntegral);
                    } else {
                        userIntegral = new UserIntegral();
                        userIntegral.setUser(user);
                        userIntegral.setVip(0);
                        userIntegral.setIntegral(B);
                        userIntegral.setTotalIntegral(B);
                        recordRepository.saveUserIntegral(userIntegral);
                    }
                    saveUserIntegralRecord(user, B, userIntegral.getIntegral(), "完善资料");
                }
            }
        }
    }


    /**
     * 保存积分记录
     *
     * @param user
     * @param integral
     * @param balance
     * @param fundFlow
     */
    public void saveUserIntegralRecord(User user, BigDecimal integral, BigDecimal balance, String fundFlow) {
        UserIntegralRecord wmUserIntegralRecord = new UserIntegralRecord();
        wmUserIntegralRecord.setUser(user);
        wmUserIntegralRecord.setFundflow(fundFlow);
        wmUserIntegralRecord.setIntegral(integral);
        wmUserIntegralRecord.setBalance(balance);
        wmUserIntegralRecord.setType(1);
        wmUserIntegralRecord.setLinkType(1);
        wmUserIntegralRecord.setCreated(DateUtil.getSystemTimeSeconds());
        userIntegralRecordRepository.save(wmUserIntegralRecord);
    }

    /**
     * 生成积分记录
     * @param user
     * @param fundFlow
     * @param integral
     * @param balance
     * @param type
     * @param linkType
     * @return
     */
    @Override
    public UserIntegralRecord generateIntegralRecord(User user, String fundFlow, BigDecimal integral,
            BigDecimal balance, Integer type, Integer linkType, Long linkId) {
        UserIntegralRecord wmUserIntegralRecord = new UserIntegralRecord();
        wmUserIntegralRecord.setUser(user);
        wmUserIntegralRecord.setFundflow(fundFlow);
        wmUserIntegralRecord.setIntegral(integral);
        wmUserIntegralRecord.setBalance(balance);
        wmUserIntegralRecord.setType(type);
        wmUserIntegralRecord.setLinkType(linkType);
        wmUserIntegralRecord.setCreated(DateUtil.getSystemTimeSeconds());
        wmUserIntegralRecord.setLinkId(linkId);
        return userIntegralRecordRepository.save(wmUserIntegralRecord);
    }


    public IUserIntegralRecordRepository getUserIntegralRecordRepository() {
        return userIntegralRecordRepository;
    }

    public void setUserIntegralRecordRepository(IUserIntegralRecordRepository userIntegralRecordRepository) {
        this.userIntegralRecordRepository = userIntegralRecordRepository;
    }

    public UserIntegralRecordRepository getRecordRepository() {
        return recordRepository;
    }

    public void setRecordRepository(UserIntegralRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
