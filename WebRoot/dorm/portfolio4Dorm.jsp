<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />

<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>Portfolio 4 Column</h1>
    <div>You are here: &nbsp&nbsp<a href="#">Home</a> &nbsp/&nbsp Portfolio 4 column</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <!--filter-->
        <ul id="filtrable">
            <li class="current all"><a href="#">All</a></li>
            <li class="html"><a href="#">Html</a></li>
            <li class="css"><a href="#">CSS</a></li>
            <li class="php"><a href="#">PHP</a></li>
            <li class="javascript"><a href="#">JavaScript</a></li>
        </ul>

        <div class="clear"></div>
        
        <section class="row da-thumbs portfolio filtrable clearfix">
            <article data-id="id-1" data-type="javascript html" class="span3">
                <img src="example/latest1.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-2" data-type="php" class="span3">
                <img src="example/latest2.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-3" data-type="javascript" class="span3">
                <img src="example/latest3.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-4" data-type="php" class="span3">
                <img src="example/latest4.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-5" data-type="javascript html" class="span3">
                <img src="example/latest5.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-6" data-type="php" class="span3">
                <img src="example/latest6.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-7" data-type="php" class="span3">
                <img src="example/latest7.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-8" data-type="css" class="span3">
                <img src="example/latest8.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-9" data-type="html" class="span3">
                <img src="example/latest4.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-10" data-type="css" class="span3">
                <img src="example/latest3.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-11" data-type="html" class="span3">
                <img src="example/latest2.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
            <article data-id="id-12" data-type="css" class="span3">
                <img src="example/latest1.jpg" alt="photo" />
                <div>
                    <a href="example/view.jpg" class="p-view" data-rel="prettyPhoto"></a>
                    <a href="blog-single.html" class="p-link"></a>
                </div>
            </article>
        </section>
        <!--pagination-->
        <div class="pagination pagination-centered">
            <ul>
                <li class="disabled"><a href="#">&laquo;</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
    </div>
</section>
<jsp:include page="include/footer.jsp" />