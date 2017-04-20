package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.OtherPlatformUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 其他平台用户repository
 */
public interface IOtherPlatformUserRepository extends IBaseRepository<OtherPlatformUser, Long> {

    @Query(" FROM OtherPlatformUser WHERE mobile = :mobile ")
    List<OtherPlatformUser> queryByMobile(@Param("mobile") String mobile);
}