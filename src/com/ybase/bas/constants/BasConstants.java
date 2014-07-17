package com.ybase.bas.constants;

/**
 * bas 常量类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class BasConstants {
	/** 请求范围 */
	public static final int SCOPE_REQ = 0;
	/** 响应范围 */
	public static final int SCOPE_RESP = 1;
	/** 会话范围 */
	public static final int SCOPE_SESSION = 2;
	/** 应用范围 */
	public static final int SCOPE_APPL = 3;

	/** 响应状态码-0(正确) */
	public static final String DISP_STATUS_CRR = "0";
	/** 响应状态码-1(错误) */
	public static final String DISP_STATUS_ERR = "1";

	/** SQL 关系运算符 */
	public static final String SQL_Eq = "=";
	public static final String SQL_Eq_KEY = "Eq";
	public static final String SQL_Gt = ">";
	public static final String SQL_Gt_KEY = "Gt";
	public static final String SQL_Lt = "<";
	public static final String SQL_Lt_KEY = "Lt";
	public static final String SQL_EGt = ">=";
	public static final String SQL_EGt_KEY = "Eg";
	public static final String SQL_ELt = "<=";
	public static final String SQL_ELt_KEY = "El";
	public static final String SQL_NEq = "<>";
	public static final String SQL_NEq_KEY = "Nq";
	public static final String[][] SQL_OPCHAR_MAP = { { SQL_Eq_KEY, SQL_Gt_KEY, SQL_Lt_KEY, SQL_EGt_KEY, SQL_ELt_KEY, SQL_NEq_KEY }, { SQL_Eq, SQL_Gt, SQL_Lt, SQL_EGt, SQL_ELt, SQL_NEq } };

	/** SQL 语句操作符 */
	public static final String SQL_SELECT = "select";
	public static final String SQL_UPDATE = "update";
	public static final String SQL_DELETE = "delete";
	public static final String SQL_INSERT = "insert";

	/** SQL 语法关键字 */
	public static final String SQL_WHERE = "where";
	public static final String SQL_SET = "set";
	public static final String SQL_VALUES = "values";

	/** SQL 占位符 */
	public static final String SQL_MARK_QUESTION = "?";

	/** SQL 连接条件 */
	public static final Integer SQL_AND = 0;
	public static final Integer SQL_OR = 1;

	/** SQL 排序符号 */
	public static final String SQL_SORT_ASC = "asc";
	public static final String SQL_SORT_DESC = "desc";

	/** SQL 算数运算符 */
	public static final String SQL_EXP_ADD = "+";
	public static final String SQL_EXP_SUB = "-";
	public static final String[] SQL_EXP = { SQL_EXP_ADD, SQL_EXP_SUB };

	/** XML SQL 节点 */
	public static final String XML_NODE_ROOT = "sqlxml";
	public static final String XML_NODE_SQL = "sql";
	public static final String XML_NODE_OP = "op";
	public static final String XML_NODE_AS = "as";
	public static final String XML_NODE_WHERE = "where";
	public static final String XML_NODE_AND = "and";
	public static final String XML_NODE_OR = "or";

	/** Scan Service 后缀 */
	public static final String MANGET_IMPL = "Impl";

	/** XML SQL 模板路径 */
	public static final String XML_SQL_PATH = "/sql-template.xml";
	public static final String SCAN_PACKAGE_PATH = "/scan-package.properties";
	public static final String BAS_MESSAGE_URL = "bas-message.properties";
	public static final String WEB_MESSAGE_URL = "/web-message.properties";
	public static final String COMMON_MESSAGE_URL = "/common-message.properties";
	public static final String GETTER_PREFIX = "get";
	public static final String SETTER_PREFIX = "set";

}
