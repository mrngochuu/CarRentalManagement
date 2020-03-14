<%-- 
    Document   : payment
    Created on : Mar 15, 2020, 2:16:41 AM
    Author     : ngochuu
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>G.A.U | Payment</title>
    </head>
    <body>
        <div class="container-fluid"> 
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-5">PAYMENT FORM</h3>
                            <h6 class="mb-3">Hello <s:property value="#session.USER.fullname"/>, please fill in the information below to complete payment.</h6>
                            <form action="PaymentAction" method="POST" class="form-signin">
                                <input type="text" name="receiverName" placeholder="Receiver's name" class="form-control mb-3" />
                                <font class="mb-3" color="red"><p class="mb-3"><s:fielderror fieldName="receiverName"/></p></font>

                                <input type="text" name="receiverPhone" placeholder="Receiver's phone number" class="form-control mb-3" />
                                <font class="mb-3" color="red"><p class="mb-3"><s:fielderror fieldName="receiverPhone"/></p></font>

                                <input type="text" name="address" placeholder="Pick up address" class="form-control mb-3" />
                                <font color="red"><p class="mb-3">${requestScope.INVALID.addressError}</p></font>

                                <button type="submit" class="btn btn-lg btn-primary btn-block text-uppercase mb-3">Complete the payment</button>
                            </form>
                            <hr class="my-4">
                            <s:url action="SearchingCarAction" var="homeLink"/>
                            <s:url action="ShowingCartAction" var="showCartLink"/>
                            <div class="mb-3">
                                <a href="<s:property value="#homeLink"/>">Home Page</a>
                            </div>
                            <div class="mb-3">
                                <a href="<s:property value="#showCartLink"/>">Cart Page</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
