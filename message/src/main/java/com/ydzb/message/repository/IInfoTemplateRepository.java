package com.ydzb.message.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.sms.entity.InfoTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IInfoTemplateRepository extends IBaseRepository<InfoTemplate, Long> {
	 
	@Query("select count(it.id) from InfoTemplate it where it.id != :id and it.flag = :flag and it.status = 0") 
	public int checkInfoTemplateByFlag(@Param("id") Long id, @Param("flag") String flag);
	
	@Query("select count(it.id) from InfoTemplate it where it.flag = :flag and it.status = 0") 
	public int checkInfoTemplateByFlag(@Param("flag") String flag);
	
	
}
