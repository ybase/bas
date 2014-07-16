<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>Blog Single</h1>
    <div>You are here: &nbsp&nbsp<a href="#">Home</a> &nbsp/&nbsp Blog Single</div>
</div>
<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article class="blog-post">
                    <h1>Lorem ipsum dolor sit amet consectetur</h1>
                    <ul class="meta clearfix">
                        <li>01/09/2012</li>
                        <li>By: <a href="#">DXThemes</a></li>
                        <li>Comments: <a href="#">3</a></li>
                        <li>Posted in: <a href="#">News</a> | <a href="#">Business</a></li>
                    </ul>
                    <img src="example/blog2.jpg" alt="photo" />
                    <p>
                        Etiam imperdiet, ante at porttitor rutrum, felis nisi pellentesque enim, vel viverra quam orci eu massa. 
                        Integer commodo, velit eget dapibus tincidunt, mauris ligula porttitor sapien, eget rhoncus dolor mi eu velit. 
                        Fusce mattis dui eu velit fringilla viverra. Aliquam non nibh non orci imperdiet venenatis. 
                        Quisque varius orci a lacus adipiscing id tempus elit pharetra. 
                    </p>
                    <blockquote>
                        <p>
                            Etiam imperdiet, ante at porttitor rutrum, felis nisi pellentesque enim, vel viverra quam orci eu massa. 
                            Integer commodo, velit eget dapibus tincidunt, mauris ligula porttitor sapien, eget rhoncus dolor mi eu velit. 
                        </p>
                    </blockquote>
                    <p>
                        Duis et eros at nulla euismod aliquet vitae ac odio. 
                        Nulla porta metus vitae mi ornare quis accumsan ligula congue. 
                        Integer commodo, velit eget dapibus tincidunt, mauris ligula porttitor sapien, eget rhoncus dolor mi eu velit. 
                        Fusce mattis dui eu velit fringilla viverra. Aliquam non nibh non orci imperdiet venenatis. 
                    </p>
                </article>  

                <hr />

                <!--comments-->
                <div class="title-divider">
                    <div class="divider-arrow"></div>                                  
                    <h3>4 Comments</h3>
                </div>
                <div class="comments">
                    <ul class="comments-list">
                        <li class="clearfix comments_li">
                            <img src="images/avatar.png" alt="avatar" class="avatar" />
                            <div class="textarea">
                                <p class="meta">May 18, 2012 Designmd says:</p>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                    Etiam a sapien odio, sit amet 
                                </p>
                            </div>
                            <ul>
                                <li>
                                    <img src="images/avatar.png" alt="avatar" class="avatar" />
                                    <div class="textarea">
                                        <p class="meta">May 17, 2012 bingumd says:</p>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                            </p>
                                        <div class="shadow-comments2"></div>
                                    </div>
                                    <ul>
                                        <li>
                                            <img src="images/avatar.png" alt="avatar" class="avatar" />
                                            <div class="textarea">
                                                <p class="meta">May 17, 2012 bingumd says:</p>
                                                <p>
                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                                </p>
                                                <div class="shadow-comments2"></div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                       	</li>
                        <li>
                            <div><img src="images/avatar.png" alt="avatar" class="avatar" /></div>
                            <div class="textarea lastbt">
                                <p class="meta">May 16, 2012 Designmd says:</p>
                                <p>
                                    scelerisque felis. Maecenas tincidunt ligula eu magna tincidunt eget imperdiet erat malesuada. 
                                    Ut in diam et metus facilisis venenatis sit amet vel enim. 
                                </p>
                            </div>
                        </li>
                    </ul>
                </div>

                <hr />
                
                <!--commetns form-->
                <div class="title-divider">
                    <div class="divider-arrow"></div>                                  
                    <h3>Leave A Reply</h3>       
                </div>
                <form name="comment" method="post" action="" class="af-form" id="af-form" />
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="name" id="name_label">Your Name:</label>
                            <input type="text" name="name" id="name" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="name" id="name_error">Name is required.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="email" id="email_label">Your Email:</label>
                            <input type="text" name="email" id="email" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="email" id="email_error">Email is required.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="input-message" id="message_label">Your Message:</label>
                            <textarea name="message" id="input-message" cols="30" class="text-input"></textarea>
                            <label class="error" for="input-message" id="message_error">Message is required.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <input type="submit" name="submit" class="form-button btn" id="submit_btn" value="Send Message!" />
                        </div>
                    </div>
                </form>
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
                           //twitter
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