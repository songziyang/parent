package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmDepositWeekSort;
import com.ydzb.account.repository.WmDepositWeekSortRepository;
import com.ydzb.account.service.IWmDepositWeekSortService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 结构化产品排行榜
 */
@Service
public class WmDepositWeekSortServiceImpl implements IWmDepositWeekSortService {

    @Autowired
    private WmDepositWeekSortRepository depositWeekSortRepository;

    /**
     * 查询结构化产品排行榜
     *
     * @throws Exception
     */
    @Override
    public void accountDepositWeekSort() throws Exception {

        //开始时间
        long endDate = DateUtil.getSystemTimeDay(DateUtil.curDate());

        //结束时间
        long startDate = DateUtil.getSystemTimeDay(DateUtil.subMonth(DateUtil.curDate()));

        System.out.println("=endDate=" + endDate + "=startDate=" + startDate);

        //排行榜数据
        List<Object[]> dataLst = depositWeekSortRepository.findDepositWeekSort(startDate, endDate);
        if (dataLst != null && !dataLst.isEmpty()) {
            for (int i = 0; i < dataLst.size(); i++) {
                int ranking = i + 1;
                BigDecimal totalFund = (BigDecimal) dataLst.get(i)[1];
                BigInteger userId = (BigInteger) dataLst.get(i)[0];
                saveWmDepositWeekSort(userId.longValue(), totalFund, ranking);
            }
        }

    }


    public void saveWmDepositWeekSort(Long userId, BigDecimal totalFund, Integer ranking) throws Exception {
        WmDepositWeekSort depositWeekSort = new WmDepositWeekSort();
        depositWeekSort.setUserId(userId);
        depositWeekSort.setTotalFund(totalFund);
        depositWeekSort.setRanking(ranking);
        depositWeekSort.setOptime(DateUtil.getSystemTimeDay(DateUtil.curDate()));
        depositWeekSortRepository.saveWmDepositWeekSort(depositWeekSort);
    }

    public WmDepositWeekSortRepository getDepositWeekSortRepository() {
        return depositWeekSortRepository;
    }

    public void setDepositWeekSortRepository(WmDepositWeekSortRepository depositWeekSortRepository) {
        this.depositWeekSortRepository = depositWeekSortRepository;
    }
}
