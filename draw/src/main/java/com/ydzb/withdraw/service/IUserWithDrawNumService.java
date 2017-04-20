package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserWithDrawNum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 用户提现次数service接口
 */
public interface IUserWithDrawNumService extends IBaseService<UserWithDrawNum, Long> {

    /**
     * 导出excel
     *
     * @param withDrawNums
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> withDrawNums, String address);

    /**
     * 导出体现excel
     *
     * @param userWithDraws
     * @param address
     * @return
     */
    public String exportWithDrawExcel(List<Object[]> userWithDraws, String address);

    /**
     * 查询提现导出excel数据
     *
     * @param filters
     * @return
     */
    public List<Object[]> findWithdrawExportData(Map<String, Object> filters);

    public List<Object[]> findExportData(Map<String, Object> filter);
}
