package com.ybase.bas;

/**
 * bas 异常<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class BasException extends Exception {

	private static final long serialVersionUID = 1L;

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
}
