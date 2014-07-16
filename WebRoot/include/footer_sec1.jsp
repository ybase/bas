<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<footer id="footer">
<div class="container">
    <div class="row">
        <div class="span3">
            <h3>Menu</h3>
           <ul class="menu-widget">
               <li><a>About</a></li>
               <li><a>Elements</a></li>
               <li><a>Blog</a></li>
               <li><a>Portfolio</a></li>
               <li><a>Contacts</a></li>
           </ul>

        </div>
        <div class="span3">
            <h3>Follow Us</h3>
         <!--   <ul class="flickr clearfix"></ul>  -->
            <ul class="menu-widget">
                <li><img class="mim-bottom" src="./images/fba.png" /><img class="mim-top" src="./images/fb.png" /><a class="follow-widget">Facebook</a></li>
                <li><img class="mim-bottom" src="./images/fla.png" /><img class="mim-top" src="./images/fl.png" /><a class="follow-widget">Flickr</a></li>
                <li><img class="mim-bottom" src="./images/twa.png" /><img class="mim-top" src="./images/tw.png" /><a class="follow-widget">Twitter</a></li>
                <li><img class="mim-bottom" src="./images/bla.png" /><img class="mim-top" src="./images/bl.png" /><a class="follow-widget">Blogger</a></li>
                <li><img class="mim-bottom" src="./images/dla.png" /><img class="mim-top" src="./images/dl.png" /><a class="follow-widget">Delicious</a></li>
            </ul>
        </div>
        <div class="span3">
            <h3>Contact Form</h3>

            <form id="contact" class="form-horizontal" method="post" />
                <div class="control-group">
                    <label class="control-label" for="inputName">Name</label>

                    <div class="controls">
                        <input type="text" id="inputName" placeholder="Name" name="inputName" />
                        <label class="ferror" for="inputName" id="fname_error">Name is required.</label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputEmail">Email</label>

                    <div class="controls">
                        <input type="text" id="inputEmail" placeholder="Email" name="inputEmail" />
                        <label class="ferror" for="inputEmail" id="femail_error">Email is required.</label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputMessage"></label>

                    <div class="controls">
                        <textarea rows="3" id="inputMessage" name="inputMessage"></textarea>
                        <label class="ferror" for="inputMessage" id="fmessage_error">Message is required.</label>
                    </div>
                </div>
                <div class="submit-div">
                    <input type="submit" class="btn pull-right footer-button" value="SUBMIT!" />
                </div>
            </form>
        </div>
        <div class="span3">
            <h3>Address</h3>
            <address>
                Companyname inc.<br />
                London, Baker Street, 90210<br />
                <i class="myicon-phone"></i>(123) 456-7890<br />
                <i class="myicon-mail"></i>info@dxthemes.com
            </address>
            Ut eu nulla eget massa blandit eleifend vel sedis lacus. Sed at viverra nulla. Fusce vel adipisci odio.
            Phasellus commodo, mauris eget pharetra scelerisque, est justo lacinia tortor.
        </div>
    </div>
</div>
</footer>