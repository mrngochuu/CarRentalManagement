<%-- 
    Document   : promotion
    Created on : Mar 17, 2020, 4:17:56 AM
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
                            <h3 class="card-title text-center">PROMOTION FORM</h3>
                            <form class="from-signin" action="CreatingPromotionAction" method="POST">
                                <input type="text" name="promotionName" value="<s:property value="promotionName"/>" placeholder="Promotion's name" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="promotionName"/></font>
                                <input type="num" min="1" name="percents" value="<s:property value="percents"/>" placeholder="Percent discount" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="percents"/></font>
                                <input type="num" min="1" name="conditionAmount" value="<s:property value="conditionAmount"/>" placeholder="Condition discount" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="conditionAmount"/></font>
                                <input type="date" name="startedDate" value="<s:property value="startedDate"/>" placeholder="Start date" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="startedDate"/></font>
                                <input type="date" name="endedDate" value="<s:property value="endedDate"/>" placeholder="End date" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="endedDate"/></font>
                                <input type="num" name="expiryDate" value="<s:property value="expiryDate"/>" placeholder="Expiry day" class="form-control mb-3"/>
                                <font color="red"><s:fielderror fieldName="expiryDate"/></font>
                                <input type="submit" class="btn btn-lg btn-primary btn-block text-uppercase mb-3" value="Create Promotion"/>
                            </form>
                            <hr class="mb-3">
                            <div class="mb-3">
                                <s:url var="homeLink" action="SearchingCarAction"/>
                                <a href="<s:property value="#homeLink"/>">Home Page</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
