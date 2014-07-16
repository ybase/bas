package com.ybase.dorm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CacheFilter implements Filter {
	private static final Logger log = Logger.getLogger(CacheFilter.class.getName());

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		log.info("CacheFilter[before]:[Request_URL]=" + request.getRequestURL());
		request.setAttribute("static_resource", "true");
		filterChain.doFilter(request, response);
		log.info("CacheFilter[after]:[Request_URL]=" + request.getRequestURL());
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}