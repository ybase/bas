package com.ybase.bas.web;

import java.util.HashMap;
import java.util.Map;

import com.ybase.bas.constants.BasConstants;

import net.sf.json.JSONObject;

/**
 * bas 异步请求转发基类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public abstract class AbstractAjaxDispatch extends AbstractDispatch {
	private static final long serialVersionUID = 1L;
	private ThreadLocal<Map<String, Object>> jsonMap = new ThreadLocal<Map<String, Object>>();

	protected void wrappRequest() throws Exception {
		process();
		if (jsonMap.get() != null && jsonMap.get().size() > 0) {
			if(wrappOut.get() == null){
				wrappOut.set(wrappResp.get().getWriter());
			}
			writeJson(jsonMap.get());
			wrappOut.get().close();
			wrappOut.remove();
		}
	}

	private void writeJson(Map<String, Object> json) {
		JSONObject obj = JSONObject.fromObject(json);
		wrappOut.get().print(obj.toString());
		wrappOut.get().flush();
		wrappOut.get().close();
	}

	protected void setJsonMap(String key, Object value) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put(key, value);
	}

	/**
	 * 设置错误服务器响应状态<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setErrStatus() {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("status", BasConstants.DISP_STATUS_ERR);
	}

	/**
	 * 设置正确服务器响应状态<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	protected void setSuccStatus() {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("status", BasConstants.DISP_STATUS_CRR);
	}

	/**
	 * 设置页面弹出提示<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param tip
	 */
	protected void setTip(String tip) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("tip", tip);
	}

	/**
	 * 设置页面显示信息1<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param tip
	 */
	protected void setShow(String show) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("show", show);
	}

	/**
	 * 设置页面显示信息2<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param tip
	 */
	protected void setShow2(String show2) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("show2", show2);
	}

}
