package com.taurus.zjbm.common.aop.aspect;

import cn.hutool.json.JSONUtil;
import com.taurus.zjbm.common.aop.annotation.RepeatRequest;
import com.taurus.zjbm.common.exception.BusinessException;
import com.taurus.zjbm.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 日志切面
 *
 * @author taurus
 * @date 2022/8/4
 **/
@Aspect
@Component
@Slf4j
public class RepeatAspect {

    @Autowired
    private RedisUtil redisService;

    @Pointcut("@annotation(com.taurus.zjbm.common.aop.annotation.RepeatRequest)")
    private void pt() {
    }

    @Before("pt()")
    public Object around(final JoinPoint joinPoint){

        log.info("重复提交校验");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method1 = signature.getMethod();
        RepeatRequest repeatRequest = method1.getAnnotation(RepeatRequest.class);
        boolean opened = repeatRequest.opened();
        String[] argNames = signature.getParameterNames();
        log.info("argNames"+JSONUtil.toJsonStr(argNames));
        if (opened) {
            String name = signature.getName();
            System.out.println("name = " + name);

            long time = repeatRequest.time();
            log.info("开启重复提交校验");
            HttpServletRequest request = attributes.getRequest();
            Object[] args = joinPoint.getArgs();
            String url = request.getRequestURI();
            String param = JSONUtil.toJsonStr(args);
            log.info("param:{}", param);
            final Map<String, String> map = new HashMap<>(1);
            map.put(url, param);
            String userId = "1";
            // 如果缓存中有这个url视为重复提交
            final String nowUrlParams = map.toString();
            log.info("重复提交校验修改map" + nowUrlParams);
            final String requestRepeatKey = userId.concat(url).concat("/").concat(nowUrlParams);
            final String msg = (String) redisService.get(requestRepeatKey);
            if (ObjectUtils.isEmpty(msg)) {
                log.info("重复提交校验修改不存在" + msg);
                // 当前key 不存在则允许请求
                time = ObjectUtils.isEmpty(time) ? 1 : time;
                //默认200毫秒
                redisService.set(requestRepeatKey, nowUrlParams, time);
            } else {
                // 当前key存在 判断值是否一致
                if (msg.equals(nowUrlParams)) {
                    log.info("重复提交校验修改存在" + msg);
                    throw new BusinessException("112", "请勿重复提交或者操作过于频繁");
                }
            }
        }
        return null;
    }
}
