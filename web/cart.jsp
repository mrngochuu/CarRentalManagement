<%-- 
    Document   : cart
    Created on : Mar 13, 2020, 9:09:20 PM
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

        <title>G.A.U | Cart</title>
    </head>
    <body>
        <s:include value="layout/header.jsp"/>
        <s:include value="layout/slideShow.jsp"/>
        <div container-fluid>
            <div class="row">
                <div class="col-lg-10 col-md-10 col-sm-10 mr-auto ml-auto">
                    <h2>Your shopping cart</h2>
                    <h4 class="text-center"><font color="red">${requestScope.MESSAGE}</font></h4>
                        <s:if test="%{#request.LIST_ORDER_DETAILS != null}">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th style="width: 10%; text-align: center;">Image</th>
                                    <th style="width: 10%; text-align: center;">Name</th>
                                    <th style="width: 10%; text-align: center;">Category</th>
                                    <th style="width: 5%; text-align: center;">Price</th>
                                    <th style="width: 5%; text-align: center;">Quantity</th>
                                    <th style="width: 12%; text-align: center;">Rental Date</th>
                                    <th style="width: 12%; text-align: center;">Return Date</th>
                                    <th style="width: 5%; text-align: center;">Amount</th>
                                    <th style="width: 11%; text-align: center;">Status</th>
                                    <th style="width: 10%; text-align: center;">Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <s:set var="total" value="0"/>
                                <s:iterator value="%{#request.LIST_ORDER_DETAILS}" var="dto">
                                    <s:set var="total" value="%{#total + (#dto.price * #dto.quantity * #request.HASHTABLE_DAY[#dto.carID])}"/>
                                    <tr>
                                        <td>
                                            <img class="ml-auto mr-auto" src="http://localhost:8084/Image/<s:property value="#request.HASHTABLE_CAR[#dto.carID].imgURL"/>" width="80px" height="80px">
                                        </td>
                                        <td>
                                            <p class="text-center font-weight-bold"><s:property value="#request.HASHTABLE_CAR[#dto.carID].carName"/> - <s:property value="#request.HASHTABLE_CAR[#dto.carID].model"/></p>
                                        </td>
                                        <td>
                                            <p class="text-center"><s:property value="#session.HASHTABLE_CATEGORY[#request.HASHTABLE_CAR[#dto.carID].categoryID]"/></p>
                                        </td>
                                        <td>
                                            <p class="text-center">$<s:property value="#dto.price"/>/day</p>
                                        </td>
                                        <td>
                                            <form action="UpdatingQuantityCartAction" method="POST">
                                                <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                <input type="hidden" name="rentalDate" value="<s:property value="rentalDate"/>"/>
                                                <input type="hidden" name="returnDate" value="<s:property value="returnDate"/>"/>
                                                <input type="number" style="width: 50px;" class="text-center" name="quantity" min="1" value="<s:property value="#dto.quantity"/>" onchange="this.form.submit()"/>
                                            </form>
                                        </td>
                                        <td>
                                            <form action="UpdatingFromDateCartAction" method="POST">
                                                <div class="form-group">
                                                    <div class="input-group date datetimepicker">
                                                        <input type="text" class="form-control" name="rentalDate" value="<s:property value="#dto.rentalDate.date"/>/<s:property value="#dto.rentalDate.month"/>/<s:property value="%{#dto.rentalDate.year + 1900}"/>" />
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                            </form>
                                        </td>
                                        <td>
                                            <form action="UpdatingToDateCartAction" method="POST">
                                                <div class="form-group">
                                                    <div class="input-group date datetimepicker">
                                                        <input type="text" class="form-control" name="returnDate" value="<s:property value="#dto.returnDate.date"/>/<s:property value="#dto.returnDate.month"/>/<s:property value="%{#dto.returnDate.year + 1900}"/>" />
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                            </form>
                                        </td>
                                        <td>
                                            <p class="text-center">$<s:property value="%{#dto.price * #dto.quantity * #request.HASHTABLE_DAY[#dto.carID]}"/></p>
                                        </td>
                                        <td>
                                            <p class="text-center">$<s:property value="#statusMessage"/></p>
                                        </td>
                                        <td>
                                            <form action="DeletingFromCartAction" class="form-signin">
                                                <input type="submit" class="form-control btn btn-danger" value="Delete"/>
                                            </form>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="6"></td>
                                    <td class="text-center" colspan="1"><strong>Total</strong></td>
                                    <td class="text-center"><strong>$<s:property value="#total"/></strong></td>
                                    <td colspan="2"></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <a href="${homeLink}" class="btn btn-primary text-center">Home</a>
                                    </td>
                                    <td colspan="4"></td>
                                    <td colspan="2"><a href="orderDetails.jsp" class="btn btn-danger float-right">Click continue to pay</a></td>
                                </tr>
                            </tfoot>
                        </table>
                    </s:if>
                    <s:else>
                        <h4 class="text-center">There is no added product!</h4>
                    </s:else>
                </div>
            </div>
        </div>
        <s:include value="layout/footer.jsp"/>
        <script type="text/javascript">
            $(function () {
                $(".datetimepicker").datetimepicker({
                    format: "DD/MM/YYYY"
                });
                
            });
        </script>
    </body>
</html>