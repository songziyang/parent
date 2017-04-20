package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRagularUserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class RagularExpireInfoRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 根据到期时间查询到期复投的定存产品
     *
     * @param expireTime 到期日期
     * @return
     */
    public List<WmRagularUserAccount> findWmRagularUserAccounts(Long expireTime) {
        String sql = "select wrua From WmRagularUserAccount as wrua WHERE wrua.status = 0 AND  wrua.expireMode = 1 AND wrua.expireTime = " + expireTime;
        TypedQuery<WmRagularUserAccount> query = manager.createQuery(sql, WmRagularUserAccount.class);
        if (query != null) {
            List<WmRagularUserAccount> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
