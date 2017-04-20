package com.ydzb.core.service;

import com.ydzb.core.entity.search.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface IBaseService <M , ID extends Serializable> {

    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
	public M save(M m);
	
	  /**
     * 更新单个实体
     *
     * @param m 实体
     * @return 返回更新的实体
     */
    public M update(M m) ;
    
    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    public void delete(ID id);
    
    
    /**
     * 删除实体
     *
     * @param m 实体
     */
    public void delete(M m) ;
    
    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    public void delete(ID[] ids);
    
    
    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public M findOne(ID id);
    
    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(ID id) ;
    
    
    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() ;

    /**
     * 查询所有实体
     *
     * @return
     */
    public List<M> findAll() ;
    
    
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
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public Page<M> findAll(Searchable searchable);
    
    /**
     * 按条件不分页不排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithNoPageNoSort(Searchable searchable) ;
    
    /**
     * 按条件排序查询实体(不分页)
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithSort(Searchable searchable) ;
    
    /**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
    public Long count(Searchable searchable) ;
    
}
