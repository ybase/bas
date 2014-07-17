package com.ybase.bas.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ybase.bas.annotation.Service;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.ConnectionManager;
import com.ybase.bas.util.BasUtil;

/**
 * Jdk事物代理类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public final class TransactionWrapper {
	
	private static final Logger log = Logger.getLogger(TransactionWrapper.class);

	/**
	 * 装饰原始的业务代表对象，返回一个与业务代表对象有相同接口的代理对象
	 * 
	 * @throws Exception
	 */
	public static Object decorate(Object delegate) throws Exception {
		if (delegate != null) {
			Field[] services = delegate.getClass().getDeclaredFields();
			for (Field service : services) {
				if (service.isAnnotationPresent(Service.class)) {
					try {
						service.setAccessible(true);
						String clzName = convertImplPackage(service.getType().getCanonicalName());
						service.set(delegate, Class.forName(clzName).newInstance());
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), new XAWrapperHandler(delegate));
	}

	private static String convertImplPackage(String daoName) {
		String splits[] = daoName.split("\\.");
		StringBuffer implName = new StringBuffer();
		int count = 0;
		for (String split : splits) {
			if (count == splits.length - 1) {
				split = convertFirstCase(BasConstants.MANGET_IMPL) + "." + split + BasConstants.MANGET_IMPL;
				implName.append(split);
			} else {
				implName.append(split + ".");
			}
			count++;
		}
		return implName.toString();
	}

	private static String convertFirstCase(String str) {
		StringBuffer rst = new StringBuffer();
		if (!BasUtil.isNullOrEmpty(str)) {
			rst.append(str.substring(0, 1).toLowerCase());
			rst.append(str.substring(1));
		}
		return rst.toString();
	}

	// 动态代理技术
	static final class XAWrapperHandler implements InvocationHandler {
		private final Object delegate;

		XAWrapperHandler(Object delegate) {
			this.delegate = delegate;
		}

		// 简单起见，包装业务代表对象所有的业务方法
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = null;
			Connection con = ConnectionManager.getInstance().getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "Connection[ " + con + " ], Where[TransactionWrapper>>Delegate>>" + method.getName() + "]");
			try {
				// 开始一个事务
				con.setAutoCommit(false);
				// 调用原始业务对象的业务方法
				result = method.invoke(delegate, args);
				con.commit(); // 提交事务
				con.setAutoCommit(true);
			} catch (Throwable t) {
				// 回滚
				con.rollback();
				con.setAutoCommit(true);
				throw t;
			} finally {
				ConnectionManager.getInstance().freeConnection("", con);
			}
			return result;
		}
	}
}
