package com.ybase.dorm.filter;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 用于检测用户是否登陆的过滤器，如果未登录，则重定向到指的登录页面
 * <p>
 * 配置参数
 * <p>
 * checkSessionKey 需检查的在 Session 中保存的关键字<br/>
 * redirectURL 如果用户未登录，则重定向到指定的页面，URL不包括 ContextPath<br/>
 * notCheckURLList 不做检查的URL列表，以分号分开，并且 URL 中不包括 ContextPath<br/>
 */
public class CheckLoginFilter implements Filter {
	private static final Logger log = Logger.getLogger(CheckLoginFilter.class.getName());
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private String sessionKey = null;
	private Vector<String> notCheckURLList = null;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		log.info("CheckLoginFilter[before]:[Request_URL]=" + request.getRequestURL());

		String isStatic = (String) request.getAttribute("static_resource");
		Object loginusr = session.getAttribute(sessionKey);

		if ("true".equals(isStatic)) {
			loginusr = new Object();
		} else {
			String requestURL = request.getRequestURL().toString();
			if (notCheckURLList != null && notCheckURLList.size() > 0) {
				for (int i = 0; i < notCheckURLList.size(); i++) {
					if (requestURL.indexOf(notCheckURLList.get(i).trim()) > 0) {
						loginusr = new Object();
						break;
					}
				}
			}
		}

		if (loginusr != null) {
			filterChain.doFilter(request, response);
		} else {
			response.setContentType("text/html;charset=GB18030");
			request.getRequestDispatcher(redirectURL).forward(request, response);
		}
		log.info("CheckLoginFilter[after]:[Request_URL]=" + request.getRequestURL());
		return;
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		StringTokenizer token = new StringTokenizer(notCheckURLListStr);
		notCheckURLList = new Vector<String>();
		while (token.hasMoreElements()) {
			notCheckURLList.add((String) token.nextElement());
		}
	}
}