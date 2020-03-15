<%-- 
    Document   : history
    Created on : Mar 15, 2020, 8:39:57 PM
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

        <title>G.A.U | History</title>
    </head>
    <body>
        <s:include value="layout/header.jsp"/>
        <s:include value="layout/slideShow.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <!-- search -->
                <div class="col-lg-3 mt-3 bg-light rounded" style="min-height: 280px; max-height: 350px;">
                    <h1 class="text-center">History</h1>
                    <form action="SearchingHistoryAction" class="form-signin">
                        <input class="form-control mb-5 mt-3" type="text" name="txtSearch" placeholder="Car Name" value="<s:property value="txtSearch"/>"/>
                        <!-- Type -->
                        <h4 class="text-center mb-3">Date rental</h5>
                            <div class="row">
                                <div class='col-md-6 mr-auto ml-auto'>
                                    <div class="form-group">
                                        <div class='input-group date' id='datetimepicker1'>
                                            <input type="text" class="form-control" name="fromDate" value="${param.fromDate}"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class='mr-auto ml-auto col-md-6'>
                                    <div class="form-group">
                                        <div class='input-group date' id='datetimepicker2'>
                                            <input type="text" class="form-control" name="toDate" value="${param.toDate}"/>
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
                        <s:if test="%{#request.LIST_PAYMENT == null || #request.HASHTABLE_LIST_ORDER_DETAILS == null || #request.HASHTABLE_LIST_ORDER_DETAILS.isEmpty()}">
                            <h3 class="text-center mr-auto ml-auto">There is no transaction!</h3>
                        </s:if>
                        <s:else>
                            <s:iterator value="#request.LIST_PAYMENT" var="dto">
                                <s:if test="%{#request.HASHTABLE_LIST_ORDER_DETAILS[#dto] != null}">
                                    <s:set value="%{#request.HASHTABLE_LIST_ORDER_DETAILS[#dto]}" var="entry"/>
                                    <div class="col-lg-11 mb-5 border border-info mr-auto ml-auto">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th style="width: 13%; text-align: center;">Image</th>
                                                    <th style="width: 13%; text-align: center;">Name</th>
                                                    <th style="width: 10%; text-align: center;">Category</th>
                                                    <th style="width: 10%; text-align: center;">Price</th>
                                                    <th style="width: 5%; text-align: center;">Quantity</th>
                                                    <th style="width: 12%; text-align: center;">Rental Date</th>
                                                    <th style="width: 12%; text-align: center;">Return Date</th>
                                                    <th style="width: 5%; text-align: center;">Amount</th>
                                                    <th style="width: 10%; text-align: center;">Status</th>
                                                    <th style="width: 10%; text-align: center;">Feedback</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <s:set var="total" value="0"/>
                                                <s:iterator value="#entry" var="record">
                                                    <tr>
                                                        <td>
                                                            <img class="ml-auto mr-auto" src="http://localhost:8084/Image/<s:property value="#request.HASHTABLE_CAR[#record.carID].imgURL"/>" width="80px" height="80px">
                                                        </td>
                                                        <td>
                                                            <s:if test="%{#request.HASHTABLE_CAR[#record.carID].carName.toLowerCase().contains(txtSearch.toLowerCase()) && txtSearch.length() > 0}"><p class="text-center font-weight-bold bg-warning"><s:property value="#request.HASHTABLE_CAR[#record.carID].carName"/> - <s:property value="#request.HASHTABLE_CAR[#record.carID].model"/></p></s:if>
                                                            <s:else><p class="text-center font-weight-bold"><s:property value="#request.HASHTABLE_CAR[#record.carID].carName"/> - <s:property value="#request.HASHTABLE_CAR[#record.carID].model"/></p></s:else>
                                                        </td>
                                                        <td>
                                                            <p class="text-center"><s:property value="#session.HASHTABLE_CATEGORY[#request.HASHTABLE_CAR[#record.carID].categoryID]"/></p>
                                                        </td>
                                                        <td>
                                                            <p class="text-center">$<s:property value="#record.price"/>/day</p>
                                                        </td>
                                                        <td>
                                                            <p class="text-center"><s:property value="#record.quantity"/></p>
                                                        </td>
                                                        <td>
                                                            <p class="text-center"><s:property value="#record.rentalDate.date"/>/<s:property value="%{#record.rentalDate.month + 1}"/>/<s:property value="%{#record.rentalDate.year + 1900}"/></p>
                                                        </td>
                                                        <td>
                                                            <p class="text-center"><s:property value="#record.returnDate.date"/>/<s:property value="%{#record.returnDate.month + 1}"/>/<s:property value="%{#record.returnDate.year + 1900}"/></p>
                                                        </td>
                                                        <td>
                                                            <s:set var="total" value="%{#total + (#record.price * #record.quantity * #request.HASHTABLE_DATE[#record])}"/>
                                                            <p class="text-center"><s:property value="%{#record.price * #record.quantity * #request.HASHTABLE_DATE[#record]}"/></p>
                                                        </td>
                                                        <td>
                                                            <s:if test="%{#record.status == 'waiting'}"><p class="text-center text-warning">Waiting</p></s:if>
                                                            <s:if test="%{#record.status == 'rentaling'}"><p class="text-center text-success">Rentaling</p></s:if>
                                                            <s:if test="%{#record.status == 'returned'}"><p class="text-center text-danger">Returned</p></s:if>
                                                            </td>
                                                            <td>
                                                                <form action="ShowingRecordAction" method="POST">
                                                                    
                                                                </form>
                                                            </td>
                                                        </tr>
                                                </s:iterator>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <td colspan="8">
                                                        <h4>Payment information:</h4>
                                                        <p>Receiver's name: <s:property value="#dto.receiverName"/></p>
                                                        <p>Receiver's phone: <s:property value="#dto.receiverPhone"/></p>
                                                        <p>Pick up address: <s:property value="#dto.address"/></p>
                                                        <p>Payment date: <s:property value="#dto.paymentDate.date"/>/<s:property value="%{#dto.paymentDate.month + 1}"/>/<s:property value="%{#dto.paymentDate.year + 1900}"/></p>
                                                        <form action="DeletingHistoryAction" method="POST">
                                                            <input type="hidden" name="orderID" value="<s:property value="#dto.orderID"/>"/>
                                                            <input type="hidden" name="txtSearch" value="<s:property value="txtSearch"/>"/>
                                                            <input type="hidden" name="fromDate" value="<s:property value="fromDate"/>"/>
                                                            <input type="hidden" name="toDate" value="<s:property value="toDate"/>"/>
                                                            <input type="submit" class="btn btn-danger" value="Delete transaction"/>
                                                        </form>
                                                    </td>
                                                    <td class="text-center" colspan="1"><strong>Total</strong></td>
                                                    <td class="text-center">
                                                        <strong>$<s:property value="#total"/></strong><br><br>
                                                        <s:if test="%{#dto.promotionID != 0}">
                                                            <strong>- <s:property value="%{#request.HASHTABLE_PROMOTION[#dto.promotionID].percents}"/>%</strong>
                                                            <hr>
                                                            <strong>$<s:property value="%{(#total * (100 - #request.HASHTABLE_PROMOTION[#dto.promotionID].percents))/100}"/></strong>
                                                        </s:if>
                                                    </td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </s:if>
                            </s:iterator>

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
            $("#datetimepicker1").on("dp.change", function (e) {
                $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
            });
            $("#datetimepicker2").on("dp.change", function (e) {
                $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
            });
        </script>
    </body>
</html>