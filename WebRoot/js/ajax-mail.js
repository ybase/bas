// Content Contact Form
$(function () {
	layer.use('extend/layer.ext.js', function(){});
	
	if(login === "true"){
		$('#login').hide();
		$('#logout').show();
	}else{
		$('#logout').hide();
		$('#login').show();
	}
    $('.error').hide();
    $('.text-input').css({backgroundColor:"#FFFFFF"});
    $('.text-input').focus(function () {
        $(this).css({backgroundColor:"#FCFCFC"});
    });
    $('.text-input').blur(function () {
        $(this).css({backgroundColor:"#FFFFFF"});
    });

    $(".form-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('.error').hide();

        var name = $("input#name").val();
        if (name == "") {
            $("label#name_error").show();
            $("input#name").focus();
            return false;
        }
        
        var message = $("#input-message").val();
        if (message == "") {
            $("label#message_error").show();
            $("#input-message").focus();
            return false;
        }

    });
    
    $(".update-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('.error').hide();

        var oldpass = $("input#oldpass").val();
        if (oldpass == "") {
            $("label#oldpass_error").show();
            $("input#oldpass").focus();
            return false;
        }
        
        var newpass = $("input#newpass").val();
        if (newpass == "") {
            $("label#newpass_error").show();
            $("input#newpass").focus();
            return false;
        }
        
        var cfpass = $("input#cfpass").val();
        if (cfpass == "") {
            $("label#cfpass_error_1").show();
            $("input#cfpass").focus();
            return false;
        }else if(cfpass !== newpass){
        	$("label#cfpass_error_2").show();
            $("input#cfpass").focus();
            return false;
        }
        
    });
    
    $("#passwd-button").click(function(){
    	window.location.href = context_path + "dormPass";
    	return false;
    });
    
    $("#login-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('.error').hide();

        var email = $("input#usrEmail").val();
        var filter = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
        console.log(filter.test(email));
        if (!filter.test(email)) {
            $("input#usrEmail").focus();
            return false;
        }
        
        var passwd = $("input#usrPasswd").val();
        if (passwd == "") {
            $("input#usrPasswd").focus();
            return false;
        }
        
        $.ajax({
            type:"POST",
            url:context_path + "/login",
            data:{"usrPasswd":passwd,"usrEmail":email},
            dataType:"json",
            timeout:60000,
            success:function (data) {
            	$("#log-usr-name").text(data.show);
            	layer.alert(data.tip,9);
            	if(data.status === "1"){
            		$('#login').hide();
            		$('#logout').show();
            	}
            },
            error:function(err){
            	layer.alert("登录失败",8);
            }
        });
        return false;
    });
    
    $("#logout-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('.error').hide();

        $.ajax({
            type:"POST",
            url:context_path + "/logout",
            dataType:"json",
            timeout:60000,
            success:function (data) {
            	layer.alert("注销成功",9,function(){window.location.href=context_path + "dormIndex";});
            },
            error:function(err){
            	layer.alert("注销失败",8);
            }
        });
        return false;
    });
    
    $("#input-msg").on("keydown", function(e){
    	if (e.keyCode == 13 && e.ctrlKey) {
    		var current = $(this).attr("current");
        	var first = $(this).attr("first");
        	var last = $(this).attr("last");
    		$.ajax({
                type:"POST",
                url:context_path + "/addTalk",
                data:{"blogId":$(this).attr("blog"),"talkDesc":$(this).val()},
                contentType: "application/x-www-form-urlencoded",
                dataType:"json",
                timeout:60000,
                success:function (data) {
                	if(current === "" || $.type(current) == "undefined"){
                		layer.alert(data.tip,9,function(){window.location.href=context_path + "/dormBlog?pageCurrent=1";});
                		return ;
                	}
                	if(first === "" || $.type(first) == "undefined"){
                		layer.alert(data.tip,9,function(){window.location.href=context_path + "/dormBlog?pageCurrent=1";});
                		return ;
                	}
                	if(last === "" || $.type(last) == "undefined"){
                		layer.alert(data.tip,9,function(){window.location.href=context_path + "/dormBlog?pageCurrent=1";});
                		return ;
                	}
                	layer.alert(data.tip,9,function(){window.location.href=context_path + "/dormBlog?pageCurrent=" + current + "&pageFirst=" + first + "&pageLast=" + last;});
                },
                error:function(err){
                	layer.alert("添加失败",8);
                }
            });
        }
    });
});

// Footer Contact Form
$(function () {

    $('.ferror').hide();

    $(".footer-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('.ferror').hide();

        var name = $("#inputName").val();
        if (name == "") {
            $("label#fname_error").show();
            $("#inputName").focus();
            return false;
        }
        var message = $("#inputMessage").val();
        if (message == "") {
            $("label#fmessage_error").show();
            $("#inputMessage").focus();
            return false;
        }

    });
    
});

/*
 * 分页请求
 */
function pageRequest(url, pageCurrent, pageFirst, pageLast) {
	window.location.href = context_path + url + "?pageCurrent="+pageCurrent+"&pageFirst=" + pageFirst + "&pageLast=" + pageLast;
}

function addYes(blogId){
	$.ajax({
        type:"POST",
        url:context_path + "/addYes",
        data:{"blogId":blogId},
        contentType: "application/x-www-form-urlencoded",
        dataType:"json",
        timeout:60000,
        success:function (data) {
        	if(data.status == "0"){
        		layer.alert(data.tip,9);
        	}else{
        		$("#blogYes").text(data.show);
        	}
        },
        error:function(err){
        	layer.alert("点赞失败",8);
        }
    });
}

function topRequest(topId,type){
	$.ajax({
        type:"POST",
        url:context_path + "/ynTop",
        data:{"topId":topId,"type":type},
        contentType: "application/x-www-form-urlencoded",
        dataType:"json",
        timeout:60000,
        success:function (data) {
        	if(data.status == "0"){
        		layer.alert(data.tip,9);
        	}else{
        		if(type === "Y"){
        			$("#topY").text(data.show1);
        		}else{
        			$("#topN").text(data.show2);
        		}
        	}
        },
        error:function(err){
        	layer.alert("点赞失败",9);
        }
    });
}

function addYnTalk(talkId,type){
	$.ajax({
        type:"POST",
        url:context_path + "/ynTalk",
        data:{"talkId":talkId,"type":type},
        contentType: "application/x-www-form-urlencoded",
        dataType:"json",
        timeout:60000,
        success:function (data) {
        	if(data.status == "0"){
        		layer.alert(data.tip,9);
        	}else{
        		if(type === "Y"){
        			$("#topY"+talkId).text(data.show1);
        		}else{
        			$("#topN"+talkId).text(data.show2);
        		}
        	}
        },
        error:function(err){
        	layer.alert("点赞/踩死失败",8);
        }
    });
}

function addImgYes(imageId){
	$.ajax({
        type:"POST",
        url:context_path + "/addImgY",
        data:{"imageId":imageId},
        contentType: "application/x-www-form-urlencoded",
        dataType:"json",
        timeout:60000,
        success:function (data) {
        	if(data.status == "0"){
        		layer.alert(data.tip,9);
        	}
        },
        error:function(err){
        	layer.alert("点赞失败",8);
        }
    });
}

function dispatchBlog(param, url){
	window.location.href= context_path + "dormBlogSingle?imageId=" + param + "&redirectURL=" + url;
}

function alertFengYun(){
	layer.prompt({title:"争霸简介",type: 3}, function(val){
		if($.trim(val) !== '' ){
			layer.prompt({title:"争霸名称"}, function(val2){
				if($.trim(val) !== '' ){
					$.ajax({
		                type:"POST",
		                url:context_path + "/topAdd",
		                data:{"topDesc":$.trim(val),"topName":val2},
		                contentType: "application/x-www-form-urlencoded",
		                dataType:"json",
		                timeout:60000,
		                success:function (data) {
		                	if(data.status == "0"){
		                		layer.alert(data.tip,9);
		                	}else{
		                		layer.alert(data.tip,8);
		                	}
		                	
		                },
		                error:function(err){
		                	layer.alert("添加失败",8);
		                }
		            });
				}else{
					layer.alert("名称为空",8);
				}
			});
		}else{
        	layer.alert("描述为空",8);
        }
    });
}

function alertAddPlan(){
	 layer.prompt({title:"添加计划",type: 3}, function(val){
	        if($.trim(val) !== '' ){
	        	$.ajax({
	                type:"POST",
	                url:context_path + "/planAdd",
	                data:{"planDesc":$.trim(val)},
	                contentType: "application/x-www-form-urlencoded",
	                dataType:"json",
	                timeout:60000,
	                success:function (data) {
	                	if(data.status == "0"){
	                		layer.alert(data.tip,9);
	                		if($("#planWhere span").length >= 4){
	                			$("#planWhere span:first").remove();
	                			$("#planWhere br:first").remove();
	                		}
	                		
	                		$("<span>").text(splitSomeChar(val,20)).appendTo($("#planWhere"));
	                		$("<br/>").appendTo($("#planWhere"));
	                	}else{
	                		layer.alert(data.tip,8);
	                	}
	                	
	                },
	                error:function(err){
	                	layer.alert("添加失败",8);
	                }
	            });
	        }else{
	        	layer.alert("计划描述为空",8);
	        }
	    });
}

function splitSomeChar(str,len){
	   var str_length = 0;
	   var str_len = 0;
	      str_cut = new String();
	      str_len = str.length;
	      for(var i = 0;i<str_len;i++)
	     {
	        a = str.charAt(i);
	        str_length++;
	        if(escape(a).length > 4)
	        {
	         //中文字符的长度经编码之后大于4
	         str_length++;
	         }
	         str_cut = str_cut.concat(a);
	         if(str_length>=len)
	         {
	         str_cut = str_cut.concat("...");
	         return str_cut;
	         }
	    }
	    //如果给定字符串小于指定长度，则返回源字符串；
	    if(str_length<len){
	     return  str;
	    }
}

function alertNetTv(){
	$.layer({
	       type: 2,
	       maxmin: true,
	       title: '网络电视',
	       area: ['580px', '350px'],
	       iframe: {
	           src: 'http://t.cn/zW9SbfS'
	       }
	});
}



function alertTemplate(){
	var loginhtml, validation = function(){
	    //验证写在这
	    $('.BB_button').on('click', function(){
	        return false; //此处只作演示
	    });
	};
	var page = {};
	layer.login = function(options){
	    options = options || {};
	    $.layer({
	        type: 1,
	        title: '添加计划',
	        offset: [($(window).height() - 290)/2+'px', ''],
	        border : [5, 0.5, '#666'],
	        area: ['450px','320px'],
	        shadeClose: true,
	        page: page
	    });
	};
	
	//如果已经请求过，则直接读取缓存节点
    if(loginhtml){
        page.html = loginhtml;
    } else {
        page.url = 'alert/addplan.html'
        page.ok = function(datas){
            loginhtml = datas; //保存登录节点
            validation();
        }
    }
    layer.login();   
}






