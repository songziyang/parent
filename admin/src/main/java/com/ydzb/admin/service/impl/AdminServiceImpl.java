package com.ydzb.admin.service.impl;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.entity.OnetimePassword;
import com.ydzb.admin.repository.IAdminRepository;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.shiro.Digests;
import com.ydzb.core.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements
		IAdminService {

	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;

	@Autowired
	private IAdminRepository adminRepository;

	@Override
	public Admin findAdminByUsername(String username) {
		return adminRepository.findAdminByUsername(username);
	}

	@Override
	public String saveAdmin(Admin admin) throws Exception {
		if (admin != null) {
			if (admin.getId() != null) {
				OnetimePassword otp = admin.getOnetimePassword();
				if (otp.getId() == null) {
					admin.setOnetimePassword(null);
				}
			} else {
				Admin a = adminRepository.findAdminByUsername(admin.getUsername());
				if (a != null ) {
					return "用户名存在！";
				}
				admin.setCreated(System.currentTimeMillis());
				admin.setStatus(1);
				admin.setOnetimePassword(null);
			}
			// 设定安全的密码，生成随机的salt并经过1024 SHA hash
			if (StringUtils.isNotBlank(admin.getPassword())) {
				entryptPassword(admin);
			}
			adminRepository.save(admin);
		}
		return null;
	}

	@Override
	public void deleteAdmin(Long id) throws Exception {
		Admin admin = adminRepository.findOne(id);
		if (admin != null) {
			admin.setStatus(2);
			adminRepository.save(admin);
		}
	}

	@Override
	public void deleteAdmin(Long[] ids) throws Exception {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				Admin admin = adminRepository.findOne(ids[i]);
				if (admin != null) {
					admin.setStatus(2);
					adminRepository.save(admin);
				}
			}
		}
	}

	/**
	 * 根据数字令牌id查找后台用户
	 * @param otpId
	 * @return
	 */
	@Override
	public Admin findByOtpId(Long otpId) {
		return adminRepository.findByOtpId(otpId);
	}

	public void entryptPassword(Admin admin) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		admin.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(admin.getPassword().getBytes(),
				salt, HASH_INTERATIONS);
		admin.setPassword(Encodes.encodeHex(hashPassword));
	}

	public IAdminRepository getAdminRepository() {
		return adminRepository;
	}

	public void setAdminRepository(IAdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

}
