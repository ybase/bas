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
		wrappReq.get().setCharacterEncoding("utf-8");
		wrappResp.get().setCharacterEncoding("GB18030");
		wrappResp.get().setContentType("text/html");
		process();
		if (jsonMap.get() != null && jsonMap.get().size() > 0) {
			if(wrappOut.get() == null){
				wrappOut.set(wrappResp.get().getWriter());
			}
			setJson(jsonMap.get());
			wrappOut.get().close();
			wrappOut.remove();
		}
	}

	private void setJson(Map<String, Object> json) {
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

	protected void setErrStatus() {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("status", BasConstants.DISP_STATUS_ERR);
	}

	protected void setSuccStatus() {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("status", BasConstants.DISP_STATUS_CRR);
	}

	protected void setTip(String tip) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("tip", tip);
	}

	protected void setShow(String show) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("show", show);
	}

	protected void setShow2(String show2) {
		if (jsonMap.get() == null) {
			jsonMap.set(new HashMap<String, Object>());
		}
		jsonMap.get().put("show2", show2);
	}

}
