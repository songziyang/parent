package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ActivityYuanXiao;
import com.ydzb.packet.service.IActivityYuanXiaoService;
import com.ydzb.product.entity.PlatformTradingDeal;
import com.ydzb.user.entity.User;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ActivityYuanXiaoServiceImpl extends BaseServiceImpl<ActivityYuanXiao, Long> implements IActivityYuanXiaoService {

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(final List<ActivityYuanXiao> data, final String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(data);

            fileName = "/activityyuanxiao_" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("活动积分", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = {"用户名", "手机号", "答题次数", "积分"};
        String[] keys = {"username", "mobile", "answerCount", "rewardScore"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i ++) {
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
     * @param data
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<ActivityYuanXiao> data) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (ActivityYuanXiao yuanxiao: data) {
            rowMap = new HashMap<String, Object>();
            if (yuanxiao == null) {
                rowMap.put("username", "");
                rowMap.put("mobile", "");
                rowMap.put("answerCount", "");
                rowMap.put("rewardScore", "");
            } else {
                final User user = yuanxiao.getUser();
                rowMap.put("username", user == null? "": user.getUsername());
                rowMap.put("mobile", user == null? "": user.getMobile());
                rowMap.put("answerCount", user == null? 0: yuanxiao.getNums());
                rowMap.put("rewardScore", user == null? 0: yuanxiao.getScores());
            }
            rowList.add(rowMap);
        }
        return rowList;
    }
}
