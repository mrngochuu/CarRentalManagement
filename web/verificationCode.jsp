<%-- 
    Document   : verificationCode
    Created on : Mar 9, 2020, 12:22:17 AM
    Author     : ngochuu
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Verification Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h2>Hello <s:property value="fullname"/></h2>
                            <p>A code send your email. Please enter code to complete registration!</p>
                            
                            <s:form theme="simple" cssClass="form-signin" action="VerificationAction" method="POST" >
                                <s:textfield cssClass="form-control mb-3" name="code" placeholder="Code"/>
                                <font color="red"><s:fielderror fieldName="code"/></font>
                                <font color="red"><p class="mb-3"><s:property value="#request.INVALID"/></p></font>
                                <s:submit cssClass="btn btn-lg btn-primary btn-block text-uppercase mb-3" value="Verify"/>
                                <s:hidden name="email" value="%{email}"/>
                                <s:hidden name="fullname" value="%{fullname}"/>
                            </s:form>
                                
                            <hr class="m3-b">
                            <s:url action="SearchingCarAction" var="homeLink"/>
                            <div class="mb-3">
                                <a href="<s:property value="#homeLink"/>">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <s:url action="SendingMailAction" var="resendLink" escapeAmp="false">
                                    <s:param name="email" value="%{email}"/>
                                    <s:param name="fullname" value="%{fullname}"/>
                                </s:url>
                                <a href="<s:property value="#resendLink"/>">Resend code</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
