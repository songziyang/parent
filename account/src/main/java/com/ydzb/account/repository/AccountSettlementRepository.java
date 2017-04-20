package com.ydzb.account.repository;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;


@Repository
public class AccountSettlementRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询现有用户的最大ID 和 最小ID
     *
     * @return ID 区间 最大ID 和 最小ID
     */
    public IDRange findWmUserMaxIdAndMinId() {
        String sql = "select max(wuu.id) as maxId, min(wuu.id) as minId from wm_user_users as wuu  ";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            List<Object> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                Object[] obj = (Object[]) lst.get(0);
                if (obj != null && obj.length > 0) {
                    IDRange idRange = new IDRange((BigInteger) obj[0], (BigInteger) obj[1]);
                    return idRange;
                }
            }
        }
        return null;
    }


    /**
     * 根据用户ID 查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public WmUser findWmUserUserById(Long userId) {
        String sql = "select wm from WmUser as wm where wm.status = 0 and wm.id = " + userId;
        TypedQuery<WmUser> query = manager.createQuery(sql, WmUser.class);
        if (query != null) {
            List<WmUser> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
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
