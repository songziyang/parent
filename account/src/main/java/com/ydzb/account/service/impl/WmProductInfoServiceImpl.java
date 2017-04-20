package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.IWmProductInfoService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class WmProductInfoServiceImpl implements IWmProductInfoService {

    @Autowired
    private WmProductInfoRepository productInfoRepository;

    @Value("${product.starttime}")
    private Long start_time;

    /**
     * 自动发布活期宝
     * @throws Exception
     */
    @Override
    public void autoCurrentProductInfo() throws Exception {

        //查询最新一期活期宝产品
        WmProductInfo productInfo = productInfoRepository.findWmProductInfo();
        if (productInfo != null) {
            //生成产品名称
            String name = getProductInfoName();
            //预投总份数
            Integer copies = productInfoRepository.findTotalCopies();
            if (copies == null) copies = 0;
            //查询活期宝利率
            WmCurrentRate currentRate = productInfoRepository.findWmCurrentRate(0);
            //产品往期剩余份数
            int surplus = 0;
            //判读是否有设置对照表
            if (currentRate != null) {
                //判断是否作为发布份数
                if (currentRate.getReleaseType() == null || currentRate.getReleaseType() == 0) {
                    //发布份数 > 0
                    if ((copies + currentRate.getCopies()) > 0) surplus = sumProductSurplus(productInfo);
                    //发布产品
                    Long productId = saveWmProductInfo(name, productInfo.getType(), productInfo.getProductClas(), productInfo.getStage(), currentRate.getCurrentRate(), copies + currentRate.getCopies() + surplus);
                    //保存产品销售记录
                    saveWmProductSalesRecord(productId, 1, productInfo.getType(), copies + currentRate.getCopies());
                } else if (currentRate.getReleaseType() == 1) {
                    //发布份数 > 0
                    if (currentRate.getCopies() > 0) surplus = sumProductSurplus(productInfo);
                    //发布产品
                    Long productId = saveWmProductInfo(name, productInfo.getType(), productInfo.getProductClas(), productInfo.getStage(), currentRate.getCurrentRate(), currentRate.getCopies() + surplus);
                    //保存产品销售记录
                    saveWmProductSalesRecord(productId, 1, productInfo.getType(), currentRate.getCopies());
                }
            } else {
                //发布份数 > 0
                if (copies > 0) surplus = sumProductSurplus(productInfo);
                //发布产品
                Long productId = saveWmProductInfo(name, productInfo.getType(), productInfo.getProductClas(), productInfo.getStage(), productInfo.getInterestRate(), copies + surplus);
                //保存产品销售记录
                saveWmProductSalesRecord(productId, 1, productInfo.getType(), copies);
            }
            //更新产品记录
            productInfo.setStatus(1);
            productInfo.setUpdatedUser("系统");
            productInfoRepository.updateWmProductInfo(productInfo);
        }
    }


    /**
     * 活期产品名称
     * @return
     */
    private String getProductInfoName() {
        StringBuffer name = new StringBuffer();
        name.append("活期宝");
        name.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        name.append("-1期");
        return name.toString();
    }

    /**
     * 保存活期产品信息
     *
     * @param name         产品名称
     * @param type         产品类型 如活期 定存
     * @param productClas  产品类别
     * @param stage        期数
     * @param interestRate 年化收益率
     * @param copies       份数
     */
    private Long saveWmProductInfo(String name, Integer type, Integer productClas, Integer stage, BigDecimal interestRate, Integer copies) {
        WmProductInfo productInfo = new WmProductInfo();
        //产品名称
        productInfo.setName(name);
        //类型 : 1活期宝，2定存宝，3麦多宝
        productInfo.setType(type);
        //产品类别： 1银多 2机构
        productInfo.setProductClas(productClas);
        //期数 +1
        productInfo.setStage(stage + 1);
        //年利率
        productInfo.setInterestRate(interestRate);
        //发布金额
        productInfo.setFunds(copies);
        //发布份数
        productInfo.setCopies(copies);
        //剩余金额
        productInfo.setSurplus(copies);
        //剩余份数
        productInfo.setResidue(copies);
        //每份金额
        productInfo.setUnit(1);
        //天数
        productInfo.setCylcleDays(1);
        //状态 0使用/1停用
        productInfo.setStatus(0);
        //创建人真实姓名
        productInfo.setCreatedUser("系统");
        //创建时间
        productInfo.setCreated(DateUtil.getSystemTimeSeconds());
        //发布方式
        productInfo.setCreateWay(1);
        //发布产品
        return productInfoRepository.saveWmProductInfo(productInfo);

    }

    /**
     * 自动发布半月宝
     *
     * @throws Exception
     */
    @Override
    public void autoShortRagularProductInfo() throws Exception {
        //查询半月宝产品
        WmProductInfo productInfo = productInfoRepository.findRagularProductInfo();

        if (productInfo != null && productInfo.getCreateWay() == 1) {
            WmProductInfo ragularProductInfo = new WmProductInfo();
            ragularProductInfo.setName(productInfo.getName());
            ragularProductInfo.setType(productInfo.getType());
            ragularProductInfo.setProductClas(productInfo.getProductClas());
            ragularProductInfo.setStage(productInfo.getStage() + 1);
            ragularProductInfo.setInterestRate(productInfo.getInterestRate());
            ragularProductInfo.setFunds(productInfo.getFunds());
            ragularProductInfo.setCopies(productInfo.getFunds());
            ragularProductInfo.setSurplus(productInfo.getFunds());
            ragularProductInfo.setResidue(productInfo.getFunds());
            ragularProductInfo.setUnit(productInfo.getUnit());
            ragularProductInfo.setCylcleDays(productInfo.getCylcleDays());
            ragularProductInfo.setStatus(productInfo.getStatus());
            ragularProductInfo.setCreatedUser(productInfo.getCreatedUser());
            ragularProductInfo.setCreated(DateUtil.getSystemTimeSeconds());
            ragularProductInfo.setCreateWay(productInfo.getCreateWay());
            productInfoRepository.saveWmProductInfo(ragularProductInfo);

            //更新产品记录
            productInfo.setStatus(1);
            productInfo.setUpdatedUser("系统");
            productInfoRepository.updateWmProductInfo(productInfo);

        }
    }

    /**
     * 定存宝自动发布
     *
     * @throws Exception
     */
    @Override
    public void autoRagularProductInfo() throws Exception {
        //查询产品
        List<WmProductInfo> productInfoLst = productInfoRepository.findRagular();
        if (productInfoLst != null && !productInfoLst.isEmpty()) {
            for (WmProductInfo productInfo : productInfoLst) {
                //重新锁表查询
                productInfo = productInfoRepository.findWmProductInfoById(productInfo.getId());
                //如果产品不为空 并且自动发布
                if (productInfo != null && productInfo.getCreateWay() == 1 && productInfo.getSurplus() == 0) {
                    //定存宝利率设置
                    WmRagularRate wmRagularRate = productInfoRepository.findWmRagularRate(productInfo.getCylcleDays());
                    //往期产品剩余份数
                    int surplus = 0;
                    //是否设置
                    if (wmRagularRate != null) {
                        //发布份数是否大于0
                        if (wmRagularRate.getCopies() > 0) surplus = sumProductSurplus(productInfo);
                        //发布产品
                        Long productId = saveRagularRateProductInfo(productInfo.getName(), productInfo.getType(),
                                productInfo.getProductClas(), productInfo.getStage() + 1,
                                wmRagularRate.getRagularRate(), wmRagularRate.getCopies() + surplus,
                                productInfo.getUnit(), productInfo.getCylcleDays(),
                                productInfo.getStatus(), productInfo.getCreatedUser(),
                                productInfo.getCreateWay(), wmRagularRate.getActivityRate());
                        //保存产品销售记录
                        saveWmProductSalesRecord(productId, productInfo.getCylcleDays(), productInfo.getType(), wmRagularRate.getCopies());
                    } else {
                        //发布份数是否大于0
                        if (productInfo.getFunds() > 0) surplus = sumProductSurplus(productInfo);
                        //发布产品
                        Long productId = saveRagularRateProductInfo(productInfo.getName(), productInfo.getType(),
                                productInfo.getProductClas(), productInfo.getStage() + 1,
                                productInfo.getInterestRate(), productInfo.getFunds() + surplus,
                                productInfo.getUnit(), productInfo.getCylcleDays(),
                                productInfo.getStatus(), productInfo.getCreatedUser(),
                                productInfo.getCreateWay(), productInfo.getActivityRate());

                        //保存产品销售记录
                        saveWmProductSalesRecord(productId, productInfo.getCylcleDays(), productInfo.getType(), productInfo.getFunds());
                    }
                    //更新产品记录
                    productInfo.setStatus(1);
                    productInfo.setUpdatedUser("系统");
                    productInfoRepository.updateWmProductInfo(productInfo);
                }
            }
        }
    }

    /**
     * 保存定存宝产品信息
     *
     * @param name         产品名称
     * @param typeId       产品类型 如活期 定存
     * @param productClas  产品类别
     * @param stage        期数
     * @param interestRate 年化收益率
     * @param funds        份数
     * @param unit         单位
     * @param cylcleDays   周期
     * @param status       状态
     * @param createdUser  创建人
     * @param createWay    创建方式
     */
    private Long saveRagularRateProductInfo(String name, Integer typeId, Integer productClas, Integer stage, BigDecimal interestRate, Integer funds, Integer unit, Integer cylcleDays, Integer status, String createdUser, Integer createWay, BigDecimal activityRate) {
        WmProductInfo ragularProductInfo = new WmProductInfo();
        ragularProductInfo.setName(name);
        ragularProductInfo.setType(typeId);
        ragularProductInfo.setProductClas(productClas);
        ragularProductInfo.setStage(stage);
        ragularProductInfo.setInterestRate(interestRate);
        ragularProductInfo.setFunds(funds);
        ragularProductInfo.setCopies(funds);
        ragularProductInfo.setSurplus(funds);
        ragularProductInfo.setResidue(funds);
        ragularProductInfo.setUnit(unit);
        ragularProductInfo.setCylcleDays(cylcleDays);
        ragularProductInfo.setStatus(status);
        ragularProductInfo.setCreatedUser(createdUser);
        ragularProductInfo.setCreated(DateUtil.getSystemTimeSeconds());
        ragularProductInfo.setCreateWay(createWay);
        ragularProductInfo.setActivityRate(activityRate);
        return productInfoRepository.saveWmProductInfo(ragularProductInfo);
    }

    /**
     * 保存份数
     *
     * @param copies 份数
     */
    public void saveWmNewStandard(Integer copies) {
        WmNewStandard newStandard = productInfoRepository.findWmNewStandard();
        if (newStandard != null) {
            newStandard.setStatus(1);
            productInfoRepository.updateWmNewStandard(newStandard);
        }

        WmNewStandard standard = new WmNewStandard();
        standard.setFunds(copies);
        standard.setSurplus(copies);
        standard.setStatus(0);
        standard.setCreated(DateUtil.getSystemTimeSeconds());
        productInfoRepository.saveWmNewStandard(standard);

    }


    /**
     * 保存产品销售统计数据
     *
     * @param productId     产品ID
     * @param days          天数
     * @param type          类型
     * @param releaseAmount 发布金额
     */
    public void saveWmProductSalesRecord(Long productId, Integer days, Integer type, Integer releaseAmount) {
        //判断发布金额
        if (releaseAmount != null && releaseAmount > 0) {
            //根据ID查询产品销售记录
            WmProductSalesRecord productSalesRecord = productInfoRepository.findWmProductSalesRecord(productId);
            if (productSalesRecord == null) {
                productSalesRecord = new WmProductSalesRecord();
                productSalesRecord.setProductId(productId);
                productSalesRecord.setDays(days);
                productSalesRecord.setType(type);
                productSalesRecord.setReleaseTime(DateUtil.getSystemTimeSeconds());
                productSalesRecord.setReleaseAmount(releaseAmount);
                productSalesRecord.setSalesAmount(releaseAmount);
                productSalesRecord.setStatus(1);
                productSalesRecord.setCreated(DateUtil.getSystemTimeSeconds());
                productInfoRepository.saveWmProductSalesRecord(productSalesRecord);
            }
        }
    }


    /**
     * 产品自动清零
     *
     * @throws Exception
     */
    @Override
    public void autoClearProductInfo() throws Exception {
//
//        //查询活期宝产品
//        WmProductInfo currentProductInfo = productInfoRepository.findWmProductInfo();
//        if (currentProductInfo != null) {
//
//            //剩余份数 > 0
//            if (currentProductInfo.getSurplus() > 0) {
//                //更新产品销售统计  产品ID 和 产品销售份数
//                updateWmProductSalesRecord(currentProductInfo.getId(), currentProductInfo.getFunds() - currentProductInfo.getSurplus());
//
//                //清除产品剩余份数
//                currentProductInfo.setSurplus(0);
//                productInfoRepository.updateWmProductInfo(currentProductInfo);
//            }
//
//
//        }
//
//
//        //查询定存产品
//        List<WmProductInfo> productInfoLst = productInfoRepository.findRagular();
//        if (productInfoLst != null && !productInfoLst.isEmpty()) {
//            //遍历定存产品
//            for (WmProductInfo ragularProductInfo : productInfoLst) {
//
//                //剩余份数 > 0
//                if (ragularProductInfo.getSurplus() > 0) {
//                    //更新产品销售统计  产品ID 和 产品销售份数
//                    updateWmProductSalesRecord(ragularProductInfo.getId(), ragularProductInfo.getFunds() - ragularProductInfo.getSurplus());
//
//                    //清除产品剩余份数
//                    ragularProductInfo.setSurplus(0);
//                    productInfoRepository.updateWmProductInfo(ragularProductInfo);
//                }
//
//            }
//        }


    }


//    /**
//     * 更新产品销售统计数据
//     *
//     * @param productId   产品ID
//     * @param salesAmount 销售金额
//     */
//    public void updateWmProductSalesRecord(Long productId, Integer salesAmount) {
//        WmProductSalesRecord productSalesRecord = productInfoRepository.findWmProductSalesRecord(productId);
//        if (productSalesRecord != null) {
//            productSalesRecord.setSalesAmount(salesAmount);
//            productSalesRecord.setSalesTime(DateUtil.getSystemTimeSeconds());
//            productInfoRepository.updateWmProductSalesRecord(productSalesRecord);
//        }
//    }


    /**
     * 累加和清空产品往期剩余份数
     *
     * @param productInfo 产品信息
     * @return
     */
    public int sumProductSurplus(WmProductInfo productInfo) {
        //产品往期剩余份数
        int surplus = 0;
        //往期剩余产品集合
        List<WmProductInfo> historyProductInfoLst = productInfoRepository.findProductInfoAllSurplus(productInfo.getType().longValue(), productInfo.getCylcleDays(), start_time);
        if (historyProductInfoLst != null && !historyProductInfoLst.isEmpty()) {
            //遍历往期剩余产品集合
            for (WmProductInfo historyProductInfo : historyProductInfoLst) {
                if (historyProductInfo != null) {
                    //锁表查询往期剩余份数
                    historyProductInfo = productInfoRepository.findWmProductInfoById(historyProductInfo.getId());
                    //累加剩余份数
                    surplus += historyProductInfo.getSurplus() == null? 0: historyProductInfo.getSurplus();
                    historyProductInfo.setSurplus(0);
                    productInfoRepository.updateWmProductInfo(historyProductInfo);
                }
            }
        }
        return surplus;
    }

    public WmProductInfoRepository getProductInfoRepository() {
        return productInfoRepository;
    }

    public void setProductInfoRepository(WmProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }
}
