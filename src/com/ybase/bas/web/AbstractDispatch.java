package com.ybase.bas.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ybase.bas.constants.BasConstants;

/**
 * bas 请求转发公共抽象基类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public abstract class AbstractDispatch extends HttpServlet implements BasDispatch {

	private static final long serialVersionUID = 1L;
	/** Request Wrapper */
	protected ThreadLocal<HttpServletRequest> wrappReq = new ThreadLocal<HttpServletRequest>();
	/** Response Wrapper */
	protected ThreadLocal<HttpServletResponse> wrappResp = new ThreadLocal<HttpServletResponse>();
	/** Session Wrapper */
	protected ThreadLocal<HttpSession> wrappSession = new ThreadLocal<HttpSession>();
	/** PrintWriter Wrapper */
	protected ThreadLocal<PrintWriter> wrappOut = new ThreadLocal<PrintWriter>();

	protected abstract void wrappRequest() throws Exception;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		wrappReq.set(request);
		wrappResp.set(response);
		wrappSession.set(request.getSession());
		try {
			wrappRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		wrappReq.set(request);
		wrappResp.set(response);
		wrappSession.set(request.getSession());
		wrappOut.set(response.getWriter());
		try {
			wrappRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取表单数据<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param attr
	 * @return
	 */
	protected String getPar(String attr) {
		return wrappReq.get().getParameter(attr);
	}

	/**
	 * 获取Request,Session,Application属性值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param scope
	 * @param attr
	 * @return
	 */
	protected Object getAttr(int scope, String attr) {
		switch (scope) {
		case BasConstants.SCOPE_REQ:
			return wrappReq.get().getAttribute(attr);
		case BasConstants.SCOPE_RESP:
			// return wrappResp.get();
		case BasConstants.SCOPE_SESSION:
			return wrappSession.get().getAttribute(attr);
		case BasConstants.SCOPE_APPL:
			return wrappReq.get().getAttribute(attr);
		default:
			return null;
		}
	}

	/***
	 * 设置Request,Session,Application属性值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param scope
	 * @param attr
	 * @param value
	 */
	protected void setAttr(int scope, String attr, Object value) {
		switch (scope) {
		case BasConstants.SCOPE_REQ:
			wrappReq.get().setAttribute(attr, value);
		case BasConstants.SCOPE_RESP:
			// wrappOut.get().print(value);
		case BasConstants.SCOPE_SESSION:
			wrappReq.get().setAttribute(attr, value);
		case BasConstants.SCOPE_APPL:
			wrappSession.get().setAttribute(attr, value);
		default:
			;
		}
	}

	/**
	 * 转发URL<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void dispatchURL(String url) throws ServletException, IOException {
		wrappReq.get().getRequestDispatcher(url).forward(wrappReq.get(), wrappResp.get());
	}

	/**
	 * 重定向URL<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param url
	 * @throws IOException
	 */
	protected void redirectURL(String url) throws IOException {
		wrappResp.get().sendRedirect(url);
	}
}
