package com.taurus.zjbm.common.aop.aspect;

import cn.hutool.json.JSONUtil;
import com.taurus.zjbm.common.aop.annotation.LogAnnotation;
import com.taurus.zjbm.utils.HttpContextUtils;
import com.taurus.zjbm.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 日志切面
 *
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 日志切点
     */
    @Pointcut("@annotation(com.taurus.zjbm.common.aop.annotation.LogAnnotation)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executeTime = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, executeTime);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long executeTime) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("==================== log start ====================");

        String module = logAnnotation.module();
        String operation = logAnnotation.operation();
        log.info("module:{}", module);
        log.info("operation:{}", operation);

        String methodName = joinPoint.getTarget().getClass().getName().concat(".").concat(signature.getName());
        log.info("request method:{}", methodName);

        Object[] args = joinPoint.getArgs();
        String param = JSONUtil.toJsonStr(args);
        log.info("param:{}", param);

        String ip = IpUtils.getIp(HttpContextUtils.getHttpServletRequest());
        log.info("ip:{}", ip);

        log.info("execute time:{} ms", executeTime);

        log.info("==================== log end ====================");

    }
}
