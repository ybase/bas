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
	/** 转发url	*/
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
	
	protected void setTip(Object tip){
		wrappReq.get().setAttribute("tip", tip);
	}
	
	protected void setErrStatus(){
		wrappReq.get().setAttribute("status", BasConstants.DISP_STATUS_ERR);
	}
	
	protected void setCrrStatus(){
		wrappReq.get().setAttribute("status", BasConstants.DISP_STATUS_CRR);
	}

	protected void setReqAttr(String attr, Object value) {
		wrappReq.get().setAttribute(attr, value);
	}

	protected void setSesAttr(String attr, Object value) {
		wrappSession.get().setAttribute(attr, value);
	}

	protected void setDUrl(String url) {
		durl.set(url);
	}

	protected void setRUrl(String url) {
		rurl.set(url);
	}

	protected void setDOwnUrl() {
		String file = wrappReq.get().getRequestURI();
		if (wrappReq.get().getQueryString() != null) {
			file += '?' + wrappReq.get().getQueryString();
		}
		file = file.replaceAll(wrappReq.get().getContextPath().trim() + "/", "");
		setDUrl(file);
	}

	protected void setROwnUrl() {
		String file = wrappReq.get().getRequestURI();
		if (wrappReq.get().getQueryString() != null) {
			file += '?' + wrappReq.get().getQueryString();
		}
		file = file.replaceAll(wrappReq.get().getContextPath().trim() + "/", "");
		setRUrl(file);
	}
}
