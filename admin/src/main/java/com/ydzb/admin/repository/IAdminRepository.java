package com.ydzb.admin.repository;

import java.util.List;

import com.ydzb.admin.entity.Admin;
import com.ydzb.core.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IAdminRepository extends IBaseRepository<Admin, Long> {

	@Query("from Admin admin where admin.username = :username and admin.status = 1 ") 
	public Admin findAdminByUsername(@Param("username") String username);
	
	
	@Query("from Admin admin where admin.role.id = :roleId and admin.status = 1 ") 
	public List<Admin> findAdminByRoleId(@Param("roleId") Long roleId);

	@Query(" FROM Admin WHERE onetimePassword.id = :otpId AND status = 1 ")
	public Admin findByOtpId(@Param("otpId") Long otpId);

}
