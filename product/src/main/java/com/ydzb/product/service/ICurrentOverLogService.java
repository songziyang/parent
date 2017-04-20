package com.ydzb.product.service;



import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.CurrentOverLog;

import java.util.List;

public interface ICurrentOverLogService extends IBaseService<CurrentOverLog, Long> {

    public String exportExcele(List<CurrentOverLog> currentOverLogs,String address);

}
