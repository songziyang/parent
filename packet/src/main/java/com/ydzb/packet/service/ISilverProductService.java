package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.SilverProduct;

/**
 * 银币活动
 */
public interface ISilverProductService extends IBaseService<SilverProduct, Long> {

    public void saveSilver(SilverProduct silverProduct);

    public void deleteSilver(Long id) throws Exception;

    public void deleteSilver(Long[] ids) throws Exception;
}
