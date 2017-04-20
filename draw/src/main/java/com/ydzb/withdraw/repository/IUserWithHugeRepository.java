package com.ydzb.withdraw.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.withdraw.entity.UserWithHuge;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public interface IUserWithHugeRepository extends IBaseRepository<UserWithHuge, Long> {
}