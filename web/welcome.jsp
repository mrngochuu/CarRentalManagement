<%-- 
    Document   : wellcome
    Created on : Mar 12, 2020, 5:12:19 PM
    Author     : ngochuu
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>G.A.U | Welcome</title>
    </head>
    <body>
        <s:action name="SearchingCarAction" namespace="/load" executeResult="true" />
    </body>
</html>
