package com.flowring.cn.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    /**
     * Set the applicationContext value.
     *
     * @param appContext new value
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        ctx = appContext;
    }

    /**
     * Returns the applicationContext object.
     *
     * @return applicationContext object
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}