<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
    <h1>密码修改</h1>
    <div>在这里您可以修改用户密码</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article>
                    <h3>修改密码事项</h3>
                    <p>
				                    在您修改密码之前，需要您输入旧密码，若您的账号出现异常请联系系统管理员为您解决问题！
                    </p>
                </article>  

                <hr />
                
                <form name="contact" method="post" action="<%=basePath%>updatePasswd" class="af-form" id="af-form" />
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="oldpass" id="oldpass_label">旧密码:</label>
                            <input type="password" name="oldpass" id="oldpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="oldpass" id="oldpass_error">必填.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="newpass" id="newpass_label">新密码:</label>
                            <input type="password" name="newpass" id="newpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="newpass" id="newpass_error">必填.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="cfpass" id="cfpass_label">确认密码:</label>
                            <input type="password" name="cfpass" id="cfpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="cfpass" id="cfpass_error_1">必填.</label>
                            <label class="error" for="cfpass" id="cfpass_error_2">密码不一致.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <input type="submit" name="submit" class="update-button btn" id="submit_btn" value="提交" />
                        </div>
                    </div>
                </form>
            </section>
            <!--sidebar-->
            <aside id="sidebar" class="alignright span3">
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>武林争霸</h4>             
                    </div>
                    <address>
                        <strong><c:out value="${top.name}"/></strong><br />
                      	<c:out value="${top.topDesc}"/><br />
                      </address>
                    <address>
                        <strong>霸气:<a href="javascript:topRequest('<c:out value="${top.id}" />','Y');" id="topY"><c:out value="${top.yesCount}" /></a>次</strong><br />
                    </address>
                    <address>
                        <strong>吐槽:<a href="javascript:topRequest('<c:out value="${top.id}"/>','N');" id="topN"><c:out value="${top.noCount}" /></a>次</strong><br />
                    </address>
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
<script type="text/javascript">
	$(document).ready(function() {
		<%
			String prevText = (String)request.getAttribute("tip");
			String status = (String)request.getAttribute("status");
			if(prevText != null){
				%>
			 layer.alert("<%= prevText%>",9);
			<% }%>
			 <% if(status !=null && "0".equals(status.trim())){%>
			 	 <% session.invalidate(); %>
				 location.href="<%=basePath%>" + "dormIndex";
			 <%} %>
			
	});
	$("#A").addClass("current");
</script>
}
<jsp:include page="include/footer.jsp" />