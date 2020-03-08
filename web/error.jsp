<%-- 
    Document   : error
    Created on : Mar 8, 2020, 7:07:25 PM
    Author     : ngochuu
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Sorry, <s:property value="#request.ERROR"/></h1>
    <center>
        <h1>Sorry, unexpected exception occurred:</h1>
        <h2>Exception name: ${exception}</h2>
    </center>
</body>
</html>
