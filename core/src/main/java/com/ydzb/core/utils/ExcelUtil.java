package com.ydzb.core.utils;

/**
 * Created by Administrator on 15-9-23.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导出工具类
 * @author sy
 */
public class ExcelUtil {

    private static final int DEFAULT_CELL_WIDTH = 30;  //默认单元格宽度

    /**
     * 设置头部信息
     * @param names
     * @param keys
     * @return
     */
    public static List setHeadInfo(String[] names, String[] keys) {
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> headCell;
        try {
            for (int i = 0; i < names.length; i ++) {
                headCell = new HashMap<String, Object>();
                headCell.put("title", names[i]);
                headCell.put("columnWidth", DEFAULT_CELL_WIDTH);
                headCell.put("dataKey", keys[i]);
                headInfoList.add(headCell);
            }
            return headInfoList;
        } catch (Exception e) {
            return new ArrayList<Map<String, Object>>();
        }
    }
}
