package com.ydzb.account.quartz;


import com.ydzb.account.service.IPayService;
import com.ydzb.withdraw.service.IUserWithDrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 第三方自动代付
 */
public class AccountAutoPayQuartz {

    @Autowired
    private IPayService payService;

    @Autowired
    private IUserWithDrawService userWithDrawService;


    //定时方法
    public void accountJob() {
        //查询自动代付待打款状态记录
        List<Object[]> autoPays = payService.findAutoUserWithDraw();
        if (autoPays != null && !autoPays.isEmpty()) {
            //遍历待打款状态记录
            for (Object[] obj : autoPays) {
                //用户提现记录主键
                Long id = obj[0] == null ? Long.valueOf(0) : (Long) obj[0];
                //用户提现记录打款类型
                Integer drawAuto = obj[1] == null ? Integer.valueOf(0) : (Integer) obj[1];
                // 1 自动处理 ID 存在
                if (drawAuto == 1 && id > 0) {
                    try {
                        //自动代付
                        autoPay(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    //每周日定时执行
    public void accountEverySundayJob() {
        //查询待打款记录
        List<Object> autoPays = payService.findUserWithDrawStatus();
        if (autoPays != null && !autoPays.isEmpty()) {
            //遍历待打款记录
            for (Object obj : autoPays) {
                //用户提现记录主键
                Long id = obj == null ? Long.valueOf(0) : (Long) obj;
                if (id > 0) {
                    try {
                        autoPay(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 处理自动放款信息
     *
     * @param id 用户提现表ID
     * @throws Exception
     */
    public void autoPay(Long id) throws Exception {
        //处理数据
        String result = userWithDrawService.updateLoanMoney(id, "系统自动");
        if (StringUtils.isEmpty(result)) {
            //调用民生接口
            userWithDrawService.querysendRequest(id);
            //发送短信
            userWithDrawService.sendMessage(id);
        }
    }


    public IPayService getPayService() {
        return payService;
    }

    public void setPayService(IPayService payService) {
        this.payService = payService;
    }


}
