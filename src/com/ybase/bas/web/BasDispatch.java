package com.ybase.bas.web;

import org.apache.log4j.Logger;

/**
 * 请求转发接口<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public interface BasDispatch {
	public static final Logger log = Logger.getLogger(BasDispatch.class);
	
	/**
	 * 请求转发实现<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @throws Exception
	 */
	public void process() throws Exception;
}
