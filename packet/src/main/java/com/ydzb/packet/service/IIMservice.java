package com.ydzb.packet.service;

import com.ydzb.core.entity.search.Searchable;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * Created by CRF on 2017/3/10 0010.
 */
public interface IIMservice {

    /**
     * 获取即信异常信息统计结果
     * @return
     */
    List<Object> getIMErrorCount();

    /**
     * 根据条件获取详细异常信息
     * @param searchable
     * @param para
     * @return
     */
    Page<Object[]> getIMErrorDetailList(Searchable searchable, Integer para);
}
