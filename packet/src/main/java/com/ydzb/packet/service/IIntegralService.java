package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.Integral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by sy on 2016/6/6.
 */
public interface IIntegralService extends IBaseService<Integral, Long> {

    /**
     * 保存
     * @param integral
     * @return
     */
    public Integral saveOne(Integral integral);
    /**
     * 停用
     * @param id
     */
    public void disable(Long id);

    /**
     * 启用
     * @param id
     */
    public void enable(Long id);

    /**
     * 改变状态
     * @param id
     * @param status
     */
    public void updateStatus(Long id, Integer status);

    /**
     * 根据状态查询所有所有积分
     * @param status
     * @return
     */
    public List<Integral> findAll(Integer status);
}