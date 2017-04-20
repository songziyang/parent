package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserIncomeRecord;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户收益记录service接口
 * @author sy
 */
public interface IUserIncomeRecordService extends IBaseService<UserIncomeRecord, Long> {

    /**
     * 导出excel
     * @param records
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> records, String address);

    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 根据操作时间查询总收益
     * @param startTime 操作时间-开始
     * @param endTime 操作时间-结束
     * @return
     */
    public BigDecimal findSumIncome(String startTime, String endTime);

    /**
     * 查询每日收益
     * @param filter
     * @param pageCurrent 当前页数
     * @param pageSize 每页显示条数
     * @return
     */
    public Page<Object[]> findDailyIncome(Map<String, Object> filter, int pageCurrent, int pageSize);

    /**
     * 查询每日收益导出excel数据
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