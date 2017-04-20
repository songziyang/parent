package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.DepositTypeExpire;

import java.util.List;

public interface IDepositTypeExpireService extends IBaseService<DepositTypeExpire, Long> {


    /**
     * 查询数据
     *
     * @param startDate 开始日期
     * @param endDate   到期日期
     * @param type      产品类型
     */
    void findDataBetweenDate(String startDate, String endDate, Integer type);


    /**
     * 导出数据
     *
     * @param data    数据
     * @param address 地址
     * @return
     */
    String exportExcel(List<DepositTypeExpire> data, String address);

}