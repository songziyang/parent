package com.ydzb.sms.repository;

import com.ydzb.sms.entity.AuthCode;
import com.ydzb.sms.entity.Message;
import com.ydzb.sms.entity.SmsLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class SmsHandleRepository {


    @PersistenceContext
    private EntityManager manager;


    //通过模板标识短信网站模板
    public String querySmsTemplateByFlag(String flag) {
        String sql = "select it.smsContent from InfoTemplate as it where it.flag = '" + flag + "' and it.status = 0";
        Query query = manager.createQuery(sql);
        List<String> list = query.getResultList();
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    //通过模板标识查询网站模板
    public String querySiteTemplateByFlag(String flag) {
        String sql = "select it.siteContent from InfoTemplate as it where it.flag = '" + flag + "' and it.status = 0";
        Query query = manager.createQuery(sql);
        List<String> list = query.getResultList();
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public void saveAuthCode(AuthCode ac) {
        manager.persist(ac);
    }


    public void saveSmsLog(SmsLog sl) {
        manager.persist(sl);
    }

    public void saveSiteMessage(Message m) {
        manager.persist(m);
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
