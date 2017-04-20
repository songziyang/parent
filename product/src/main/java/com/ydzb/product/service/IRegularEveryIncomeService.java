package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.RegularEveryIncome;

import java.util.List;

public interface IRegularEveryIncomeService extends IBaseService<RegularEveryIncome, Long> {

    public void findRevenuBetweenDate(String startDate, String endDate);

    public String exportExcel(List<RegularEveryIncome> data, String address);


}
