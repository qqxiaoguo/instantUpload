package com.lpjj.application.xin;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect//切面
@Component
public class LogProt {
    @Pointcut("@annotation(com.lpjj.application.xin.Log)") //切点有接口
   private void pincut(){

    }

    //连接点就是连接方法
    @Around("pincut()")
    public  Object arround(ProceedingJoinPoint point) {
        System.out.println("方法前面执行的内容");
        System.out.println("获取方法名称"+point.getSignature().getName());
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("方法后面执行的内容");

           return   proceed;
    }
}
