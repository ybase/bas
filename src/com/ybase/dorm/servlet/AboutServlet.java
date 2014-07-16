package com.ybase.dorm.servlet;

import com.ybase.bas.web.AbstractCommonDispatch;

/**
 * Some things<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class AboutServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		setRUrl("aboutDorm.jsp");
	}

}
