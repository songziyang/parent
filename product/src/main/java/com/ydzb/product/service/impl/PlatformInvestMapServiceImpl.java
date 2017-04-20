package com.ydzb.product.service.impl;

import com.google.gson.Gson;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.InvestMap;
import com.ydzb.product.repository.PlatformInvestMapRepository;
import com.ydzb.product.service.IPlatformInvestMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlatformInvestMapServiceImpl implements IPlatformInvestMapService {

    @Autowired
    private PlatformInvestMapRepository platformInvestMapRepository;

    /**
     * 查询实时在投金额地区分布json
     *
     * @return
     */
    @Override
    public String findInvestFunds() {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findInvestData();
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String provinceName = String.valueOf(row[2]);
                if (provinceName != null && provinceName.contains("省")) {
                    provinceName = provinceName.replaceAll("省", "");
                }
                if (provinceName != null && provinceName.contains("自治区")) {
                    provinceName = provinceName.replaceAll("自治区", "");
                }
                if (provinceName != null && provinceName.contains("市")) {
                    provinceName = provinceName.replaceAll("市", "");
                }
                investMap.setName(provinceName);
                investMap.setValue(df.format(row[1]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询实时在投人数地区分布json
     *
     * @return
     */
    @Override
    public String findInvestPersons() {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findInvestData();
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String provinceName = String.valueOf(row[2]);
                if (provinceName != null && provinceName.contains("省")) {
                    provinceName = provinceName.replaceAll("省", "");
                }
                if (provinceName != null && provinceName.contains("自治区")) {
                    provinceName = provinceName.replaceAll("自治区", "");
                }
                if (provinceName != null && provinceName.contains("市")) {
                    provinceName = provinceName.replaceAll("市", "");
                }
                investMap.setName(provinceName);
                investMap.setValue(df.format(row[0]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }

    /**
     * 查询实时在投金额地区分布json
     *
     * @param pname 省份
     * @return
     */
    @Override
    public String findInvestFundsByPName(String pname) {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findInvestDataByPName(pname);
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String cityName = String.valueOf(row[2]);
                if (cityName != null && !cityName.endsWith("市") && !cityName.endsWith("区") && !cityName.endsWith("自治州") && !cityName.endsWith("县")) {
                    cityName = cityName + "市";
                }
                investMap.setName(cityName);
                investMap.setValue(df.format(row[1]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询实时在投人数地区分布json
     *
     * @param pname 省份
     * @return
     */
    @Override
    public String findInvestPersonsByPName(String pname) {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findInvestDataByPName(pname);
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String cityName = String.valueOf(row[2]);
                if (cityName != null && !cityName.endsWith("市") && !cityName.endsWith("区") && !cityName.endsWith("自治州") && !cityName.endsWith("县")) {
                    cityName = cityName + "市";
                }
                investMap.setName(cityName);
                investMap.setValue(df.format(row[0]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询累计充值金额地区分布json
     *
     * @return
     */
    @Override
    public String findRechargeFunds() {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findRechargeData();
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String provinceName = String.valueOf(row[2]);
                if (provinceName != null && provinceName.contains("省")) {
                    provinceName = provinceName.replaceAll("省", "");
                }
                if (provinceName != null && provinceName.contains("自治区")) {
                    provinceName = provinceName.replaceAll("自治区", "");
                }
                if (provinceName != null && provinceName.contains("市")) {
                    provinceName = provinceName.replaceAll("市", "");
                }
                investMap.setName(provinceName);
                investMap.setValue(df.format(row[1]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询累计充值人数地区分布json
     *
     * @return
     */
    @Override
    public String findRechargePersons() {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findRechargeData();
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String provinceName = String.valueOf(row[2]);
                if (provinceName != null && provinceName.contains("省")) {
                    provinceName = provinceName.replaceAll("省", "");
                }
                if (provinceName != null && provinceName.contains("自治区")) {
                    provinceName = provinceName.replaceAll("自治区", "");
                }
                if (provinceName != null && provinceName.contains("市")) {
                    provinceName = provinceName.replaceAll("市", "");
                }
                investMap.setName(provinceName);
                investMap.setValue(df.format(row[0]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询累计充值金额地区分布json
     *
     * @param pname 省份
     * @return
     */
    @Override
    public String findRechargeFundsByPName(String pname) {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findRechargeDataByPName(pname);
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String cityName = String.valueOf(row[2]);
                if (cityName != null && !cityName.endsWith("市") && !cityName.endsWith("区") && !cityName.endsWith("自治州") && !cityName.endsWith("县")) {
                    cityName = cityName + "市";
                }
                investMap.setName(cityName);
                investMap.setValue(df.format(row[1]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }

    /**
     * 查询累计充值人数地区分布json
     *
     * @param pname 省份
     * @return
     */
    @Override
    public String findRechargePersonsByPName(String pname) {
        // 0 人数 1 金额 2 省份
        DecimalFormat df = new DecimalFormat("#.0");
        List<InvestMap> data = new ArrayList<>();
        List<Object[]> records = platformInvestMapRepository.findRechargeDataByPName(pname);
        if (records != null && !records.isEmpty()) {
            for (Object[] row : records) {
                if (row == null) {
                    continue;
                }
                InvestMap investMap = new InvestMap();
                String cityName = String.valueOf(row[2]);
                if (cityName != null && !cityName.endsWith("市") && !cityName.endsWith("区") && !cityName.endsWith("自治州") && !cityName.endsWith("县")) {
                    cityName = cityName + "市";
                }
                investMap.setName(cityName);
                investMap.setValue(df.format(row[0]));
                data.add(investMap);
            }
        }
        return new Gson().toJson(data);
    }


    /**
     * 查询性别
     *
     * @return
     */
    @Override
    public String findSexNum() {
        List<InvestMap> data = new ArrayList<>();
        InvestMap nan = new InvestMap();
        nan.setName("男");
        nan.setValue(String.valueOf(platformInvestMapRepository.findSexNum(1)));
        data.add(nan);

        InvestMap nv = new InvestMap();
        nv.setName("女");
        nv.setValue(String.valueOf(platformInvestMapRepository.findSexNum(0)));
        data.add(nv);

        return new Gson().toJson(data);
    }

    /**
     * 查询年龄
     * <p/>
     * {value: 335, name: '0-25'},
     * {value: 310, name: '25-35'},
     * {value: 310, name: '35-45'},
     * {value: 310, name: '45-55'},
     * {value: 310, name: '55-65'},
     * {value: 335, name: '65-100'}
     */
    @Override
    public String findAgeNum() {

        List<InvestMap> data = new ArrayList<>();
        InvestMap i25 = new InvestMap();
        i25.setName("0-25");
        i25.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(0, 25)));
        data.add(i25);

        InvestMap i35 = new InvestMap();
        i35.setName("25-35");
        i35.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(25, 35)));
        data.add(i35);

        InvestMap i45 = new InvestMap();
        i45.setName("35-45");
        i45.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(35, 45)));
        data.add(i45);


        InvestMap i55 = new InvestMap();
        i55.setName("45-55");
        i55.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(45, 55)));
        data.add(i55);

        InvestMap i65 = new InvestMap();
        i65.setName("55-65");
        i65.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(55, 65)));
        data.add(i65);

        InvestMap i100 = new InvestMap();
        i100.setName("65-100");
        i100.setValue(String.valueOf(platformInvestMapRepository.findAgeNum(65, 100)));
        data.add(i100);

        return new Gson().toJson(data);
    }


    /**
     * 在投统计各省列表
     * @param searchable
     * @param province
     * @return
     */
    public Page<Object[]> getInvestList(Searchable searchable, String province) {
        List<Object[]> list =  platformInvestMapRepository.findInvestDataByPName(province);
        return new PageImpl<Object[]>(
                getSubList(searchable, list),
                searchable.getPage(),
                list.size()
        );
    }

    /**
     * 根据分页条件获取子集合
     * @param searchable
     * @param list
     * @return
     */
    private List<Object[]> getSubList(Searchable searchable, List<Object[]> list) {
        List<Object[]> sublist = null;
        PageRequest page = (PageRequest)searchable.getPage();
        int pageNo = page.getPageNumber();
        int pageSize = page.getPageSize();
        int toIndex = 0;
        if (pageNo * pageSize + pageSize > list.size()) {
            toIndex = list.size();
        }
        else {
            toIndex = pageNo * pageSize + pageSize;
        }
        sublist = list.subList(pageNo * pageSize, toIndex);
        return sublist;
    }


    /**
     * 根据省份条件查询待导出的数据集合
     * @author: CRF
     * @2017.03.09
     * @param province
     * @param city
     * @return
     */
    public List<Object[]> getExportData(String province, String city) {
        return platformInvestMapRepository.getExportData(province, city);
    }

    /**
     * 将数据导出到Excel
     * @author:CRF
     * @2017.03.09
     * @param list
     * @param path
     * @return
     */
    public String exportExcel(List<Object[]> list, String path, String province, String city) {
        String fileName = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();

            Map<String, Object> itemMap = new HashMap<String, Object>();

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "用户名");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "username");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "手机号");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "mobile");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "年龄");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "age");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "性别");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "sex");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "总投资金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "allinvest");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "活期投资金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "dayloan");
            headInfoList.add(itemMap);

            itemMap = new HashMap<String, Object>();
            itemMap.put("title", "定存投资金额");
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", "deposit");
            headInfoList.add(itemMap);

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

            Map<String, Object> dataItem = null;

            for (Object[] row : list) {
                if (row == null) {
                    continue;
                }
                dataItem = new HashMap<String, Object>();
                dataItem.put("username", row[0] == null ? "" : row[0]);
                dataItem.put("mobile", row[1] == null ? "" : row[1]);
                dataItem.put("age", row[2] == null ? "" : row[2]);
                dataItem.put("sex", row[3] == null ? "" : row[3]);
                dataItem.put("allinvest", row[4] == null ? "0.000000" : row[4]);
                dataItem.put("dayloan", row[5] == null ? "0.000000" : row[5]);
                dataItem.put("deposit", row[6] == null ? "0.000000" : row[6]);
                dataList.add(dataItem);
            }

            fileName = "/investmap" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = path + fileName;

            String title = province + city;
            if ("all".equals(city)) {
                title = province;
            }
            POIUtil.exportExcel2FilePath(title, filePath, headInfoList, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }



    public PlatformInvestMapRepository getPlatformInvestMapRepository() {
        return platformInvestMapRepository;
    }

    public void setPlatformInvestMapRepository(PlatformInvestMapRepository platformInvestMapRepository) {
        this.platformInvestMapRepository = platformInvestMapRepository;
    }
}