package com.ydzb.admin.repository;

import com.ydzb.admin.entity.Role;
import com.ydzb.core.repository.IBaseRepository;

// 角色持久化
public interface IRoleRepository extends IBaseRepository<Role, Long> {

	public Role findRoleByName(String name);
	
	
}
