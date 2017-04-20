package com.ydzb.admin.service.impl;

import java.util.List;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.entity.Role;
import com.ydzb.admin.repository.IAdminRepository;
import com.ydzb.admin.repository.IMenuRepository;
import com.ydzb.admin.repository.IPurviewRepository;
import com.ydzb.admin.repository.IRoleRepository;
import com.ydzb.admin.service.IRoleService;
import com.ydzb.core.exception.BaseException;
import com.ydzb.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//角色业务接口实现类
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements
		IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IAdminRepository adminRepository;

	@Autowired
	private IPurviewRepository purviewRepository;

	@Autowired
	private IMenuRepository menuRepository;

	@Override
	public String saveRole(Role role) throws Exception {
		if (role != null) {
			if (role.getId() != null) {
			} else {
				role.setCreated(System.currentTimeMillis());
				Role r = roleRepository.findRoleByName(role.getName());
				if (r != null) {
					return "角色名称存在！";
				}
			}
			roleRepository.save(role);
		}
		return null;
	}

	@Override
	public String deleteRole(Long id) throws Exception {
		List<Admin> adminLst = adminRepository.findAdminByRoleId(id);
		if (adminLst != null && adminLst.size() > 0) {
			return "请先删除角色下的用户！";
		}
		roleRepository.delete(id);
		return null;
	}

	@Override
	public String deleteRole(Long[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			List<Admin> adminLst = adminRepository.findAdminByRoleId(ids[i]);
			if (adminLst != null && adminLst.size() > 0) {
				return "请先删除角色下的用户！";
			}
		}
		roleRepository.delete(ids);
		return null;
	}

	@Override
	public void saveAuthorization(Role role, Long[] purviewids)
			throws BaseException {
		StringBuffer sb = new StringBuffer();
		if (purviewids != null && purviewids.length > 0) {
			for (int i = 0; i < purviewids.length; i++) {
				sb.append(purviewids[i]);
				if (i < purviewids.length - 1) {
					sb.append(",");
				}
			}
		}
		role.setPurviews(sb.toString());
		// List<Purview> purviewsLst = new ArrayList<Purview>();
		// for (int i = 0; i < purviewids.length; i++) {
		// purviewsLst.add(purviewRepository.findOne(purviewids[i]));
		// }
		// Set<Menu> menusSet = new HashSet<Menu>();
		// for (Purview purview : purviewsLst) {
		// if (menuRepository.findMenuByPurflag(purview.getFlag()) != null) {
		// menusSet.add(menuRepository.findMenuByPurflag(purview.getFlag()));
		// menusSet.add(menuRepository
		// .findMenuByPurflag(purview.getFlag()).getParentMenu());
		// }
		//
		// }
		// Gson gson = new Gson();
		// role.setMenutree(gson.toJson(new ArrayList<Menu>(menusSet)));
		roleRepository.save(role);
	}

	public IRoleRepository getRoleRepository() {
		return roleRepository;
	}

	public void setRoleRepository(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public IAdminRepository getAdminRepository() {
		return adminRepository;
	}

	public void setAdminRepository(IAdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public IPurviewRepository getPurviewRepository() {
		return purviewRepository;
	}

	public void setPurviewRepository(IPurviewRepository purviewRepository) {
		this.purviewRepository = purviewRepository;
	}

	public IMenuRepository getMenuRepository() {
		return menuRepository;
	}

	public void setMenuRepository(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

}
