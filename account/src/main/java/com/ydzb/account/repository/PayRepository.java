package com.ydzb.account.repository;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmPayManuaStatus;
import com.ydzb.account.entity.WmPayManualRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PayRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 代付请求未结算最小ID和最大ID
     *
     * @return ID 区间
     */
    public IDRange findMaxIdAndMinId() {
        String sql = "select max(wm.id) as maxId, min(wm.id) as minId from pay_manual_request as wm where wm.acc_state = 0 ";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object[] obj = (Object[]) query.getSingleResult();
            if (obj != null && obj.length > 0) {
                IDRange idRange = new IDRange((BigInteger) obj[0], (BigInteger) obj[1]);
                return idRange;
            }
        }
        return null;
    }


    /**
     * 根据ID 查询 未结算记录
     *
     * @param id 主键
     * @return
     */
    public WmPayManualRequest findWmPayManualRequestById(Long id) {
        String sql = "select wm from WmPayManualRequest as wm  where wm.accState = 0 and wm.id = " + id + " order by  id desc ";
        TypedQuery<WmPayManualRequest> query = manager.createQuery(sql, WmPayManualRequest.class);
        if (query != null) {
            List<WmPayManualRequest> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 更新代付请求记录
     *
     * @param payManualRequest 代付请求记录
     */
    public void updateWmPayManualRequest(WmPayManualRequest payManualRequest) {
        manager.merge(payManualRequest);
    }


    /**
     * 查询代付运行状态
     *
     * @return 代付运行状态
     */
    public WmPayManuaStatus findStatus() {
        String sql = "select wp from WmPayManuaStatus as wp order by id desc ";
        TypedQuery<WmPayManuaStatus> query = manager.createQuery(sql, WmPayManuaStatus.class);
        if (query != null) {
            List<WmPayManuaStatus> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 更新代付运行状态记录
     *
     * @param payManuaStatus 代付运行状态记录
     */
    public void updateWmPayManuaStatus(WmPayManuaStatus payManuaStatus) {
        manager.merge(payManuaStatus);
    }


    /**
     * 查询自动放款记录
     *
     * @return
     */
    public List<Object[]> findAutoUserWithDraw() {
        String jpql = " SELECT wwd.id , wwd.drawAuto  FROM WmUserWithDraw as wwd WHERE wwd.status = 3 AND wwd.drawAuto = 1 ";
        List<Object[]> resultList = manager.createQuery(jpql).getResultList();
        return resultList == null? new ArrayList<Object[]>(): resultList;
    }


    /**
     * 查询待放款记录
     *
     * @return
     */
    public List<Object> findUserWithDrawStatus() {
        String jpql = " SELECT wwd.id FROM WmUserWithDraw as wwd WHERE wwd.status = 0 ";
        List<Object> resultList = manager.createQuery(jpql).getResultList();
        return resultList == null? new ArrayList<>(): resultList;
    }



    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }


}
