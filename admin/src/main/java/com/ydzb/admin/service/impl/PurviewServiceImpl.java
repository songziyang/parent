package com.ydzb.admin.service.impl;

import com.ydzb.admin.entity.Purview;
import com.ydzb.admin.repository.IPurviewRepository;
import com.ydzb.admin.service.IPurviewService;
import com.ydzb.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PurviewServiceImpl extends BaseServiceImpl<Purview, Long>
		implements IPurviewService {

	@Autowired
	private IPurviewRepository purviewRepository;

	public IPurviewRepository getPurviewRepository() {
		return purviewRepository;
	}

	public void setPurviewRepository(IPurviewRepository purviewRepository) {
		this.purviewRepository = purviewRepository;
	}

}
