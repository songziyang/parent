package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.ViSilverExchange;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2015/12/8.
 */
public interface IViSilverExchangeRepository extends IBaseRepository<ViSilverExchange, Long> {

}
