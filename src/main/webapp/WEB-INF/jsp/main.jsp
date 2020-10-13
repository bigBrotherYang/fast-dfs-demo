<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/10/8
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>"></base>
</head>
<body>
<form id="uploadForm" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="button" onclick="demo()" value="上传"/>
</form>
<script src="static/js/jquery-3.5.1.min.js"></script>
<script type="application/javascript">
    function demo(){
        $.ajax({
            url:"shangchaun",
            type:"post",
            data:new FormData($("#uploadForm")[0]),
            processData:false,
            contentType:false,
            cache: false,
            dataType:"json",
            success:function(data){
                console.log(data)
            },
            error:function(data){
                console.log("--------")
                console.log(data)
            }
        })
    }
</script>
</body>
</html>
