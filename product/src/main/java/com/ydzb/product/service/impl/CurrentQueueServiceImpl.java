package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.CurrentQueue;
import com.ydzb.product.repository.ICurrentQueueRepository;
import com.ydzb.product.service.ICurrentQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝排队service实现类
 */
@Service
public class CurrentQueueServiceImpl extends BaseServiceImpl<CurrentQueue, Long> implements ICurrentQueueService {

    @Autowired
    private ICurrentQueueRepository currentQueueRepository;

    /**
     * 查询预投总份数
     * @return
     */
    @Override
    public BigDecimal querySumCopies() {
        return currentQueueRepository.querySumCopies();
    }

    @Override
    public BigDecimal querySumCopies(Long startTime, Long endTime) {
        return  currentQueueRepository.querySumCopies(startTime, endTime);
    }

    @Override
    public BigDecimal queryTodaySumCopies() {
        Long today = DateUtil.getSystemTimeDay(DateUtil.curDate());
        Long tomorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.curDate()));
        return querySumCopies(today, tomorrow);
    }
}
