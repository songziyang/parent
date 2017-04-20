package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.BeautyUserAccount;

import java.util.List;
import java.util.Map;

public interface IBeautyUserAccountService extends IBaseService<BeautyUserAccount, Long> {


    List<Object[]> findExportData(Map<String, Object> filter);

    String exportExcele(List<Object[]> beautyUserAccounts, String address);

}
