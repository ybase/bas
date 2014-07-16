package com.ybase.bas.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.bas.annotation.Column;
import com.ybase.bas.vo.BasVO;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.bas.DormConstant;

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

	public static Integer countRs(ResultSet rs) throws SQLException {
		Integer count = 0;
		if (rs != null) {
			while (rs.next()) {
				count = rs.getInt("num");
			}
		}
		return count;
	}

	public static <T> List<T> resultSet2VoProp(ResultSet rs, Class<T> clz) throws Exception {
		List<T> list = new ArrayList<T>();
		if (rs != null && clz != null) {
			try {
				if (BasVO.class.getName().equals(clz.getSuperclass().getName())) {
					while (rs.next()) {
						T vo = clz.newInstance();
						Field[] fields = clz.getDeclaredFields();
						for (Field f : fields) {
							if (f.isAnnotationPresent(Column.class)) {
								Column colAn = f.getAnnotation(Column.class);
								String setterMethodName = getSetterMethodName(f.getName());
								Object invokeParamVal = rs.getClass().getMethod(getGetterMethodName(colAn.type()), String.class).invoke(rs, colAn.name());
								if ("blob".equals(colAn.type())) {
									clz.getMethod(setterMethodName, f.getType()).invoke(vo, convertBlobToString((Blob) invokeParamVal));
								} else {
									clz.getMethod(setterMethodName, f.getType()).invoke(vo, invokeParamVal);
								}
							} else if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
								log.debug(String.format("VO Field[%s] 字段为static 或 final 修饰符所修饰，将被忽略", f.getName()));
							} else {
								log.error(String.format("VO Field[%s] 找不到对应column 注解", f.getName()));
								throw new BasException(String.format("VO Field[%s] 找不到对应column 注解", f.getName()));
							}
						}
						list.add(vo);
					}
				} else {
					log.error("VO 类型错误!");
					throw new BasException("VO 类型错误!");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(e.getMessage());
			}
		}

		if (!isNullOrEmpty(list)) {
			return list;
		}

		return null;
	}

	public static <T> List<Object> resultSet2ColProp(ResultSet rs, Class<T> clz, String loadCol) throws Exception {
		List<Object> list = new ArrayList<Object>();
		if (rs != null && clz != null) {
			try {
				if (BasVO.class.getName().equals(clz.getSuperclass().getName())) {
					while (rs.next()) {
						Field[] fields = clz.getDeclaredFields();
						for (Field f : fields) {
							if (f.isAnnotationPresent(Column.class)) {
								if (f.getName().equals(loadCol)) {
									Column colAn = f.getAnnotation(Column.class);
									Object invokeParamVal = rs.getClass().getMethod(getGetterMethodName(colAn.type()), String.class).invoke(rs, colAn.name());
									if ("blob".equals(colAn.type())) {
										invokeParamVal = convertBlobToString((Blob) invokeParamVal);
									}
									list.add(invokeParamVal);
								}
							} else if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
								log.debug(String.format("VO Field[%s] 字段为static 或 final 修饰符所修饰，将被忽略", f.getName()));
							} else {
								log.error(String.format("VO Field[%s] 找不到对应column 注解", f.getName()));
								throw new BasException(String.format("VO Field[%s] 找不到对应column 注解", f.getName()));
							}
						}
					}
				} else {
					log.error("VO 类型错误!");
					throw new BasException("VO 类型错误!");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(e.getMessage());
			}
		}

		if (!isNullOrEmpty(list)) {
			return list;
		}

		return null;
	}

	public static String getSetterMethodName(String fieldName) {
		if (!isNullOrEmpty(fieldName)) {
			fieldName = fieldName.trim();
			StringBuffer tmp = new StringBuffer("set");
			tmp.append(fieldName.substring(0, 1).toUpperCase());
			tmp.append(fieldName.substring(1));
			return tmp.toString();
		}
		return null;
	}

	public static String getGetterMethodName(String fieldName) {
		if (!isNullOrEmpty(fieldName)) {
			fieldName = fieldName.trim();
			StringBuffer tmp = new StringBuffer("get");
			tmp.append(fieldName.substring(0, 1).toUpperCase());
			tmp.append(fieldName.substring(1));
			return tmp.toString();
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

	/*
	 * 将BLOB 转出 String<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
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

	public static String appendPage(StringBuffer sql, Page page) {
		String tmpSql = null;
		if (sql != null && page != null) {
			tmpSql = sql.toString();
		}
		return appendPage(tmpSql, page);
	}

	public static String appendPage(String sql, Page page) {
		StringBuffer tmp = new StringBuffer();
		if (sql != null && page != null) {
			tmp.append(sql.trim().toLowerCase());
			int subLen = page.getLastRecord() - page.getFirstRecord() + 1;
			tmp.append(" limit ").append(page.getFirstRecord()).append(", ").append(subLen);
			return tmp.toString();
		} else {
			return sql;
		}
	}

	public static String[] getColumnName(String sql) {
		if (isNullOrEmpty(sql)) {
			return null;
		}

		sql = sql.trim();
		int last = sql.indexOf("from");
		sql = sql.substring(4, last).trim();
		String[] cols = sql.split(",");
		String[] rsSql = new String[cols.length];
		int i = 0;
		for (String col : cols) {
			col = col.trim();
			if (col.indexOf(".") != -1) {
				rsSql[i] = col.split(" ")[1];
			} else {
				rsSql[i] = col;
			}
			i++;
		}
		return rsSql;
	}

	public static String isoToG180(String str) {
		if (!isNullOrEmpty(str)) {
			try {
				return new String(str.getBytes("ISO8859_1"), "GB18030");
			} catch (UnsupportedEncodingException e) {
				System.out.println("DormUtil.isoToGb 转码失败！");
			}
		}
		return str;
	}

	/**
	 * 使用文件通道的方式复制文件
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

	public static int randomOne(int max, int min) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	public static boolean checkDrRecordTp(Integer type) {
		if (type == null) {
			return false;
		} else if (type == Integer.valueOf(DormConstant.DR_RECORD_TYPE_0) || type == Integer.valueOf(DormConstant.DR_RECORD_TYPE_1) || type == Integer.valueOf(DormConstant.DR_RECORD_TYPE_2)
				|| type == Integer.valueOf(DormConstant.DR_RECORD_TYPE_3)) {
			return true;
		} else {
			return false;
		}
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll(" ");
		}
		return dest;
	}

	public static String trim(String str) {
		if (!isNullOrEmpty(str)) {
			return replaceBlank(str).trim();
		} else {
			return "";
		}
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

	public static String byteTOString(byte[] in) throws Exception {
		InputStream is = byteTOInputStream(in);
		return InputStreamTOString(is, "GB18030");// TODO
	}

	/**
	 * MD5 加密<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param inStr
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