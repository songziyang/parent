package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.ActivityYuanXiao;
import com.ydzb.product.entity.PlatformTradingDeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IActivityYuanXiaoService extends IBaseService<ActivityYuanXiao, Long> {

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    public String exportExcel(List<ActivityYuanXiao> data, String address);
}
