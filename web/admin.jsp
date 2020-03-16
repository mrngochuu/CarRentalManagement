<%-- 
    Document   : admin
    Created on : Mar 17, 2020, 1:05:59 AM
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

        <title>G.A.U | Admin</title>
    </head>
    <body>
        <s:include value="layout/header.jsp"/>
        <s:include value="layout/slideShow.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <!-- search -->
                <div class="col-lg-3 mt-3 bg-light rounded" style="min-height: 600px; max-height: 600px;">
                    <h1 class="text-center">Transaction Search</h1>
                    <form action="ManagingTransactionAction" class="form-signin">
                        <input class="form-control mb-5 mt-3" type="text" name="carName" placeholder="Car's name" value="<s:property value="carName"/>"/>
                        <input class="form-control mb-5 mt-3" type="text" name="email" placeholder="Email" value="<s:property value="email"/>"/>
                        <select class="form-control browser-default custom-select mb-5" name="status">
                            <option value="">Rental's status</option>
                            <option value="waiting" <s:if test="%{status == 'waiting'}"> selected</s:if>>Waiting</option>
                            <option value="rentaling" <s:if test="%{status == 'rentaling'}"> selected</s:if>>Rentaling</option>
                            <option value="returned" <s:if test="%{status == 'returned'}"> selected</s:if>>Returned</option>
                            </select>
                            <!-- Type -->
                            <h4 class="text-center mb-3">Date rental</h5>
                                <div class="row">
                                    <div class='col-md-6 mr-auto ml-auto'>
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker1'>
                                                <input type="text" class="form-control" name="rentalFromDate" value="${param.rentalFromDate}"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class='mr-auto ml-auto col-md-6'>
                                    <div class="form-group">
                                        <div class='input-group date' id='datetimepicker2'>
                                            <input type="text" class="form-control" name="rentalToDate" value="${param.rentalToDate}"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h4 class="text-center mb-3">Date return</h5>
                                <div class="row">
                                    <div class='col-md-6 mr-auto ml-auto'>
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker3'>
                                                <input type="text" class="form-control" name="returnFromDate" value="${param.returnFromDate}"/>
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='mr-auto ml-auto col-md-6'>
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker4'>
                                                <input type="text" class="form-control" name="returnToDate" value="${param.returnToDate}"/>
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <input class="form-control btn btn-outline-success bg-warning mb-5" type="submit" value="SEARCH" />
                                </form>
                                </div>

                                <div class="col-lg-9">
                                    <h3 class="text-center text-danger mb-5"><s:property value="message"/></h3>
                                    <div class="row">
                                        <s:if test="%{#request.RESULT == null || #request.RESULT.isEmpty()}">
                                            <h3 class="text-center mr-auto ml-auto">There is no transaction!</h3>
                                        </s:if>
                                        <s:else>
                                            <div class="col-lg-11 mb-5 mr-auto ml-auto">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th style="width: 12%; text-align: center;">Image</th>
                                                            <th style="width: 12%; text-align: center;">Name</th>
                                                            <th style="width: 10%; text-align: center;">Category</th>
                                                            <th style="width: 8%; text-align: center;">Price</th>
                                                            <th style="width: 5%; text-align: center;">Quantity</th>
                                                            <th style="width: 12%; text-align: center;">Rental Date</th>
                                                            <th style="width: 12%; text-align: center;">Return Date</th>
                                                            <th style="width: 5%; text-align: center;">Amount</th>
                                                            <th style="width: 10%; text-align: center;">Status</th>
                                                            <th style="width: 12%; text-align: center;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <s:iterator value="#request.RESULT" var="dto">
                                                            <tr>
                                                                <td>
                                                                    <img class="ml-auto mr-auto" src="http://localhost:8084/Image/<s:property value="#HT_CAR[#dto.carID].imgURL"/>" width="80px" height="80px">
                                                                </td>
                                                                <td>
                                                                    <s:if test="%{#request.HT_CAR[#dto.carID].carName.toLowerCase().contains(carName.toLowerCase()) && txtSearch.length() > 0}"><p class="text-center font-weight-bold bg-warning"><s:property value="#request.HT_CAR[#dto.carID].carName"/> - <s:property value="#request.HT_CAR[#dto.carID].model"/></p></s:if>
                                                                    <s:else><p class="text-center font-weight-bold"><s:property value="#request.HT_CAR[#dto.carID].carName"/> - <s:property value="#request.HT_CAR[#dto.carID].model"/></p></s:else>
                                                                    </td>
                                                                    <td>
                                                                        <p class="text-center"><s:property value="#session.HASHTABLE_CATEGORY[#request.HT_CAR[#dto.carID].categoryID]"/></p>
                                                                </td>
                                                                <td>
                                                                    <p class="text-center">$<s:property value="#dto.price"/>/day</p>
                                                                </td>
                                                                <td>
                                                                    <p class="text-center"><s:property value="#dto.quantity"/></p>
                                                                </td>
                                                                <td>
                                                                    <p class="text-center"><s:property value="#dto.rentalDate.date"/>/<s:property value="%{#dto.rentalDate.month + 1}"/>/<s:property value="%{#dto.rentalDate.year + 1900}"/></p>
                                                                </td>
                                                                <td>
                                                                    <p class="text-center"><s:property value="#dto.returnDate.date"/>/<s:property value="%{#dto.returnDate.month + 1}"/>/<s:property value="%{#dto.returnDate.year + 1900}"/></p>
                                                                </td>
                                                                <td>
                                                                    <p class="text-center"><s:property value="%{#dto.price * #dto.quantity * #request.HT_DATE[#dto]}"/></p>
                                                                </td>
                                                                <td>
                                                                    <s:if test="%{#dto.status == 'waiting'}"><p class="text-center text-warning">Waiting</p></s:if>
                                                                    <s:if test="%{#dto.status == 'rentaling'}"><p class="text-center text-success">Rentaling</p></s:if>
                                                                    <s:if test="%{#dto.status == 'returned'}"><p class="text-center text-info">Returned</p></s:if>
                                                                    </td>
                                                                    <td>
                                                                    <s:if test="%{#dto.status == 'rentaling'}">
                                                                        <form action="UpdatingRentalStatusAction" method="POST">
                                                                            <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                                            <input type="hidden" name="orderID" value="<s:property value="#dto.orderID"/>"/>
                                                                            <input type="hidden" name="statusAction" value="returned"/>
                                                                            <input type="hidden" name="carName" value="<s:property value="carName"/>"/>
                                                                            <input type="hidden" name="email" value="<s:property value="email"/>"/>
                                                                            <input type="hidden" name="status" value="<s:property value="status"/>"/>
                                                                            <input type="hidden" name="rentalFromDate" value="<s:property value="rentalFromDate"/>"/>
                                                                            <input type="hidden" name="rentalToDate" value="<s:property value="rentalToDate"/>"/>
                                                                            <input type="hidden" name="returnFromDate" value="<s:property value="returnFromDate"/>"/>
                                                                            <input type="hidden" name="returnToDate" value="<s:property value="returnToDate"/>"/>
                                                                            <input type="submit" class="btn btn-info" value="Picked up"/>
                                                                        </form>
                                                                    </s:if>
                                                                    <s:elseif test="%{#dto.status == 'waiting'}">
                                                                        <form action="UpdatingRentalStatusAction" method="POST">
                                                                            <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                                            <input type="hidden" name="orderID" value="<s:property value="#dto.orderID"/>"/>
                                                                            <input type="hidden" name="statusAction" value="rentaling"/>
                                                                            <input type="hidden" name="carName" value="<s:property value="carName"/>"/>
                                                                            <input type="hidden" name="email" value="<s:property value="email"/>"/>
                                                                            <input type="hidden" name="status" value="<s:property value="status"/>"/>
                                                                            <input type="hidden" name="rentalFromDate" value="<s:property value="rentalFromDate"/>"/>
                                                                            <input type="hidden" name="rentalToDate" value="<s:property value="rentalToDate"/>"/>
                                                                            <input type="hidden" name="returnFromDate" value="<s:property value="returnFromDate"/>"/>
                                                                            <input type="hidden" name="returnToDate" value="<s:property value="returnToDate"/>"/>
                                                                            <input type="submit" class="btn btn-info" value="Deliveried"/>
                                                                        </form>
                                                                    </s:elseif>
                                                                    <s:else>
                                                                        <p class="text-center">---</p>
                                                                    </s:else>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="5">
                                                                    <h4>Payment information:</h4>
                                                                    <p>Receiver's name: <s:property value="#request.HT_ORDER[#dto.orderID].receiverName"/></p>
                                                                    <p>Receiver's phone: <s:property value="#request.HT_ORDER[#dto.orderID].receiverPhone"/></p>
                                                                    <p>Pick up address: <s:property value="#request.HT_ORDER[#dto.orderID].address"/></p>
                                                                    <p>Payment date: <s:property value="#request.HT_ORDER[#dto.orderID].paymentDate.date"/>/<s:property value="%{#request.HT_ORDER[#dto.orderID].paymentDate.month + 1}"/>/<s:property value="%{#request.HT_ORDER[#dto.orderID].paymentDate.date + 1900}"/></p>
                                                                    <br><br>
                                                                </td>
                                                                <td colspan="5">
                                                                    <s:if test="%{#request.HT_ORDER[#dto.orderID].promotionID != 0}">
                                                                        <h4>Promotion information:</h4>
                                                                        <p>Promotion's name: <s:property value="#request.HT_PROMOTION[#request.HT_ORDER[#dto.orderID].promotionID].promotionName"/></p>
                                                                        <p>Percent discount: <s:property value="#request.HT_PROMOTION[#request.HT_ORDER[#dto.orderID].promotionID].percents"/>%</p>
                                                                    </s:if>
                                                                </td>
                                                            </tr>
                                                        </s:iterator>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </s:else>
                                    </div>
                                </div>
                                </div>
                                </div>
                                <s:include value="layout/footer.jsp"/>
                                <script type="text/javascript">
                                    $('#datetimepicker1').datetimepicker({
                                        format: 'DD/MM/YYYY'
                                    });
                                    $('#datetimepicker2').datetimepicker({
                                        format: 'DD/MM/YYYY',
                                        useCurrent: false
                                    });
                                    
                                    $('#datetimepicker3').datetimepicker({
                                        format: 'DD/MM/YYYY'
                                    });
                                    $('#datetimepicker4').datetimepicker({
                                        format: 'DD/MM/YYYY',
                                        useCurrent: false
                                    });
                                    
                                    $("#datetimepicker1").on("dp.change", function (e) {
                                        $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
                                    });
                                    $("#datetimepicker2").on("dp.change", function (e) {
                                        $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
                                    });

                                    $("#datetimepicker3").on("dp.change", function (e) {
                                        $('#datetimepicker4').data("DateTimePicker").minDate(e.date);
                                    });
                                    $("#datetimepicker4").on("dp.change", function (e) {
                                        $('#datetimepicker3').data("DateTimePicker").maxDate(e.date);
                                    });
                                </script>
                                </body>
                                </html>
