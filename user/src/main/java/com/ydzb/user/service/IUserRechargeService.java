package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.entity.UserRecharge;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUserRechargeService extends IBaseService<UserRecharge, Long> {

    public BigDecimal findSumRecharge(String startTime, String endTime);


    public String exportExcele(List<Object> userRecharges,String address);

    /**
     * 导出充值状态
     * @param userRecharges
     * @param address
     * @return
     */
    public String exportRechargeState(List<Object[]> userRecharges, String address);

    /**
     * 导出充值详情
     * @param userInfos
     * @param address
     * @return
     */
    public String exportDetail(List<Object[]> userInfos,String address);


    /**
     * 查询导出excel所需的数据
     * @return
     */
    public List<Object> findExportData(Map<String, Object> filters);

    /**
     * 查询充值详细导出excel所需的数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportDetailData(Map<String, Object> filters);

    /**
     * 查询充值状态导出excel所需数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportResponseData(Map<String, Object> filter);
}
