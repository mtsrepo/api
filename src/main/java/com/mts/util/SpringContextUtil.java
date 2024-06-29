package com.mts.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class SpringContextUtil implements ApplicationContextAware, ServletContextAware{
	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setServletContext(ServletContext srvletContext) {
		servletContext = srvletContext;
	}
	
	public static ServletContext getServletContext() {
		return servletContext;
	}


	

}
