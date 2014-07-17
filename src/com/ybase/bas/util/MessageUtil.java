package com.ybase.bas.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ybase.bas.constants.BasConstants;

/**
 * 消息工具类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class MessageUtil {

	private static final Logger log = Logger.getLogger(MessageUtil.class.getName());
	private static Properties commProp = new Properties();
	private static Properties webProp = new Properties();
	private static Properties basProp = new Properties();

	static {
		System.out.println("Load Message Start.");
		InputStream is1 = MessageUtil.class.getResourceAsStream(BasConstants.BAS_MESSAGE_URL);
		InputStream is2 = MessageUtil.class.getClassLoader().getResourceAsStream(BasConstants.WEB_MESSAGE_URL);
		InputStream is3 = MessageUtil.class.getClassLoader().getResourceAsStream(BasConstants.COMMON_MESSAGE_URL);
		try {
			basProp.load(is1);
		} catch (IOException e) {
			log.info("Message[bas] propertie file is not load.");
		}

		try {
			commProp.load(is3);
		} catch (Throwable e) {
			log.info("Message[common] propertie file is not load.");
		}

		try {
			webProp.load(is2);
		} catch (Throwable e) {
			log.info("Message[web] propertie file is not load.");
		}

	}

	/**
	 * 根据key 获取消息，查找顺序如下<br/>
	 * 1.bas-message.properties中查找<br/>
	 * 2.common-message.properties中查找<br/>
	 * 3.web-message.properties中查找<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param msgCode
	 * @param args
	 * @return
	 */
	public static String getText(String msgCode, Object... args) {
		if (BasUtil.isNullOrEmpty(msgCode)) {
			log.debug("msgCode is null");
			return "";
		}

		String value = (String) basProp.get(msgCode);
		if (BasUtil.isNullOrEmpty(value)) {
			value = (String) commProp.get(msgCode);
		}

		if (BasUtil.isNullOrEmpty(value)) {
			value = (String) webProp.get(msgCode);
		}

		if (BasUtil.isNullOrEmpty(value)) {
			log.debug("Message[bas,web,common] key[" + msgCode + "] is not value.");
			return "";
		}

		return MessageFormat.format(value, args);
	}

	/**
	 * 根据key 获取消息，查找bas-message.properties<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param msgCode
	 * @param args
	 * @return
	 */
	public static String getBasText(String msgCode, Object... args) {
		if (BasUtil.isNullOrEmpty(msgCode)) {
			log.debug("msgCode is null");
			return "";
		}

		String value = (String) basProp.get(msgCode);
		if (BasUtil.isNullOrEmpty(value)) {
			log.debug("Message[bas] key[" + msgCode + "] is not value.");
			return "";
		}

		return MessageFormat.format(value, args);
	}

	/**
	 * 根据key 获取消息，查找web-message.properties<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param msgCode
	 * @param args
	 * @return
	 */
	public static String getWebText(String msgCode, Object... args) {
		if (BasUtil.isNullOrEmpty(msgCode)) {
			log.debug("msgCode is null");
			return "";
		}

		String value = (String) webProp.get(msgCode);
		if (BasUtil.isNullOrEmpty(value)) {
			log.debug("Message[web] key[" + msgCode + "] is not value.");
			return "";
		}

		return MessageFormat.format(value, args);
	}

	/**
	 * 根据key 获取消息，查找common-message.properties<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param msgCode
	 * @param args
	 * @return
	 */
	public static String getCommText(String msgCode, Object... args) {
		if (BasUtil.isNullOrEmpty(msgCode)) {
			log.debug("msgCode is null");
			return "";
		}

		String value = (String) commProp.get(msgCode);
		if (BasUtil.isNullOrEmpty(value)) {
			log.debug("Message[common] key[" + msgCode + "] is not value.");
			return "";
		}

		return MessageFormat.format(value, args);
	}
}
