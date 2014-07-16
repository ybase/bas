<%@ page language="java" import="java.util.*,com.ybase.dorm.bas.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@taglib uri="/dorm" prefix="dorm"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />

<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>往事</h1>
    <div>在这里回忆我们的过去</div>
</div>

<!--container-->
<section id="container">
    <div class="container">

        <div class="clear"></div>
        
        <section class="row da-thumbs portfolio filtrable clearfix">
        	<c:choose>
        		<c:when test="${image!=null}">
        			<c:forEach var="item" items="${image}">
		        		<article data-id="id-1" data-type="javascript html" class="span3">
		        			<img src="<%=basePath%>images/upload/<c:out value="${dorm:covertAddStr(item.picPath,'mid')}"/>" alt="photo" />
			                <div>
			                    <a href="javascript:void(0)" class="p-view" onclick="addImgYes('<c:out value="${item.id}"/>')"></a>
			                    <a href="<%=basePath%>dormBlogSingle?imageId=<c:out value="${item.id}"/>&dormPortfolio4" class="p-link"></a>
			                </div>
			            </article>
		        	</c:forEach>
        		</c:when>
        		<c:otherwise>
        			<article data-id="id-1" data-type="javascript html" class="span3">
	        			<img src="<%=basePath%>images/test8.jpg" alt="photo" />
		                <div>
		                    <a href="javascript:void(0)" class="p-view" onclick="javascript:void(0)"></a>
		                    <a href="javascript:void(0)" class="p-link"></a>
		                </div>
		            </article>
		            <article data-id="id-1" data-type="javascript html" class="span3">
	        			<img src="<%=basePath%>images/test8.jpg" alt="photo" />
		                <div>
		                    <a href="javascript:void(0)" class="p-view" onclick="javascript:void(0)"></a>
		                    <a href="javascript:void(0)" class="p-link"></a>
		                </div>
		            </article>
		            <article data-id="id-1" data-type="javascript html" class="span3">
	        			<img src="<%=basePath%>images/test8.jpg" alt="photo" />
		                <div>
		                    <a href="javascript:void(0)" class="p-view" onclick="javascript:void(0)"></a>
		                    <a href="javascript:void(0)" class="p-link"></a>
		                </div>
		            </article>
		            <article data-id="id-1" data-type="javascript html" class="span3">
	        			<img src="<%=basePath%>images/test8.jpg" alt="photo" />
		                <div>
		                    <a href="javascript:void(0)" class="p-view" onclick="javascript:void(0)"></a>
		                    <a href="javascript:void(0)" class="p-link"></a>
		                </div>
		            </article>
		            <article data-id="id-1" data-type="javascript html" class="span3">
	        			<img src="<%=basePath%>images/test8.jpg" alt="photo" />
		                <div>
		                    <a href="javascript:void(0)" class="p-view" onclick="javascript:void(0)"></a>
		                    <a href="javascript:void(0)" class="p-link"></a>
		                </div>
		            </article>
        		</c:otherwise>
        	</c:choose>
        </section>
        <!--pagination-->
        <div class="pagination pagination-centered">
        	<c:set var="pageurl" value="/dormPortfolio4" scope="request"/>
        	<jsp:include page="include/page.jsp" />
        </div>
    </div>
</section>
<script>
	$("#A").removeClass("current");
	$("#C").addClass("current");
</script>
<jsp:include page="include/footer.jsp" />