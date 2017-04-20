package com.ydzb.message.service.impl;



import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.message.repository.IInfoTemplateRepository;
import com.ydzb.message.service.IInfoTemplateService;
import com.ydzb.sms.entity.InfoTemplate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InfoTemplateServiceImpl extends BaseServiceImpl<InfoTemplate, Long>
		implements IInfoTemplateService {

	
	
	@Autowired
	private IInfoTemplateRepository infoTemplateRepository;
	
	
	
	@Override
	public String saveInfoTemplate(InfoTemplate infoTemplate) throws Exception {
		if (checkFlag(infoTemplate) > 0) {
			return "标识已存在！";
		}
		//获取session用户
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		if (null == infoTemplate.getId() || ("").equals(infoTemplate.getId())) {
			infoTemplate.setStatus((byte) 0);
			infoTemplate.setCreated(DateUtil.getSystemTimeSeconds());
			infoTemplate.setCreatedUser(shiroUser.getUsername());
		} else {
			infoTemplate.setUpdated(DateUtil.getSystemTimeSeconds());
			infoTemplate.setUpdatedUser(shiroUser.getUsername());
		}
		infoTemplateRepository.save(infoTemplate);
		return null;
	}
	
	//检查标识是否重复
	@SuppressWarnings("unused")
	private int checkFlag(final InfoTemplate st){
		int count=0;
		if(null == st.getId()){
			//添加时不过滤ID
			count = infoTemplateRepository.checkInfoTemplateByFlag(st.getFlag());
		}else{
			//编辑时需要过滤ID
			count = infoTemplateRepository.checkInfoTemplateByFlag(st.getId(), st.getFlag());
		}
		return count;
	}
	
	
	@Override
	public void deleteInfoTemplate(Long id) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		InfoTemplate it = infoTemplateRepository.findOne(id);
		if (null != it) {
			it.setStatus((byte) -1);
			it.setUpdated(DateUtil.getSystemTimeSeconds());
			it.setUpdatedUser(shiroUser.getUsername());
			infoTemplateRepository.save(it);
		}
		
	}

	@Override
	public void deleteInfoTemplate(Long[] ids) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				InfoTemplate it = infoTemplateRepository.findOne(ids[i]);
				if (null != it) {
					it.setStatus((byte) -1);
					it.setUpdated(DateUtil.getSystemTimeSeconds());
					it.setUpdatedUser(shiroUser.getUsername());
					infoTemplateRepository.save(it);
				}
			}
		}
		
	}

	public IInfoTemplateRepository getInfoTemplateRepository() {
		return infoTemplateRepository;
	}

	public void setInfoTemplateRepository(
			IInfoTemplateRepository infoTemplateRepository) {
		this.infoTemplateRepository = infoTemplateRepository;
	}


}
