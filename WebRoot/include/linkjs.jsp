<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<meta name="viewport" content="width=100%,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<link rel="icon" href="images/favicon.png" type="image/png" />
<link rel="shortcut icon" href="images/favicon.png" type="image/png" />
<title>Home</title>
<link href="<%=basePath %>css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/style.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>css/alert.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="layer/layer.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.quicksand.js"></script>
<script type="text/javascript" src="js/superfish.js"></script>
<script type="text/javascript" src="js/hoverIntent.js"></script>
<script type="text/javascript" src="js/jquery.hoverdir.js"></script>
<script type="text/javascript" src="js/jflickrfeed.min.js"></script>
<script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
<script type="text/javascript" src="js/jquery.elastislide.js"></script>
<!-- <script type="text/javascript" src="js/jquery.tweet.js"></script> -->
<script type="text/javascript" src="js/smoothscroll.js"></script>
<script type="text/javascript" src="js/jquery.ui.totop.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/ajax-mail.js"></script>
<script type="text/javascript" src="js/accordion.settings.js"></script>
<script type="text/javascript" src="js/jquery.flexslider-min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />