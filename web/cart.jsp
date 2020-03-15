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
                    <s:if test="%{#request.LIST_ORDER_DETAILS != null}">
                        <h2>Your shopping cart</h2>
                        <h4 class="text-center"><font color="red"><s:property value="message"/></font></h4>
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
                                    <th style="width: 8%; text-align: center;">Amount</th>
                                    <th style="width: 16%; text-align: center;">Status</th>
                                    <th style="width: 2%; text-align: center;">Delete</th>
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
                                                <input type="hidden" name="rentalDate" value="<s:property value="#dto.rentalDate.date"/>/<s:property value="%{#dto.rentalDate.month + 1}"/>/<s:property value="%{#dto.rentalDate.year + 1900}"/>"/>
                                                <input type="hidden" name="returnDate" value="<s:property value="#dto.returnDate.date"/>/<s:property value="%{#dto.returnDate.month + 1}"/>/<s:property value="%{#dto.returnDate.year + 1900}"/>"/>
                                                <input type="number" style="width: 50px;" class="text-center" name="quantity" min="1" value="<s:property value="#dto.quantity"/>" onchange="this.form.submit()"/>
                                            </form>
                                        </td>
                                        <td>
                                            <s:set var="temp" value="%{(#dto.rentalDate.year + 1900) + '-'}"/>
                                            <s:set var="month" value="%{#dto.rentalDate.month + 1}"/>
                                            <s:if test="%{#month < 10}">
                                                <s:set var="temp" value="%{#temp + '0' + #month + '-'}"/>
                                            </s:if>
                                            <s:else>
                                                <s:set var="temp" value="%{#temp + #month + '-'}"/>
                                            </s:else>
                                            <s:if test="%{#dto.rentalDate.date < 10}">
                                                <s:set var="temp" value="%{#temp + '0' + #dto.rentalDate.date}"/>
                                            </s:if>
                                            <s:else>
                                                <s:set var="temp" value="%{#temp + #dto.rentalDate.date}"/>
                                            </s:else>

                                            <form action="UpdatingRentalDateCartAction" method="POST">
                                                <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                <input type="hidden" name="returnDate" value="<s:property value="#dto.returnDate.date"/>/<s:property value="%{#dto.returnDate.month + 1}"/>/<s:property value="%{#dto.returnDate.year + 1900}"/>"/>
                                                <input type="date" class="text-center" name="rentalDate" value="<s:property value="temp"/>" onchange="this.form.submit()"/>
                                            </form>
                                        </td>
                                        <td>

                                            <s:set var="temp" value="%{(#dto.returnDate.year + 1900) + '-'}"/>
                                            <s:set var="month" value="%{#dto.returnDate.month + 1}"/>
                                            <s:if test="%{#month < 10}">
                                                <s:set var="temp" value="%{#temp + '0' + #month + '-'}"/>
                                            </s:if>
                                            <s:else>
                                                <s:set var="temp" value="%{#temp + #month + '-'}"/>
                                            </s:else>
                                            <s:if test="%{#dto.returnDate.date < 10}">
                                                <s:set var="temp" value="%{#temp + '0' + #dto.returnDate.date}"/>
                                            </s:if>
                                            <s:else>
                                                <s:set var="temp" value="%{#temp + #dto.returnDate.date}"/>
                                            </s:else>
                                            <form action="UpdatingReturnDateCartAction" method="POST">
                                                <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                <input type="hidden" name="rentalDate" value="<s:property value="#dto.rentalDate.date"/>/<s:property value="%{#dto.rentalDate.month + 1}"/>/<s:property value="%{#dto.rentalDate.year + 1900}"/>"/>
                                                <input type="date" class="text-center" name="returnDate" value="<s:property value="temp"/>" onchange="this.form.submit()"/>
                                            </form>
                                        </td>
                                        <td>
                                            <p class="text-center">$<s:property value="%{#dto.price * #dto.quantity * #request.HASHTABLE_DAY[#dto.carID]}"/></p>
                                        </td>
                                        <td>
                                            <s:if test="%{#request.HASHTABLE_STATUS[#dto.carID] == 'Available'}"><p class="text-center text-success"><s:property value="%{#request.HASHTABLE_STATUS[#dto.carID]}"/></p></s:if>
                                            <s:elseif test="%{#request.HASHTABLE_STATUS[#dto.carID] == 'Unavailable'}"><p class="text-center text-danger"><s:property value="%{#request.HASHTABLE_STATUS[#dto.carID]}"/></p></s:elseif>
                                            <s:elseif test="%{#request.HASHTABLE_STATUS[#dto.carID] == 'Date mismatch'}"><p class="text-center text-danger"><s:property value="%{#request.HASHTABLE_STATUS[#dto.carID]}"/></p></s:elseif>
                                            <s:else><p class="text-center text-warning"><s:property value="%{#request.HASHTABLE_STATUS[#dto.carID]}"/></p></s:else>
                                            </td>
                                            <td>
                                                <form action="DeletingFromCartAction" class="form-signin" method="POST">
                                                    <input type="hidden" name="carID" value="<s:property value="#dto.carID"/>"/>
                                                <button type="submit" class="ml-3 bg-transparent delete-button"><i class="fas fa-trash text-danger"></i></button>
                                            </form>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="8">
                                        <s:if test="#request.PROMOTION == null">
                                            <form action="ApplyingPromotionAction" method="POST">
                                                <input type="text" name="code" class="d-inline" placeholder="Promotion code">
                                                <button type="submit" class="btn btn-primary">Apply</button>
                                            </form>
                                        </s:if>
                                        <s:else>
                                            <h4 class="text-info">Promotion applied: <s:property value="#request.PROMOTION.promotionName"/></h4>
                                        </s:else>
                                    </td>
                                    <td class="text-center" colspan="1"><strong>Total</strong></td>
                                    <td class="text-center">
                                        <strong>$<s:property value="#total"/></strong><br><br>
                                        <s:if test="%{#request.PROMOTION != null}">
                                            <strong>- <s:property value="#request.PROMOTION.percents"/>%</strong>
                                            <hr>
                                            <strong>$<s:property value="%{(#total * (100 - #request.PROMOTION.percents))/100}"/></strong>
                                        </s:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="8"></td>
                                    <td colspan="2"><a href="payment.jsp" class="btn btn-danger float-right">Click continue to pay</a></td>
                                </tr>
                            </tfoot>
                        </table>
                    </s:if>
                    <s:else>
                        <h1 class="text-center">There is no added product!</h1>
                    </s:else>
                </div>
            </div>
        </div>
        <s:include value="layout/footer.jsp"/>
        <script type="text/javascript">
            $(".delete-button").click(function () {
                if (confirm("Do you want to delete the car from cart?")) {
                    this.form.submit();
                } else {
                    return false;
                }
            });
        </script>
    </body>
</html>