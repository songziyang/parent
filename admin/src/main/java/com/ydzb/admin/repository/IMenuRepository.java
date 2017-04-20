package com.ydzb.admin.repository;

import java.util.List;

import com.ydzb.admin.entity.Menu;
import com.ydzb.core.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IMenuRepository extends IBaseRepository<Menu, Long> {

	@Query("from Menu as menu where menu.parentMenu.id is null ")
	public List<Menu> findParentMenus();

	@Query("from Menu as menu where menu.parentMenu.id =:menuId ")
	public List<Menu> findParentMenusByMenuId(@Param("menuId") Long menuId);
	
	public Menu findMenuByPurflag(String purflag);
}
