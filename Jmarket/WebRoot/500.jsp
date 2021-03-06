<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <title>出错啦</title>
    <link rel="shortcut icon" href="<%=path %>/images/favicon.ico" />
    <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%=path %>/mycss/error.css" rel="stylesheet" type="text/css">
    <script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="<%=path %>/myjs/rotate.js"></script>
    <script type="text/javascript" src="<%=path %>/myjs/jumpToMainpage.js"></script>
    <script>
        $(document).ready(function () {
            $("#btn_circle").rotate();
        });
    </script>
</head>
<body>
<div class="fluid">
    <div class="wrapper wrapper_navbar_navbar at-top">
        <div class="col-md-12 error-div">
            <div class="btn-circle" id="btn_circle">
                <p style="margin-top: 20px;"><span class="icon"><i class="fa fa-meh-o"></i></span></p>
                <p style="margin-top: 15px;"><span style="font-size: 24px;">出错啦!</span></p>
            </div>
            <hr>
            <div class="oops">
                <p class="sorry">啊咧，居然出错啦！<span id="jumpTo"> 5</span>秒后自动跳转到<a href="<%=path %>/search/main" style="color: #FFD300;">主页</a></span></p>
            </div>
            <hr>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(window).load(function() {
        // The slider being synced must be initialized first
        $('#carousel').flexslider({
            animation: "slide",
            controlNav: false,
            animationLoop: false,
            slideshow: false,
            itemWidth: 120,
            itemMargin: 5,
            asNavFor: '#slider'
        });

        $('#slider').flexslider({
            animation: "slide",
            controlNav: false,
            animationLoop: false,
            slideshow: false,
            sync: "#carousel"
        });
    });
</script>
<script>
    countDown(5,'<%=path %>/search/main');
</script>
</body>
</html>