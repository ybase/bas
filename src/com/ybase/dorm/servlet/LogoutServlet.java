package com.ybase.dorm.servlet;

import com.ybase.bas.web.AbstractAjaxDispatch;

/**
 * ע��<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class LogoutServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		wrappSession.get().invalidate();
	}

}
