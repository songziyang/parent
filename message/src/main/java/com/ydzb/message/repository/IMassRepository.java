package com.ydzb.message.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.sms.entity.Mass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * 群发短信模板-持久化层
 */
@Repository
public interface IMassRepository extends IBaseRepository<Mass, Long> {

	/**
	 * 根据模板名称查询稳群发模板
	 * @param name 模板名称
	 * @return
	 */
	@Query(" FROM Mass mass WHERE mass.name = :name ")
	public Mass findMassByName(@Param("name") String name);
}
