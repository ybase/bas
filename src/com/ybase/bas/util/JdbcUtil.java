package com.ybase.bas.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.exception.BasException;
import com.ybase.bas.vo.BasVO;
import com.ybase.bas.vo.Page;

/**
 * Jdbc 工具类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class JdbcUtil {

	private static final Logger log = Logger.getLogger(JdbcUtil.class.getSimpleName());

	/**
	 * 根据cols解析ResultSet对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param result
	 *            JDBC返回结果<br/>
	 * @param cols
	 *            解析列<br/>
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> resultSet2Map(ResultSet result, Vector<String> cols) throws BasException {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		if (result != null) {
			try {
				while (result.next()) {
					Map<String, Object> row = new TreeMap<String, Object>();
					for (String col : cols) {
						Object colObj = result.getObject(col);
						if (byte[].class.getSimpleName().equals(colObj.getClass().getSimpleName())) {
							colObj = BasUtil.byteTOString((byte[]) colObj);
						} else if (BigDecimal.class.getSimpleName().equals(colObj.getClass().getSimpleName())) {
							colObj = Integer.valueOf(((BigDecimal) colObj).intValue());
						}
						row.put(col, colObj);
					}
					list.add(row);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10016, cols.toString());
			}
		}
		return list;
	}

	/**
	 * 根据查询条件|更新条件，生成带占位符SQL语句<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询对象<br/>
	 * @param props
	 *            条件<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> String createCountSqlByProp(Class<T> clz, Map<String, Object> props) throws BasException {
		if (clz != null) {
			StringBuffer sql = new StringBuffer();
			if (clz.isAnnotationPresent(Table.class)) {
				Table table = clz.getAnnotation(Table.class);
				sql.append("select count(*) as num from ").append(table.value());
			}

			if (props != null && props.size() > 0) {
				sqlSetWhereOrSetOrValues(clz, sql, props, BasConstants.SQL_SELECT, BasConstants.SQL_WHERE);
			}

			log.info(MessageUtil.getBasText("jdbc-util-sqlstr", sql.toString()));
			return sql.toString();
		} else {
			throw new BasException(BasErrCode.E10017);
		}
	}

	/**
	 * 根据查询对象|更新条件|查询条件|操作类型|排序条件|限制数|单独列构造带占位符SQL<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            操作对象<br/>
	 * @param updateProps
	 *            更新条件<br/>
	 * @param props
	 *            查询条件<br/>
	 * @param operType
	 *            操作类型<br/>
	 * @param sort
	 *            排序条件<br/>
	 * @param limit
	 *            限制数<br/>
	 * @param loadCol
	 *            单独列<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> String createSqlByProp(Class<T> clz, Map<String, Object> updateProps, Map<String, Object> props, String operType, Map<String, String> sort, int limit, String loadCol) throws BasException {
		if (clz != null) {
			try {
				StringBuffer sql = new StringBuffer();
				if (clz.isAnnotationPresent(Table.class)) {
					Table table = clz.getAnnotation(Table.class);
					if (BasConstants.SQL_SELECT.equals(operType)) {
						String colName = "";
						if (!BasUtil.isNullOrEmpty(loadCol)) {
							Field f;

							f = clz.getDeclaredField(loadCol);

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
					throw new BasException(BasErrCode.E10018, clz.getSimpleName());
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
								throw new BasException(BasErrCode.E10019, clz.getSigners(), key);
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
				log.info(MessageUtil.getBasText("jdbc-util-sqlstr", sql.toString()));
				return sql.toString();
			} catch (SecurityException e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10020, clz.getSimpleName(), loadCol);
			} catch (NoSuchFieldException e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10020, clz.getSimpleName(), loadCol);
			}
		} else {
			throw new BasException(BasErrCode.E10017, "clz=null");
		}
	}

	/**
	 * 根据查询对象|前置SQL|更新值|查询条件设置set|where子句SQL<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            查询条件<br/>
	 * @param sql
	 *            前置sql<br/>
	 * @param props
	 *            更新条件<br/>
	 * @param operType
	 *            操作类型<br/>
	 * @param sqlWhere
	 *            设置位置<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> String sqlSetWhereOrSetOrValues(Class<T> clz, StringBuffer sql, Map<String, Object> props, String operType, String sqlWhere) throws BasException {
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

			Field f = null;
			try {
				f = clz.getDeclaredField(fieldName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10020, fieldName);
			}

			if (f == null || !f.isAnnotationPresent(Column.class)) {
				throw new BasException(BasErrCode.E10021, clz.getSimpleName(), f != null ? f.getName() : "");
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
					throw new BasException(BasErrCode.E10022);
				}

				if (count != 0 && (Integer) objs[1] == BasConstants.SQL_AND) {
					sql.append(" and ");
				} else if (count != 0 && (Integer) objs[1] == BasConstants.SQL_OR) {
					sql.append(" or ");
				} else if (count != 0) {
					throw new BasException(BasErrCode.E10023);
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

	/**
	 * 判断Set 更新值是否包含字段名称，例如x->x+1<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param fieldName
	 *            字段名称<br/>
	 * @param obj
	 *            字段值<br/>
	 * @return
	 */
	public static boolean containExpress(String fieldName, Object obj) {
		if (String.class.getSimpleName().equals(obj.getClass().getSimpleName())) {
			String tmp = (String) obj;
			if (tmp.indexOf(fieldName) == 0 && isContainAppendExp(tmp, fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断tmp串中是否包含算数运算符+-|字段名称<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param tmp
	 *            字串<br/>
	 * @param fieldName
	 *            字段名称<br/>
	 * @return
	 */
	public static boolean isContainAppendExp(String tmp, String fieldName) {
		for (String exp : BasConstants.SQL_EXP) {
			if (tmp.indexOf(fieldName + exp) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查字段操作类型，运算符类型合法性<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param fieldName
	 *            字段名称<br/>
	 * @param operType
	 *            操作类型<br/>
	 * @return
	 * @throws BasException
	 */
	public static String[] checkKey(String fieldName, String operType) throws BasException {
		if (fieldName == null || "".equals(fieldName.trim())) {
			throw new BasException(BasErrCode.E10024, fieldName);
		}

		int count = 0;
		for (String charOp : BasConstants.SQL_OPCHAR_MAP[0]) {
			if (fieldName.endsWith(charOp)) {
				if (BasConstants.SQL_UPDATE.equals(operType)) {
					if (!BasConstants.SQL_Eq_KEY.equals(charOp)) {
						throw new BasException(BasErrCode.E10025, operType, charOp);
					}
				}
				return new String[] { fieldName.substring(0, fieldName.indexOf(charOp) - 1), BasConstants.SQL_OPCHAR_MAP[1][count] };
			}
			count++;
		}
		throw new BasException(BasErrCode.E10041);
	}

	/**
	 * 追加占位符<?><br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param operChar
	 *            操作类型<br/>
	 * @return
	 */
	public static String appendSqlQuesMark(String operChar) {
		return operChar + BasConstants.SQL_MARK_QUESTION;
	}

	/**
	 * 回填操作对象主键值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param t
	 *            操作对象<br/>
	 * @param id
	 *            主键值<br/>
	 * @throws BasException
	 */
	public static <T> void supplyBackId(T t, int id) throws BasException {
		Field[] fs = t.getClass().getDeclaredFields();
		for (Field f : fs) {
			if (f.isAnnotationPresent(Column.class) && f.getAnnotation(Column.class).key()) {
				try {
					t.getClass().getMethod(getSetterMethodName(f.getName()), f.getType()).invoke(t, id);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw new BasException(BasErrCode.E10028, t.getClass().getSimpleName());
				}
				return;
			}
		}
		throw new BasException(BasErrCode.E10027, t.getClass().getSimpleName());
	}

	/**
	 * 根据更新值|查询条件|操作类型 设置Statement中占位符值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            操作对象<br/>
	 * @param preStmt
	 *            Statement对象<br/>
	 * @param updateProps
	 *            更新条件<br/>
	 * @param conProps
	 *            查询条件<br/>
	 * @param operType
	 *            操作类型<br/>
	 * @throws BasException
	 */
	public static <T> void supplyQuestionMark(Class<T> clz, PreparedStatement preStmt, Map<String, Object> updateProps, Map<String, Object> conProps, String operType) throws BasException {
		if (preStmt == null) {
			throw new BasException(BasErrCode.E10029);
		}

		if (updateProps != null && updateProps.size() > 0) {
			supplyQuestionMarkSub(clz, preStmt, updateProps, operType, BasConstants.SQL_SET, 0);
		}

		if (conProps != null && conProps.size() > 0) {
			supplyQuestionMarkSub(clz, preStmt, conProps, operType, BasConstants.SQL_WHERE, countValidProp(clz, updateProps));
		}

	}

	/**
	 * 计算占位符索引<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            操作对象 <br/>
	 * @param map
	 *            更新条件<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> int countValidProp(Class<T> clz, Map<String, Object> map) throws BasException {
		if (map != null && map.size() > 0) {
			int count = map.size();
			Set<String> keys = map.keySet();
			Iterator<String> iter = keys.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = map.get(key);
				Field f;
				try {
					f = clz.getDeclaredField(key);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw new BasException(BasErrCode.E10020, key);
				}
				if (containExpress(f.getAnnotation(Column.class).name(), value)) {
					count--;
				}
			}
			return count;
		}
		return 0;
	}

	/**
	 * 根据更新值|查询条件|操作类型 设置Statement中占位符值 ({@link supplyQuestionMark}子方法)<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param clz
	 *            操作对象<br/>
	 * @param preStmt
	 *            Statement对象<br/>
	 * @param props
	 *            条件<br/>
	 * @param operType
	 *            操作类型<br/>
	 * @param sqlWhere
	 *            位置<br/>
	 * @param addCount
	 *            占位符索引<br/>
	 * @throws BasException
	 */
	public static <T> void supplyQuestionMarkSub(Class<T> clz, PreparedStatement preStmt, Map<String, Object> props, String operType, String sqlWhere, int addCount) throws BasException {
		Set<String> keys = props.keySet();
		Iterator<String> keyIter = keys.iterator();
		int count = addCount + 1;
		try {
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

				String setterName = getSetterMethodName(column.type());
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
						if (fieldValue == null) {
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
						throw new BasException(BasErrCode.E10032, type);
					}
					preStmt.getClass().getMethod(setterName, int.class, type).invoke(preStmt, count, fieldValue);
				}
				count++;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(BasErrCode.E10031, clz.getSimpleName());
		}
	}

	/**
	 * 查找对象插入列<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param t
	 *            操作对象<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> Map<String, Object> createInsertProps(T t) throws BasException {
		if (t == null) {
			throw new BasException(BasErrCode.E10017);
		}

		if (!t.getClass().isAnnotationPresent(Table.class)) {
			throw new BasException(BasErrCode.E10018, t.getClass().getSimpleName());
		}

		Field[] fields = t.getClass().getDeclaredFields();
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		for (Field f : fields) {
			if (!f.isAnnotationPresent(Column.class) || f.getAnnotation(Column.class).key()) {
				continue;
			}
			try {
				fieldMap.put(f.getName(), t.getClass().getMethod(getGetterMethodName(f.getName())).invoke(t));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10030, t.getClass().getSimpleName());
			}
		}
		return fieldMap;
	}

	/**
	 * 将ResultSet映射操作对象<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param rs
	 *            ResultSet对象<br/>
	 * @param clz
	 *            操作对象<br/>
	 * @return
	 * @throws BasException
	 */
	public static <T> List<T> resultSet2VoProp(ResultSet rs, Class<T> clz) throws BasException {
		List<T> list = new ArrayList<T>();
		if (rs == null) {
			return list;
		}

		if (clz != null) {
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
									clz.getMethod(setterMethodName, f.getType()).invoke(vo, BasUtil.convertBlobToString((Blob) invokeParamVal));
								} else {
									clz.getMethod(setterMethodName, f.getType()).invoke(vo, invokeParamVal);
								}
							} else if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
								log.debug(MessageUtil.getBasText("jdbc-util-fignore", clz.getSimpleName(), f.getName()));
							} else {
								throw new BasException(BasErrCode.E10019, clz.getSimpleName(), f.getName());
							}
						}
						list.add(vo);
					}
				} else {
					throw new BasException(BasErrCode.E10033, clz.getSimpleName());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10034, clz.getSimpleName());
			}
		}

		if (!BasUtil.isNullOrEmpty(list)) {
			return list;
		}
		return null;
	}

	/**
	 * 将ResultSet映射值操作对象单独字段<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param rs
	 *            ResultSet对象<br/>
	 * @param clz
	 *            操作类<br/>
	 * @param loadCol
	 *            单独列<br/>
	 * @return
	 * @throws BasException
	 */
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
										invokeParamVal = BasUtil.convertBlobToString((Blob) invokeParamVal);
									}
									list.add(invokeParamVal);
								}
							} else if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
								log.debug(MessageUtil.getBasText("jdbc-util-fignore", clz.getSimpleName(), f.getName()));
							} else {
								throw new BasException(BasErrCode.E10019, clz.getSimpleName(), f.getName());
							}
						}
					}
				} else {
					throw new BasException(BasErrCode.E10033, clz.getSimpleName());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10034, clz.getSimpleName());
			}
		}

		if (!BasUtil.isNullOrEmpty(list)) {
			return list;
		}

		return null;
	}

	/**
	 * 根据字段名称返回Setter方法名<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param fieldName
	 *            字段名称<br/>
	 * @return
	 */
	public static String getSetterMethodName(String fieldName) {
		if (!BasUtil.isNullOrEmpty(fieldName)) {
			fieldName = fieldName.trim();
			StringBuffer tmp = new StringBuffer(BasConstants.SETTER_PREFIX);
			tmp.append(fieldName.substring(0, 1).toUpperCase());
			tmp.append(fieldName.substring(1));
			return tmp.toString();
		}
		return null;
	}

	/**
	 * 根据字段名返回Getter方法名<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param fieldName
	 *            字段名称<br/>
	 * @return
	 */
	public static String getGetterMethodName(String fieldName) {
		if (!BasUtil.isNullOrEmpty(fieldName)) {
			fieldName = fieldName.trim();
			StringBuffer tmp = new StringBuffer(BasConstants.GETTER_PREFIX);
			tmp.append(fieldName.substring(0, 1).toUpperCase());
			tmp.append(fieldName.substring(1));
			return tmp.toString();
		}
		return null;
	}

	/**
	 * 获取ResultSet对象中记录条数字段值<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param rs
	 *            ResultSet对象
	 * @return
	 * @throws SQLException
	 */
	public static Integer countRs(ResultSet rs) throws BasException {
		Integer count = 0;
		if (rs != null) {
			try {
				while (rs.next()) {
					count = rs.getInt("num");
				}
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
				throw new BasException(BasErrCode.E10035);
			}
		}
		return count;
	}

	/**
	 * 将分页对象追加至SQL语法中<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param sql
	 *            SQL语句<br/>
	 * @param page
	 *            分页对象<br/>
	 * @return
	 */
	public static String appendPage(StringBuffer sql, Page page) {
		String tmpSql = null;
		if (sql != null && page != null) {
			tmpSql = sql.toString();
		}
		return appendPageSub(tmpSql, page);
	}

	/**
	 * 将分页对象追加至SQL语法中{@link appendPage}<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param sql
	 *            SQL语句<br/>
	 * @param page
	 *            分页对象<br/>
	 * @return
	 */
	public static String appendPageSub(String sql, Page page) {
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

	/**
	 * 获取查询SQL中指定查询列<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param sql
	 *            查询SQL<br/>
	 * @return
	 */
	public static String[] getColumnName(String sql) {
		if (BasUtil.isNullOrEmpty(sql)) {
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
}
