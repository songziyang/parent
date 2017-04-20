package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.ProductInfo;
import com.ydzb.product.entity.VProductSales;
import com.ydzb.product.repository.ProductInfoRepository;
import com.ydzb.product.repository.ProductSalesRepository;
import com.ydzb.product.service.IVProductSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品售出service实现
 */
@Service
public class VProductSalesServiceImpl extends BaseServiceImpl<VProductSales, Long> implements IVProductSalesService {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductSalesRepository productSalesRepository;

    @Override
    public Map<String, Object> querySales() {
        return productInfoRepository.querySales();
    }

    @Override
    public String exportExcel(List<Object[]> productSales, String address) {
        String fileName = "";
        try {
            String[] names = {"产品名称", "发布金额", "剩余金额", "当日售出份数", "发布利率(%)", "售出日期"};
            String[] keys = {"proName", "fund", "surplus", "yesterdaySale", "apr", "saleDate"};
            List<Map<String, Object>> headInfoList = ExcelUtil.setHeadInfo(names, keys);
            List<Map<String, Object>> dataList = setData(keys, productSales);
            fileName = "/salelist" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("产品售出列表", filePath, headInfoList, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 设置数据
     * @param keys
     * @param productSales
     * @return
     */
    private List<Map<String, Object>> setData(String[] keys, List<Object[]> productSales) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataItem = null;
        ProductInfo productInfo = null;
        for (Object[] row: productSales) {
            if (row == null) {
                continue;
            }
            dataItem = new HashMap<String, Object>();
            dataItem.put(keys[0], row[0] == null? "": row[0]);
            dataItem.put(keys[1], row[1] == null? "": row[1]);
            dataItem.put(keys[2], row[2] == null? "": row[2]);
            dataItem.put(keys[3], row[3] == null? "": row[3]);
            dataItem.put(keys[4], row[4] == null? "": row[4]);
            dataItem.put(keys[5], row[5] == null ? "" : new SimpleDateFormat("yyyy年MM月dd日").format(DateUtil.getSystemTimeMillisecond(((BigInteger) row[5]).longValue())));
            dataList.add(dataItem);
        }
        return dataList;
    }

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return productSalesRepository.findExportData(filter);
    }
}
