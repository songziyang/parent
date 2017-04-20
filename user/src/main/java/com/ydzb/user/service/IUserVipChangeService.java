package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserVipChange;
import com.ydzb.user.entity.VipGrade;

import java.util.List;
import java.util.Map;

/**
 * 用户vip更改service接口
 */
public interface IUserVipChangeService extends IBaseService<UserVipChange, Long> {

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    List<Object[]> findExportData(Map<String, Object> filters);

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    String exportExcel(List<Object[]> data, String address);

    /**
     * 创建
     * @param user
     * @param oldGrade
     * @param newGrade
     * @param cType
     * @param oType
     * @return
     */
    UserVipChange createOne(User user, VipGrade oldGrade, VipGrade newGrade, Integer cType, Integer oType) throws Exception;

    /**
     * 创建
     * @param userId
     * @param oldGradeId
     * @param newGradeId
     * @param cType
     * @param oType
     * @return
     */
    UserVipChange createOne(Long userId, Long oldGradeId, Long newGradeId, Integer cType, Integer oType) throws Exception;

    /**
     * 创建
     * @param userId
     * @param newGradeId
     * @param otype
     * @return
     * @throws Exception
     */
    UserVipChange createOne(Long userId, Long newGradeId, Integer otype) throws Exception;
}