package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserManualRecharge;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUserManualRechargeService extends IBaseService<UserManualRecharge, Long> {


    /**
     *
     * @param userManualRecharges
     * @param address
     * @return
     */
    public String exportExcele(List<Object[]> userManualRecharges,String address);



    /**
     *
     * @param user
     * @param fund
     * @param adminId
     * @param remark
     */
    public Long saveUserManualRecharge(User user, BigDecimal fund, Long adminId, String remark) ;


    public List<Object[]> findExportData(Map<String, Object> filter);
}
