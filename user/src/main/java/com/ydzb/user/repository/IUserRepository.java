package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IUserRepository extends IBaseRepository<User, Long> {

    /**
     * 活期宝金额
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestDayloan) from User as user where user.status <> -1  ")
    public BigDecimal findHqbje();

    /**
     * 活期宝人数
     *
     * @return
     */
    @Query("select count(user) from  User as user  where user.status <> -1 and user.userInvestinfo.allInvestDayloan >= 100 ")
    public BigDecimal findHqbrs();

    /**
     * 新手宝金额
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestPrivilege) from User as user where user.status <> -1  ")
    public BigDecimal findXsbje();

    /**
     * 新手宝人数
     *
     * @return
     */
    @Query("select count(user) from  User as user  where user.status <> -1 and user.userInvestinfo.allInvestPrivilege > 0 ")
    public BigDecimal findXsbrs();


    /**
     * 定存宝金额
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestDeposit) from User as user  where user.status <> -1 and user.id <> 84033 ")
    public BigDecimal findDcbje();

    /**
     * 定存宝人数
     *
     * @return
     */
    @Query("select count(user) from  User as user  where user.status <> -1 and user.userInvestinfo.allInvestDeposit > 0  and user.id <> 84033 ")
    public BigDecimal findDcbrs();


    /**
     * 总投资人数
     *
     * @return
     */
    @Query("select count(user) from  User as user  where user.status <> -1 and user.userInvestinfo.allInvest >= 100 ")
    public BigDecimal findZtjrs();


    /**
     * 随心存人数
     *
     * @return
     */
    @Query("select count(user.id) from User as user  where user.status <> -1 and user.userInvestinfo.allInvestFree > 0 ")
    public BigDecimal findFreeUserCount();

    /**
     * 随心存在投金额
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestFree) from User as user where user.status <> -1 ")
    public BigDecimal findFreeUserInvestFund();

    /**
     * 转转赚人数
     *
     * @return
     */
    @Query("select count(user.id) from User as user  where user.status <> -1 and user.userInvestinfo.allInvestWjb > 0 ")
    public BigDecimal findZhuanUserCount();

    /**
     * 转转赚在投金额
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestWjb) from User as user where user.status <> -1 ")
    public BigDecimal findZhuanUserInvestFund();

    /**
     * 美利金融用户
     *
     * @return
     */
    @Query("select count(user.id) from User as user  where user.status <> -1 and user.userInvestinfo.allInvestSelf > 0 ")
    public BigDecimal findMljrUserCount();

    /**
     * 美利金融在投基恩
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestSelf) from User as user where user.status <> -1 ")
    public BigDecimal findMljrUserInvestFund();

    /**
     * 活期宝体验金
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestInvest ) from  User as user where user.status <> -1 ")
    public BigDecimal findHqbtyj();

    /**
     * 活期宝人民币
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestDayloan - user.userInvestinfo.allInvestInvest ) from  User as user where user.status <> -1 ")
    public BigDecimal findHqbrmb();

    /**
     * 稳进宝
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestWjb) from User as user where user.status <> -1 ")
    public BigDecimal findWjb();

    /**
     * 私人定制
     *
     * @return
     */
    @Query("select sum(user.userInvestinfo.allInvestSelf) from User as user where user.status <> -1 ")
    public BigDecimal findSrdz();

    /**
     * 根据用户名或手机查找用户
     *
     * @param username
     * @param mobile
     * @return
     */
    @Query(" FROM User AS u WHERE (u.username = :username OR mobile = :mobile) AND u.status = 0 ")
    public List<User> findByUsernameOrMobile(@Param("username") String username, @Param("mobile") String mobile);


    @Query(" FROM User AS u WHERE u.idCard = :idCard  AND u.status = 0 ")
    public List<User> findUserByIdCard(@Param("idCard") String idCard);

    /**
     * 根据手机号查询
     * @param mobile
     * @param status
     * @return
     */
    @Query(" FROM User WHERE mobile = :mobile AND status = :status")
    List<User> findByMobile(@Param("mobile") String mobile, @Param("status") Integer status);


}


