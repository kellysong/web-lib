package com.sjl.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * spring bean获取帮助类
 * 解决线程中注入bean失败问题
 * @author Kelly
 * @version 1.0.0
 * @filename SpringBeanManager.java
 * @time 2017-6-14 下午12:54:49
 * @copyright(C) 2017 song
 */
@Service
public class SpringBeanManager implements BeanFactoryAware {

	private static BeanFactory beanFactory = null;

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	/**
	 * 通过name获取bean实例
	 * 
	 * @param beanName id或者name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) beanFactory.getBean(beanName);
	}

	/**
	 * 通过bean的class获取bean实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return (T) beanFactory.getBean(clazz);
	}

	/**
	 *  通过bean的name 和 class获取bean实例
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return beanFactory.getBean(beanName, clazz);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringBeanManager.beanFactory = beanFactory;
	}
}
