<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>登录并授权oauthlogin</title>
    <style>.error{color:red;}</style>
</head>
<body>

<div>使用你的Shiro示例Server帐号访问 [${client.clientName}] ，并同时登录Shiro示例Server</div>
<div class="error">${error}</div>

<form action="/oauth2.0/authorize" method="post">
    	<input type="hidden" name="client_id" value="test"/>
        <input type="hidden" name="scope" value="read"/>
        <input type="hidden" name="response_type" value="code"/>
        <input type="hidden" name="redirect_uri" value="http://localhost:8090/success.jsp"/>
        <input type="hidden" name="state" value="state"/>
        <input type="hidden" name="client_secret" value="test"/>
    	用户名：<input type="text" name="username" value="<shiro:principal/>"><br/>
   		 密码：<input type="password" name="password"><br/>
    <input type="submit" value="登录并授权">
</form>

</body>
</html>