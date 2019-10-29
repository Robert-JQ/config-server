package com.newcapec.configserver.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jqq
 * @description spring容器工具类
 * @date 2019/5/24 19:02
 * @copyright 2016-2019 新开普 - Powered By 研发中心
 * @version 1.0
 */
@Component
public final class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil() {

    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext=applicationContext;
    }

    /**
     * @author jqq
     * @description 获取spring上下文
     * @date 2019/5/21 21:08
     * @return org.springframework.context.ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * @author jqq
     * @description 根据类型获取bean
     * @date 2019/5/21 21:08
     * @param t
     * @return T
     */
    public static <T> T getBean(Class<T> t){
        return applicationContext.getBean(t);
    }

    /**
     * @author jqq
     * @description 根据名字获取bean
     * @date 2019/5/21 21:05
     * @param beanName
     * @return T
     */
    public static <T> T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }
}
