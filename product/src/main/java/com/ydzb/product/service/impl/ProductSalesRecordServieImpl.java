package com.ydzb.product.service.impl;


import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.ProductSalesRecord;
import com.ydzb.product.repository.IProductSalesRecordRepository;
import com.ydzb.product.repository.ProductSalesRecordRepository;
import com.ydzb.product.service.IProductSalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductSalesRecordServieImpl extends BaseServiceImpl<ProductSalesRecord, Long> implements IProductSalesRecordService {

    @Autowired
    private IProductSalesRecordRepository productSalesRecordRepository;

    @Autowired
    private ProductSalesRecordRepository salesRecordRepository;


    /**
     * 查询导出excel数据
     *
     * @param filters
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filters) {
        return salesRecordRepository.findExportData(filters);
    }

    /**
     * 导出excel
     *
     * @param records
     * @param address
     * @return
     */
    @Override
    public String exportExcel(List<Object[]> records, String address) {
        String fileName = "";
        try {
            String[] names = {"产品期数", "发布时间", "发布金额", "剩余金额", "销售状态", "售罄时间", "记录时间"};
            String[] keys = {"productName", "releaseTime", "releaseAmount", "salesAmount", "status", "salesTime", "created"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, records);
            fileName = "/salesrecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("产品销售统计", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * 设置数据
     *
     * @param keys
     * @param records
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> records) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat format = new SimpleDateFormat("yyMMddHH");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataItem;
        for (Object[] row : records) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<>();
            BigInteger typeId = (BigInteger)row[8];

            if(typeId.intValue() == 1) {
                dataItem.put(keys[0], row[0] == null ? "" : row[0]);
            }

            if(typeId.intValue() == 2) {
                dataItem.put(keys[0], row[0] == null ? "" : row[0] + format.format(DateUtil.getSystemTimeMillisecond(((Integer) row[1]).longValue())));
            }

            dataItem.put(keys[1], row[1] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((Integer) row[1]).longValue())));
            dataItem.put(keys[2], row[2] == null ? "0" : row[2]);
            dataItem.put(keys[3], row[7] == null ? "0" : row[7]);
            dataItem.put(keys[4], row[4] == null ? "" : getStatus((Integer) row[4]));
            dataItem.put(keys[5], row[5] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((Integer) row[5]).longValue())));
            dataItem.put(keys[6], row[6] == null ? "" : df.format(DateUtil.getSystemTimeMillisecond(((Integer) row[6]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

    private String getStatus(Integer status) {
        if (status == 1) return "在售";
        if (status == 2) return "售罄";
        return "";
    }

    public IProductSalesRecordRepository getProductSalesRecordRepository() {
        return productSalesRecordRepository;
    }

    public void setProductSalesRecordRepository(IProductSalesRecordRepository productSalesRecordRepository) {
        this.productSalesRecordRepository = productSalesRecordRepository;
    }

    public ProductSalesRecordRepository getSalesRecordRepository() {
        return salesRecordRepository;
    }

    public void setSalesRecordRepository(ProductSalesRecordRepository salesRecordRepository) {
        this.salesRecordRepository = salesRecordRepository;
    }
}
