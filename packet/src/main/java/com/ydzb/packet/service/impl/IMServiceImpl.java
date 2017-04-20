package com.ydzb.packet.service.impl;

import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.repository.IMRepository;
import com.ydzb.packet.service.IIMservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CRF on 2017/3/10 0010.
 */
@Service
public class IMServiceImpl implements IIMservice {

    @Autowired
    private IMRepository imRepository;

    public IMRepository getImRepository() {
        return imRepository;
    }

    public void setImRepository(IMRepository imRepository) {
        this.imRepository = imRepository;
    }

    private String[] tableNameArr = {"jx_debt_register", "jx_bid_apply", "jx_lend_pay", "jx_credit_invest", "jx_repay", "jx_credit_end"};

    /**
     * 获取即信异常信息统计结果
     * @return
     */
    public List<Object> getIMErrorCount() {
        return imRepository.getIMErrorCount();
    }

    /**
     * 根据条件获取详细异常信息
     * @param searchable
     * @param para: value = 1-6
     * @return
     */
    public Page<Object[]> getIMErrorDetailList(Searchable searchable, Integer para) {

        List<Object[]> list = new ArrayList<>();
        if (para != null && para >= 1 && para <= 6) {
            list = imRepository.getIMErrorDetailList(tableNameArr[para - 1]);
        }
        return new PageImpl<>(
            getSubList(searchable, list),
            searchable.getPage(),
            list.size()
        );
    }

    /**
     * 根据分页条件获取子集合
     * @param searchable
     * @param list
     * @return
     */
    private List<Object[]> getSubList(Searchable searchable, List<Object[]> list) {
        List<Object[]> sublist = null;
        if (list == null || list.size() == 0){
            return new ArrayList<Object[]>();
        }
        PageRequest page = (PageRequest)searchable.getPage();
        int pageNo = page.getPageNumber();
        int pageSize = page.getPageSize();
        int toIndex = 0;
        if (pageNo * pageSize + pageSize > list.size()) {
            toIndex = list.size();
        }
        else {
            toIndex = pageNo * pageSize + pageSize;
        }
        sublist = list.subList(pageNo * pageSize, toIndex);
        return sublist;
    }
}
