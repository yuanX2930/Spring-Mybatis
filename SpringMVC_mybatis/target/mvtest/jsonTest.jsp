<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/12
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSON交互测试</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
   <script type="text/javascript">
    //请求的是json输出的是json

    function requestJson() {
         $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath }/requestJson.action',
            contentType:'application/json;charset=utf-8',
            //数据格式是json串，商品信息
            data:'{"name":"手机","price":999}',
            success:function(data){//返回json结果
                alert(data.price);
            }


        });


    }

    //请求的是key/value输出的是json
    function responseJson(){

        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath }/responseJson.action',
            //请求是key/value这里不需要指定contentType，因为默认就 是key/value类型
            //contentType:'application/json;charset=utf-8',
            //数据格式是json串，商品信息
            data:'name=手机&price=999',
            success:function(data){//返回json结果
                alert(data.name);
            }

        });

    }





  </script>
</head>
<body>
<input type="button" onclick="requestJson()" value="请求json，输出是json">
<input type="button" onclick="responseJson()" value="请求key/value，输出是json">

</body>
</html>
