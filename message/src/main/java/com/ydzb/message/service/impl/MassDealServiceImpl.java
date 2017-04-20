package com.ydzb.message.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.message.repository.IMassDealRepository;
import com.ydzb.message.service.IMassDealService;
import com.ydzb.sms.entity.MassDeal;
import jxl.Cell;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;



/**
 * 短信群发服务实现类
 */
@Service
public class MassDealServiceImpl extends BaseServiceImpl<MassDeal, Long> implements IMassDealService {

	@Autowired
	private IMassDealRepository iMassDealRepository;
	
	/**
	 * 保存短信群发记录
	 * @param massId 群发模板id
	 * @param mobile 电话号码
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveMassDeal(Long massId, String mobile) throws Exception {
		if(StringUtils.isEmpty(mobile)) {
			return "手机号码为空！";
		}
		MassDeal targetMassDeal = findByMobile(massId, mobile);
		if (targetMassDeal != null) {
			return "手机号已存在！";
		}
		MassDeal massDeal = new MassDeal(massId, mobile);
		massDeal.setCreated(DateUtil.getSystemTimeMillisecond(DateUtil.getSystemTimeSeconds()));
		iMassDealRepository.save(massDeal);
		return null;
	}

	/**
	 * 读取excel，将数据添加到wm_info_mass_deal表中，存储手机号
	 * @param sheet 
	 * @param column 列名
	 * @param massId 群发模板id
	 * @return
	 */
	@Override
	public String sheetProcess(Sheet sheet, String column, Long massId) throws Exception {
		String mobile;
		Cell cell;
		//循环每一个excel行
		for (int i = 0; i < sheet.getRows(); i++) {
			//获取每行的第一列的单元格
			cell = sheet.getCell(0, i);
			if (cell == null) {
				continue;
			}
			if (i == 0) {
				// 判断列名是否对应
				if(cell.getContents().equals(column)) {
					continue;
				}
				return "Excel首列名错误";
			}
			mobile = cell.getContents().trim();
			if (i == 0 || mobile == null) {
				continue;
			}
			saveMassDeal(massId, mobile);
		}
		return null;
	}

	/**
	 * 根据群发模板id，查询对应群发记录的手机号
	 * @param massId 群发模板id
	 * @return
	 * @throws Exception
	 */
	@Override
	public String[] findMobile(Long massId) throws Exception {
		List<String> mobiles = iMassDealRepository.findMobile(massId);
		String[] mobilesArray = new String[mobiles.size()];
		for (int i = 0; i < mobiles.size(); i ++) {
			mobilesArray[i] = mobiles.get(i);
		}
		return mobilesArray;
	}

	/**
	 * 根据群发模板idy以及手机号，查询群发记录
	 * @param massId 群发模板id
	 * @param mobile 手机号
	 * @return
	 * @throws Exception
	 */
	@Override
	public MassDeal findByMobile(Long massId, String mobile) throws Exception {
		return iMassDealRepository.findByMobile(massId, mobile);
	}
}