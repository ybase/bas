<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>Blog</h1>
    <div>You are here: &nbsp&nbsp<a href="#">Home</a> &nbsp/&nbsp Blog</div>
</div>
<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignleft span9">
                <article class="blog-post">
                    <a href="#"><img src="example/blog2.jpg" alt="photo" /></a>
                    <h2 class="post-title"><a href="#">Lorem ipsum dolor sit amet consectetur</a></h2>
                    <ul class="meta clearfix">
                        <li>01/09/2012</li>
                        <li>By: <a href="#">DXThemes</a></li>
                        <li>Comments: <a href="#">3</a></li>
                        <li>Posted in: <a href="#">News</a> | <a href="#">Business</a></li>
                    </ul>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                        Morbi posuere metus ut sem pellentesque nec ullamcorper leo sollicitudin. 
                        Sed adipiscing placerat eros id tincidunt. Donec eu luctus tortor.
                        Posuere metus ut sem pellentesque nec ullamcorper leo 
                    </p>
                    <a href="#" class="read-more">Read More...</a>
                </article>

                <hr />

                <article class="blog-post">
                    <h2><a href="#">Lorem ipsum dolor sit amet consectetur</a></h2>
                    <ul class="meta clearfix">
                        <li>01/09/2012</li>
                        <li>By: <a href="#">DXThemes</a></li>
                        <li>Comments: <a href="#">3</a></li>
                        <li>Posted in: <a href="#">News</a> | <a href="#">Business</a></li>
                    </ul>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                        Morbi posuere metus ut sem pellentesque nec ullamcorper leo sollicitudin. 
                        Sed adipiscing placerat eros id tincidunt. Donec eu luctus tortor.
                        Posuere metus ut sem pellentesque nec ullamcorper leo 
                    </p>
                    <a href="#" class="read-more">Read More ...</a>
                </article>

                <hr />

                <article class="blog-post">
                    <h2><a href="#">Lorem ipsum dolor sit consectetur</a></h2>
                    <ul class="meta clearfix">
                        <li>01/09/2012</li>
                        <li>By: <a href="#">DXThemes</a></li>
                        <li>Comments: <a href="#">3</a></li>
                        <li>Posted in: <a href="#">News</a> | <a href="#">Business</a></li>
                    </ul>
                    <blockquote>
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                            Morbi posuere metus ut sem pellentesque nec ullamcorper leo sollicitudin. 
                            Sed adipiscing placerat eros id tincidunt. Donec eu luctus tortor.
                        </p>
                    </blockquote>
                </article>

                <hr />

                <article class="blog-post">
                    <div class="video">
                        <iframe src="http://player.vimeo.com/video/38514156" width="" height="" frameborder="0" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen=""></iframe>
                    </div>
                    <h2><a href="#">Post With Slider</a></h2>
                    <ul class="meta clearfix">
                        <li>01/09/2012</li>
                        <li>By: <a href="#">DXThemes</a></li>
                        <li>Comments: <a href="#">3</a></li>
                        <li>Posted in: <a href="#">News</a> | <a href="#">Business</a></li>
                    </ul>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                        Morbi posuere metus ut sem pellentesque nec ullamcorper leo sollicitudin. 
                        Sed adipiscing placerat eros id tincidunt. Donec eu luctus tortor.
                        Posuere metus ut sem pellentesque nec ullamcorper leo 
                    </p>
                    <a href="#" class="read-more">Read More ...</a>
                </article>
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
            </section>
            <!--sidebar-->
            <aside id="sidebar" class="alignright span3">

                <section class="post-widget">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>Popular Post Widget</h4>             
                    </div>
                    <ul class="clearfix">
                        <li>
                            <a href="#"><img src="example/sidebar1.jpg" alt="photo" /></a>
                            <a href="#">Ut in lacus rhoncus elit egesta sluctus. Nullam at</a>
                            <p>comments 2</p>
                            <div class="clear"></div>
                        </li>
                        <li>
                            <a href="#"><img src="example/sidebar2.jpg" alt="photo" /></a>
                            <a href="#">Ut in lacus rhoncus elit egesta sluctus. Nullam at</a>
                            <p>comments 5</p>
                            <div class="clear"></div>
                        </li>
                        <li>
                            <a href="#"><img src="example/sidebar3.jpg" alt="photo" /></a>
                            <a href="#">Ut in lacus rhoncus elit egesta sluctus. Nullam at</a>
                            <p>comments 3</p>
                            <div class="clear"></div>
                        </li>
                    </ul>
                </section>
                <!--twitter -->
                <section id="twitter-sidebar">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>Twitter</h4>             
                    </div>
                    <div class="twitter"></div>
                    <script type="text/javascript">
                        $(document).ready(function(){
                            $(".twitter").tweet({
                                  join_text: "auto",
                                  username: "twitter",
                                  avatar_size:40,
                                  count: 5,
                                  auto_join_text_default: "we said,",
                                  auto_join_text_ed: "we",
                                  auto_join_text_ing: "we were",
                                  auto_join_text_reply: "we replied",
                                  auto_join_text_url: "we were checking out",
                                  loading_text: "loading tweets..."
                            });
                        });
                    </script>
                </section>
            </aside>
        </div>
    </div>
</section>
<jsp:include page="include/footer.jsp" />