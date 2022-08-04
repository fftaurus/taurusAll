package com.taurus.zjbm.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 日志注解
 *
 * @author taurus
 * @date 2022/8/4
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 模块
     */
   String module();

    /**
     * 操作
     */
   String operation();


}
