<%@ page language="java" import="java.util.*,com.ybase.dorm.bas.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@taglib uri="/dorm" prefix="dorm"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<section id="slider">
    <div class="flexslider">
        <ul class="slides">
        	<c:choose>
        		<c:when test="${tops!=null}">
       				<c:forEach var="it" items="${tops}">
			        	<li>
			                <img src="<%=basePath%>images/upload/<c:out value="${dorm:covertAddStr(it.picPath,'idx')}"/>" />
			            </li>
		        	</c:forEach>
        		</c:when>
        		<c:otherwise>
        			<li>
		                <img src="<%=basePath%>images/error.jpg" />
		            </li>
        		</c:otherwise>
        	</c:choose>
        </ul>
    </div>
</section>
<section id="container">
    <div class="container">
        <!--Recent Projects Block-->
        <div class="row">
            <div class="span3">
                <h3>暮然回首...</h3>
                <p class="hidden-phone">
				不管我们走了多远，回头看看那些走过的岁月，其实也是一种美的享受。
				岁月的记忆，美好的回忆，带上它，去走好明天的路！
                </p>
            </div>
            <div class="span9">
                <div class="carousel btleft" id="latest-work">
                    <div class="carousel-wrapper">
                        <ul class="da-thumbs filtrable" style="width: 1800px; display: block; margin-left: 0px;">
                        	<c:choose>
                        		<c:when test="${classic!=null}">
                        			<c:forEach var="item" items="${classic}">
			                        	<li style="margin-right: 30px; width: 270px;">
			                                <img src="<%=basePath %>images/upload/<c:out value="${dorm:covertAddStr(item.picPath,'mid')}"/>" />
			                                <div style="display: block;" class="da-animate da-slideFromLeft">
			                                	<a class="p-view" href="#" onclick="addImgYes('<c:out value="${item.id}"/>');"></a>
		<!-- 	                                    <a data-rel="prettyPhoto" class="p-view" href="example/view.jpg" rel="prettyPhoto" onclick=""></a> -->
			                                    <a class="p-link" href="#" onclick="dispatchBlog('<c:out value="${item.id}"/>','dormIndex');"></a>
			                                </div>
			                            </li>
						        	</c:forEach>
                        		</c:when>
                        		<c:otherwise>
                        			<li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg " />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
                        		</c:otherwise>
                        	</c:choose>
                        </ul>
                    </div>
                    <div class="es-nav"><span class="es-nav-prev" style="opacity: 0.5;">Previous</span><span class="es-nav-next" style="opacity: 1;">Next</span></div></div>
                <script type="text/javascript">
                    $(document).ready(function(){
                        $('#latest-work').elastislide({
                            imageW  : 270,
                            margin  : 30
                        });
                    });
                </script>
            </div>
        </div>
        <div class="divider">
            <img alt="" src="images/div-bg.jpg" />
        </div>
        <!--3 columns block-->
        <div class="row bottom30">
            <div class="span4 features-block">
                <img class="im-bottom" src="images/icon1a.png" />
                <img class="im-top" src="images/icon1.png" />
                <h3>风云榜</h3>
                <p> 上传您最近遇到的糗事，或者奇葩的事或人，您上传的事或人的描述简介将上传至风云榜，供大家点赞或者踩死。风云榜查看
				                步骤如下：点击开题菜单，进入页面之后，在页面的左侧上边部分可以对风云榜操作
                ...</p>
                <button class="btn btn-read" type="button" onclick="javascript:alertFengYun();">风骚一把</button>
            </div>
            <div class="span4 features-block">

                <img class="im-bottom" src="images/icon2a.png" />
                <img class="im-top" src="images/icon2.png" />
                <h3>看一看</h3>
                <p>
                	点击下方观看网络电视按钮，讲可以为大家提供CCTV1,CCTV2,CCTV3,等央视
                	频道同步播放，大家可以根据自己的喜欢选择个地方卫视同步播放个节目，
                	由于各种限制CCTV5、CCTV5+等体育频道无法进行同步播放，若大家有好的建议，
                	本站将提供各种娱乐综艺节目的播放。
                </p>
                <button class="btn btn-read" type="button" onclick="javascript:alertNetTv();" id="netTv">观看网络电视</button>
            </div>
            <div class="span4 features-block">

                <img class="im-bottom" src="images/icon3a.png" />
                <img class="im-top" src="images/icon3.png" />
                <h3>41久计划</h3>
                <p id="planWhere">
                	<c:choose>
                		<c:when test="${plans==null}">
                			<span> 您可以在这里添加您的计划！</span><br/>
                			<span> 您可以把你想法告诉我们！</span><br/>
                			<span> 一起分享想象的喜悦！</span><br/>
                			<span> 一起对未来有这美好的憧憬！</span><br/>
                		</c:when>
                		<c:otherwise>
                			<c:forEach items="${plans}" var="plan">
		                		<span><c:out value="${dorm:omitStr(plan.planDesc,30)}"/></span><br/>
		                	</c:forEach>
                		</c:otherwise>
                	</c:choose>
                </p>
                <button class="btn btn-read" type="button" onclick="JavaScript:alertAddPlan();">我想到了一个计划</button>
            </div>
        </div>

</div></section>
<jsp:include page="include/footer.jsp" />