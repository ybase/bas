<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>About Us</h1>
    <div>You are here: &nbsp&nbsp<a href="#">Home</a> &nbsp/&nbsp About Us</div>
</div>
<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page" class="alignright span9">
                <div class="row">
                    <div class="span9">
                        <h3>What We Do?</h3>
                        <p>Duo probo dicunt efficiendi in, cu vim graecis invidunt, nemore patrioque pri id. Mei aliquip menandri ea. Ex pri dictas petentium constituto, cum cu facilis blandit. Dicat summo duo ea, ex sed assum delicatissimi. Has pertinax pericula accommodare ei, diam facer deterruisset eum et. </p>
                        <blockquote>
                            <p>Est audiam urbanitas omittantur an, eum suas tota delectus an. Cu pro graece aliquando abhorreant. An usu sensibus disputationi consectetuer. His ridens tamquam prodesset id.</p>
                            <small>Something Important</small>
                        </blockquote>
                        <img src="images/about1.jpg" />
                    </div>
                </div>
                <hr />
                <div class="row">
                    <div class="span4">
                        <h3>About Us</h3>
                        <p> Cu vis integre placerat vim ut justo reprimique siame voluptatibus, unum doctus abhorreant an sea. Adrob epicuri adversarium usu, et per oblique vocibus ame hendrerit In cum erant insolens, ut mundi oratiograe sedat vel augue putent euismod. Qui cu verear facer antiopam At mea malorum epicuri accumsan Persi deleniti sapientem no vel, nec nobis equidem et. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne. His quod quodsi recteque ut, duis constituam sit no. </p>
                    </div>
                    <div class="span5">
                        <h3>Web Development</h3>
                        <p> Cu vis integre placerat vim ut justo reprimique siame voluptatibus, unum doctus abhorreant an sea. Adrob epicuri adversarium usu, et per oblique vocibus ame hendrerit In cum erant insolens, ut mundi oratiograe sedat vel augue putent euismod. Qui cu verear facer antiopam At mea malorum epicuri accumsan Persi deleniti sapientem no vel, nec nobis equidem et. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne. His quod quodsi recteque ut, duis constituam sit no. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne.</p>
                    </div>
                </div>
                <hr />
                <div class="row">
                    <div class="aboutus">
                        <h3>Qui cu verear facer antiopam At mea malorum epicuri <span class="color2">accumsan Persi</span> deleniti sapientem no vel, nec nobis equidem et.</h3>
                    </div>
                </div>
                <hr />
                <div class="row">
                    <div class="span4">
                        <h3>SEO</h3>
                        <p> Cu vis integre placerat vim ut justo reprimique siame voluptatibus, unum doctus abhorreant an sea. Adrob epicuri adversarium usu, et per oblique vocibus ame hendrerit In cum erant insolens, ut mundi oratiograe sedat vel augue putent euismod. Qui cu verear facer antiopam At mea malorum epicuri accumsan Persi deleniti sapientem no vel, nec nobis equidem et. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne. His quod quodsi recteque ut, duis constituam sit no. </p>
                    </div>
                    <div class="span5">
                        <h3>Mobile website</h3>
                        <p> Cu vis integre placerat vim ut justo reprimique siame voluptatibus, unum doctus abhorreant an sea. Adrob epicuri adversarium usu, et per oblique vocibus ame hendrerit In cum erant insolens, ut mundi oratiograe sedat vel augue putent euismod. Qui cu verear facer antiopam At mea malorum epicuri accumsan Persi deleniti sapientem no vel, nec nobis equidem et. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne. His quod quodsi recteque ut, duis constituam sit no. Ex laoreet persecuti mei, no ius lobortis interesset in appetere tamquam sensibus salutandi sit ne, nec et illum intellegam, mea suas nusquam nominavi ne.</p>
                    </div>
                </div>
            </section>
            <!--sidebar -->
            <aside id="sidebar" class="alignleft span3">
                <section id="text-widget">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>
                        <h4>Text Widget</h4>
                    </div>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Aliquam varius sapien porttitor dui lobortis ut
                        scelerisque lacus pulvinar. Sed condimentum
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Aliquam varius sapien porttitor dui lobortis ut scelerisque lacus pulvinar.
                        Sed condimentum velit a nisi
                    </p>
                </section>
                <!--Recent post widget -->
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>
                        <h4>Recent Post Widget</h4>
                    </div>
                    <ul class="clearfix post-widget">
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
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>
                        <h4>Latest Work</h4>
                    </div>
                    <div id="latestwork-sidebar" class="carousel slide">
                        <div class="carousel-inner">
                            <div class="active item"><img src="example/latest3.jpg" alt="photo" /></div>
                            <div class="item"><img src="example/latest4.jpg" alt="photo" /></div>
                            <div class="item"><img src="example/latest5.jpg" alt="photo" /></div>
                        </div>
                        <a class="carousel-control left" href="#latestwork-sidebar" data-slide="prev"></a>
                        <a class="carousel-control right" href="#latestwork-sidebar" data-slide="next"></a>
                    </div>
                    <script type="text/javascript">
                        $(document).ready(function(){
                            $('.carousel').carousel({
                                interval: 5000
                            })
                        });
                    </script>
                </section>
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>Popular Post Widget</h4>             
                    </div>
                    <ul class="clearfix post-widget">
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
            </aside>
        </div>
    </div>
</section>
<jsp:include page="include/footer.jsp" />