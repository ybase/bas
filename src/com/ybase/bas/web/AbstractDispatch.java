package com.ybase.bas.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.vo.DrUser;

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

	protected String getLoginUsr() {
		if (wrappReq.get() != null) {
			HttpSession session = wrappSession.get();
			Object obj = session.getAttribute("loginusr");
			if (obj != null) {
				DrUser usr = (DrUser) obj;
				return usr.getName();
			}
		}
		return "";
	}

	protected DrUser getLoginUsrObj() {
		if (wrappReq.get() != null) {
			HttpSession session = wrappSession.get();
			Object obj = session.getAttribute("loginusr");
			if (obj != null) {
				return (DrUser) obj;
			}
		}
		return null;
	}

	protected String getPar(String attr) {
		return wrappReq.get().getParameter(attr);
	}

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

	protected void dispatchURL(String url) throws ServletException, IOException {
		wrappReq.get().getRequestDispatcher(url).forward(wrappReq.get(), wrappResp.get());
	}

	protected void redirectURL(String url) throws IOException {
		wrappResp.get().sendRedirect(url);
	}

	protected <T> void setCommonVO(T t) {
		if (t != null) {
			try {
				if (t.getClass().getDeclaredField("crTime") != null) {
					t.getClass().getMethod("setCrTime", String.class).invoke(t, BasUtil.getTime9Str());
				}

				if (t.getClass().getDeclaredField("crDate") != null) {
					t.getClass().getMethod("setCrDate", String.class).invoke(t, BasUtil.getDate8Str());
				}

				DrUser user = getLoginUsrObj();
				if (user != null) {
					if (t.getClass().getDeclaredField("crUsr") != null) {
						t.getClass().getMethod("setCrUsr", Integer.class).invoke(t, user.getId());
					}

					if (t.getClass().getDeclaredField("usrName") != null) {
						t.getClass().getMethod("setUsrName", String.class).invoke(t, user.getName());
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}
	}
}
