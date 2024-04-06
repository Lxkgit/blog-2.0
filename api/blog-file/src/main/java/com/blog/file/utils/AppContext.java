package com.blog.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/12 11:19
 */

@Slf4j
public abstract class AppContext implements ApplicationContextAware {
    
    private static ApplicationContext context = null;
    private static Boolean serviceStartCompleted = false;

    public AppContext() {
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (AppContext.context == null) {
            setContext(context, false);
            log.info("Spring init successfully");
        }

    }

    public static <T> T getBean(String name) {
        if (context == null) {
            throw new IllegalStateException("applicaitonContext inject failure，please restart the service");
        } else {
            try {
                return (T) context.getBean(name);
            } catch (BeansException var2) {
                log.error(var2.getMessage(), var2);
                return null;
            }
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            throw new IllegalStateException("applicaitonContext inject failure，please restart the service");
        } else {
            try {
                return context.getBean(clazz);
            } catch (BeansException var2) {
                log.error(var2.getMessage(), var2);
                return null;
            }
        }
    }

    public static <T> void setBean(Class<T> clazz, String beanName) {
        if (!context.containsBean(beanName)) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            registerBean(beanName, beanDefinitionBuilder.getRawBeanDefinition());
        }

    }

    public static boolean isStartCompleted() {
        return serviceStartCompleted;
    }

    public static void setServiceStartCompleted(Boolean serviceStartCompleted) {
        AppContext.serviceStartCompleted = serviceStartCompleted;
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("application Context inject failure，please restart the service");
        } else {
            return context;
        }
    }

    public static void setContext(ApplicationContext context, boolean isStartCompleted) {
        if (context != null) {
            AppContext.context = context;
            serviceStartCompleted = serviceStartCompleted || isStartCompleted;
            log.info("Set ApplicationContext successfully");
        }

    }

    private static void registerBean(String beanName, BeanDefinition beanDefinition) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry)configurableApplicationContext.getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}
