package com.ydzb.admin.service;


import com.ydzb.admin.entity.Admin;
import com.ydzb.core.service.IBaseService;

public interface IAdminService extends IBaseService<Admin, Long> {
	
	public Admin findAdminByUsername(String username);
	
	public String saveAdmin(Admin admin) throws Exception;
	
	public void deleteAdmin(Long id) throws Exception;
	
	public void deleteAdmin(Long[] ids) throws Exception;

	public Admin findByOtpId(Long otpId);

}
