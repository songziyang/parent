/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ydzb.core.repository;

import java.io.Serializable;
import java.util.List;

import com.ydzb.core.entity.search.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * 抽象DAO层基类 提供一些简便方法
 * 泛型 ： M 表示实体类型；ID表示主键类型
 */
@NoRepositoryBean
public interface IBaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID> {

    /**
     * 根据主键删除
     *
     * @param ids
     */
    public void delete(ID[] ids);
    
    /**
     * 查询所有实体
     *
     * @return
     */
    public List<M> findAll();

    /**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
    public List<M> findAll(Sort sort);


    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
    public Page<M> findAll(Pageable pageable);

    /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序
     *
     * @param searchable
     * @return
     **/
    public Page<M> findAll(Searchable searchable);


    /**
     * 根据条件统计所有记录数
     *
     * @param searchable
     * @return
     */
    public long count(Searchable searchable);


}
