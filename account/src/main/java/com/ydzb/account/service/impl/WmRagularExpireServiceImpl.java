package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 定存宝到期处理service实现
 */
@Service
public class WmRagularExpireServiceImpl implements IWmRagularExpireService {

    @Autowired
    private WmProductInfoRepository productInfoRepository;
    @Autowired
    private IWmRagularIncomeCulculateService ragularIncomeCulculateService;
    @Autowired
    private IWmRagularUserAccountService ragularUserAccountService;
    @Autowired
    private IWmUserIntegralService userIntegralService;
    @Autowired
    private IWmRagularRefundService ragularRefundService;
    @Autowired
    private IWmRagularBuyLogService ragularBuyLogService;
    @Autowired
    private IWmRagularTradeRecordService ragularTradeRecordService;
    @Autowired
    private IWmUserIncomeService userIncomeService;

    /**
     * 保存定存复投交易记录
     *
     * @param userId       用户ID
     * @param fund         金额
     * @param expireNum    复投次数
     * @param expireMode   复投模式
     * @param incomeMode   利息复投模式
     * @return 复投生成的新ragularUserAccount
     */
    @Override
    public WmRagularUserAccount doExpire(WmProductInfo productInfo, Long userId, BigDecimal fund, Integer expireNum, Integer expireMode, Integer incomeMode) throws Exception {

        if (productInfo != null) {
            //首次复投为空
            if (expireNum == null) expireNum = 0;
            //计算收益
            WmIncomeEntity incomeEntity = ragularIncomeCulculateService.culculateRecastIncome(userId, productInfo, fund);
            WmAprEntity aprEntity = incomeEntity.getAprEntity();
            BigDecimal income = incomeEntity.getIncome();
            BigDecimal vipIncome = incomeEntity.getVipIncome();
            Long expireTime = getExpireTime(productInfo);
            //创建定存账户
            WmProductInfo currentProductInfo = productInfoRepository.queryOne(WmProductInfo.TYPE_REGULAR, WmProductInfo.STATUS_USING, WmProductInfo.PRODUCTCLAS_YINDUO, productInfo.getCylcleDays());
            WmRagularUserAccount ragularUserAccount = ragularUserAccountService.createOne(userId, productInfo.getId(), fund.add(income),
                    DateUtil.getSystemTimeSeconds(), fund, BigDecimal.ZERO, income, fund.intValue(), BigDecimal.ZERO, aprEntity.getVipApr(),
                    aprEntity.getApr(), expireTime, expireMode, incomeMode, WmRagularUserAccount.BUYTYPE_EXPIRE, productInfo.getCylcleDays(),
                    WmRagularUserAccount.STATUS_NOTEXPIRE, income, expireNum + 1, currentProductInfo.getId());
            //创建还息记录
            ragularRefundService.createOne(userId, 1, 1, expireTime,
                    income, BigDecimal.ZERO, vipIncome, BigDecimal.ZERO, WmRagularRefund.STATE_NOTREFUND,
                    WmRagularRefund.EXPIRE_LAST, WmRagularRefund.ORG_RAGULAR);
            //定存购买日志
            WmRagularBuyLog buyLog = ragularBuyLogService.createOne(productInfo.getId(), ragularUserAccount.getId(), userId, fund, fund.intValue(),
                    aprEntity.getApr(), BigDecimal.ZERO, aprEntity.getVipApr(), DateUtil.getSystemTimeSeconds(), WmRagularBuyLog.DEVICE_SYSTEM);
            //保存定存宝交易记录
            ragularTradeRecordService.createOne(productInfo.getName() + "复投", ragularUserAccount.getDays(), ragularUserAccount.getBuyFund(),
                    DateUtil.getSystemTimeSeconds(), WmRagularTradeRecored.TYPE_RECAST, userId, WmRagularTradeRecored.FUNDSOURCE_USABLE, buyLog.getId());
            //复投获得积分
            userIntegralService.investObtainIntegralNew(userId, fund, ragularUserAccount.getId());
            //增加预期收益
            userIncomeService.increasePredictIncome(userId, income);
            return ragularUserAccount;
        }
        return null;
    }

    private static String addMonth(String strdate) {
        String dateresult = null; // 返回的日期字符串
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar gc = new GregorianCalendar();
            Date date = df.parse(strdate); // 将字符串格式化为日期型
            gc.setTime(date); // 得到gc格式的时间
            gc.add(2, 1); // 2表示月的加减，年代表1依次类推(周,天。。)
            // 把运算完的时间从新赋进对象
            gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
            // 在格式化回字符串时间
            dateresult = df.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateresult;
    }

    private Long getExpireTime(WmProductInfo productInfo) {
        Long expireTime;
        //系统明天日期
        Long tormorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.curDate()));
        if (productInfo.getCylcleDays() == 365) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String addMonth = sdf.format(DateUtil.getSystemTimeMillisecond(tormorrow));
            for (int i = 1; i <= 12; i++) {
                addMonth = addMonth(addMonth);
            }
            expireTime = DateUtil.getSystemTimeDay(addMonth);
        } else {
            expireTime = tormorrow + productInfo.getCylcleDays() * 24 * 3600;
        }
        return expireTime;
    }
}
