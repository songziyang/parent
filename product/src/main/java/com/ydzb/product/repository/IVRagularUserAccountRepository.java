package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.VRagularUserAccount;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * 用户定存宝记录repository接口
 */
public interface IVRagularUserAccountRepository extends IBaseRepository<VRagularUserAccount, Long> {

    /**
     * 查看三个月定存总额
     *
     * @return
     */
    @Query(" SELECT SUM(threeMonthFund) FROM VRagularUserAccount ")
    BigDecimal queryThreeMonthFund();

    /**
     * 查看六个月定存总额
     *
     * @return
     */
    @Query(" SELECT SUM(sixMonthFund) FROM VRagularUserAccount ")
    BigDecimal querySixMonthFund();

    /**
     * 查看十二个月定存总额
     *
     * @return
     */
    @Query(" SELECT SUM(twelveMonthFund) FROM VRagularUserAccount ")
    BigDecimal queryTwelveMonthFund();


    /**
     * 查询一个月定存总额
     *
     * @return
     */
    @Query(" SELECT SUM(oneMonthFund) FROM VRagularUserAccount ")
    BigDecimal queryOneMonthFund();
}
