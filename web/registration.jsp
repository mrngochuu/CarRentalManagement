<%-- 
    Document   : register
    Created on : Mar 8, 2020, 5:25:36 PM
    Author     : ngochuu
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Registration Page</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h3 class="card-title text-center">REGISTRATION FORM</h3>
                            <s:form theme="simple" cssClass="form-signin" action="RegistrationAction" method="POST">
                                <s:textfield cssClass="form-control mb-3" name="email" placeholder="Email" />
                                <font color="red"><s:fielderror fieldName="email"/></font>
                                <font color="red"><p class="mb-3"><s:property value="#request.INVALID"/></p></font>
                                <s:password cssClass="form-control mb-3" name="password" placeholder="Password"/>
                                <font color="red"><s:fielderror fieldName="password"/></font>
                                <s:password cssClass="form-control mb-3" name="confirm" placeholder="Re-type password"/>
                                <font color="red"><s:fielderror fieldName="confirm"/></font>
                                <s:textfield cssClass="form-control mb-3" name="fullname" placeholder="Fullname"/>
                                <font color="red"><s:fielderror fieldName="fullname"/></font>
                                <s:radio cssClass="mb-3 ml-3" list="{'Male'}" name="sex" checked="true" ><p class="mr-5 d-inline">Sex:</p></s:radio>
                                <s:radio cssClass="mb-3 ml-3" list="{'Female'}" name="sex"></s:radio>
                                <s:radio cssClass="mb-3 ml-3" list="{'Unknowns'}" name="sex"></s:radio>
                                <font color="red"><s:fielderror fieldName="sex"/></font>
                                <s:textfield cssClass="form-control mb-3" name="phone" placeholder="Phone Number"/>
                                <font color="red"><s:fielderror fieldName="phone"/></font>
                                <s:textarea cssClass="form-control mb-3" name="address" placeholder="Address"></s:textarea>
                                <font color="red"><s:fielderror fieldName="address"/></font>
                                <s:submit cssClass="btn btn-lg btn-primary btn-block text-uppercase mb-3" value="Register"/>
                            </s:form>
                            <hr class="mb-3">
                            <s:url action="SearchingCarAction" var="homeLink"/>
                            <div class="mb-3">
                                <a href="<s:property value="#homeLink"/>">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <a href="login.jsp">Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
