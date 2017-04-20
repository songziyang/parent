package com.ydzb.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PlatformInvestMapRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询在投金额和人数
     * Modified BY CRF
     * 2017.03.08
     * 需求更改：使用身份证前2位获取省份信息
     * @return 0 人数 1 金额 2 省份
     */
    public List<Object[]> findInvestData() {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(distinct tt.user_id) as uid , IFNULL(sum(tt.all_invest)/10000, 0) as invest, ac.provinceName as province ")
                .append(" from apo_areacode as ac left join (select wui.user_id as user_id , wui.all_invest as all_invest, wuu.id_card as id_card")
                .append(" from wm_user_investinfo as wui, wm_user_users as wuu where wui.user_id = wuu.id and wui.all_invest >= 100) as tt  ")
                .append(" on ac.zone = CONCAT(LEFT(tt.id_card,2),'0000') where ac.zone like '__0000' group by left(ac.zone,2) order by invest desc");

        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    /**
     * 查询在投金额和人数
     * Modified BY
     * 2017.03.08
     * 需求更改：使用身份证前4位获取城市信息
     * @param pname 省份
     * @return 0 人数 1 金额 2 城市
     */
    public List<Object[]> findInvestDataByPName(String pname) {
        StringBuffer sql = new StringBuffer();

        sql.append("select count(distinct tt.user_id) as uid , IFNULL(sum(tt.all_invest)/10000, 0.000000) as invest, ac.cityName as province")
            .append(" from apo_areacode as ac left join(select wui.user_id as user_id , wui.all_invest as all_invest, wuu.id_card as id_card")
            .append(" from wm_user_investinfo as wui, wm_user_users as wuu where wui.user_id = wuu.id and wui.all_invest >= 100 ) as tt")
            .append(" on ac.zone = CONCAT(LEFT(tt.id_card,4),'00') where ac.zone like '____00' and ac.zone not like '__0000'")
            .append(" and ac.`desc` like '").append(pname).append("%' group by left(ac.zone, 4) order by invest desc");

        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    /**
     * 查询累计充值金额和人数
     *
     * @return 0 人数 1 金额 2 省份
     */
    public List<Object[]> findRechargeData() {

        String sql = "select IFNULL(count(DISTINCT id_card), 0) as uid, IFNULL(sum(money)/10000, 0.000000) as foud, aa.provinceName "
                    + " from apo_areacode as aa left join wm_user_recharge as wur on aa.province = left(wur.id_card, 2) "
                    + " where aa.zone like '__0000%' group by left(aa.province, 2)";

        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    /**
     * 查询累计充值金额和人数
     *
     * @param pname
     * @return 0 人数 1 金额 2 城市
     */
    public List<Object[]> findRechargeDataByPName(String pname) {
        String sql = "select count(wur.user_id) as uid,sum(money) as fund, aac.cityName from apo_areacode as aac "
                    + " left join wm_user_recharge as wur on aac.city = left(wur.id_card, 4) where 1=1 and aac.zone not like '__0000%' "
                    + " AND aac.provinceName like '%" + pname + "%' group by aac.city";

        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    /**
     * 查询性别
     *
     * @param sex 0 女 1 男
     * @return
     */
    public int findSexNum(int sex) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(id) FROM  wm_user_users where id_card  is not null and  substr(`id_card`,17,1)%2 = ");
        sql.append(sex);
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return ((BigInteger) query.getSingleResult()).intValue();
        }
        return 0;
    }


    /**
     * 查询年龄
     *
     * @param s 开始 e 结束
     * @return
     */
    public int findAgeNum(int s, int e) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(id) from wm_user_users  where id_card is not null and year(utc_date())- substr(id_card,7,4) >= ");
        sql.append(s);
        sql.append("  and year(utc_date())- substr(id_card,7,4) < ");
        sql.append(e);
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            return ((BigInteger) query.getSingleResult()).intValue();
        }
        return 0;
    }

    /**
     * 获取在投统计导出结果
     * @param province
     * @param city
     * @return
     */
    public List<Object[]> getExportData(String province, String city) {
        String sql = "select wuu.username,wuu.mobile,if(length(wuu.id_card)=18, year(curdate())-substring(wuu.id_card,7,4), null) as age, "
                    + " if(length(wuu.id_card)=18, case cast(substring(wuu.id_card,17,1) as UNSIGNED)%2 when 1 then '男' when 0 then '女' "
                    + " else '未知' end, '未知')as sex, wui.all_invest as allinvest, wui.all_invest_dayloan as dayloan, wui.all_invest_deposit as deposit "
                    + " from wm_user_investinfo as wui left join wm_user_users wuu  on wui.user_id = wuu.id "
                    + " left join apo_areacode ac on left(wuu.id_card, 4) = ac.city where all_invest >= 100 and ac.zone like '____00' ";

        if (!StringUtils.isEmpty(province)) {
            sql += " AND  provinceName like '%" + province + "%'";
        }
        if (!StringUtils.isEmpty(city) && !"all".equals(city)) {
            sql += " AND cityName like '%" + city + "%'";
        }

        sql += " order by allinvest desc";

        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
