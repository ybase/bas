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
    <h1>讨论</h1>
    <div>在这里您可以畅所欲言</div>
</div>
<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignleft span9">
            	<c:choose>
            		<c:when test="${blog!=null}">
            			<article class="blog-post">
		                    <a href="#"><img src="<%=basePath%>images/upload/<c:out value="${blog.picPath}"/>" alt="photo" /></a>
		                    <h2 class="post-title"><a href="#"><c:out value="${blog.theme}" /></a></h2>
		                    <ul class="meta clearfix">
		                        <li><c:out value="${dorm:dtformat(blog.crDate,blog.crTime)}"/></li>
		                        <li><a href="#"><c:out value="${blog.usrName}" /></a>创建</li>
		                        <li>被点赞<a href="javascript:void(0)" id="blogYes"><c:out value="${blog.yesCount}" /></a>次</li>
		                    </ul>
		                    <p><c:out value="${blog.blogDesc}"></c:out></p>
		                    <a href="javascript:addYes('<c:out value="${blog.id}"/>');" class="read-more">赞</a>
		                </article>
            		</c:when>
            		<c:otherwise>
            			<article class="blog-post">
		                    <a href="#"><img src="<%=basePath%>images/test9.jpg" alt="photo" /></a>
		                    <h2 class="post-title"><a href="#">您上传的主题将在此处显示，请您到开题处，上传论题!!!</a></h2>
		                    <ul class="meta clearfix">
		                        <li>0000年00月00日  00:00:00</li>
		                        <li><a href="#">【创建人名称】</a>创建</li>
		                        <li>被点赞<a href="javascript:void(0)" id="blogYes">N</a>次</li>
		                    </ul>
		                    <p><c:out value="${blog.blogDesc}"></c:out></p>
		                    <a href="javascript:void(0)" class="read-more">赞</a>
		                </article>
            		</c:otherwise>
            	</c:choose>

				<c:if test="${talk!=null}">
				<c:forEach items="${talk}" var="row">
					<hr class="soft"/>
	                <article class="blog-post">
	                    <ul class="meta clearfix">
	                        <li><c:out value="${dorm:dformat(row.crDate)}" />&nbsp;&nbsp;<c:out value="${dorm:tformat(row.crTime)}" /></li>
	                        <li><a href="javascript:addYnTalk('<c:out value="${row.id}"/>','Y')">赞</a><font id="topY<c:out value="${row.id}"/>"><c:out value="${row.yesCount}" /></font></li>
	                        <li><a href="javascript:addYnTalk('<c:out value="${row.id}"/>','N')">踩</a><font id="topN<c:out value="${row.id}"/>"><c:out value="${row.noCount}" /></font></li>
	                    </ul>
	                    <p><c:out value="${row.talkDesc}"/></p>
	                </article>
				</c:forEach>
				</c:if>
                
                 <hr class="soft"/>
                 
                <article class="blog-post">
	                <div class="af-outer af-required">
	                        <div class="af-inner">
	                            <label for="input-message" id="message_label">留一笔:</label>
	                            <c:choose>
	                            	<c:when test="${blog!=null}">
	                            		<textarea name="message" id="input-msg" cols="30" class="text-input" blog="<c:out value="${blog.id}"/>" current="<c:out value="${page.current}"/>" first="<c:out value="${page.first}"/>" last="<c:out value="${page.last}"/>" ></textarea>
	                            	</c:when>
	                            	<c:otherwise>
	                            		<textarea name="message" id="input-msg" cols="30" class="text-input" blog="<c:out value="${blog.id}"/>" disabled="disabled">此处填写您对论题的留言!!!</textarea>
	                            	</c:otherwise>
	                            </c:choose>
	                            <label class="error" for="input-message" id="message_error">留言为空</label>
	                        <p>(CTRL + ENTER 发表留言)</p>
	                        </div>
	                </div>
  				</article>
  				
                <hr />

                <!--pagination-->
                <div class="pagination pagination-centered">
                	<c:set var="pageurl" value="/dormBlog" scope="request"/>
                    <jsp:include page="include/page.jsp" />
                </div>
            </section>
            <!--sidebar-->
            <aside id="sidebar" class="alignright span3">

                <section class="post-widget">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>热门论题</h4>             
                    </div>
                    <ul class="clearfix">
                    	<c:choose>
                    		<c:when test="${fiveMap!=null}">
                    			<c:forEach var="item" items="${fiveMap}">
	                    			<li>
	                    				<dorm:map items="${item}" key="theme" />
			                            <a href="#"><img src="<%=basePath%>images/upload/<c:out value="${dorm:covertAddStr(blog.picPath,'sma')}"/>" alt="photo" /></a>
			                            <a href="#"><c:out value="${dorm:contact(item['theme'],item['blogdesc'])}" escapeXml="false" />  </a>
			                            <p> 被赞    <dorm:map items="${item}" key="yescount" /> 次</p>
			                            <div class="clear"></div>
			                        </li>
		                    	</c:forEach>
                    		</c:when>
                    		<c:otherwise>
                    				<li>
			                            <a href="#"><img src="<%=basePath%>images/test11.jpg" alt="photo" /></a>
			                            <a href="#"> 此处将展示热门论题(被赞次数最多)  </a>
			                            <p> 被赞    N 次</p>
			                            <div class="clear"></div>
			                        </li>
                    		</c:otherwise>
                    	</c:choose>
                    </ul>
                </section>
                <!--twitter -->
                <section id="twitter-sidebar">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>YBASE</h4>             
                    </div>
                    <div class="twitter"></div>
                </section>
            </aside>
        </div>
    </div>
</section>
<script>
	$("#A").removeClass("current");
	$("#D").addClass("current");
</script>
<jsp:include page="include/footer.jsp" />