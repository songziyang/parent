package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.repository.WmUserUsersRepository;
import com.ydzb.account.repository.WmVipGradeRepository;
import com.ydzb.account.service.IWmRagularIncomeCulculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/11.
 */
@Service
public class WmRagularIncomeCulculateServiceImpl implements IWmRagularIncomeCulculateService {

    @Autowired
    private WmUserUsersRepository userUsersRepository;
    @Autowired
    private WmVipGradeRepository vipGradeRepository;
    @Autowired
    private WmProductInfoRepository productInfoRepository;

    /**
     * 计算复投收益
     * @param userId
     * @param productInfo
     * @param fund
     * @return
     */
    @Override
    public WmIncomeEntity culculateRecastIncome(Long userId, WmProductInfo productInfo, BigDecimal fund) throws Exception {

        //VIP加息收益查询用户
        WmUser user = userUsersRepository.findByUserId(userId);
        //VIP利率
        BigDecimal vipApr = BigDecimal.ZERO;
        //判断用户
        if (user != null) {
            //用户等级
            WmVipGrade vipGrade = vipGradeRepository.queryById(user.getUserLeve(), LockModeType.NONE);
            if (vipGrade != null) {
                vipApr = vipGrade.getDepositApr();
            }
        }

        //收益
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal vipIncome = BigDecimal.ZERO;
        //活动利率
        if (productInfo.getActivityRate() == null) productInfo.setActivityRate(BigDecimal.ZERO);
        //产品利率 =  发布利率 - 活动利率
        BigDecimal interestRate = productInfo.getInterestRate().subtract(productInfo.getActivityRate());
        //当期产品
        WmProductInfo currentProductInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_REGULAR, WmProductInfo.STATUS_USING, WmProductInfo.PRODUCTCLAS_YINDUO, productInfo.getCylcleDays());
        if (currentProductInfo != null) {
            if (currentProductInfo.getActivityRate() == null) currentProductInfo.setActivityRate(BigDecimal.ZERO);
            //原始产品利率+当前产品活动利率
            interestRate = interestRate.add(currentProductInfo.getActivityRate());
        }

        if (productInfo.getCylcleDays() == 365) {
            vipIncome = fund.multiply(vipApr).divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_DOWN);
            income = fund.multiply(interestRate.add(vipApr)).divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_DOWN);
        } else {
            vipIncome = fund.multiply(vipApr).multiply(new BigDecimal(productInfo.getCylcleDays())).divide(new BigDecimal(36000), 6, BigDecimal.ROUND_HALF_DOWN);
            income = fund.multiply(interestRate.add(vipApr)).multiply(new BigDecimal(productInfo.getCylcleDays())).divide(new BigDecimal(36000), 6, BigDecimal.ROUND_HALF_DOWN);
        }

        WmIncomeEntity incomeEntity = new WmIncomeEntity();
        incomeEntity.setVipIncome(vipIncome);
        incomeEntity.setIncome(income);

        WmAprEntity aprEntity = new WmAprEntity();
        aprEntity.setApr(interestRate);
        aprEntity.setVipApr(vipApr);
        return incomeEntity;
    }
}
