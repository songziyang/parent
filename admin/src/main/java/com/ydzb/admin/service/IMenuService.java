package com.ydzb.admin.service;

import com.ydzb.admin.entity.Menu;
import com.ydzb.core.service.IBaseService;

import java.util.List;



public interface IMenuService extends IBaseService<Menu, Long> {

	public List<Menu> findParentMenus();

	public String deleteMenu(Long id) throws Exception;

	public String  deleteMenu(Long[] ids) throws Exception;
	
	public Menu findMenuByPurflag(String purflag);
	
	public List<Menu> listRoleMenu();

}
