package com.ydzb.packet.service.impl;

import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.packet.entity.*;
import com.ydzb.packet.repository.*;
import com.ydzb.packet.service.IUserRedPacketService;
import com.ydzb.sms.service.ISmsHandleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户红包service实现类
 *
 * @author sy
 */
@Service
public class UserRedPacketServiceImpl extends BaseServiceImpl<UserRedPacket, Long> implements IUserRedPacketService {

    @Autowired
    private RpUserRepository rpUserRepository;
    @Autowired
    private IRedPacketInterestRepository redPacketRepository;
    @Autowired
    private IRedPacketCashRepository redPacketCashRepository;
    @Autowired
    private IRedPacketVoucherRepository voucherRepository;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private UserRedPacketRepository userRedPacketRepository;

    /**
     * 查询要发送红包的用户数量
     *
     * @param rpUser 前台传递的用户条件
     * @return
     */
    @Override
    public BigInteger querySendUserCount(RpUser rpUser) {
        return rpUserRepository.querySendUserCount(rpUser);
    }

    /**
     * 发送加息券红包
     *
     * @param rpUser
     * @param redpacketId
     * @return
     */
    @Override
    public String sendRedpacketInterest(RpUser rpUser, Long redpacketId) {
        //获取当前用户
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //发送红包
        String result = rpUserRepository.sendRedpacketInterest(rpUser, redpacketId, curUser.getUsername());
        //获取用户手机号
        String[] mobile = rpUserRepository.queryUserMoblie(rpUser);
        //查询红包实体
        RedPacketInterest redpacket = redPacketRepository.findOne(redpacketId);
        //模板替换内容
        StringBuffer sb = new StringBuffer();
        sb.append("redpktype:" + "加息券");        //红包类型
        sb.append(",value:" + redpacket.getGiveValue());    //值
        //发送用户红包短信
        smsHandleService.sendUserMassSms("redpacket_send", mobile, sb.toString());
        return "发送成功";
    }

    /**
     * 发送现金红包
     *
     * @param rpUser
     * @param redpacketId
     * @return
     */
    @Override
    public String sendRedpacketCash(RpUser rpUser, Long redpacketId) {
        //获取当前用户
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //发送红包
        String result = rpUserRepository.sendRedpacketCash(rpUser, redpacketId, curUser.getUsername());
        //获取用户手机号
        String[] mobile = rpUserRepository.queryUserMoblie(rpUser);
        //查询红包实体
        RedPacketCash redpacket = redPacketCashRepository.findOne(redpacketId);
        //模板替换内容
        StringBuffer sb = new StringBuffer();
        sb.append("redpktype:" + "现金红包");        //红包类型
        sb.append(",value:" + redpacket.getFund());    //值
        //发送用户红包短信
        smsHandleService.sendUserMassSms("redpacket_send", mobile, sb.toString());
        return "发送成功";
    }

    /**
     * 发送代金券红包
     *
     * @param rpUser
     * @param redpacketId
     * @return
     */
    @Override
    public String sendRedpacketVoucher(RpUser rpUser, Long redpacketId) {
        //获取当前用户
        ShiroUser curUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //发送红包
        String result = rpUserRepository.sendRedpacketVoucher(rpUser, redpacketId, curUser.getUsername());
        //获取用户手机号
        String[] mobile = rpUserRepository.queryUserMoblie(rpUser);
        //查询红包实体
        RedPacketVoucher voucher = voucherRepository.findOne(redpacketId);
        //模板替换内容
        StringBuffer sb = new StringBuffer();
        sb.append("redpktype:" + "定存红包");        //红包类型
        sb.append(",value:" + voucher.getFund());    //值
        //发送用户红包短信
        smsHandleService.sendUserMassSms("redpacket_send", mobile, sb.toString());
        return "发送成功";
    }

    /**
     * 查询导出excel数据
     *
     * @param filter
     * @return
     */
    @Override
    public List<Object[]> findExportData(Map<String, Object> filter) {
        return userRedPacketRepository.findExportData(filter);
    }

    @Override
    public String exportExcel(List<Object[]> data, String address) {
        String fileName = "";
        try {
            List<Map<String, Object>> headInfo = createHeadInfo();
            List<Map<String, Object>> rowInfo = createRowInfo(data);

            fileName = "/user_redpacketrecord" + DateUtil.getSystemTimeSeconds() + ".xls";
            String filePath = address + fileName;
            POIUtil.exportExcel2FilePath("用户红包记录", filePath, headInfo, rowInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 创建头信息
     *
     * @return
     */
    private List<Map<String, Object>> createHeadInfo() {
        String[] titles = {"用户名", "手机号", "红包赠送值", "产品类型", "红包类型", "状态", "发放日期", "使用日期", "到期日期"};
        String[] keys = {"username", "mobile", "giveValue", "productType", "redpacketType", "status", "getTime", "useTime", "finishTime"};
        List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemMap = null;
        for (int i = 0; i < titles.length; i++) {
            itemMap = new HashMap<String, Object>();
            itemMap.put("title", titles[i]);
            itemMap.put("columnWidth", 30);
            itemMap.put("dataKey", keys[i]);
            headInfoList.add(itemMap);
        }
        return headInfoList;
    }

    /**
     * 创建行信息
     *
     * @param record
     * @return
     */
    private List<Map<String, Object>> createRowInfo(List<Object[]> record) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowMap = null;
        for (Object[] row : record) {
            if (row == null) {
                continue;
            }
            rowMap = new HashMap<String, Object>();
            rowMap.put("username", row[0] == null ? "" : row[0]);
            rowMap.put("mobile", row[1] == null ? "" : row[1]);
            rowMap.put("giveValue", row[2] == null ? "" : row[2]);
            rowMap.put("redpacketType", getRedpacketType(row[3]));
            rowMap.put("productType", getProductType(row[4]));
            rowMap.put("status", getStatus(row[5]));
            rowMap.put("getTime", getTime(row[6]));
            rowMap.put("useTime", getTime(row[7]));
            rowMap.put("finishTime", getFinishTime((Byte) row[5], row[7], row[8], row[9]));

            rowList.add(rowMap);
        }
        return rowList;
    }


    private String getRedpacketType(Object obj) {
        if (obj == null) {
            return "";
        }
        //1、现金红包，2加息卷 3、代金券',
        switch ((Byte) obj) {
            case 1:
                return "现金红包";
            case 2:
                return "加息券";
            case 3:
                return "代金券";
            default:
                return "";
        }
    }

    private String getProductType(Object obj) {
        if (obj == null) {
            return "";
        }
        //0-现金 1-活期宝 2-定存宝
        switch ((Byte) obj) {
            case 1:
                return "活期宝";
            case 2:
                return "定存宝";
            default:
                return "";
        }
    }

    private String getTriggerType(Object obj) {
        if (obj == null) {
            return "";
        }
        switch ((Byte) obj) {
            case -1:
                return "抽奖";
            case 0:
                return "手动";
            case 1:
                return "注册";
            case 2:
                return "充值";
            case 3:
                return "投资";
            case 4:
                return "推荐";
            case 8:
                return "分享";
            case 9:
                return "七日推荐返现";
            case 10:
                return "VIP生日福利";
            case 11:
                return "推荐定存返现";
            case 12:
                return "定存排行活动";
            case 13:
                return "周年活动现金红包";
            case 14:
                return "周年活动加息券";
            default:
                return "";
        }
    }

    private String getStatus(Object obj) {
        if (obj == null) {
            return "";
        }
        switch ((Byte) obj) {
            case 1:
                return "未使用";
            case 2:
                return "已冻结";
            case 3:
                return "已使用";
            case 4:
                return "已过期";
            case 5:
                return "现金领取";
            default:
                return "";
        }
    }

    private String getTime(Object obj) {
        if (obj == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(((Integer) obj).longValue() * 1000));
    }

    private String getFinishTime(Byte status, Object userUseTime, Object userFinishTime, Object investFinishTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (status == 1) {
            return userFinishTime == null ? "" : formatter.format(new Date(((Integer) userFinishTime).longValue() * 1000));
        } else if (status == 3) {
            return investFinishTime == null ? "" : formatter.format(new Date(((Integer) investFinishTime).longValue() * 1000));
        } else if (status == 4) {
            if (userUseTime == null) {
                return userFinishTime == null ? "" : formatter.format(new Date(((Integer) userFinishTime).longValue() * 1000));
            } else {
                return investFinishTime == null ? "" : formatter.format(new Date(((Integer) investFinishTime).longValue() * 1000));
            }
        }
        return "";
    }
}