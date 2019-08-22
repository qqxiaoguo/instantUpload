package com.lpjj.application.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //切面
@Component//不知道的模块
@Slf4j//slf4j注解
public class TimeAspect {

    // 修正Timer注解的全局唯一限定符
    @Pointcut("@annotation(com.lpjj.application.annotation.Timer)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取目标类名称
        String clazzName = joinPoint.getTarget().getClass().getName();

        // 获取目标类方法名称
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();
        log.info("{}: {}: start...", clazzName, methodName);

        // 调用目标方法
        Object result = joinPoint.proceed();


        long time = System.currentTimeMillis() - start;
        log.info("{}: {}: : end... cost time: {} ms", clazzName, methodName, time);

        return result;
    }
}