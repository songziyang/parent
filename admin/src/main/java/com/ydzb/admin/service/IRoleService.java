package com.ydzb.admin.service;


import com.ydzb.admin.entity.Role;
import com.ydzb.core.service.IBaseService;

// 角色业务接口
public interface IRoleService extends IBaseService<Role, Long> {
	
	public String saveRole(Role role)  throws Exception; 
	
	public String deleteRole(Long id) throws Exception;
	
	public String deleteRole(Long[] ids) throws Exception;
	
	public void saveAuthorization(Role role, Long[] purviewids) throws  Exception;
	
}
