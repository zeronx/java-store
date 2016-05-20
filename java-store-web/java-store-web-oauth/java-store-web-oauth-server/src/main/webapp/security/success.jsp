<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h4>Success Page</h4>
	 <form action="oauth2.0/accessToken" method="POST">
    	<!--  <input type="hidden" name="response_type" value="code"/> -->
    	  <input type="hidden" name="grant_type" value="authorization_code"/>
    	   <input type="hidden" name="client_id" value="test"/>
    	    <input type="hidden" name="client_secret" value="test"/>
    	    <input type="hidden" name="redirect_uri" value="http://localhost:8080/success.jsp"/>
    	    <input type="text" name="code"/><br>
    	    <input type="submit" value="submitValidCode get token" />
    </form>
</body>
</html>