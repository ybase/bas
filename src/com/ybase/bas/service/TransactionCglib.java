package com.ybase.bas.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.ybase.bas.jdbc.ConnectionManager;
import com.ybase.bas.util.BasUtil;

public final class TransactionCglib {
	private static final String MANGET_IMPL = "Impl";
	private static final Logger log = Logger.getLogger(TransactionCglib.class);

	/**
	 * 创建代理对象
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static Object getInstance(Object delegate) throws Exception {
		XAWrapperHandler handler = new XAWrapperHandler();
		Object obj = handler.createProxy(delegate);
		if (obj != null) {
			Field[] services = obj.getClass().getFields();
			for (Field service : services) {
				if (service.isAnnotationPresent((Class<? extends Annotation>) delegate.getClass().getClassLoader().loadClass("com.ybase.bas.annotation.Service"))) {
					try {
						service.setAccessible(true);
						String clzName = convertImplPackage(service.getType().getCanonicalName());
						service.set(obj, Class.forName(clzName).newInstance());
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}
		return obj;
	}

	private static String convertImplPackage(String daoName) {
		String splits[] = daoName.split("\\.");
		StringBuffer implName = new StringBuffer();
		int count = 0;
		for (String split : splits) {
			if (count == splits.length - 1) {
				split = convertFirstCase(MANGET_IMPL) + "." + split + MANGET_IMPL;
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
	static final class XAWrapperHandler implements MethodInterceptor {

		private Object delegate;

		public Object createProxy(Object target) {
			this.delegate = target;
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(this.delegate.getClass());// 设置代理目标
			enhancer.setCallback(this);// 设置回调
			enhancer.setClassLoader(target.getClass().getClassLoader());
			return enhancer.create();
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			Object result = null;
			Connection con = ConnectionManager.getInstance().getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "Connection[ " + con + " ], Where[TransactionCglib>>Delegate>>" + method.getName() + "]");
			try {
				// 开始一个事务
				con.setAutoCommit(false);
				// 调用原始业务对象的业务方法
				result = proxy.invokeSuper(obj, args);
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
