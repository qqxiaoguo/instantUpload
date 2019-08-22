package com.lpjj.application.xin;/**
 * guo
 *   gjh
 *  时间：2019/8/22-9:27  
 */

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class ApplicationContextAwareImpl implements ApplicationContextAware {


    /**
     * 实现该接口用来初始化应用程序上下文
     * 该接口会在执行完毕@PostConstruct的方法后被执行
     * <p>
     * 接着，会进行Mapper地址扫描并加载，就是RequestMapping中指定的那个路径
     *
     * @param applicationContext 应用程序上下文
     * @throws BeansException beans异常
     */
    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        log.info("应用程序上下文 ： [{}]", "开始初始化");
        ApplicationContextUtils.applicationContext = applicationContext;


        log.info("应用程序上下文 getId ： [{}]", applicationContext.getId());
        log.info("应用程序上下文 getApplicationName ： [{}]", applicationContext.getApplicationName());
        //  log.info("应用程序上下文 getAutowireCapableBeanFactory ： [{}]",applicationContext.getAutowireCapableBeanFactory());
        //  log.info("应用程序上下文 getDisplayName ： [{}]",applicationContext.getDisplayName());
        //  log.info("应用程序上下文 getParent ： [{}]",applicationContext.getParent());
        log.info("应用程序上下文 getStartupDate ： [{}]", applicationContext.getStartupDate());
        //  log.info("应用程序上下文 getEnvironment ： [{}]",applicationContext.getEnvironment());

        log.info("应用程序上下文 ： [{}]", "初始化完成");
    }
}




