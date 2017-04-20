package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.DepositTypeExpire;
import com.ydzb.product.repository.DepositTypeExpireRepository;
import com.ydzb.product.repository.IDepositTypeExpireRepository;
import com.ydzb.product.service.IDepositTypeExpireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class DepositTypeExpireServiceImpl extends BaseServiceImpl<DepositTypeExpire, Long> implements IDepositTypeExpireService {


    @Autowired
    private IDepositTypeExpireRepository typeExpireRepository;

    @Autowired
    private DepositTypeExpireRepository depositTypeExpireRepository;

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

    /**
     * 查询数据
     *
     * @param startDate 开始日期
     * @param endDate   到期日期
     * @param type      产品类型
     */
    @Override
    public void findDataBetweenDate(String startDate, String endDate, Integer type) {
        //清除所有数据
        typeExpireRepository.removDepositTypeExpires();

        List<DepositTypeExpire> lst = new ArrayList();

        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            //开始时间
            Long start = getSystemTimeDay(startDate);
            //结束时间
            Long end = getSystemTimeDay(endDate);
            if (end >= start) {
                String currentDate = startDate;
                for (int i = 0; i <= (end - start) / (24 * 3600); i++) {
                    if (type == null) {

                        //定存宝
                        BigDecimal opfund1 = depositTypeExpireRepository.findRagularByExpireTime(getSystemTimeDay(currentDate));
                        if (opfund1 != null && opfund1.doubleValue() > 0) {
                            DepositTypeExpire depositTypeExpire1 = new DepositTypeExpire();
                            depositTypeExpire1.setType(1);
                            depositTypeExpire1.setOptime(getSystemTimeDay(currentDate));
                            depositTypeExpire1.setOpfund(opfund1);
                            lst.add(depositTypeExpire1);
                        }

                        //随心存
                        BigDecimal opfund2 = depositTypeExpireRepository.findFreeByExpireTime(getSystemTimeDay(currentDate));
                        if (opfund2 != null && opfund2.doubleValue() > 0) {
                            DepositTypeExpire depositTypeExpire2 = new DepositTypeExpire();
                            depositTypeExpire2.setType(2);
                            depositTypeExpire2.setOptime(getSystemTimeDay(currentDate));
                            depositTypeExpire2.setOpfund(opfund2);
                            lst.add(depositTypeExpire2);
                        }

                        //转转赚
                        BigDecimal opfund3 = depositTypeExpireRepository.findStructureByExpireTime(getSystemTimeDay(currentDate));
                        if (opfund3 != null && opfund3.doubleValue() > 0) {
                            DepositTypeExpire depositTypeExpire3 = new DepositTypeExpire();
                            depositTypeExpire3.setType(3);
                            depositTypeExpire3.setOptime(getSystemTimeDay(currentDate));
                            depositTypeExpire3.setOpfund(opfund3);
                            lst.add(depositTypeExpire3);
                        }

                        //美利金融
                        BigDecimal opfund4 = depositTypeExpireRepository.findBeautyByExpireTime(getSystemTimeDay(currentDate));
                        if (opfund4 != null && opfund4.doubleValue() > 0) {
                            DepositTypeExpire depositTypeExpire4 = new DepositTypeExpire();
                            depositTypeExpire4.setType(4);
                            depositTypeExpire4.setOptime(getSystemTimeDay(currentDate));
                            depositTypeExpire4.setOpfund(opfund4);
                            lst.add(depositTypeExpire4);
                        }

                        //买单侠
                        BigDecimal opfund5 = depositTypeExpireRepository.findMdxByExpireTime(getSystemTimeDay(currentDate));
                        if (opfund5 != null && opfund5.doubleValue() > 0) {
                            DepositTypeExpire depositTypeExpire5 = new DepositTypeExpire();
                            depositTypeExpire5.setType(5);
                            depositTypeExpire5.setOptime(getSystemTimeDay(currentDate));
                            depositTypeExpire5.setOpfund(opfund5);
                            lst.add(depositTypeExpire5);
                        }


                    } else {

                        DepositTypeExpire depositTypeExpire = new DepositTypeExpire();

                        //定存宝
                        if (type == 1) {
                            BigDecimal opfund = depositTypeExpireRepository.findRagularByExpireTime(getSystemTimeDay(currentDate));
                            if (opfund != null && opfund.doubleValue() > 0) {
                                depositTypeExpire.setType(type);
                                depositTypeExpire.setOptime(getSystemTimeDay(currentDate));
                                depositTypeExpire.setOpfund(opfund);
                                lst.add(depositTypeExpire);
                            }
                        }


                        //随心存
                        if (type == 2) {
                            BigDecimal opfund = depositTypeExpireRepository.findFreeByExpireTime(getSystemTimeDay(currentDate));
                            if (opfund != null && opfund.doubleValue() > 0) {
                                depositTypeExpire.setType(type);
                                depositTypeExpire.setOptime(getSystemTimeDay(currentDate));
                                depositTypeExpire.setOpfund(opfund);
                                lst.add(depositTypeExpire);
                            }
                        }


                        //转转赚
                        if (type == 3) {
                            BigDecimal opfund = depositTypeExpireRepository.findStructureByExpireTime(getSystemTimeDay(currentDate));
                            if (opfund != null && opfund.doubleValue() > 0) {
                                depositTypeExpire.setType(type);
                                depositTypeExpire.setOptime(getSystemTimeDay(currentDate));
                                depositTypeExpire.setOpfund(opfund);
                                lst.add(depositTypeExpire);
                            }
                        }

                        //美利金融
                        if (type == 4) {
                            BigDecimal opfund = depositTypeExpireRepository.findBeautyByExpireTime(getSystemTimeDay(currentDate));
                            if (opfund != null && opfund.doubleValue() > 0) {
                                depositTypeExpire.setType(type);
                                depositTypeExpire.setOptime(getSystemTimeDay(currentDate));
                                depositTypeExpire.setOpfund(opfund);
                                lst.add(depositTypeExpire);
                            }
                        }

                        //买单侠
                        if (type == 5) {
                            BigDecimal opfund = depositTypeExpireRepository.findMdxByExpireTime(getSystemTimeDay(currentDate));
                            if (opfund != null && opfund.doubleValue() > 0) {
                                depositTypeExpire.setType(type);
                                depositTypeExpire.setOptime(getSystemTimeDay(currentDate));
                                depositTypeExpire.setOpfund(opfund);
                                lst.add(depositTypeExpire);
                            }
                        }


                    }

                    currentDate = addDay(currentDate);
                }
                //保存数据
                typeExpireRepository.save(lst);
            }
        }

    }


    @Override
    public String exportExcel(List<DepositTypeExpire> data, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(data);
            fileName = "/depositexpire" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("定存类到期", filePath, headInfo, rowInfo);
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
        String[] titles = {"产品", "到期金额", "到期日期"};
        String[] keys = {"type", "opfund", "optime"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<>();
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
    private List<Map<String, Object>> createRowInfo(List<DepositTypeExpire> data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap;
        for (DepositTypeExpire depositTypeExpire : data) {
            rowMap = new HashMap<>();
            if (depositTypeExpire != null) {
                rowMap.put("type", getType(depositTypeExpire.getType()));
                rowMap.put("opfund", depositTypeExpire.getOpfund());
                rowMap.put("optime", df.format(DateUtil.getSystemTimeMillisecond(depositTypeExpire.getOptime())));
            }
            rowList.add(rowMap);
        }
        return rowList;
    }


    private String getType(Integer type) {
        switch (type) {
            case 1:
                return "定存宝";
            case 2:
                return "随心存";
            case 3:
                return "转转赚";
            case 4:
                return "美利金融";
            case 5:
                return "买单侠";
        }
        return "";
    }

    public IDepositTypeExpireRepository getTypeExpireRepository() {
        return typeExpireRepository;
    }

    public void setTypeExpireRepository(IDepositTypeExpireRepository typeExpireRepository) {
        this.typeExpireRepository = typeExpireRepository;
    }

    public DepositTypeExpireRepository getDepositTypeExpireRepository() {
        return depositTypeExpireRepository;
    }

    public void setDepositTypeExpireRepository(DepositTypeExpireRepository depositTypeExpireRepository) {
        this.depositTypeExpireRepository = depositTypeExpireRepository;
    }
}
