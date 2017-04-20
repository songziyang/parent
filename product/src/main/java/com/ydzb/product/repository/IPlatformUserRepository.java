package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.PlatformUser;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

/**
 * 平台用户repository
 */
public interface IPlatformUserRepository extends IBaseRepository<PlatformUser, Long> {

    /**
     * 查询最新的一条数据
     * @return
     */
    @Query(nativeQuery = true, value = " SELECT * FROM wm_platform_user ORDER BY id DESC LIMIT 0, 1")
    PlatformUser queryLastOne();

    /**
     * 使用悲观锁根据主键id查询
     * @param id
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" FROM PlatformUser WHERE id = :id ")
    PlatformUser queryByIdWithPessimisticWriteLock(@Param("id") Long id);
}