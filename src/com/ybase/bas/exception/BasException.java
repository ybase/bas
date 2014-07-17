package com.ybase.bas.exception;

import org.apache.log4j.Logger;

import com.ybase.bas.util.BasUtil;
import com.ybase.bas.util.MessageUtil;

/**
 * bas 异常<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class BasException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BasException.class.getName());

	public BasException() {
		super();
	}

	public BasException(String msg) {
		super(msg);
	}

	public BasException(String msg, Throwable thr) {
		super(msg, thr);
	}

	public BasException(Throwable thr) {
		super(thr);
	}

	public BasException(String msgCode, Object... args) {
		super(msgCode);
		String message = MessageUtil.getText(msgCode, args);
		if (BasUtil.isNullOrEmpty(message)) {
			message = msgCode;
			log.error(String.format("BasException -- %s", message));
		} else {
			log.error(String.format("BasException[%s] -- %s", msgCode, message));
		}
	}
}
