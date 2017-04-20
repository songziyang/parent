package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserFundBlokedLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserFundBlokedLogRepository extends IBaseRepository<UserFundBlokedLog, Long> {



    /**
     * 查询冻结资金
     *
     * @param userId
     * @return
     */
    @Query(" SELECT  ufb  FROM  UserFundBlokedLog as ufb  where  ufb.linkType <> 3  and ufb.userId = :userId  GROUP BY  ufb.linkId , ufb.linkType  HAVING  COUNT(ufb.id) = 1 ")
    public List<UserFundBlokedLog> findUserFundBlokedLogByType(@Param("userId") Long userId);


    /**
     * 查询冻结体验金
     * @param userId
     * @return
     */
    @Query(" SELECT  ufb  FROM  UserFundBlokedLog as ufb  where  ufb.linkType = 3  and ufb.userId = :userId  GROUP BY  ufb.linkId , ufb.linkType  HAVING  COUNT(ufb.id) = 1 ")
    public List<UserFundBlokedLog> findExUserFundBlokedLogByType(@Param("userId") Long userId);

}


