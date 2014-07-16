<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>¿ªÌâ</h1>
    <div>You are here: &nbsp&nbsp<a href="#">Home</a> &nbsp/&nbsp Contact</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article>
                    <h3>Contact us</h3>
                    <p>
                        Etiam imperdiet, ante at porttitor rutrum, felis nisi pellentesque enim, vel viverra quam orci eu massa. 
                        Integer commodo, velit eget dapibus tincidunt, mauris ligula porttitor sapien, eget rhoncus dolor mi eu velit. 
                        Fusce mattis dui eu velit fringilla viverra. Aliquam non nibh non orci imperdiet venenatis. 
                        Quisque varius orci a lacus adipiscing id tempus elit pharetra. 
                    </p>
                </article>  

                <hr />
                
                <form name="contact" method="post" action="contact.html" class="af-form" id="af-form" />
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
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>Address</h4>             
                    </div>
                    <address>
                        <strong>Organization Name</strong><br />
                        Street Name 432/2<br />
                        London, 90210<br />
                        <abbr title="Phone">P:</abbr> (123) 456-7890
                      </address>
                    <address>
                        <strong>Full Name</strong><br />
                        <a href="mailto:#">info@email.com</a>
                    </address>
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
                                count: 2,
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