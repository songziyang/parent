package com.ydzb.admin.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.admin.entity.OnetimePassword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数字令牌repository
 */
public interface IOnetimePasswordRepository extends IBaseRepository<OnetimePassword, Long> {

    /**
     * 根据数字令牌号或者密钥查找数字令牌
     * @param number
     * @param authkey
     * @return
     */
    @Query(" FROM OnetimePassword WHERE otpNumber = :number OR authkey = :authkey ")
    public List<OnetimePassword> findByNumberOrAuthkey(@Param("number") Long number, @Param("authkey") String authkey);
}