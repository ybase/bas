package com.ybase.bas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * 格式化工具<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月27日<br/>
 */
public class FormatUtil {

	private static final Logger log = Logger.getLogger(FormatUtil.class.getName());

	public static String dtformat(String date, String time) {
		if (!BasUtil.isNullOrEmpty(date) && !BasUtil.isNullOrEmpty(time)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf2.format(sdf.parse(date + time));
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}
		return "未知时间";
	}

	public static String dformat(String date) {
		if (!BasUtil.isNullOrEmpty(date)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				return sdf2.format(sdf.parse(date));
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}
		return "未知日期";
	}

	public static String tformat(String time) {
		if (!BasUtil.isNullOrEmpty(time)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
				return sdf2.format(sdf.parse(time));
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}
		return "未知时间";
	}

	public static String contact(String theme, String desc) {
		StringBuffer str = new StringBuffer();
		if (!BasUtil.isNullOrEmpty(theme)) {
			if (theme.length() >= 12) {
				str.append(theme.subSequence(0, 12));
				str.append("...");
			} else {
				str.append(theme);
				for (int i = 0; i < 12 - theme.length(); i++) {
					str.append("&nbsp;");
				}
			}
			str.append("<br/>");
		}

		if (!BasUtil.isNullOrEmpty(desc)) {
			if (desc.length() > 15) {
				str.append(desc.subSequence(0, 15));
				str.append("......");
			} else {
				str.append(desc);
			}
		}
		return str.toString();
	}

	public static String covertAddStr(String picPath, String addStr) {
		if (picPath.indexOf(".") != -1) {
			String[] arr = picPath.split("\\.");
			return arr[0] + "_" + addStr + "." + arr[1];
		}
		return picPath;
	}

	public static String omitStr(String string, Integer length) {
		StringBuffer sb = new StringBuffer();
		if (byteLength(string) > length) {
			int count = 0;
			for (int i = 0; i < string.length(); i++) {
				char temp = string.charAt(i);
				if (Integer.toHexString(temp).length()

				== 4) {
					count += 2;
				} else {
					count++;
				}
				if (count < length - 3) {
					sb.append(temp);
				}
				if (count == length - 3) {
					sb.append(temp);
					break;
				}
				if (count > length - 3) {
					sb.append(" ");
					break;
				}
			}
			sb.append("...");
		} else {
			sb.append(string);
		}
		return sb.toString();
	}

	public static int byteLength(String string) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (Integer.toHexString(string.charAt

			(i)).length() == 4) {
				count += 2;
			} else {
				count++;
			}
		}
		return count;
	}
}
