package com.ybase.bas.service;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ybase.bas.annotation.Component;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;

public class ContainerHodler {
	private static final Logger log = Logger.getLogger(ContainerHodler.class.getName());
	private static Hashtable<String, Object> daoCache = new Hashtable<String, Object>();

	static {
		InputStream is = ContainerHodler.class.getClassLoader().getResourceAsStream(BasConstants.SCAN_PACKAGE_PATH);
		Properties prop = new Properties();
		try {
			prop.load(is);
			Enumeration<?> props = prop.keys();
			while (props.hasMoreElements()) {
				String key = (String) props.nextElement();
				String value = (String) prop.get(key);
				@SuppressWarnings("unchecked")
				Class<Package> pkg = (Class<Package>) Class.forName(value);
				Class<?>[] clzs = pkg.getClasses();
				for (Class<?> clz : clzs) {
					if (clz.isAnnotationPresent(Component.class)) {
						Component anot = clz.getAnnotation(Component.class);
						Class<?>[] interfs = clz.getInterfaces();
						for (int i = 0; i < interfs.length; i++) {
							Class<?> interf = interfs[i];
							if (interf.getSimpleName().equals(JdbcEntityDaoTemplate.class.getSimpleName())) {
								daoCache.put(anot.value(), TransactionWrapper.decorate(clz.newInstance()));
								break;
							}
						}
						daoCache.put(anot.value(), TransactionCglib.getInstance(clz.newInstance()));
					}
				}
			}
		} catch (Exception e) {
			log.info("Service Container Init fail!");
			log.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getComponent(String compName) {
		return (T) daoCache.get(compName);
	}
}
