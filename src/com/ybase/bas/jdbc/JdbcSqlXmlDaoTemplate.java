package com.ybase.bas.jdbc;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.exception.BasException;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.util.JdbcUtil;
import com.ybase.bas.util.MessageUtil;
import com.ybase.bas.vo.Page;

public class JdbcSqlXmlDaoTemplate {
	private static final Logger log = Logger.getLogger(JdbcSqlXmlDaoTemplate.class);
	private static DocumentBuilderFactory docBuilderFac = null;
	private static DocumentBuilder docBuilder = null;
	private static Document xmlSql = null;
	private static Element root = null;
	static {
		try {
			docBuilderFac = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFac.newDocumentBuilder();
			xmlSql = docBuilder.parse(JdbcSqlXmlDaoTemplate.class.getResourceAsStream(BasConstants.XML_SQL_PATH));
			root = xmlSql.getDocumentElement();
			log.info(MessageUtil.getBasText("jdbc-xmlsql-loadsucc"));
		} catch (Exception e) {
			log.info(MessageUtil.getBasText("jdbc-xmlsql-loadfail"));
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据XmlSQL主标识|分页对象，获取SQL语句和查询字段列表<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param id
	 *            主标识<br/>
	 * @param page
	 *            分页对象<br/>
	 * @return
	 * @throws BasException
	 */
	public static Object[] getXmlSqlById(String id, Page page) throws BasException {
		Object[] rst = getXmlSqlById(id);
		if (rst.length > 0) {
			if (page != null) {
				rst[0] = JdbcUtil.appendPageSub((String) rst[0], page);
			}
			return rst;
		} else {
			throw new BasException("获取sql xml 失败");
		}
	}

	/**
	 * 根据XmlSQL主标识，获取SQL语句和查询字段列表<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param id
	 *            XmlSQL主标识<br/>
	 * @return
	 * @throws BasException
	 */
	public static Object[] getXmlSqlById(String id) throws BasException {
		if (BasUtil.isNullOrEmpty(id)) {
			throw new BasException(BasErrCode.E10036);
		}

		NodeList sqls = root.getElementsByTagName(BasConstants.XML_NODE_SQL);
		for (int i = 0; i < sqls.getLength(); i++) {
			Node node = sqls.item(i);
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null && attrs.getLength() > 0) {
				for (int j = 0; j < attrs.getLength(); j++) {
					Attr attr = (Attr) attrs.item(j);
					if ("id".equals(attr.getName()) && id.equals(attr.getValue())) {
						NodeList childs = node.getChildNodes();
						return dealSqlSyntax(childs);
					}
				}
			} else {
				throw new BasException(BasErrCode.E10037);
			}
		}
		throw new BasException(BasErrCode.E10038, id);
	}

	/**
	 * 解析指定SQL标签为SQL语句和获取查询字段列表<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param childs
	 *            SQL节点下子节点<br/>
	 * @return
	 * @throws BasException
	 */
	private static Object[] dealSqlSyntax(NodeList childs) throws BasException {
		StringBuffer sql = new StringBuffer();
		Vector<String> columns = new Vector<String>();
		for (int i = 0; i < childs.getLength(); i++) {
			Node node = childs.item(i);
			String nodeValue = node.getNodeValue() != null ? BasUtil.trim(node.getNodeValue()) : "";
			int nodeType = node.getNodeType();
			String nodeName = node.getNodeName().trim();
			if (Node.TEXT_NODE == nodeType) {
				if (!"".equals(nodeValue)) {
					sql.append(" ").append(nodeValue.trim());
				}
			} else {
				if (BasConstants.XML_NODE_OP.equals(nodeName)) {
					if (BasUtil.isNullOrEmpty(node.getTextContent())) {
						throw new BasException(BasErrCode.E10039, BasConstants.XML_NODE_OP);
					}
					sql.append(" ").append(node.getTextContent());
				} else if (BasConstants.XML_NODE_AS.equals(nodeName)) {
					if (BasUtil.isNullOrEmpty(node.getTextContent())) {
						throw new BasException(BasErrCode.E10039, BasConstants.XML_NODE_AS);
					}

					columns.add(BasUtil.trim(node.getTextContent()));
					sql.append(" ").append(BasConstants.XML_NODE_AS).append(" ").append(node.getTextContent());
					if (node.hasAttributes()) {
						sql.append(" ");
					} else {
						sql.append(",");
					}
				} else if (BasConstants.XML_NODE_WHERE.equals(nodeName)) {
					sql.append(" ").append(BasConstants.XML_NODE_WHERE);
					apendSqlCon(sql, node);
				} else {
					throw new BasException(BasErrCode.E10040);
				}
			}
		}

		log.debug(MessageUtil.getBasText("jdbc-xmlsql-sqlstr", sql.toString()));
		log.debug(MessageUtil.getBasText("jdbc-xmlsql-colstr", columns.toArray().toString()));
		return new Object[] { sql.toString(), columns };
	}

	/**
	 * 前置SQL语句追加Where子句<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 * @param sql
	 *            前置SQL<br/>
	 * @param parent
	 *            where节点<br/>
	 * @throws BasException
	 */
	private static void apendSqlCon(StringBuffer sql, Node parent) throws BasException {
		NodeList nodes = parent.getChildNodes();
		int count = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String nodeName = node.getNodeName().trim();
			if (BasConstants.XML_NODE_AND.equals(nodeName)) {
				if (BasUtil.isNullOrEmpty(node.getTextContent())) {
					throw new BasException(BasErrCode.E10039, BasConstants.XML_NODE_AND);
				}

				if (count != 0) {
					sql.append(" ").append(BasConstants.XML_NODE_AND);
				}
				sql.append(" ").append(node.getTextContent());
				count++;
			} else if (BasConstants.XML_NODE_OR.equals(nodeName)) {
				if (BasUtil.isNullOrEmpty(node.getTextContent())) {
					throw new BasException(BasErrCode.E10039, BasConstants.XML_NODE_OR);
				}

				if (count != 0) {
					sql.append(" ").append(BasConstants.XML_NODE_OR);
				}
				sql.append(" ").append(node.getTextContent());
				count++;
			}
		}
	}

}
