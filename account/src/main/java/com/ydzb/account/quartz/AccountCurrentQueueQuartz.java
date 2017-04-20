package com.ydzb.account.quartz;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.service.IWmCurrentQueueService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 活期宝预投处理
 */
public class AccountCurrentQueueQuartz {


    @Autowired
    private IWmCurrentQueueService currentQueueService;

    public void accountJob() {
        //结算活期宝预投
        accountCurrentQueue();
    }

    //结算活期宝预投
    public void accountCurrentQueue() {

        //查询产品
        WmProductInfo productInfo = currentQueueService.findWmProductInfo();
        if (productInfo != null) {
            //更新预投份数
            Integer copies = currentQueueService.findTotalCopies();
            if (copies != null && copies > 0) {
                //判断产品份数是否足够
                if (copies <= productInfo.getSurplus()) {
                    //更新产品剩余份数
                    productInfo.setSurplus(productInfo.getSurplus() - copies);
                    currentQueueService.updateWmProductInfo(productInfo);

                    //活期宝预投 最大ID 和 最小ID
                    IDRange idRange = currentQueueService.findMaxIdAndMinId();
                    //判断是否有要结算的活期宝
                    if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
                        // 根据ID遍历用户活期宝预投
                        for (long id = idRange.getMinId(); id <= idRange.getMaxId(); id++) {
                            try {
                                currentQueueService.accountCurrentQueue(id, productInfo.getId(), productInfo.getInterestRate());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                } else {
                    //更新产品剩余份数
                    productInfo.setSurplus(0);
                    currentQueueService.updateWmProductInfo(productInfo);


                    //活期宝预投 最大ID 和 最小ID
                    IDRange idRange = currentQueueService.findMaxIdAndMinId();
                    //判断是否有要结算的活期宝
                    if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {

                        //购买剩余份数
                        int remainCopies = productInfo.getFunds();
                        int dealCopies = 0;

                        // 根据ID遍历用户活期宝预投
                        for (long id = idRange.getMinId(); id <= idRange.getMaxId(); id++) {
                            try {
                                int buyCopies = currentQueueService.accountCurrentQueue(id, productInfo.getId(), productInfo.getInterestRate());
                                //累计成交份数
                                dealCopies = dealCopies + buyCopies;
                                //判断是否还有剩余份数
                                remainCopies = remainCopies - buyCopies;
                                //无剩余份数
                                if (remainCopies <= 0) {
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        WmProductInfo wmProductInfo = currentQueueService.findWmProductInfo();
                        //更新真实的成交额
                        wmProductInfo.setFunds(dealCopies);
                        currentQueueService.updateWmProductInfo(wmProductInfo);

                    }

                }

            }
        }

    }


    public IWmCurrentQueueService getCurrentQueueService() {
        return currentQueueService;
    }

    public void setCurrentQueueService(IWmCurrentQueueService currentQueueService) {
        this.currentQueueService = currentQueueService;
    }
}
