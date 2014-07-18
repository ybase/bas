package com.ybase.bas.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.exception.BasException;
import com.ybase.bas.util.JdbcUtil;
import com.ybase.bas.util.MessageUtil;
import com.ybase.bas.vo.Page;

/**
 * JDBC DAO 操作模板<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public abstract class JdbcEntityDaoTemplate {
	private static final Logger log = Logger.getLogger(JdbcEntityDaoTemplate.class.getName());

	private ConnectionManager connMgr;
	private Statement stmt;
	private PreparedStatement preStmt;
	private Connection con;

	public JdbcEntityDaoTemplate() {
		connMgr = ConnectionManager.getInstance();
		con = connMgr.getConnection("");
	}

	/**
	 * 新增对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param t
	 * @throws Exception
	 */
	public <T> T addVO(T t) throws BasException {
		int result = -1;
		ResultSet idRs = null;
		try {
			Map<String, Object> updateProps = JdbcUtil.createInsertProps(t);
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[addVO]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(t.getClass(), updateProps, null, BasConstants.SQL_INSERT, null, 0, null));
			JdbcUtil.supplyQuestionMark(t.getClass(), preStmt, updateProps, null, BasConstants.SQL_INSERT);
			result = preStmt.executeUpdate();
			if (result == 0) {
				log.info(MessageUtil.getBasText("jdbc-addVO-addfail", t.getClass().getSimpleName()));
			} else {
				idRs = preStmt.getGeneratedKeys();
				while (idRs.next()) {
					JdbcUtil.supplyBackId(t, idRs.getInt(1));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10001, t.getClass().getSimpleName());
		} finally {
			close(idRs);
		}
		return t;
	}

	/**
	 * 执行对象查询，返回结果记录数<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件map<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> int executeCountQuery(Class<T> clz, Map<String, Object> props) throws BasException {
		ResultSet result = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createCountSqlByProp(clz, props));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			if (result != null) {
				while (result.next()) {
					return result.getInt("num");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10004, clz.getSimpleName(), props.toString());
		} finally {
			close(result);
		}
		return 0;
	}

	/**
	 * 根据条件，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件map<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props) throws BasException {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, null));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10003, clz.getSimpleName(), props.toString());
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件map<br/>
	 * @param loadCol
	 *            如果不为空，单独查询某列值<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, String loadCol) throws BasException {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, loadCol));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10006, clz.getSimpleName(), props.toString(), loadCol);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param limit
	 *            返回限制条数<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, int limit) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, limit, null));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10005, clz.getSimpleName(), props.toString(), limit);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param limit
	 *            限制条数<br/>
	 * @param loadCol
	 *            单独返回某列值<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, int limit, String loadCol) throws BasException {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, limit, loadCol));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10007, clz.getSimpleName(), props.toString(), limit, loadCol);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件|排序，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param sort
	 *            排序<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort) throws BasException {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, null));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10008, clz.getSimpleName(), props.toString(), sort.toString());
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件|排序，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param sort
	 *            排序<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, String loadCol) throws BasException {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, loadCol));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10009, clz.getSimpleName(), props.toString(), sort.toString(), loadCol);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件|排序，查询对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param sort
	 *            排序条件<br/>
	 * @param limit
	 *            限制数<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, int limit) throws BasException {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, limit, null));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10010, clz.getSimpleName(), props.toString(), sort.toString(), limit);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据条件|排序|限制|排序，查询对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param sort
	 *            排序条件<br/>
	 * @param limit
	 *            限制数<br/>
	 * @param loadCol
	 *            单独列<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, int limit, String loadCol) throws BasException {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, limit, loadCol));
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10011, clz.getSimpleName(), props.toString(), sort.toString(), limit, loadCol);
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据分页对象，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param page
	 *            分页对象<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Page page) throws BasException {
		ResultSet result = null;
		List<T> list = null;
		try {
			String sql = JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, null);
			if (page != null) {
				sql = JdbcUtil.appendPageSub(sql, page);
			}
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(sql);
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10012, clz.getSimpleName(), props.toString(), page.toString());
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据分页对象，执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param page
	 *            分页对象<br/>
	 * @param sort
	 *            排序条件<br/>
	 * @return
	 * @throws BasException
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Page page, Map<String, String> sort) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			String sql = JdbcUtil.createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, null);
			if (page != null) {
				sql = JdbcUtil.appendPageSub(sql, page);
			}
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(sql);
			JdbcUtil.supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = JdbcUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10013, clz.getSimpleName(), props.toString(), page.toString(), sort.toString());
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 根据xmlsql 主标识|分页对象|查询参数，执行xmlsql查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param id
	 *            主标识<br/>
	 * @param page
	 *            分页对象<br/>
	 * @param obj
	 *            查询参数<br/>
	 * @return
	 * @throws BasException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execXmlSqlQuery(String id, Page page, Object... obj) throws BasException {
		ResultSet result = null;
		Object[] rst = null;
		List<Map<String, Object>> mapRs = null;
		try {
			if (page != null) {
				rst = JdbcSqlXmlDaoTemplate.getXmlSqlById(id, page);
			} else {
				rst = JdbcSqlXmlDaoTemplate.getXmlSqlById(id);
			}

			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[execXmlSqlQuery]");
			preStmt = con.prepareStatement((String) rst[0]);
			int count = 1;
			for (Object par : obj) {
				String typeName = par.getClass().getSimpleName();
				Class<?> clz = par.getClass();
				if ("Integer".equals(typeName)) {
					typeName = "int";
					clz = int.class;
				} else if ("Double".equals(typeName)) {
					typeName = "double";
					clz = double.class;
				} else if ("Float".equals(typeName)) {
					typeName = "float";
					clz = float.class;
				} else if ("Long".equals(typeName)) {
					typeName = "long";
					clz = long.class;
				}

				preStmt.getClass().getMethod(JdbcUtil.getSetterMethodName(typeName), int.class, clz).invoke(preStmt, count, par);
				count++;
			}
			result = preStmt.executeQuery();
			mapRs = JdbcUtil.resultSet2Map(result, (Vector<String>) rst[1]);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10014, id, page.toString(), obj.toString());
		} finally {
			close(result);
		}
		return mapRs;
	}

	/**
	 * 根据条件，更新对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            更新对象<br/>
	 * @param updateProps
	 *            更新值<br/>
	 * @param conProp
	 *            条件<b/>
	 * @return
	 * @throws BasException
	 */
	public <T> int update(Class<T> clz, Map<String, Object> updateProps, Map<String, Object> conProp) throws Exception {
		int result = -1;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[update]");
			preStmt = con.prepareStatement(JdbcUtil.createSqlByProp(clz, updateProps, conProp, BasConstants.SQL_UPDATE, null, 0, null).trim());
			JdbcUtil.supplyQuestionMark(clz, preStmt, updateProps, conProp, BasConstants.SQL_UPDATE);
			result = preStmt.executeUpdate();
			if (result == 0) {
				log.info(MessageUtil.getBasText("jdbc-update-updatefail", clz.getSimpleName()));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10015, clz.getSimpleName());
		} finally {
			close(null);
		}
		return result;
	}

	/**
	 * 关闭ResultSet连接，关闭Stmt连接<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param rs
	 * @throws BasException
	 */
	public void close(ResultSet rs) throws BasException {
		try {
			if (stmt != null) {
				stmt.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10002);
		}
	}

}
