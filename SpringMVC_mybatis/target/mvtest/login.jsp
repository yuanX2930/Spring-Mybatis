<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/14
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<
<form action="${pageContext.request.contextPath }/login.action" method="post">
    用户名：<input type="text" name="username" />
    密码：<input type="password" name="password" />
    <input type="submit" value="提交">

</form>

</body>
</html>
