package com.ydzb.admin.service.impl;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.repository.IAdminRepository;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.admin.entity.OnetimePassword;
import com.ydzb.admin.repository.IOnetimePasswordRepository;
import com.ydzb.admin.service.IOnetimePasswordService;
import ft.otp.verify.OTPVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数字令牌service实现
 */
@Service
public class OnetimePasswordServiceImpl extends BaseServiceImpl<OnetimePassword, Long> implements IOnetimePasswordService {

    @Autowired
    private IOnetimePasswordRepository onetimePasswordRepository;
    @Autowired
    private IAdminRepository adminRepository;

    /**
     * 保存数字令牌信息
     * @param onetimePassword
     */
    @Override
    public String saveOne(OnetimePassword onetimePassword) {
        if (onetimePassword.getId() == null) {
            return createOne(onetimePassword);
        }
        return editOne(onetimePassword);
    }

    /**
     * 删除数字令牌信息
     * @param otpId
     */
    @Override
    public void deleteOne(Long otpId) {
        //移除数字令牌与后台用户的关系
        Admin admin = adminRepository.findByOtpId(otpId);
        if (admin != null) {
            admin.setOnetimePassword(null);
        }
        //删除数字令牌信息
        onetimePasswordRepository.delete(otpId);
    }

    /**
     * 新建
     * @param otp
     */
    private String createOne(OnetimePassword otp) {
        boolean alreadyExist = onetimePasswordRepository.findByNumberOrAuthkey(otp.getOtpNumber(), otp.getAuthkey()).isEmpty()? false: true;
        if (alreadyExist) {
            return "repeat";
        }
        //保存数字令牌信息
        otp.setCreated(DateUtil.getSystemTimeSeconds());
        //如果adminId为空，证明前台没选择用户，需要设置为null,否则报错
        if (otp.getAdmin() != null && otp.getAdmin().getId() == null) {
            otp.setAdmin(null);
        }
        onetimePasswordRepository.save(otp);

        //保存数字令牌与后台人员关系
        if (otp.getAdmin() != null && otp.getAdmin().getId() != null) {
            Admin admin = adminRepository.findOne(otp.getAdmin().getId());
            admin.setOnetimePassword(otp);
            adminRepository.save(admin);
        }
        return "success";
    }

    /**
     * 编辑
     * @param otp
     */
    private String editOne(OnetimePassword otp) {

        List<OnetimePassword> otps = onetimePasswordRepository.findByNumberOrAuthkey(otp.getOtpNumber(), otp.getAuthkey());
        if (otps!= null && !otps.isEmpty()) {
            if (otps.size() > 1) {
                return "repeat";
            } else if (!otps.get(0).getId().equals(otp.getId())) {
                return "repeat";
            }
        }

        //更改数字令牌信息
        OnetimePassword oldOtp = onetimePasswordRepository.findOne(otp.getId());
        oldOtp.setOtpNumber(otp.getOtpNumber());
        oldOtp.setAuthkey(otp.getAuthkey());
        onetimePasswordRepository.save(oldOtp);

        //更新数字令牌与后台人员关系
        Long oldAdminId = null;
        Long newAdminId = null;
        if (oldOtp.getAdmin() != null && oldOtp.getAdmin().getId() != null) {
            oldAdminId = oldOtp.getAdmin().getId();
        }
        if (otp.getAdmin() != null && otp.getAdmin().getId() != null) {
            newAdminId = otp.getAdmin().getId();
        }
        //如果没指定新的绑定用户，则不需要操作
        if (newAdminId == null) {
            return "";
        }
        //如果原来绑定了用户，则需要解绑
        if (oldAdminId != null) {
            Admin oldAdmin = oldOtp.getAdmin();
            oldAdmin.setOnetimePassword(null);
            adminRepository.save(oldAdmin);
        }
        Admin newAdmin = adminRepository.findOne(newAdminId);
        newAdmin.setOnetimePassword(otp);
        adminRepository.save(newAdmin);
        return "success";
    }

    /**
     * 数字令牌同步
     * @param otpId
     * @param password1
     * @param password2
     * @return
     */
    @Override
    public String sync(Long otpId, String password1, String password2) {
        OnetimePassword otp = onetimePasswordRepository.findOne(otpId);
        String authkey = otp.getAuthkey();
        int iDrift = otp.getDriftVal();
        long lSucc = otp.getSuccessVal();
        Long nReturn;

        Map hashMap = OTPVerify.ET_Syncz201(authkey,
                System.currentTimeMillis() / 1000,
                0,
                60,
                iDrift,
                40,
                lSucc,
                password1,
                password2);
        nReturn = (Long)hashMap.get("returnCode");

        if(nReturn == OTPVerify.OTP_SUCCESS) {
            iDrift = ((Long)hashMap.get("currentDrift")).intValue();
            lSucc = ((Long)hashMap.get("currentUTCEpoch")).longValue();
            //保存偏移值以及成功值
            otp.setDriftVal(iDrift);
            otp.setSuccessVal(lSucc);
            onetimePasswordRepository.save(otp);
            return "同步成功！";
        }
        return "同步失败";
    }
}