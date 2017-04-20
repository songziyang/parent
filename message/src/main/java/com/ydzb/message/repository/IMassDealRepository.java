package com.ydzb.message.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.sms.entity.MassDeal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 群发记录持久化层
 */
@Repository
public interface IMassDealRepository extends IBaseRepository<MassDeal, Long> {

    /**
     * 根据群发模板id，查询群发记录手机号码
     *
     * @param massId 群发模板id
     * @return
     */
    @Query(" SELECT deal.mobile FROM MassDeal deal WHERE deal.massId = :massId ")
    public List<String> findMobile(@Param("massId") Long massId);

    /**
     * 根据群发模板id以及手机号查询群发记录
     *
     * @param mobile
     * @return
     */
    @Query(" FROM MassDeal deal WHERE deal.massId = :massId AND deal.mobile = :mobile ")
    public MassDeal findByMobile(@Param("massId") Long massId, @Param("mobile") String mobile);

    /**
     * 根据群发模版id删除详情
     * @param massId
     */
    @Modifying
    @Query(" DELETE FROM MassDeal WHERE massId = :massId")
    public void deleteByMassId(@Param("massId") Long massId);
}