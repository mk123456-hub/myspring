package com.spring.core;

/**
 * @Author Administrator
 * @create 2020/12/17 0017 13:30
 */
public interface ApplicationContext {
    Object getBean(String className);
}
