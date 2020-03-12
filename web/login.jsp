<%-- 
    Document   : login
    Created on : Mar 8, 2020, 5:25:16 PM
    Author     : ngochuu
--%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
        <title>Login Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center mb-3"><font color="red"><s:property value="#request.MESSAGE"/></font></h5>
                            <h3 class="card-title text-center">LOGIN FORM</h3>
                            <s:form theme="simple" cssClass="form-signin" action="LoginAction" method="POST" >
                                <s:textfield cssClass="form-control mb-3" name="email" placeholder="Email" />
                                <font color="red"><s:fielderror fieldName="email"/></font>
                                <s:password cssClass="form-control mb-3" name="password" placeholder="Password"/>
                                <font color="red"><s:fielderror fieldName="password"/></font>
                                <font color="red"><p class="mb-3"><s:property value="#request.INVALID"/></p></font>
                                <div class="g-recaptcha mb-3" data-sitekey="6LcEWOAUAAAAAM71s-PXbNHNQMR8mtFlN1LW3DsB">
                                </div>
                                <s:submit cssClass="btn btn-lg btn-primary btn-block text-uppercase mb-3" value="Log in"/>
                            </s:form>
                            <hr class="my-4">
                            <div class="mb-3">
                                <a href="">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <a href="registration.jsp">Register Account</a>
                                <div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

