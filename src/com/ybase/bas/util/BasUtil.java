package com.ybase.bas.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {
		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		log.info(MessageUtil.getBasText("util-bas-scanpackage", packageName));
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					log.info(MessageUtil.getBasText("util-bas-scanfile", pack));
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					log.info(MessageUtil.getBasText("util-bas-scanjar", pack));
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											log.error(MessageUtil.getBasText("util-bas-sacnclassnf", className));
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.error(MessageUtil.getBasText("util-bas-scanjarfail"));
					}
				}
			}
		} catch (IOException e) {
			log.error(MessageUtil.getBasText("util-bas-scanpkgfail"));
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			log.warn(MessageUtil.getBasText("util-bas-scanpkgnull", packageName));
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					log.error(MessageUtil.getBasText("util-bas-sacnclassnf", className));
				}
			}
		}
	}
}