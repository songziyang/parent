package com.ydzb.admin.service.impl;


import com.ydzb.admin.entity.Menu;
import com.ydzb.admin.entity.Purview;
import com.ydzb.admin.entity.Role;
import com.ydzb.admin.repository.IMenuRepository;
import com.ydzb.admin.repository.IPurviewRepository;
import com.ydzb.admin.repository.IRoleRepository;
import com.ydzb.admin.service.IMenuService;
import com.ydzb.admin.shiro.Contains;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements
		IMenuService {

	@Autowired
	private IMenuRepository menuRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IPurviewRepository purviewRepository;


	@Override
	public List<Menu> findParentMenus() {
		return menuRepository.findParentMenus();
	}

	@Override
	public String deleteMenu(Long id) throws Exception {
		List<Menu> menus = menuRepository.findParentMenusByMenuId(id);
		if (menus != null && menus.size() > 0) {
			return "请先删除下级菜单！";
		}
		menuRepository.delete(id);
		return null;

	}

	@Override
	public String deleteMenu(Long[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			List<Menu> menus = menuRepository.findParentMenusByMenuId(ids[i]);
			if (menus != null && menus.size() > 0) {
				return "请先删除下级菜单！";
			}
		}
		menuRepository.delete(ids);
		return null;
	}

	@Override
	public Menu findMenuByPurflag(String purflag) {
		return menuRepository.findMenuByPurflag(purflag);
	}


	public IMenuRepository getMenuRepository() {
		return menuRepository;
	}

	public void setMenuRepository(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public List<Menu> listRoleMenu() {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		if (shiroUser != null) {
			if (Contains.USERNAME.equals(shiroUser.getUsername())) {
				return findAll();
			}
			if (shiroUser.getRoleId() != null) {
				Role role = roleRepository.findOne(shiroUser.getRoleId());
				if (role != null && role.getPurviews() != null) {
					String[] purviewIds = role.getPurviews().split(",");
					List<Purview> purviewsLst = new ArrayList<Purview>();
					List<Purview> lst = purviewRepository.findAll();
					for (int i = 0; i < purviewIds.length; i++) {
						for (Purview purview : lst) {
							if (purview.getId().compareTo(Long.parseLong(purviewIds[i])) == 0) {
								purviewsLst.add(purview);
							}
						}
					}

					Set<Menu> menusSet = new HashSet<Menu>();
					List<Menu> menuLst = findAll();
					for (Purview purview : purviewsLst) {
						for (Menu menu : menuLst) {
							if (purview.getFlag().equals(menu.getPurflag())) {
								menusSet.add(menu);
								menusSet.add(menu.getParentMenu());
							}
						}
					}
					List<Menu> sortMenuLst = new ArrayList<Menu>(menusSet);
					Collections.sort(sortMenuLst, new MenuComparator());
					return sortMenuLst;
				}
			}
		}
		return new ArrayList<Menu>();
	}

	class MenuComparator implements Comparator<Menu> {
		@Override
		public int compare(Menu o1, Menu o2) {
			return o1.getCreated().compareTo(o2.getCreated());
		}
	}

	public IRoleRepository getRoleRepository() {
		return roleRepository;
	}

	public void setRoleRepository(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public IPurviewRepository getPurviewRepository() {
		return purviewRepository;
	}

	public void setPurviewRepository(IPurviewRepository purviewRepository) {
		this.purviewRepository = purviewRepository;
	}




}


