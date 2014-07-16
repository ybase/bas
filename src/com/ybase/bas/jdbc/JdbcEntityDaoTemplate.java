package com.ybase.bas.jdbc;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.util.BasUtil;
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
	public <T> T addVO(T t) throws Exception {
		int result = -1;
		ResultSet idRs = null;
		try {
			Map<String, Object> updateProps = createInsertProps(t);
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[addVO]");
			preStmt = con.prepareStatement(createSqlByProp(t.getClass(), updateProps, null, BasConstants.SQL_INSERT, null, 0, null));
			supplyQuestionMark(t.getClass(), preStmt, updateProps, null, BasConstants.SQL_INSERT);
			result = preStmt.executeUpdate();
			if (result == 0) {
				log.info("执行delete,update,insert SQL出错");
			} else {
				idRs = preStmt.getGeneratedKeys();
				while (idRs.next()) {
					supplyBackId(t, idRs.getInt(1));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.info("执行delete,update,insert SQL出错");
		} finally {
			close(idRs);
		}
		return t;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> int executeCountQuery(Class<T> clz, Map<String, Object> props) throws Exception {
		ResultSet result = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createCountSqlByProp(clz, props));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			if (result != null) {
				while (result.next()) {
					return result.getInt("num");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return 0;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, null));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, String loadCol) throws Exception {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, loadCol));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, int limit) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, limit, null));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, int limit, String loadCol) throws Exception {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, limit, loadCol));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, null));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, String loadCol) throws Exception {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, loadCol));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, int limit) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, limit, null));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<Object> executeQuery(Class<T> clz, Map<String, Object> props, Map<String, String> sort, int limit, String loadCol) throws Exception {
		ResultSet result = null;
		List<Object> list = null;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, limit, loadCol));
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2ColProp(result, clz, loadCol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Page page) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			String sql = createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, null, 0, null);
			if (page != null) {
				sql = BasUtil.appendPage(sql, page);
			}
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(sql);
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行对象查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> List<T> executeQuery(Class<T> clz, Map<String, Object> props, Page page, Map<String, String> sort) throws Exception {
		ResultSet result = null;
		List<T> list = null;
		try {
			String sql = createSqlByProp(clz, null, props, BasConstants.SQL_SELECT, sort, 0, null);
			if (page != null) {
				sql = BasUtil.appendPage(sql, page);
			}
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[executeQuery]");
			preStmt = con.prepareStatement(sql);
			supplyQuestionMark(clz, preStmt, null, props, BasConstants.SQL_SELECT);
			result = preStmt.executeQuery();
			list = BasUtil.resultSet2VoProp(result, clz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行对象查询语句出错");
		} finally {
			close(result);
		}
		return list;
	}

	/**
	 * 执行XML SQL框架查询<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execXmlSqlQuery(String id, Page page, Object... obj) throws Exception {
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

				preStmt.getClass().getMethod(BasUtil.getSetterMethodName(typeName), int.class, clz).invoke(preStmt, count, par);
				count++;
			}
			result = preStmt.executeQuery();
			mapRs = resultSet2Map(result, (Vector<String>) rst[1]);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("执行查询语句出错");
		} finally {
			close(result);
		}
		return mapRs;
	}

	/**
	 * 执行对象更新<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public <T> int update(Class<T> clz, Map<String, Object> updateProps, Map<String, Object> conProp) throws Exception {
		int result = -1;
		try {
			con = connMgr.getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "SQL Connection[ " + con + " ], Where[update]");
			preStmt = con.prepareStatement(createSqlByProp(clz, updateProps, conProp, BasConstants.SQL_UPDATE, null, 0, null).trim());
			supplyQuestionMark(clz, preStmt, updateProps, conProp, BasConstants.SQL_UPDATE);
			result = preStmt.executeUpdate();
			if (result == 0)
				log.info("执行delete,update,insert SQL出错");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.info("执行delete,update,insert SQL出错");
		} finally {
			close(null);
		}
		return result;
	}

	private List<Map<String, Object>> resultSet2Map(ResultSet result, Vector<String> cols) throws Exception {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		if (result != null) {
			while (result.next()) {
				Map<String, Object> row = new TreeMap<String, Object>();
				for (String col : cols) {
					Object colObj = result.getObject(col);
					if (byte[].class.getSimpleName().equals(colObj.getClass().getSimpleName())) {
						colObj = BasUtil.byteTOString((byte[]) colObj);
					}else if(BigDecimal.class.getSimpleName().equals(colObj.getClass().getSimpleName())){
						colObj = Integer.valueOf(((BigDecimal)colObj).intValue());
					}
					row.put(col, colObj);
				}
				list.add(row);
			}
		}
		return list;
	}

	private <T> String createCountSqlByProp(Class<T> clz, Map<String, Object> props) throws Exception {
		if (clz != null) {
			StringBuffer sql = new StringBuffer();
			if (clz.isAnnotationPresent(Table.class)) {
				Table table = clz.getAnnotation(Table.class);
				sql.append("select count(*) as num from ").append(table.value());
			}

			if (props != null && props.size() > 0) {
				sqlSetWhereOrSetOrValues(clz, sql, props, BasConstants.SQL_SELECT, BasConstants.SQL_WHERE);
			}

			log.info("JDBC 简单ORM框架SQL语句:" + sql.toString());
			return sql.toString();
		} else {
			throw new Exception("VO 对象为空");
		}
	}

	private <T> String createSqlByProp(Class<T> clz, Map<String, Object> updateProps, Map<String, Object> props, String operType, Map<String, String> sort, int limit, String loadCol) throws Exception {
		if (clz != null) {
			StringBuffer sql = new StringBuffer();
			if (clz.isAnnotationPresent(Table.class)) {
				Table table = clz.getAnnotation(Table.class);
				if (BasConstants.SQL_SELECT.equals(operType)) {
					String colName = "";
					if (!BasUtil.isNullOrEmpty(loadCol)) {
						Field f = clz.getDeclaredField(loadCol);
						if (f.isAnnotationPresent(Column.class)) {
							colName = f.getAnnotation(Column.class).name();
						}
					}

					if (!BasUtil.isNullOrEmpty(colName)) {
						sql.append("select ").append(colName).append(" from ").append(table.value());
					} else {
						sql.append("select * from ").append(table.value());
					}
				} else if (BasConstants.SQL_UPDATE.equals(operType)) {
					sql.append("update ").append(table.value());
				} else if (BasConstants.SQL_DELETE.equals(operType)) {
					sql.append("delete from ").append(table.value());
				} else if (BasConstants.SQL_INSERT.equals(operType)) {
					sql.append("insert into ").append(table.value());
				}
			} else {
				throw new Exception(clz.getSimpleName() + " VO 对象找不到table配置");
			}

			if (BasConstants.SQL_INSERT.equals(operType)) {
				return sqlSetWhereOrSetOrValues(clz, sql, updateProps, operType, BasConstants.SQL_VALUES);
			}

			if (BasConstants.SQL_UPDATE.equals(operType)) {
				sqlSetWhereOrSetOrValues(clz, sql, updateProps, operType, BasConstants.SQL_SET);
			}

			if (props != null && props.size() > 0) {
				sqlSetWhereOrSetOrValues(clz, sql, props, operType, BasConstants.SQL_WHERE);
			}

			if (BasConstants.SQL_SELECT.equals(operType)) {
				if (sort != null && sort.size() > 0) {
					sql.append(" order by ");
					Set<String> cols = sort.keySet();
					Iterator<String> iter = cols.iterator();
					int count = 0;
					while (iter.hasNext()) {
						String key = iter.next();
						Field f = clz.getDeclaredField(key);
						if (f == null || !f.isAnnotationPresent(Column.class)) {
							throw new Exception(key + "没有该字段或字段对应的列不存在");
						}
						Column col = f.getAnnotation(Column.class);
						String sortAD = sort.get(key);
						sql.append(col.name()).append(" ").append(sortAD);
						if (count != sort.size() - 1) {
							sql.append(",");
						}
						count++;
					}
				}

				if (limit != 0) {
					sql.append(" limit ").append(limit);
				}
			}
			log.info("JDBC 简单ORM框架SQL语句:" + sql.toString());
			return sql.toString();
		} else {
			throw new Exception("VO 对象为空");
		}
	}

	private <T> String sqlSetWhereOrSetOrValues(Class<T> clz, StringBuffer sql, Map<String, Object> props, String operType, String sqlWhere) throws Exception {
		StringBuffer addSql = new StringBuffer();
		Set<String> fields = props.keySet();
		Iterator<String> fIter = fields.iterator();
		int count = 0;
		while (fIter.hasNext()) {
			String fieldNameKey = fIter.next();
			String[] fieldOperChar = null;
			String fieldName = null;
			if (BasConstants.SQL_WHERE.equals(sqlWhere)) {
				fieldOperChar = checkKey(fieldNameKey, operType);
				fieldName = fieldOperChar[0];
			} else {
				fieldName = fieldNameKey;
				fieldOperChar = new String[2];
				fieldOperChar[0] = fieldName;
				fieldOperChar[1] = BasConstants.SQL_Eq;
			}
			Field f = clz.getDeclaredField(fieldName);
			if (f == null || !f.isAnnotationPresent(Column.class)) {
				throw new Exception(fieldName + "没有该字段或字段对应的列不存在");
			}

			Column column = f.getAnnotation(Column.class);
			if (count == 0) {
				if (BasConstants.SQL_WHERE.equals(sqlWhere)) {
					sql.append(" where ");
				} else if (BasConstants.SQL_SET.equals(sqlWhere)) {
					sql.append(" set ");
				} else if (BasConstants.SQL_VALUES.equals(sqlWhere)) {
					sql.append("(");
					addSql.append(" values(");
				}
			}

			Object obj = props.get(fieldNameKey);
			if (new Object[] {}.getClass().getName().equals(obj.getClass().getName())) {
				Object[] objs = (Object[]) obj;
				if (objs == null || objs.length != 2) {
					throw new Exception(fieldName + "该字段对应的参数错误");
				}

				if (count != 0 && (Integer) objs[1] == BasConstants.SQL_AND) {
					sql.append(" and ");
				} else if (count != 0 && (Integer) objs[1] == BasConstants.SQL_OR) {
					sql.append(" or ");
				} else if (count != 0) {
					throw new Exception(fieldName + "该字段对应的参数错误");
				}
			} else {
				if (count != 0) {
					if (BasConstants.SQL_WHERE.equals(sqlWhere)) {
						sql.append(" and ");
					} else if (BasConstants.SQL_SET.equals(sqlWhere)) {
						sql.append(",");
					} else if (BasConstants.SQL_VALUES.equals(sqlWhere)) {
						sql.append(",");
						addSql.append(",");
					}
				}
			}

			if (BasConstants.SQL_INSERT.equals(operType)) {
				sql.append(fieldName);
				addSql.append("?");
				if (count == fields.size() - 1) {
					sql.append(")");
					addSql.append(")");
				}
			} else {
				if (BasConstants.SQL_SET.equals(sqlWhere) && containExpress(column.name(), obj)) {
					sql.append(column.name()).append(fieldOperChar[1] + (String) obj);
				} else {
					sql.append(column.name()).append(appendSqlQuesMark(fieldOperChar[1]));
				}
			}

			count++;
		}

		return sql.append(addSql.toString()).toString();
	}

	private boolean containExpress(String fieldName, Object obj) {
		if (String.class.getSimpleName().equals(obj.getClass().getSimpleName())) {
			String tmp = (String) obj;
			if (tmp.indexOf(fieldName) == 0 && isContainAppendExp(tmp, fieldName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isContainAppendExp(String tmp, String fieldName) {
		for (String exp : BasConstants.SQL_EXP) {
			if (tmp.indexOf(fieldName + exp) == 0) {
				return true;
			}
		}
		return false;
	}

	private String[] checkKey(String fieldName, String operType) throws Exception {
		if (fieldName == null || "".equals(fieldName.trim())) {
			throw new Exception("字段类型为空");
		}

		int count = 0;
		for (String charOp : BasConstants.SQL_OPCHAR_MAP[0]) {
			if (fieldName.endsWith(charOp)) {
				if (BasConstants.SQL_UPDATE.equals(operType)) {
					if (!BasConstants.SQL_Eq_KEY.equals(charOp)) {
						throw new Exception("update 语句中存在非法操作符");
					}
				}
				return new String[] { fieldName.substring(0, fieldName.indexOf(charOp) - 1), BasConstants.SQL_OPCHAR_MAP[1][count] };
			}
			count++;
		}
		throw new Exception("不支持的操作类型");
	}

	private String appendSqlQuesMark(String operChar) {
		return operChar + BasConstants.SQL_MARK_QUESTION;
	}

	private <T> void supplyBackId(T t, int id) throws Exception {
		Field[] fs = t.getClass().getDeclaredFields();
		for (Field f : fs) {
			if (f.isAnnotationPresent(Column.class) && f.getAnnotation(Column.class).key()) {
				t.getClass().getMethod(BasUtil.getSetterMethodName(f.getName()), f.getType()).invoke(t, id);
				return;
			}
		}
		throw new Exception("未定义主键");
	}

	private <T> void supplyQuestionMark(Class<T> clz, PreparedStatement preStmt, Map<String, Object> updateProps, Map<String, Object> conProps, String operType) throws Exception {
		if (preStmt == null) {
			throw new Exception("prepareStatement 为空或关闭");
		}

		if (updateProps != null && updateProps.size() > 0) {
			supplyQuestionMarkSub(clz, preStmt, updateProps, operType, BasConstants.SQL_SET, 0);
		}

		if (conProps != null && conProps.size() > 0) {
			supplyQuestionMarkSub(clz, preStmt, conProps, operType, BasConstants.SQL_WHERE, countValidProp(clz, updateProps));
		}

	}

	/**
	 * 适用于update props map<br/>
	 * 
	 * @param <T>
	 * 
	 * @param map
	 * @return
	 * @throws
	 * @throws SecurityException
	 */
	private <T> int countValidProp(Class<T> clz, Map<String, Object> map) throws Exception {
		if (map != null && map.size() > 0) {
			int count = map.size();
			Set<String> keys = map.keySet();
			Iterator<String> iter = keys.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = map.get(key);
				Field f = clz.getDeclaredField(key);
				if (containExpress(f.getAnnotation(Column.class).name(), value)) {
					count--;
				}
			}
			return count;
		}
		return 0;
	}

	private <T> void supplyQuestionMarkSub(Class<T> clz, PreparedStatement preStmt, Map<String, Object> props, String operType, String sqlWhere, int addCount) throws Exception {
		Set<String> keys = props.keySet();
		Iterator<String> keyIter = keys.iterator();
		int count = addCount + 1;
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String fieldName = key;
			Object fieldValue = props.get(key);
			if (BasConstants.SQL_WHERE.equals(sqlWhere)) {
				String[] checks = checkKey(key, operType);
				fieldName = checks[0];
				if (new Object[] {}.getClass().getName().equals(fieldValue.getClass().getName())) {
					Object[] obj = (Object[]) fieldValue;
					fieldValue = obj[0];
				}
			}
			Field f = clz.getDeclaredField(fieldName);
			Column column = f.getAnnotation(Column.class);

			if (BasConstants.SQL_SET.equals(sqlWhere) && containExpress(column.name(), fieldValue)) {
				continue;
			}

			String setterName = BasUtil.getSetterMethodName(column.type());
			String colType = column.type();
			if ("blob".equals(colType)) {
				byte[] bytes = null;
				try {
					bytes = ((String) fieldValue).getBytes("GB18030");
				} catch (UnsupportedEncodingException e) {
					bytes = ((String) fieldValue).getBytes();
					log.error(e.getMessage(), e);
				}
				ByteArrayInputStream blogBin = new ByteArrayInputStream(bytes);
				preStmt.setBinaryStream(count, blogBin, bytes.length);
			} else {
				Class<?> type = f.getType();
				if ("int".equals(colType)) {
					type = int.class;
					if(fieldValue == null){
						fieldValue = 0;
					}
				} else if ("float".equals(colType)) {
					type = float.class;
				} else if ("double".equals(colType)) {
					type = double.class;
				} else if ("long".equals(colType)) {
					type = long.class;
				} else if ("string".equals(colType)) {
					type = String.class;
				} else {
					throw new Exception("不支持字段类型");
				}
				preStmt.getClass().getMethod(setterName, int.class, type).invoke(preStmt, count, fieldValue);
			}
			count++;
		}
	}

	private <T> Map<String, Object> createInsertProps(T t) throws Exception {
		if (t == null) {
			throw new Exception("保存VO 对象为空");
		}

		if (!t.getClass().isAnnotationPresent(Table.class)) {
			throw new Exception("保存VO 类缺少Table 关联标记");
		}

		Field[] fields = t.getClass().getDeclaredFields();
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		for (Field f : fields) {
			if (!f.isAnnotationPresent(Column.class) || f.getAnnotation(Column.class).key()) {
				continue;
			}
			fieldMap.put(f.getName(), t.getClass().getMethod(BasUtil.getGetterMethodName(f.getName())).invoke(t));
		}
		return fieldMap;
	}

	/**
	 * 关闭ResultSet连接，关闭Stmt连接<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param <T>
	 */
	public void close(ResultSet rs) throws Exception {
		try {
			if (stmt != null) {
				stmt.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
