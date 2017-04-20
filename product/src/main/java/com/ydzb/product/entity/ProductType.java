package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 产品类别
 *
 * @author sy
 */
@Entity
@Table(name = "wm_product_type")
public class ProductType extends BaseEntity<Long> {

    public static final long CURRENT = 1l;   //活期宝
    public static final long REGULAR = 2l;  //定存宝
    public static final long MAIDUOBAO_FIRST_STAGE = 3l; //麦罗产品1期
    public static final long MAIDUOBAO_SECOND_STAGE = 4l; //麦罗产品2期
    public static final long[] MAILUO_PRODUCTS = {ProductType.MAIDUOBAO_FIRST_STAGE, ProductType.MAIDUOBAO_SECOND_STAGE};    //麦罗产品
    public static final long YJM = 5l;   //涌金门
    public static final long JMJ = 6l;   //涌金门

    /**
     * 类别名称
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
