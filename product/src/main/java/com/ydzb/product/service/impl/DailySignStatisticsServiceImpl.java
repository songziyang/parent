package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.DailySignStatistics;
import com.ydzb.product.service.IDailySignStatisticsService;
import org.springframework.stereotype.Service;

/**
 * 每日签到统计service实现
 */
@Service
public class DailySignStatisticsServiceImpl extends BaseServiceImpl<DailySignStatistics, Long> implements IDailySignStatisticsService {
}