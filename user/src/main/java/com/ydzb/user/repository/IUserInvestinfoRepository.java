package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserInvestinfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户投资记录
 * @author sy
 */
public interface IUserInvestinfoRepository extends IBaseRepository<UserInvestinfo, Long> {

    /**
     * 根据用户查询该用户的投资信息
     * @param userId
     * @return
     */
    @Query(" FROM UserInvestinfo WHERE user.id = :userId ")
    public UserInvestinfo queryByUser(@Param("userId")Long userId);
}
