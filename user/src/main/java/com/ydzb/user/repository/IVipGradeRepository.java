package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.VipGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * vip等级repository
 * @author sy
 */
public interface IVipGradeRepository extends IBaseRepository<VipGrade, Long> {

    /**
     * 查询过期天数
     * @param id
     * @return
     */
    @Query(" SELECT expireDays FROM VipGrade WHERE id = :id")
    Integer queryExpireDays(@Param("id") Long id);

    /**
     * 根据用户查询所属vip等级
     * @param userId
     * @return
     */
    @Query(" SELECT userLeve FROM User AS user WHERE user.id = :userId ")
    VipGrade queryByUser(@Param("userId") Long userId);
}