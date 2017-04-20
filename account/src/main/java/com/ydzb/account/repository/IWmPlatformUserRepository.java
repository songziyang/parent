package com.ydzb.account.repository;


import com.ydzb.account.entity.WmPlatformUser;

import com.ydzb.core.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

/**
 * 平台用户repository
 */
public interface IWmPlatformUserRepository extends IBaseRepository<WmPlatformUser, Long> {

    /**
     * 查询最新的一条数据
     *
     * @return
     */
    @Query(nativeQuery = true, value = " SELECT id FROM wm_platform_user ORDER BY id DESC LIMIT 1 ")
    Long findLastPlatformUser();

    /**
     * 使用悲观锁根据主键id查询
     *
     * @param id
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" FROM WmPlatformUser WHERE id = :id ")
    WmPlatformUser findPlatformUserById(@Param("id") Long id);
}