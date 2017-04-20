package com.ydzb.product.service.impl;


import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.CurrentHistory;
import com.ydzb.product.entity.ProductInfo;
import com.ydzb.product.entity.ProductSalesRecord;
import com.ydzb.product.entity.ProductType;
import com.ydzb.product.repository.*;
import com.ydzb.product.service.IProductInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 产品信息service实现类
 *
 * @author sy
 */
@Service
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo, Long> implements IProductInfoService {

    private static final Long DAYLOAN = 1L;
    private static final Long PRIVILEGE = 9L;
    @Autowired
    private IProductInfoRepository productInfoRepository;
    @Autowired
    private IProductTypeRepository productTypeRepository;
    @Autowired
    private ICurrentHistoryRepository historyRepository;
    @Autowired
    private ProductInfoRepository proeuctRepository;
    @Autowired
    private ProductSalesRecordRepository productSalesRecordRepository;

    @Value("${product.starttime}")
    private Long start_time;

    /**
     * 清空活期宝剩余份数
     *
     * @param productId
     * @return
     */
    @Override
    public String emptyCurrent(Long productId) {
        if (productId != null) {
            ProductInfo productInfo = productInfoRepository.queryByIdWithPessimisticWriteLock(productId);
            if (productInfo != null) {
                if (productInfo.getType().getId() != 1) {
                    return "清空失败,该产品不是活期宝";
                }

                //更新产品销售统计数据
                updateProductSalesRecord(productId, (int) (productInfo.getFunds() - productInfo.getSurplus()));

                ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                productInfo.setSurplus(0L);
                productInfo.setUpdatedUser(user == null ? null : user.getUsername());
                productInfoRepository.save(productInfo);

                return "清空成功";
            }
        }
        return "清空失败,不存在该产品";
    }

    /**
     * 保存单个产品信息
     *
     * @param productInfo
     * @return
     */
    @Override
    public String saveOne(ProductInfo productInfo) {
        if (productInfo == null) {
            return null;
        }
        Long infoId = productInfo.getId();
        String infoName = productInfo.getName();
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        boolean isCreate = infoId == null ? true : false;
        //如果是新建
        if (isCreate) {
            productInfo.setCreated(DateUtil.getSystemTimeSeconds());
            productInfo.setCopies(productInfo.getFunds());
            productInfo.setCreatedUser(currentUser.getUsername());
            productInfo.setSurplus(productInfo.getFunds());
            productInfo.setResidue(productInfo.getFunds());
            ProductType productType = productTypeRepository.findOne(productInfo.getType().getId());
            productInfo.setType(productType);
            if (productInfo.getType().getId().equals(DAYLOAN) || productInfo.getType().getId().equals(PRIVILEGE)) {
                productInfo.setCylcleDays(1);
            }


            disablePastProductInfo(productInfo);
            saveCurrentHistory(productInfo);
            productInfo.setStage(getCurrentStage(productInfo));
            productInfoRepository.save(productInfo);

            //新建产品销售统计金额
            if (productInfo.getType().getId() == 1 || productInfo.getType().getId() == 2) {
                //新建产品销售统计金额
                if (productInfo.getFunds().intValue() > 0) {
                    //保存产品销售记录
                    saveProductSalesRecord(productInfo.getId(), productInfo.getCylcleDays(), productInfo.getType().getId().intValue(), productInfo.getFunds().intValue());
                    //往期产品剩余份数
                    Integer surplus = productInfoRepository.findProductInfoAllSurplus(productInfo.getType().getId(), productInfo.getCylcleDays(), start_time);
                    if (surplus == null) surplus = 0;
                    //当前期数产品份数 + 往期产品剩余份数
                    productInfo.setCopies(productInfo.getCopies() + surplus);
                    productInfo.setSurplus(productInfo.getSurplus() + surplus);
                    productInfo.setResidue(productInfo.getResidue() + surplus);
                    productInfo.setFunds(productInfo.getFunds() + surplus);
                    productInfoRepository.save(productInfo);
                    //清空往期产品剩余份数
                    productInfoRepository.updateProductInfoAllSurplus(productInfo.getType().getId(), productInfo.getCylcleDays(), start_time);
                }
            }

        }
        //复制产品信息
        else {
            ProductInfo sourceInfo = productInfoRepository.findOne(infoId);
            productInfo.setId(null);
            productInfo.setName(sourceInfo.getName());
            productInfo.setProductClas(sourceInfo.getProductClas());
            productInfo.setCylcleDays(sourceInfo.getCylcleDays());
            productInfo.setCreated(DateUtil.getSystemTimeSeconds());
            productInfo.setCopies(productInfo.getFunds());
            productInfo.setCreatedUser(currentUser.getUsername());
            productInfo.setSurplus(productInfo.getFunds());
            productInfo.setResidue(productInfo.getFunds());
            productInfo.setActivityRate(productInfo.getActivityRate());
            ProductType productType = productTypeRepository.findOne(sourceInfo.getType().getId());
            productInfo.setType(productType);
            if (productInfo.getType().getId().equals(DAYLOAN)) {
                productInfo.setCylcleDays(1);
            }
            disablePastProductInfo(productInfo);
            saveCurrentHistory(productInfo);
            productInfo.setStage(getCurrentStage(productInfo));
            productInfoRepository.save(productInfo);

            //新建产品销售统计金额
            if (productInfo.getFunds().intValue() > 0) {
                //保存产品销售记录
                saveProductSalesRecord(productInfo.getId(), productInfo.getCylcleDays(), productInfo.getType().getId().intValue(), productInfo.getFunds().intValue());
                //往期产品剩余份数
                Integer surplus = productInfoRepository.findProductInfoAllSurplus(productInfo.getType().getId(), productInfo.getCylcleDays(), start_time);
                if (surplus == null) surplus = 0;
                //当前期数产品份数 + 往期产品剩余份数
                productInfo.setCopies(productInfo.getCopies() + surplus);
                productInfo.setSurplus(productInfo.getSurplus() + surplus);
                productInfo.setResidue(productInfo.getResidue() + surplus);
                productInfo.setFunds(productInfo.getFunds() + surplus);
                productInfoRepository.save(productInfo);
                //清空往期产品剩余份数
                productInfoRepository.updateProductInfoAllSurplus(productInfo.getType().getId(), productInfo.getCylcleDays(), start_time);
            }

        }

        return null;
    }

    /**
     * 查询产品名称
     *
     * @param productTypeId
     * @return
     */
    @Override
    public String queryProductName(Long productTypeId) {
        ProductType type = productTypeRepository.findOne(productTypeId);
        //如果是活期宝
        if (ProductType.CURRENT == productTypeId) {
            String todayDate = DateUtil.getCurrentDate();
            Long today = DateUtil.getSystemTimeDay(todayDate);
            Long tomorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(todayDate));
            //获得期数
            Integer stage = productInfoRepository.getProductCount(productTypeId, today, tomorrow);
            //如果不存在则设置当前期数为1，如果存在则期数+1
            stage = stage == null ? 1 : stage + 1;
            //格式：活期宝20150909-1期
            return type.getType() + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + stage + "期";
        }
        //如果是定存宝或者麦多宝
      /*  else if (ProductType.REGULAR == productTypeId || ProductType.MAIDUOBAO_FIRST_STAGE == productTypeId
                || ProductType.MAIDUOBAO_SECOND_STAGE == productTypeId) {
            return type.getType();
        } else if (ProductType.YJM == productTypeId) {
            //如果是涌金门
            return type.getType();
        } else if (ProductType.JMJ == productTypeId) {
            //如果是金贸街
            return type.getType();
        } else if (ProductType.JMJTG == productTypeId) {
            return type.getType();
        }
        return "";*/
        else {
            return type == null ? "" : type.getType();
        }
    }

    /**
     * 获得本期期数
     *
     * @param productInfo
     * @return
     */
    private int getCurrentStage(ProductInfo productInfo) {
        Integer maxStage = productInfoRepository.queryMaxStage(productInfo.getType().getId(),
                productInfo.getProductClas(), productInfo.getCylcleDays());
        //如果不存在最大的期数，证明数据库没有数据，本次期数为起始期数1
        if (maxStage == null) {
            return ProductInfo.INIT_STAGE;
        }
        //如果存在最大期数，则加一
        return ++maxStage;
    }

    /**
     * 禁用往期产品
     *
     * @param productInfo
     */
    private void disablePastProductInfo(ProductInfo productInfo) {
        productInfoRepository.updateStatus(ProductInfo.STATUS_NOTUSE, ProductInfo.STATUS_INUSE, productInfo.getType().getId(),
                productInfo.getProductClas(), productInfo.getCylcleDays());
    }

    /**
     * 保存活期宝历史记录
     *
     * @param productInfo
     */
    private void saveCurrentHistory(ProductInfo productInfo) {
        ProductInfo productHistory = proeuctRepository.queryProductHavingMaxStage(productInfo.getType().getId(),
                productInfo.getProductClas(), productInfo.getCylcleDays());
        if (productInfo.getType().getId() == ProductType.CURRENT && productHistory != null) {
            CurrentHistory history = new CurrentHistory();
            //投资金额 = 发布金额 - 剩余金额
            history.setCopies(productHistory.getFunds() - productHistory.getSurplus());
            history.setApr(productHistory.getInterestRate());
            history.setProductId(productHistory.getId());
            history.setProductName(productHistory.getName());
            history.setPubDate(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
            historyRepository.save(history);
        }
    }


    /**
     * 更新产品销售统计数据
     *
     * @param productId   产品ID
     * @param salesAmount 销售金额
     */
    public void updateProductSalesRecord(Long productId, Integer salesAmount) {
        ProductSalesRecord productSalesRecord = productSalesRecordRepository.findWmProductSalesRecord(productId);
        if (productSalesRecord != null) {
            productSalesRecord.setSalesAmount(salesAmount);
            productSalesRecord.setSalesTime(DateUtil.getSystemTimeSeconds());
            productSalesRecord.setStatus(2);
            productSalesRecordRepository.updateWmProductSalesRecord(productSalesRecord);
        }
    }


    /**
     * 保存产品销售统计数据
     *
     * @param productId     产品ID
     * @param days          天数
     * @param type          类型
     * @param releaseAmount 发布金额
     */
    public void saveProductSalesRecord(Long productId, Integer days, Integer type, Integer releaseAmount) {
        //根据ID查询产品销售记录
        ProductSalesRecord productSalesRecord = productSalesRecordRepository.findWmProductSalesRecord(productId);
        if (productSalesRecord == null) {
            productSalesRecord = new ProductSalesRecord();
            productSalesRecord.setProductInfo(productInfoRepository.findOne(productId));
            productSalesRecord.setDays(days);
            productSalesRecord.setType(type);
            productSalesRecord.setReleaseTime(DateUtil.getSystemTimeSeconds());
            productSalesRecord.setReleaseAmount(releaseAmount);
            productSalesRecord.setSalesAmount(releaseAmount);
            productSalesRecord.setStatus(1);
            productSalesRecord.setCreated(DateUtil.getSystemTimeSeconds());
            productSalesRecordRepository.saveWmProductSalesRecord(productSalesRecord);
        }
    }


    public IProductInfoRepository getProductInfoRepository() {
        return productInfoRepository;
    }

    public void setProductInfoRepository(IProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    public IProductTypeRepository getProductTypeRepository() {
        return productTypeRepository;
    }

    public void setProductTypeRepository(IProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ICurrentHistoryRepository getHistoryRepository() {
        return historyRepository;
    }

    public void setHistoryRepository(ICurrentHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public ProductInfoRepository getProeuctRepository() {
        return proeuctRepository;
    }

    public void setProeuctRepository(ProductInfoRepository proeuctRepository) {
        this.proeuctRepository = proeuctRepository;
    }

    public ProductSalesRecordRepository getProductSalesRecordRepository() {
        return productSalesRecordRepository;
    }

    public void setProductSalesRecordRepository(ProductSalesRecordRepository productSalesRecordRepository) {
        this.productSalesRecordRepository = productSalesRecordRepository;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }
}