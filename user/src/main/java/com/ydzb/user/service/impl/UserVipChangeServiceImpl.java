package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserVipChange;
import com.ydzb.user.entity.VipGrade;
import com.ydzb.user.repository.IVipGradeRepository;
import com.ydzb.user.repository.UserVipChangeRepository;
import com.ydzb.user.service.IUserVipChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户vip更改service实现
 */
@Service
public class UserVipChangeServiceImpl extends BaseServiceImpl<UserVipChange, Long> implements IUserVipChangeService {

    @Autowired
    private UserVipChangeRepository userVipChangeRepository;
    @Autowired
    private IVipGradeRepository vipGradeRepository;

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return userVipChangeRepository.findExportData(filters);
    }

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> data, String address) {
        String fileName = "";
        try {
            String[] names = {"用户名", "手机号", "原vip等级", "现vip等级", "变更类型", "操作类型", "操作日期"};
            String[] keys = {"username", "mobile", "oldVipGrade", "newVipGrade", "ctype", "oType", "opdate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, data);
            fileName = "/vipchange" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("vip变更列表", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     * @param keys
     * @param records
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> records) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row: records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? "": row[2]);
            dataItem.put(keys[3], row[3] == null? "": row[3]);
            dataItem.put(keys[4], row[4] == null? "": getCtypeName((Integer) row[4]));
            dataItem.put(keys[5], row[5] == null? "": getOtypeName((Integer) row[5]));
            dataItem.put(keys[6], row[6] == null? "": getOpdate((Integer) row[6]));
            dataList.add(dataItem);
        }
        return dataList;
    }

    private String getCtypeName(Integer ctype) {
        if (ctype == null) {
            return "";
        }
        switch (ctype) {
            case 1: return "vip升级";
            case 2: return "vip降级";
            default: return "";
        }
    }

    private String getOtypeName(Integer otype) {
        if (otype == null) {
            return "";
        }
        switch (otype) {
            case 1: return "投资";
            case 2: return "手动";
            default: return "";
        }
    }

    private String getOpdate(Integer optime) {
        if (optime == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getSystemTimeMillisecond(Long.valueOf(optime)));
    }

    /**
     * 创建
     * @param user
     * @param oldGrade
     * @param newGrade
     * @param cType
     * @param oType
     * @return
     */
    @Override
    public UserVipChange createOne(User user, VipGrade oldGrade, VipGrade newGrade, Integer cType, Integer oType) throws Exception {

        UserVipChange userVipChange = new UserVipChange();
        userVipChange.setUser(user);
        userVipChange.setOldGrade(oldGrade);
        userVipChange.setNewGrade(newGrade);
        userVipChange.setcType(cType);
        userVipChange.setoType(oType);
        userVipChange.setOptime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        return save(userVipChange);
    }

    /**
     * 创建
     * @param userId
     * @param oldGradeId
     * @param newGradeId
     * @param cType
     * @param oType
     * @return
     */
    @Override
    public UserVipChange createOne(Long userId, Long oldGradeId, Long newGradeId, Integer cType, Integer oType) throws Exception {

        return createOne(new User(userId), new VipGrade(oldGradeId), new VipGrade(newGradeId), cType, oType);
    }

    /**
     * 创建
     * @param userId
     * @param newGradeId
     * @param otype
     * @return
     * @throws Exception
     */
    @Override
    public UserVipChange createOne(Long userId, Long newGradeId, Integer otype) throws Exception {

        VipGrade sourceGrade = vipGradeRepository.queryByUser(userId);
        VipGrade targetGrade = vipGradeRepository.findOne(newGradeId);
        Integer cType = culculateCType(sourceGrade, targetGrade);
        return createOne(new User(userId), sourceGrade, targetGrade, cType, otype);
    }

    /**
     * 计算改变类型
     * @param sourceGrade
     * @param targetGrade
     * @return
     */
    private Integer culculateCType(VipGrade sourceGrade, VipGrade targetGrade) {

        if (targetGrade == null || targetGrade.getGradeNum() == null) {
            return UserVipChange.CTYPE_LEVELDOWN;
        }
        if (sourceGrade == null || sourceGrade.getGradeNum() == null) {
            return UserVipChange.CTYPE_LEVELUP;
        }
        Integer compareValue = targetGrade.compareTo(sourceGrade);
        if (compareValue == 1) return UserVipChange.CTYPE_LEVELUP;
        if (compareValue == -1) return UserVipChange.CTYPE_LEVELDOWN;
        return 0;
    }
}