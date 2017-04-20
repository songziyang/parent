package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.RegularEveryIncome;
import com.ydzb.product.repository.IRegularEveryIncomeRepository;
import com.ydzb.product.repository.RagularUserAccountRepository;
import com.ydzb.product.service.IRegularEveryIncomeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RegularEveryIncomeServiceImpl extends BaseServiceImpl<RegularEveryIncome, Long> implements IRegularEveryIncomeService {

    @Autowired
    private IRegularEveryIncomeRepository regularEverIncomeRepository;

    @Autowired
    private RagularUserAccountRepository ragularUserAccountRepository;

    /**
     * 给定的日期加一天 格式为：2009-08-01
     */
    public static String addDay(String strdate) {
        String dateresult = null; // 返回的日期字符串
        try {
            Date date = new Date(); // 构造一个日期型中间变量
            // 创建格式化格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            // 加减日期所用
            GregorianCalendar gc = new GregorianCalendar();
            date = df.parse(strdate); // 将字符串格式化为日期型
            gc.setTime(date); // 得到gc格式的时间
            gc.add(5, 1); // 2表示月的加减，年代表1依次类推(３周....5天。。)
            // 把运算完的时间从新赋进对象
            gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
            // 在格式化回字符串时间
            dateresult = df.format(gc.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateresult;
    }

    public static Long getSystemTimeDay(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            if (!StringUtils.isEmpty(date)) {
                Date d = sdf.parse(date);
                return d.getTime() / 1000L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void findRevenuBetweenDate(String startDate, String endDate) {
        regularEverIncomeRepository.removeAllInfo();
        DecimalFormat df = new DecimalFormat("0.00");
        List<RegularEveryIncome> lst = new ArrayList();
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            Long start = getSystemTimeDay(startDate);
            Long end = getSystemTimeDay(endDate);
            if (end >= start) {
                String currentDate = startDate;
                for (int i = 0; i <= (end - start) / (24 * 3600); i++) {
                    RegularEveryIncome regularEveryIncome = new RegularEveryIncome();
                    BigDecimal b = ragularUserAccountRepository.findEveryDayIncome(getSystemTimeDay(currentDate));
                    regularEveryIncome.setFund(new BigDecimal(df.format(b)));
                    regularEveryIncome.setSdate(currentDate);
                    lst.add(regularEveryIncome);
                    currentDate = addDay(currentDate);
                }
                regularEverIncomeRepository.save(lst);
            }
        }
    }

    /**
     * 导出excel
     */
    @Override
    public String exportExcel(List<RegularEveryIncome> data, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(data);
            fileName = "/income" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("定存每日支付", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     *
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = {"支出", "日期"};
        String[] keys = {"fund", "sdate"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<String, Object>();
            itemMap.put("title", titles[i]);
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", keys[i]);
            headInfoList.add(itemMap);
        }
        return headInfoList;
    }

    /**
     * 创建行信息
     *
     * @param data
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<RegularEveryIncome> data) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (RegularEveryIncome regularEveryIncome : data) {
            rowMap = new HashMap<String, Object>();
            if (regularEveryIncome != null) {
                rowMap.put("fund", regularEveryIncome.getFund().doubleValue());
                rowMap.put("sdate", regularEveryIncome.getSdate());
            }
            rowList.add(rowMap);
        }
        return rowList;
    }

    public IRegularEveryIncomeRepository getRegularEverIncomeRepository() {
        return regularEverIncomeRepository;
    }

    public void setRegularEverIncomeRepository(IRegularEveryIncomeRepository regularEverIncomeRepository) {
        this.regularEverIncomeRepository = regularEverIncomeRepository;
    }

    public RagularUserAccountRepository getRagularUserAccountRepository() {
        return ragularUserAccountRepository;
    }

    public void setRagularUserAccountRepository(RagularUserAccountRepository ragularUserAccountRepository) {
        this.ragularUserAccountRepository = ragularUserAccountRepository;
    }
}
