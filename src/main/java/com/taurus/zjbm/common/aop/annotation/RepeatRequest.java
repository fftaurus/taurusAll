package com.taurus.zjbm.common.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author taurus
 * @Date 2022/8/4
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatRequest {

    /**
     * 是否开启 默认false 不开启
     * @return
     */
    boolean opened() default false;

    /**
     * 等待时间 单位 秒 s
     * @return
     */
    long time() default 1;
}