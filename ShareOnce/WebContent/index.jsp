<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  bookmarklet:javascript:window.open('http://localhost:8080/InternetOS/signal/share?shareUrl='+document.location)
    <form action="http://localhost:8080/InternetOS/signal/share" target="_blank">
    <!-- User ID <input type="text" name="userID"/> -->
    Share URL <input type="text" name="shareUrl"/>
    <input type="submit" value="share"/>
    </form>
  </body>
</html>
