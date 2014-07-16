<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<ul>
	<li <c:if test="${page.current<=page.pageRow-1}">class="disabled"</c:if>><a href="javascript:void(0)">&laquo;</a></li>
	<c:forEach begin="${page.first}" end="${page.last}" var="idx">
		<li <c:if test="${page.current==idx}">class="active"</c:if>><a href="javascript:pageRequest('<c:out value="${pageurl}"/>','<c:out value="${idx}"/>','<c:out value="${page.first}"/>','<c:out value="${page.last}"/>')"><c:out value="${idx}"/></a></li>
	</c:forEach>
    <li <c:if test="${page.current>=page.totalPage-page.pageRow}">class="disabled"</c:if>><a href="javascript:void(0)">&raquo;</a></li>
</ul>