package com.ydzb.user.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUserService extends IBaseService<User, Long> {

    /**
     * 设置用户vip等级
     * @param userId
     * @param vipGradeId
     * @throws Exception
     */
    String userSettingLeve(Long userId, Long vipGradeId) throws Exception;

    /**
     * 删除用户
     *
     * @param id
     * @throws Exception
     */
    public void deleteUser(Long id) throws Exception;


    /**
     * 删除用户
     *
     * @param ids
     * @throws Exception
     */
    public void deleteUser(Long[] ids) throws Exception;


    /**
     * 导出Excel
     *
     * @param users
     * @param address
     * @return
     */
    public String exportUser(List<Object[]> users, String address);


    /**
     * @param users
     * @param address
     * @return
     */
    public String exportUserMoney(List<Object[]> users, String address);

    /**
     * 导出推荐用户
     *
     * @param users
     * @param address
     * @return
     */
    public String exportRefferralUser(List<Object[]> users, String address);

    /**
     * 活期宝金额
     *
     * @return
     */
    public BigDecimal findHqbje();

    /**
     * 活期宝人数
     *
     * @return
     */
    public BigDecimal findHqbrs();

    /**
     * 定存宝金额
     *
     * @return
     */
    public BigDecimal findDcbje();

    /**
     * 定存宝人数
     *
     * @return
     */
    public BigDecimal findDcbrs();

    /**
     * 新手宝金额
     *
     * @return
     */
    public BigDecimal findXsbje();

    /**
     * 新手宝人数
     *
     * @return
     */
    public BigDecimal findXsbrs();

    /**
     * 活期宝体验金
     *
     * @return
     */
    public BigDecimal findHqbtyj();

    /**
     * 活期宝人民币
     *
     * @return
     */
    public BigDecimal findHqbrmb();

    /**
     * 稳进宝
     *
     * @return
     */
    public BigDecimal findWjb();

    /**
     * 私人定制
     *
     * @return
     */
    public BigDecimal findSrdz();


    /**
     * 总投资人数
     * @return
     */
    public BigDecimal findZtjrs();

    /**
     * @param userId
     * @param fund
     * @param remark
     * @throws Exception
     */
    public void saveRecharge(Long userId, BigDecimal fund, String remark) throws Exception;

    /**
     * 根据用户名或者手机号查找用户
     *
     * @param username
     * @param mobile
     * @return
     */
    public String findByUsernameOrMobile(String username, String mobile);

    /**
     * 根据手机号判断是否存在拥有相同手机号或用户名的用户
     *
     * @param mobile
     * @param userId
     * @return
     */
    public String existUsernameOrMobile(String mobile, Long userId);

    /**
     * 设置推荐人手机号
     *
     * @param user
     * @param refferralUserId
     * @return
     */
    public String setRefferralUser(User user, Long refferralUserId);


    /**
     * @param users
     * @param address
     * @return
     */
    public String exportVipBirthdayUser(List<Object[]> users, String address);

    /**
     * 设置微信号
     *
     * @param user         目标用户
     * @param wechatNumber 微信号
     * @return
     * @throws Exception
     */
    public String setWechatNumber(User user, String wechatNumber) throws Exception;

    /**
     * 设置qq号
     *
     * @param user     目标用户
     * @param qqNumber qq号
     * @return
     * @throws Exception
     */
    public String setQqNumber(User user, String qqNumber) throws Exception;


    /**
     * 设置购买限额
     *
     * @param user
     * @param buyLimit
     * @return
     * @throws Exception
     */
    public String setBuyLimit(User user, Integer buyLimit) throws Exception;


    /**
     * 设置赎回限额
     *
     * @param user
     * @param redeemLimit
     * @return
     * @throws Exception
     */
    public String setRedeemLimit(User user, Integer redeemLimit) throws Exception;

    public List<Object[]> findExportData(Map<String, Object> filter);

    public List<Object[]> findMoneyExportData(Map<String, Object> filter);

    public List<Object[]> findReferralExportData(Map<String, Object> filter);

    public List<Object[]> findVipBirthdayExportData(Map<String, Object> filter);

    public List<User> findVipBirthdayByDate(Map<String, Object> filter);

    /**
     * 查询与银多同一天生日的用户
     *
     * @param filter
     * @return
     */
    public List<User> findSameBirthdayUsers(Map<String, Object> filter);

    public List<Object[]> findSameBirthdayExportData(Map<String, Object> filter);

    /**
     * @param users
     * @param address
     * @return
     */
    public String exportSameBirthdayUser(List<Object[]> users, String address);


    /**
     * @param IdCard
     * @return
     */
    public boolean findUserByIdCard(String IdCard);

    BigDecimal findFreeUserCount();

    BigDecimal findZhuanUserCount();

    BigDecimal findMljrUserCount();

    BigDecimal findFreeUserInvestFund();

    BigDecimal findZhuanUserInvestFund();

    BigDecimal findMljrUserInvestFund();


    /**
     * 设置电子账户类型
     * @param userId
     * @param accounttype
     * @throws Exception
     */
    String userSettingAccountType(Long userId, Integer accounttype) throws Exception;


}
