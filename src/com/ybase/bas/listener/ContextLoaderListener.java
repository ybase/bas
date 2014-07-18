package com.ybase.bas.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.log4j.PropertyConfigurator;

import com.ybase.bas.service.ContainerHodler;
import com.ybase.bas.util.MessageUtil;

/**
 * WEB 应用初始化<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class ContextLoaderListener implements ServletContextListener {

	private static final Log log = LogFactoryImpl.getLog(ContextLoaderListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ContainerHodler.destory();
		log.info(MessageUtil.getBasText("listener-container-destorysucc"));
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		PropertyConfigurator.configure(ContextLoaderListener.class.getClassLoader().getResourceAsStream("/log4j.properties"));
		MessageUtil.init();
		ContainerHodler.init();
		log.info(MessageUtil.getBasText("listener-container-initsucc"));
	}

}
