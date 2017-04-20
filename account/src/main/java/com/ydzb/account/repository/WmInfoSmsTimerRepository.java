package com.ydzb.account.repository;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmInfoSmsTimer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;


@Repository
public class WmInfoSmsTimerRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 短信定时发送记录 最大ID 和 最小ID
     *
     * @return
     */
    public IDRange findMaxIdAndMinId() {
        String sql = "select max(wist.id) as maxId, min(wist.id) as minId from wm_info_sms_timer as wist ";
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
     * 查询用户定时短信
     *
     * @param id
     * @return
     */
    public WmInfoSmsTimer findWmInfoSmsTimer(Long id) {
        return manager.find(WmInfoSmsTimer.class, id);
    }

    /**
     * 删除发送的短信
     *
     * @param infoSmsTimer
     */
    public void removeWmInfoSmsTimer(WmInfoSmsTimer infoSmsTimer) {
        manager.remove(infoSmsTimer);
    }

    /**
     * 保存
     *
     * @param infoSmsTimer
     */
    public void saveWmInfoSmsTimer(WmInfoSmsTimer infoSmsTimer) {
        manager.persist(infoSmsTimer);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
