<%-- 
    Document   : car
    Created on : Mar 16, 2020, 6:15:00 AM
    Author     : ngochuu
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/5a401084f7.js" crossorigin="anonymous"></script>

        <link href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet"/>
        <link href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.css" rel="stylesheet"/>

        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.2/moment.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
        <title>G.A.U | <s:property value="#request.CAR.carName"/></title>
    </head>
    <body>
        <s:include value="layout/header.jsp"/>
        <s:include value="layout/slideShow.jsp"/>
        <div class="container mt-3">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="well well-sm">
                        <h3 class="text-center text-danger"><s:property value="message"/></h3>
                        <div class="row">
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <img src="http://localhost:8084/Image/<s:property value="#request.CAR.imgURL"/>" alt="" class="img-rounded img-responsive" />
                            </div>
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <h2><s:property value="#request.CAR.carName"/> - <s:property value="#request.CAR.model"/></h2>
                                <hr>
                                <h3 class="font-italic">Information</h3>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Model:</p></td>
                                            <td><p class="mr-3 mb-3"><s:property value="#request.CAR.model"/></p></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Color:</p></td>
                                            <td><p class="mr-3 mb-3"><s:property value="#request.CAR.color"/></p></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Price:</p></td>
                                            <td><p class="mr-3 mb-3">$<s:property value="#request.CAR.price"/>/day</p></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Quantity:</p></td>
                                            <td><p class="mr-3 mb-3"><s:property value="#request.CAR.quantity"/></p></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Created date:</p></td>
                                            <td><p class="mr-3 mb-3"><s:property value="#request.CAR.createdDate.date"/>/<s:property value="%{#request.CAR.createdDate.month + 1}"/>/<s:property value="%{#request.CAR.createdDate.year + 1900}"/></p></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><p class="font-weight-bold mr-3 mb-3">Average rate:</p></td>
                                            <td>
                                                <p class="mr-3 mb-3">
                                                    <s:if test="%{#request.AVERAGE_RATING != null}">
                                                        <s:iterator begin="1" end="#request.AVERAGE_RATING" step="1">
                                                            <span class="fa fa-star" style="color: yellow;"></span>
                                                        </s:iterator>
                                                        <s:iterator begin="%{#request.AVERAGE_RATING + 1}" end="10" step="1">
                                                            <span class="fa fa-star"></span>
                                                        </s:iterator>
                                                    </s:if>
                                                    <s:else>
                                                        <s:iterator begin="1" end="10" step="1">
                                                            <span class="fa fa-star" style="color: darkgrey;"></span>
                                                        </s:iterator>
                                                    </s:else>
                                                </p>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <s:iterator value="#request.LIST_FEEDBACK" var="feedback">
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-2">
                                            <div class="thumbnail">
                                                <img class="img-responsive user-photo" src="http://localhost:8084/Image/avatar.png">
                                            </div><!-- /thumbnail -->
                                        </div><!-- /col-sm-1 -->
                                        <div class="col-sm-10">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <strong><s:property value="#feedback.email"/></strong> <span class="text-muted"><s:property value="#feedback.feedbackDate.date"/>/<s:property value="%{#feedback.feedbackDate.month + 1}"/>/<s:property value="%{#feedback.feedbackDate.year + 1900}"/></span>
                                                </div>
                                                <div class="panel-body">
                                                    <s:iterator begin="1" end="#feedback.rating" step="1">
                                                        <span class="fa fa-star" style="color: yellow;"></span>
                                                    </s:iterator>
                                                    <s:iterator begin="%{#feedback.rating + 1}" end="10" step="1">
                                                        <span class="fa fa-star"></span>
                                                    </s:iterator>
                                                    <p><s:property value="#feedback.feedback"/></p>
                                                </div><!-- /panel-body -->
                                            </div><!-- /panel panel-default -->
                                        </div><!-- /col-sm-5 -->
                                    </div>
                                </s:iterator>
                                <s:if test="%{#session.ROLE.roleName == 'customer'}">
                                    <hr>
                                    <div class="row">
                                        <form action="FeedbackAction" method="POST" class="w-100">
                                            <input type="hidden" name="carID" value="<s:property value="#request.CAR.carID"/>">
                                            <div class="col-sm-2">
                                                <div class="thumbnail">
                                                    <img class="img-responsive user-photo" src="http://localhost:8084/Image/avatar.png">
                                                </div>
                                            </div>
                                            <div class="col-sm-10">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <input type="hidden" name="rating" id="rating"/>
                                                        <span class="fa fa-star" id="rating1"></span>
                                                        <span class="fa fa-star" id="rating2"></span>
                                                        <span class="fa fa-star" id="rating3"></span>
                                                        <span class="fa fa-star" id="rating4"></span>
                                                        <span class="fa fa-star" id="rating5"></span>
                                                        <span class="fa fa-star" id="rating6"></span>
                                                        <span class="fa fa-star" id="rating7"></span>
                                                        <span class="fa fa-star" id="rating8"></span>
                                                        <span class="fa fa-star" id="rating9"></span>
                                                        <span class="fa fa-star" id="rating10"></span>
                                                        <font color="red"><s:fielderror fieldName="rating"/></font>
                                                    </div>
                                                    <div class="panel-body">
                                                        <textarea name="feedback" class="form-control mb-3" placeholder="Feedback for this car" rows="2"></textarea>
                                                        <font color="red"><s:fielderror fieldName="feedback"/></font>
                                                        <input type="submit" class="form-control mb-5 btn btn-info" value="Submit"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </s:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <s:include value="layout/footer.jsp"/>
        <script>
            document.getElementById("rating1").onclick = function () {
                document.getElementById("rating").value = "1";
                document.getElementById("rating1").style.color = 'yellow';
                for (var i = 2; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating2").onclick = function () {
                document.getElementById("rating").value = "2";
                for (var i = 1; i <= 2; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 3; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating3").onclick = function () {
                document.getElementById("rating").value = "3";
                for (var i = 1; i <= 3; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 4; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating4").onclick = function () {
                document.getElementById("rating").value = "4";
                for (var i = 1; i <= 4; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 5; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating5").onclick = function () {
                document.getElementById("rating").value = "5";
                for (var i = 1; i <= 5; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 6; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating6").onclick = function () {
                document.getElementById("rating").value = "6";
                for (var i = 1; i <= 6; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 7; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating7").onclick = function () {
                document.getElementById("rating").value = "7";
                for (var i = 1; i <= 7; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 8; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating8").onclick = function () {
                document.getElementById("rating").value = "8";
                for (var i = 1; i <= 8; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                for (var i = 9; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'black';
                }
            };

            document.getElementById("rating9").onclick = function () {
                document.getElementById("rating").value = "9";
                for (var i = 1; i <= 9; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
                document.getElementById("rating" + 10).style.color = 'black';
            };

            document.getElementById("rating10").onclick = function () {
                document.getElementById("rating").value = "10";
                for (var i = 1; i <= 10; i++) {
                    document.getElementById("rating" + i).style.color = 'yellow';
                }
            };
        </script>
    </body>
</html>
