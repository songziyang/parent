package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.packet.entity.RedPacketLog;
import com.ydzb.packet.service.IRedPacketLogService;
import org.springframework.stereotype.Service;

/**
 * 红包操作日志service实现类
 * @author sy
 */
@Service
public class RedPacketLogServiceImpl extends BaseServiceImpl<RedPacketLog, Long> implements IRedPacketLogService {
}
