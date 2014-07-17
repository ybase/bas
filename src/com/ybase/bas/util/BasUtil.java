package com.ybase.bas.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * bas 工具类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class BasUtil {

	private static final Logger log = Logger.getLogger(BasUtil.class.getName());

	final static int BUFFER_SIZE = 4096;

	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static <T> boolean isNullOrEmpty(List<T> list) {
		if (list == null || list.isEmpty() || list.size() == 0) {
			return true;
		}
		return false;
	}

	public static <T> T getOne(List<T> list) {
		if (!isNullOrEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public static String getDate8Str() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	public static String getTime9Str() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		return sdf.format(new Date());
	}

	/**
	 * 将Blob对象转换成字符串<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param blob
	 *            Blob对象<br/>
	 * @return
	 */
	public static String convertBlobToString(Blob blob) {
		String result = "";
		try {
			ByteArrayInputStream msgContent = (ByteArrayInputStream) blob.getBinaryStream();
			byte[] byte_data = new byte[msgContent.available()];

			msgContent.read(byte_data, 0, byte_data.length);
			result = new String(byte_data, "GB18030");// TODO
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 文件拷贝<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param s
	 *            原文件<br/>
	 * @param t
	 *            目标文件<br/>
	 */
	public static void fileChannelCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取max-min中的自然数<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param max
	 *            最大数<br/>
	 * @param min
	 *            最小数<br/>
	 * @return
	 */
	public static int randomOne(int max, int min) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 用空格代替字串中的连续存在换行符、制表符<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param str
	 *            字串<br/>
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll(" ");
		}
		return dest;
	}

	/**
	 * trim字串两端空格符<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (!isNullOrEmpty(str)) {
			return replaceBlank(str).trim();
		} else {
			return "";
		}
	}

	/**
	 * 将byte数组转换成自定字符编码字串<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param in
	 *            字节数组<br/>
	 * @parma encoding 字符编码<br/>
	 * @return
	 * @throws Exception
	 */
	public static String byteTOString(byte[] in, String encoding) throws Exception {
		InputStream is = byteTOInputStream(in);
		return InputStreamTOString(is, encoding);
	}

	/**
	 * 将byte数组转换成字串<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param in
	 *            字节数组<br/>
	 * @return
	 * @throws Exception
	 */
	public static String byteTOString(byte[] in) throws Exception {
		InputStream is = byteTOInputStream(in);
		return InputStreamTOString(is, "UTF-8");
	}

	public static InputStream byteTOInputStream(byte[] in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	public static String InputStreamTOString(InputStream in, String encoding) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), encoding);
	}

	/**
	 * MD5 加密<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param inStr
	 *            待加密字串<br/>
	 * @return
	 */
	public static String md5Encrypt(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("MD5");
			// 可以选中其他的算法如SHA
			byte[] digest = md.digest(inStr.getBytes());
			// 返回的是byet[]，要转化为String存储比较方便
			outStr = byteToHexStr(digest);
		} catch (NoSuchAlgorithmException nsae) {
			log.error(nsae.getMessage(), nsae);
		}
		return outStr;
	}

	public static String byteToHexStr(byte[] digest) {
		String str = "";
		String tempStr = "";
		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}

		return str.toLowerCase();
	}

}