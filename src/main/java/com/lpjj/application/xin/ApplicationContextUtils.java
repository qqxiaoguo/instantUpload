package com.lpjj.application.xin;/**
 * guo
 *   gjh
 *  时间：2019/8/22-9:31  
 */

import org.springframework.context.ApplicationContext;


public class ApplicationContextUtils {


    public static ApplicationContext applicationContext;

    /**
     * 通过名称获取bean
     */
    public static Object get(String name) {
        return applicationContext.getBean(name);
    }


    /**
     * 通过类型获取bean
     */
    public static Object get(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 判断某个bean是不是存在
     */
    public static boolean has(String name) {
        return applicationContext.containsBean(name);
    }


}