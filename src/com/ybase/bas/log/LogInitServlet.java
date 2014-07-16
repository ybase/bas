package com.ybase.bas.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.PropertyConfigurator;

/**
 * 日志初始化<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class LogInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	public void init() throws ServletException {
		String fileName = this.getInitParameter("log4jPro");
		String file = this.getServletContext().getRealPath("/") + fileName;

		if (fileName != null) {
			System.out.println("Log4j开始启动 : ----------------");
			PropertyConfigurator.configure(file);
		} else {
			System.out.println("Log4j启动失败!");
		}
	}

}
