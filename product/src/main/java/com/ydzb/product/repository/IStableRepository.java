package com.ydzb.product.repository;

import java.math.BigDecimal;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.Stable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * 稳进宝持久层
 *
 */
@Repository
public interface IStableRepository extends IBaseRepository<Stable, Long> {

	/**
	 * 根据项目名称查询稳进宝
	 * @param name 项目名称
	 * @return
	 */
	@Query(" FROM Stable stable WHERE stable.name = :name ")
	public Stable findStableByName(@Param("name") String name);
	
	/**
	 * 根据用户ID 查询用户稳进宝投资总额
	 * 
	 * @param userId
	 * @return
	 */
	@Query("select sum(sd.copies) from StableDeal as sd  where sd.user.id = :userId and sd.state = 0 ")
	public BigDecimal findSumCopiesFromStableDealByUserId(@Param("userId") Long userId);

	
}
