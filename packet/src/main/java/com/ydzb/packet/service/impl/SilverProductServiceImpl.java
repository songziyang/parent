package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.SilverProduct;
import com.ydzb.packet.repository.ISilverProductRepository;
import com.ydzb.packet.service.ISilverProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 银币活动
 */
@Service
public class SilverProductServiceImpl extends BaseServiceImpl<SilverProduct, Long> implements ISilverProductService {

    @Autowired
    private ISilverProductRepository silverProductRepository;

    @Override
    public void saveSilver(SilverProduct silverProduct) {
        if (silverProduct != null) {
            if (silverProduct.getId() != null) {

            } else {
                //获取session用户
                silverProduct.setCreated(DateUtil.getSystemTimeSeconds());
                silverProduct.setStatus(0);
            }
            silverProductRepository.save(silverProduct);
        }

    }


    @Override
    public void deleteSilver(Long id) throws Exception {
        SilverProduct silverProduct = silverProductRepository.findOne(id);
        if (silverProduct != null) {
            silverProduct.setStatus(1);
            silverProductRepository.save(silverProduct);
        }
    }

    @Override
    public void deleteSilver(Long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                SilverProduct silverProduct = silverProductRepository.findOne(ids[i]);
                if (silverProduct != null) {
                    silverProduct.setStatus(1);
                    silverProductRepository.save(silverProduct);
                }
            }
        }
    }

    public ISilverProductRepository getSilverProductRepository() {
        return silverProductRepository;
    }

    public void setSilverProductRepository(ISilverProductRepository silverProductRepository) {
        this.silverProductRepository = silverProductRepository;
    }
}
