package com.ydzb.core.repository.support.annotation;

import com.ydzb.core.repository.callback.SearchCallback;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 覆盖默认的根据条件查询数据
 *
 * @author Zhang Kaitao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchableQuery {

    /**
     * 覆盖默认的查询所有ql
     *
     * @return
     */
    String findAllQuery() default "";

    /**
     * 覆盖默认的统计所有ql
     *
     * @return
     */
    String countAllQuery() default "";

    /**
     * 给ql拼条件及赋值的回调类型
     *
     * @return com.sishuok.es.common.repository.callback.SearchCallback子类
     */
    Class<? extends SearchCallback> callbackClass() default SearchCallback.class;


    QueryJoin[] joins() default {};


}
