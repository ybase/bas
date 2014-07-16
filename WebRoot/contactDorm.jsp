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
    <h1>开题</h1>
    <div>在这里发起讨论主题</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article>
                    <h3>开题事项</h3>
                    <p>
                    在您发起讨论主题之前，您需要了解发起主题前后的一些流程。首先您可以在下方填写相关的主题信息，开始一个讨论主题；创建主
                    题之后，您可以再讨论区查看您刚才发起的讨论主题，每个讨论主题下方都有点赞，回复主题留言。 如果您不想发起一个新的讨论主
                    题，您可以直接在讨论区，选择您喜欢的讨论主题，查看讨论主题的最新动向，或者对讨论主题进行留言。	您也可以再往事区查看过
                    往图片，选择你喜好的往事图片，点击图片链接进入相关讨论主题。祝您开题愉快！
                    </p>
                </article>  

                <hr />
                
                <form name="contact" method="post" action="<%=basePath%>addBlog" class="af-form" id="af-form" enctype="multipart/form-data"/>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="name" id="name_label">主题:</label>
                            <input type="text" name="name" id="name" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="name" id="name_error">必填.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="file" id="file_label">图片:</label>
                            <input type="file" name="file" id="file" size="30" value=""  />
                             <label class="error" for="file" id="file_error">选择图片.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="input-message" id="message_label">描述:</label>
                            <textarea name="message" id="input-message" cols="30" class="text-input"></textarea>
                            <label class="error" for="input-message" id="message_error">必填.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <input type="submit" name="submit" class="form-button btn" id="submit_btn" value="提交主题" />
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
			String prevText = (String)request.getAttribute("prevText");
			if(prevText != null){
				%>
			 layer.alert("<%= prevText%>",9);
			<% }%>
		
	});
	$("#A").removeClass("current");
	$("#E").addClass("current");
</script>
}
<jsp:include page="include/footer.jsp" />