package com.ydzb.message.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.message.repository.IMassDealRepository;
import com.ydzb.message.repository.IMassRepository;
import com.ydzb.message.service.IMassService;
import com.ydzb.sms.entity.Mass;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 群发模板服务实现类
 */
@Service
public class MassServiceImpl extends BaseServiceImpl<Mass, Long> implements IMassService {

	@Autowired
	private IMassRepository iMassRepository;

	@Autowired
	private IMassDealRepository iMassDealRepository;
	
	/**
	 * 保存模板
	 * @param mass 模板实体
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveMass(Mass mass) throws Exception {
		if(mass != null) {
			Subject currentUser = SecurityUtils.getSubject();
			ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
			if (mass.getId() == null) {
				Mass targetMass = iMassRepository.findMassByName(mass.getName());
				if (targetMass != null) {
					return "模板已存在！";
				}
				initData(mass, shiroUser.getId());
			} else {
				mass = updateData(mass, shiroUser.getId());
			}

			iMassRepository.save(mass);
		}
		return null;
	}

	/**
	 * 保存模板发送短信状态
	 * @param mass 模板实体
	 * @param result 发送短信状态 
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveMass(Mass mass, String result) throws Exception {
		byte state = Mass.STATE_SUCCESS;
		if (!"ok".equals(result)) {
			state = Mass.STATE_FAILURE;
		}
		mass.setState(state);
		Byte num = mass.getNum() == null? 0: mass.getNum();
		mass.setNum((byte)(num + 1));
		iMassRepository.save(mass);
	}

	/**
	 * 初始化模板数据
	 * @param mass
	 * @param curUserId
	 */
	private void initData(Mass mass, Long curUserId) {
		mass.setCreated(DateUtil.getSystemTimeSeconds(DateUtil.curDateTime()));
		mass.setCreateUser(curUserId);
		mass.setNum((byte)0);
	}
	
	/**
	 * 更新模板信息
	 * @param mass
	 * @param curUserId
	 */
	private Mass updateData(Mass mass, Long curUserId) {
		Mass targetMass = iMassRepository.findOne(mass.getId());
		targetMass.setName(mass.getName());
		targetMass.setContent(mass.getContent());
		targetMass.setUpdateUser(curUserId);
		return targetMass;
	}
}