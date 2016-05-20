<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>test oauth2.0 demo</h2>
	<a href="#"> test url </a>
    <form action="oauth2.0/authorize" method="POST">
    	 <input type="hidden" name="response_type" value="code"/>
    	  <input type="hidden" name="scope" value="read"/>
    	   <input type="hidden" name="client_id" value="test"/>
    	   <input type="hidden" name="state" value="state"/>
    	    <input type="hidden" name="redirect_uri" value="http://localhost:8090/success.jsp"/>
    	    <input type="submit" value="submitAndAuthrios" />
    </form>
</body>
</html>