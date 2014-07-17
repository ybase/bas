package com.ybase.bas.web;

import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.util.BasUtil;

/**
 * bas 表单提交请求转发基类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public abstract class AbstractCommonDispatch extends AbstractDispatch {

	private static final long serialVersionUID = 1L;
	/** 转发url */
	private ThreadLocal<String> durl = new ThreadLocal<String>();
	/** 重定向url */
	private ThreadLocal<String> rurl = new ThreadLocal<String>();

	protected void wrappRequest() throws Exception {
		process();
		if (durl != null && !BasUtil.isNullOrEmpty(durl.get())) {
			dispatchURL(durl.get());
		} else if (rurl != null && !BasUtil.isNullOrEmpty(rurl.get())) {
			redirectURL(rurl.get());
		}
	}

	/**
	 * 设置页面提示信息<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param tip
	 */
	protected void setTip(Object tip) {
		wrappReq.get().setAttribute("tip", tip);
	}

	/**
	 * 设置错误服务器响应状态<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setErrStatus() {
		wrappReq.get().setAttribute("status", BasConstants.DISP_STATUS_ERR);
	}

	/**
	 * 设置正确服务器响应状态<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setCrrStatus() {
		wrappReq.get().setAttribute("status", BasConstants.DISP_STATUS_CRR);
	}

	/**
	 * 设置Request attr值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param attr
	 * @param value
	 */
	protected void setReqAttr(String attr, Object value) {
		wrappReq.get().setAttribute(attr, value);
	}

	/**
	 * 设置Session attr值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param attr
	 * @param value
	 */
	protected void setSesAttr(String attr, Object value) {
		wrappSession.get().setAttribute(attr, value);
	}

	/**
	 * 设置转发URL值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param attr
	 * @param value
	 */
	protected void setDUrl(String url) {
		durl.set(url);
	}

	/**
	 * 设置重定向URL值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param url
	 */
	protected void setRUrl(String url) {
		rurl.set(url);
	}

	/**
	 * 设置转发形式页面刷新<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setDOwnUrl() {
		String file = wrappReq.get().getRequestURI();
		if (wrappReq.get().getQueryString() != null) {
			file += '?' + wrappReq.get().getQueryString();
		}
		file = file.replaceAll(wrappReq.get().getContextPath().trim() + "/", "");
		setDUrl(file);
	}

	/**
	 * 设置重定向形式页面刷新<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setROwnUrl() {
		String file = wrappReq.get().getRequestURI();
		if (wrappReq.get().getQueryString() != null) {
			file += '?' + wrappReq.get().getQueryString();
		}
		file = file.replaceAll(wrappReq.get().getContextPath().trim() + "/", "");
		setRUrl(file);
	}
}
