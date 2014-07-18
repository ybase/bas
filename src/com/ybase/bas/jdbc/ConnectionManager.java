package com.ybase.bas.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Driver;
import com.ybase.bas.util.MessageUtil;

/**
 * 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接池的访问.<br/>
 * 客户程序可以调用getInstance()方法访问本类的唯一实例.<br/>
 * 
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class ConnectionManager {
	private static final Logger log = Logger.getLogger(ConnectionManager.class.getName());
	/** 当前线程数据库连接 */
	private static ThreadLocal<Connection> connectionHandler = new ThreadLocal<Connection>();
	/** 管理类唯一实例 */
	static public ConnectionManager instance;
	/** 当前连接客户数 */
	static public int clients;
	public String defaultPoolName = "";
	/** 连接驱动 */
	public Vector<Driver> drivers = new Vector<Driver>();
	/** 连接池列表 */
	public Hashtable<String, ConnectionPool> pools = new Hashtable<String, ConnectionPool>();

	private ConnectionManager() {
		log.info(MessageUtil.getBasText("jdbc-manager-initstart"));
		init();
		log.info(MessageUtil.getBasText("jdbc-manager-initend"));
	}

	/**
	 * 返回唯一实例.如果是第一次调用此方法,则创建实例<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @return
	 */
	static synchronized public ConnectionManager getInstance() {
		if (instance == null) {
			log.info(MessageUtil.getBasText("jdbc-manager-instancemanager"));
			instance = new ConnectionManager();
		}

		clients++;
		return instance;
	}

	/**
	 * 将连接对象返回给由名字指定的连接池<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @param con连接对象
	 */
	public void freeConnection(String name, Connection con) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		log.info(MessageUtil.getBasText("jdbc-manager-freeconn", con.toString(), name));
		ConnectionPool pool = (ConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		} else {
			log.info("pool ==null");
		}
		clients--;
	}

	/**
	 * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数 限制,则创建并返回新连接<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @return
	 */
	public Connection getConnection(String name) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		ConnectionPool pool = (ConnectionPool) pools.get(name);
		if (pool != null) {
			Connection conn = pool.returnConnection();
			log.info(MessageUtil.getBasText("jdbc-manager-getconn", conn.toString(), name));
			return conn;
		}
		return null;
	}

	/**
	 * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制, 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param name
	 *            连接池名字
	 * @param time
	 *            以毫秒计的等待时间
	 * @return
	 */
	public Connection getConnection(String name, long time) {
		if (name == null || "".equals(name.trim())) {
			name = this.defaultPoolName;
		}

		ConnectionPool pool = (ConnectionPool) pools.get(name);
		if (pool != null) {
			Connection conn = pool.getConnection(time);
			log.info(MessageUtil.getBasText("jdbc-manager-getconn", conn.toString(), name));
			return conn;
		}
		return null;
	}

	/**
	 * 关闭所有连接,撤销驱动程序的注册<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	public synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--clients != 0) {
			return;
		}

		Enumeration<ConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			ConnectionPool pool = (ConnectionPool) allPools.nextElement();
			pool.release();
		}
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.error(MessageUtil.getBasText("jdbc-manager-driverunregsucc", driver.getClass().getName()));
			} catch (SQLException e) {
				log.error(MessageUtil.getBasText("jdbc-manager-driverunregfail", driver.getClass().getName()));
			}
		}
	}

	/**
	 * 根据指定属性创建连接池实例.<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param props
	 *            连接池属性
	 */
	private void createPools(Properties props) {
		Enumeration<?> propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					log.warn(MessageUtil.getBasText("jdbc-conpoll-urlnf", poolName));
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					log.error(MessageUtil.getBasText("jdbc-conpoll-maxlimierr", poolName, maxconn));
					max = 0;
				}
				ConnectionPool pool = new ConnectionPool(poolName, url, user, password, max);
				pools.put(poolName, pool);
				log.info(MessageUtil.getBasText("jdbc-conpoll-createsucc", poolName));
			} else if (name.endsWith("pool")) {
				this.defaultPoolName = props.getProperty(name);
			}
		}
	}

	/**
	 * 读取属性完成初始化<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	private void init() {
		try {
			InputStream in = this.getClass().getResourceAsStream("/db.properties");
			Properties dbProps = new Properties();
			try {
				dbProps.load(in);
			} catch (Exception e) {
				log.error(MessageUtil.getBasText("jdbc-manager-dbpnf"));
				return;
			}
			loadDrivers(dbProps);
			createPools(dbProps);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error(MessageUtil.getBasText("jdbc-manager-dbinitfail"));
		}
	}

	/**
	 * 装载和注册所有JDBC驱动程序<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param props
	 *            属性
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				log.info(MessageUtil.getBasText("jdbc-manager-driverregsucc", driverClassName));
			} catch (Exception e) {
				log.info(MessageUtil.getBasText("jdbc-manager-driverregfail", driverClassName));
			}
		}
	}

	class ConnectionPool {
		private Vector<Connection> freeConnections = new Vector<Connection>();
		/** 连接池允许建立的最大连接数 */
		private int maxConn;
		/** 连接池名称 */
		private String name;
		/** 数据库的JDBC URL */
		private String URL;
		/** 数据库帐号 */
		private String user;
		/** 数据库密码 */
		private String password;

		/**
		 * 创建新的连接池<br/>
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 * @param name
		 *            连接池名字
		 * @param URL
		 *            数据库的JDBC URL
		 * @param user
		 *            数据库帐号
		 * @param password
		 *            数据库密码
		 * @param maxConn
		 *            连接池允许建立的最大连接数
		 */
		public ConnectionPool(String name, String URL, String user, String password, int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * 将不再使用的连接返回给连接池
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 * @param con
		 *            客户程序释放的连接
		 */
		public synchronized void freeConnection(Connection con) {
			// 将指定连接加入到向量末尾
			try {
				if (con == connectionHandler.get()) {
					connectionHandler.remove();
				}

				if (con.isClosed()) {
					freeConnections.remove(con);
					log.info("before freeConnection con is closed");
					return;
				}
				freeConnections.addElement(con);

				Connection contest = (Connection) freeConnections.lastElement();
				if (contest.isClosed()) {
					freeConnections.remove(contest);
					log.info("after freeConnection contest is closed");
				}
				notifyAll();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}

		/**
		 * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接.<br/>
		 * 如原来登记为可用的连接不再有效,则从向量删除之, 然后递归调用自己以尝试新的可用连接.<br/>
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 */
		public synchronized Connection getConnection() {
			Connection con = connectionHandler.get();
			if (con == null) {
				if (freeConnections.size() > 0) {
					// 获取向量中第一个可用连接
					con = (Connection) freeConnections.firstElement();
					freeConnections.removeElementAt(0);
					try {
						if (con.isClosed()) {
							log.info(MessageUtil.getBasText("jdbc-conpoll-ivconndelsucc", name));
							// 递归调用自己,尝试再次获取可用连接
							con = getConnection();
						}
					} catch (SQLException e) {
						log.info(MessageUtil.getBasText("jdbc-conpoll-ivconndelfail", name));
						log.error(e.getMessage(), e);
						// 递归调用自己,尝试再次获取可用连接
						con = getConnection();
					}

					if (freeConnections.size() > maxConn) {
						log.info(MessageUtil.getBasText("jdbc-conpoll-connout", name));
						releaseOne();
					}
				} else if ((maxConn == 0) || (freeConnections.size() < maxConn)) {
					con = newConnection();
				}
				connectionHandler.set(con);
			}
			return con;
		}

		public synchronized Connection returnConnection() {
			Connection con = connectionHandler.get();
			if (con == null) {
				// 如果闲置小于最大连接,返回一个新连接
				if (freeConnections.size() + clients < maxConn) {
					con = newConnection();
				} else if (freeConnections.size() + clients >= maxConn) {
					// 如果闲置大于最大连接，返回一个可用的旧连接
					con = (Connection) freeConnections.firstElement();
					log.info(MessageUtil.getBasText("jdbc-conpoll-validconnsize", name,freeConnections.size()));
					freeConnections.removeElementAt(0);
					log.info(MessageUtil.getBasText("jdbc-conpoll-validconnsize", name,freeConnections.size()));
					try {
						if (con.isClosed()) {
							log.info(MessageUtil.getBasText("jdbc-conpoll-ivconndelsucc", name));
							returnConnection();
						}
					} catch (SQLException e) {
						log.info(MessageUtil.getBasText("jdbc-conpoll-ivconndelfail", name));
						log.error(e.getMessage(),e);
						returnConnection();
					}
				}
				connectionHandler.set(con);
			}
			return con;
		}

		/**
		 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 * @param timeout
		 *            以毫秒计的等待时间限制
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}

				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()返回的原因是超时
					return null;
				}
			}
			return con;
		}

		/**
		 * 关闭所有连接<br/>
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 */
		public synchronized void release() {
			Enumeration<?> allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = (Connection) allConnections.nextElement();
				try {
					if (con == connectionHandler.get()) {
						connectionHandler.remove();
					}
					con.close();
					log.info(MessageUtil.getBasText("jdbc-conpoll-connclosesucc", name, con));
				} catch (SQLException e) {
					log.info(MessageUtil.getBasText("jdbc-conpoll-connclosefail", name, con));
					log.error(e.getMessage(), e);
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * 关闭一个连接<br/>
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 */
		public synchronized void releaseOne() {
			if (freeConnections.firstElement() != null) {
				Connection con = (Connection) freeConnections.firstElement();
				try {
					if (con == connectionHandler.get()) {
						connectionHandler.remove();
					}
					log.info(MessageUtil.getBasText("jdbc-conpoll-connclosesucc", name, con));
					con.close();
					freeConnections.remove(con);
				} catch (SQLException e) {
					log.info(MessageUtil.getBasText("jdbc-conpoll-connclosefail", name, con));
					log.error(e.getMessage(), e);
				}
			} else {
				log.info(MessageUtil.getBasText("jdbc-conpoll-connf", name));
			}
		}

		/**
		 * 创建新的连接<br/>
		 * 
		 * @bas_V1.0, yangxb, 2014-7-16<br/>
		 * @return
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL);
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				log.info(MessageUtil.getBasText("jdbc-conpoll-crconnsucc", name));
			} catch (SQLException e) {
				log.info(MessageUtil.getBasText("jdbc-conpoll-crconnfail", name));
				log.error(e.getMessage(), e);
				return null;
			}
			return con;
		}
	}

}