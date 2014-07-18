package com.ybase.bas.constants;

/**
 * 错误代码常量类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class BasErrCode {

	/**
	 * 规范 <br/>
	 * bas 错误代码1xxxx<br/>
	 * web 错误代码2xxxx<br/>
	 */

	/** 添加{0}对象失败 */
	public static final String E10001 = "10001";
	/** 关闭ResultSet或者Statement失败 */
	public static final String E10002 = "10002";
	/** 查询对象[{0}]|条件[{1}]失败 */
	public static final String E10003 = "10003";
	/** 查询条数[{0}]|条件[{1}]失败 */
	public static final String E10004 = "10004";
	/** 查询对象[{0}]|条件[{1}]|限制[{2}]失败 */
	public static final String E10005 = "10005";
	/** 查询对象[{0}]|条件[{1}]|单独列[{2}]失败 */
	public static final String E10006 = "10006";
	/** 查询对象[{0}]|条件[{1}]|限制[{2}]|单独列[{3}]失败 */
	public static final String E10007 = "10007";
	/** 查询对象[{0}]|条件[{1}]|排序[{2}]失败 */
	public static final String E10008 = "10008";
	/** 查询对象[{0}]|条件[{1}]|排序[{2}]|单独列[{3}]失败 */
	public static final String E10009 = "10009";
	/** 查询对象[{0}]|条件[{1}]|排序[{2}]|限制[{3}]失败 */
	public static final String E10010 = "10010";
	/** 查询对象[{0}]|条件[{1}]|排序[{2}]|限制[{3}]|单独列[{4}]失败 */
	public static final String E10011 = "10011";
	/** 分页查询对象[{0}]|条件[{1}]|分页对象[{2}]失败 */
	public static final String E10012 = "10012";
	/** 分页查询对象[{0}]|条件[{1}]|分页对象[{2}]|排序[{3}]失败 */
	public static final String E10013 = "10013";

	/** XmlSQL查询主标识[{0}]|分页对象[{1}]|参数[{2}]] */
	public static final String E10014 = "10014";

	/** 更新对象[{0}]失败 */
	public static final String E10015 = "10015";

	/** 解析ResultSet失败，解析列[{0}] */
	public static final String E10016 = "10016";
	/** 操作对象[{0}]为空 */
	public static final String E10017 = "10017";
	/** 操作对象[{0}类，找不到@table配置] */
	public static final String E10018 = "10018";
	/** 操作对象[{0}类，字段[{1}]找不到@Column配置] */
	public static final String E10019 = "10019";
	/** 操作对象[{0}类，字段[{1}]不存在] */
	public static final String E10020 = "10020";
	/** 操作对象[{0}类，字段[{1}]不存在或找不到@Column配置 */
	public static final String E10021 = "10021";
	/** 查询条件Map格式错误，格式应为xxx_Eq->{12,and} xxx_Eq->13 */
	public static final String E10022 = "10022";
	/** 查询条件逻辑运算符只能为and,or */
	public static final String E10023 = "10023";

	/** 操作字段[{0}]为空 */
	public static final String E10024 = "10024";
	/** 操作类型[{0}]存在非法操作运算符[{1}] */
	public static final String E10025 = "10025";
	/** 操作类型[{0}]不支持 */
	public static final String E10026 = "10026";

	/** 操作对象[{0}]未定义主键 */
	public static final String E10027 = "10027";
	/** 操作对象[{0}]回填主键发生异常 */
	public static final String E10028 = "10028";

	/** Statement 关闭|为空 */
	public static final String E10029 = "10029";

	/** 操作对象[{0}] 无法列出插入列失败 */
	public static final String E10030 = "10030";
	/** 操作对象[{0}] 设置占位符值失败 */
	public static final String E10031 = "10031";
	/** 字段类型[{0}]系统不支持 */
	public static final String E10032 = "10032";
	/** 操作对象[{0}]非BasVO衍生类 */
	public static final String E10033 = "10033";
	/** 操作 - [ResultSet映射操作对象[{0}] ] 失败*/
	public static final String E10034 = "10034";
	/** 操作 - [ResultSet获取记录条数] 失败*/
	public static final String E10035 = "10035";
	
	/** XmlSQL主标识为空 */
	public static final String E10036 = "10036";
	/** XmlSQL-SQL标签主标识为空 */
	public static final String E10037 = "10037";
	/** XmlSQL主标识[{0}] 不存在 */
	public static final String E10038 = "10038";
	/** XmlSQL 操作类型[{0}] 值为空 */
	public static final String E10039 = "10039";
	/** XmlSQL-SQL子标签非法 */
	public static final String E10040 = "10040";
	
	/** 条件逻辑判断符不在支持范围(= <> > <等) */
	public static final String E10041 = "10041";

}
