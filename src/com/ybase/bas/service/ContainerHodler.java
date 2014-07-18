package com.ybase.bas.service;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ybase.bas.annotation.Component;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.util.MessageUtil;

/**
 * 容器句柄<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class ContainerHodler {
	private static final Logger log = Logger.getLogger(ContainerHodler.class.getName());
	/** dao缓存 */
	private static Hashtable<String, Object> daoCache = new Hashtable<String, Object>();

	public static void init() {
		try {
			InputStream is = ContainerHodler.class.getClassLoader().getResourceAsStream(BasConstants.SCAN_PACKAGE_PATH);
			Properties prop = new Properties();
			prop.load(is);
			Enumeration<?> props = prop.keys();
			while (props.hasMoreElements()) {
				String key = (String) props.nextElement();
				String value = (String) prop.get(key);
				Set<Class<?>> classes = BasUtil.getClasses(value);
				for (Class<?> clz : classes) {
					if (clz.isAnnotationPresent(Component.class)) {
						Component anot = clz.getAnnotation(Component.class);
						if (clz.getInterfaces().length > 0 && clz.getSuperclass().getSimpleName().equals(JdbcEntityDaoTemplate.class.getSimpleName())) {
							log.info(MessageUtil.getBasText("util-bas-scancompjdk", clz.getSimpleName()));
							daoCache.put(anot.value(), TransactionWrapper.decorate(clz.newInstance()));
						} else if (clz.getInterfaces().length == 0 && !clz.getSuperclass().getSimpleName().equals(JdbcEntityDaoTemplate.class.getSimpleName())) {
							log.info(MessageUtil.getBasText("util-bas-scancompcglib", clz.getSimpleName()));
							daoCache.put(anot.value(), TransactionCglib.getInstance(clz.newInstance()));
						}
					}
				}
				log.info(MessageUtil.getBasText("util-bas-scanpkgover", value));
			}
			log.info(MessageUtil.getBasText("service-container-initsucc"));
		} catch (Exception e) {
			log.info(MessageUtil.getBasText("service-container-initfail"));
			log.error(e.getMessage(), e);
		}
	}

	public static void destory() {
		daoCache.clear();
	}

	/**
	 * 根据组件名称获取Component组件实例<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param compName
	 *            组件名称<br/>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getComponent(String compName) {
		return (T) daoCache.get(compName);
	}
}
