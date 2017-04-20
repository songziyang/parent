package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserProfitRecord;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户每日额外收益记录service接口
 */
public interface IUserProfitRecordService extends IBaseService<UserProfitRecord, Long> {

    /**
     * 查询每日
     * @param filter
     * @param pageCurrent 当前页数(从0开始)
     * @param pageSize 每页显示条数
     * @return
     */
    public Page<Object[]> findDailyFund(Map<String, Object> filter, int pageCurrent, int pageSize);

    /**
     * 根据记录日期段查询总收益
     * @param startTime 操作时间-开始
     * @param endTime 操作时间-结束
     * @return
     */
    public BigDecimal findSumIncome(String startTime, String endTime);

    /**
     * 查询每日额外收益导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findDailyIncomeExportData(Map<String, Object> filter);

    /**
     * 每日收益导出excel
     * @param records
     * @param address
     * @return
     */
    public String dailyIncomeExportExcel(List<Object[]> records, String address);
}