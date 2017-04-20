package com.ydzb.admin.shiro;

import com.google.code.kaptcha.Constants;
import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.entity.OnetimePassword;
import com.ydzb.admin.entity.Purview;
import com.ydzb.admin.entity.Role;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.admin.service.IOnetimePasswordService;
import com.ydzb.admin.service.IPurviewService;
import com.ydzb.admin.service.IRoleService;
import com.ydzb.core.shiro.CaptchaUsernamePasswordToken;
import com.ydzb.core.utils.Encodes;
import ft.otp.verify.OTPVerify;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShiroDbRealm extends AuthorizingRealm {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;

    protected boolean useCaptcha = false;


    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPurviewService purviewService;

    @Autowired
    private IOnetimePasswordService onetimePasswordService;


    /**
     * 给ShiroDbRealm提供编码信息，用于密码密码比对 描述
     */
    public ShiroDbRealm() {
        super();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 认证回调函数, 登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

//        if (useCaptcha) {
//            CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
//            String parm = token.getCaptcha();
//            String c = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//            SecurityUtils.getSubject().getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
//            if (c != null && c.length() == 5) {
//                // 忽略大小写
//                if (!parm.equalsIgnoreCase(c)) {
//                    throw new IncorrectCaptchaException("验证码错误！");
//                }
//            } else {
//                throw new IncorrectCaptchaException("验证码错误！");
//            }
//        }


        if (useCaptcha) {
            CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
            String vNumber = token.getCaptcha();
            SecurityUtils.getSubject().getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);

            if (vNumber != null && vNumber.length() == 6) {
                // 忽略大小写
                Admin admin = adminService.findAdminByUsername(token.getUsername());
                if (admin == null) {
                    //如果找不到用户就跳出数字令牌验证，进入用户验证，也可以直接抛出找不到用户异常
                    throw new IncorrectCaptchaException("用户名或密码错误！");
                }
                OnetimePassword otp = admin.getOnetimePassword();
                if (otp == null) {
                    throw new IncorrectCaptchaException("数字令牌错误！");
                }
                String seed = otp.getAuthkey();
                int iDrift = otp.getDriftVal();
                long lSucc = otp.getSuccessVal();
                Map hashMap;
                Long nReturn;
                hashMap = OTPVerify.ET_CheckPwdz201(
                        seed,                                    //令牌密钥
                        System.currentTimeMillis() / 1000,        //调用本接口计算机的当前时间
                        0,                                        //给0
                        60,                                        //给60，因为每60秒变更新的动态口令
                        iDrift,                                        //漂移值，用于调整硬件与服务器的时间偏差，见手册说明
                        40,                                        //认证窗口，见手册说明
                        lSucc,                                    //成功值，用于调整硬件与服务器的时间偏差，见手册说明
                        vNumber);                                    //要认证的动态口令OTP

                nReturn = (Long) hashMap.get("returnCode");
                System.out.println("returnCode = " + nReturn);

                if (nReturn == OTPVerify.OTP_SUCCESS) {
                    System.out.println("check success");
                    System.out.println("currentSucc = " + hashMap.get("currentUTCEpoch"));
                    System.out.println("currentDrift = " + hashMap.get("currentDrift"));

                    iDrift = ((Long) hashMap.get("currentDrift")).intValue();
                    lSucc = ((Long) hashMap.get("currentUTCEpoch")).longValue();
                    //保存返回的偏移值与成功值
                    otp.setDriftVal(iDrift);
                    otp.setSuccessVal(lSucc);
                    onetimePasswordService.save(otp);
                } else {
                    throw new IncorrectCaptchaException("数字令牌错误！");
                }
            } else {
                throw new IncorrectCaptchaException("数字令牌错误！");
            }
        }


        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        if (token.getUsername() != null) {
            Admin admin = adminService.findAdminByUsername(token.getUsername());
            if (admin != null) {
                byte[] salt = Encodes.decodeHex(admin.getSalt());
                Long roleId = null;
                if (admin.getRole() != null) {
                    roleId = admin.getRole().getId();
                }
                return new SimpleAuthenticationInfo(new ShiroUser(admin.getId(), roleId, admin.getUsername()), admin.getPassword(), ByteSource.Util.bytes(salt), getName());
            } else {
                throw new IncorrectCaptchaException("用户名或密码错误！");
            }
        } else {
            throw new IncorrectCaptchaException("用户名或密码错误！");
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        List<String> purviewsLst = new ArrayList<String>();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        if (shiroUser != null) {
            if (Contains.USERNAME.equals(shiroUser.getUsername())) {
                List<Purview> pList = purviewService.findAll();
                for (Purview purview : pList) {
                    purviewsLst.add(purview.getFlag());
                }
            } else if (shiroUser.getRoleId() != null) {
                Role role = roleService.findOne(shiroUser.getRoleId());
                if (role != null && role.getPurviews() != null) {
                    String[] purviewIds = role.getPurviews().split(",");
                    List<Purview> lst = purviewService.findAll();
                    for (int i = 0; i < purviewIds.length; i++) {
                        if (lst != null && lst.size() > 0) {
                            for (Purview p : lst) {
                                if (p.getId().compareTo(Long.parseLong(purviewIds[i])) == 0) {
                                    purviewsLst.add(p.getFlag());
                                }
                            }
                        }
                    }
                }

            }
        }

        if (purviewsLst != null && purviewsLst.size() > 0) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addStringPermissions(purviewsLst);
            return info;
        }
        return null;
    }

    public boolean isUseCaptcha() {
        return useCaptcha;
    }

    public void setUseCaptcha(boolean useCaptcha) {
        this.useCaptcha = useCaptcha;
    }

    public IAdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public IPurviewService getPurviewService() {
        return purviewService;
    }

    public void setPurviewService(IPurviewService purviewService) {
        this.purviewService = purviewService;
    }


}
