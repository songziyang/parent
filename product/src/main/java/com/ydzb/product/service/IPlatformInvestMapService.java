package com.ydzb.product.service;

import com.ydzb.core.entity.search.Searchable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPlatformInvestMapService {

    /**
     * 查询实时在投金额地区分布json
     *
     * @return
     */
    public String findInvestFunds();

    /**
     * 查询实时在投人数地区分布json
     *
     * @return
     */
    public String findInvestPersons();


    /**
     * 查询实时再投金额地区分布json
     *
     * @param pname 省份
     * @return
     */
    public String findInvestFundsByPName(String pname);


    /**
     * 查询实时再投人数地区分布json
     *
     * @param pname 省份
     * @return
     */
    public String findInvestPersonsByPName(String pname);


    /**
     * 查询累计充值金额地区分布json
     *
     * @return
     */
    public String findRechargeFunds();


    /**
     * 查询累计充值人数地区分布json
     *
     * @return
     */
    public String findRechargePersons();


    /**
     * 查询累计充值金额地区分布json
     *
     * @param pname 省份
     * @return
     */
    public String findRechargeFundsByPName(String pname);


    /**
     * 查询累计充值人数地区分布json
     *
     * @param pname 省份
     * @return
     */
    public String findRechargePersonsByPName(String pname);


    /**
     * 查询性别
     *
     * @return
     */
    public String findSexNum();


    /**
     * 查询年龄
     *
     * @return
     */
    public String findAgeNum();


    /**
     * 根据省份条件查询待导出的数据集合
     * @param province
     * @param city
     * @return
     */
    public List<Object[]> getExportData(String province, String city);


    /**
     * 将数据导出到Excel
     * @param list
     * @param path
     * @return
     */
    public String exportExcel(List<Object[]>list, String path, String province, String city);


    /**
     * 在投统计各省列表
     * @param searchable
     * @param province
     * @return
     */
    public Page<Object[]> getInvestList(Searchable searchable, String province);

}
