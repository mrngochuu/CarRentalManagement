<%-- 
    Document   : index
    Created on : Mar 7, 2020, 6:13:18 PM
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

        <title>G.A.U | Home</title>
    </head>
    <body>
        <s:include value="layout/header.jsp"/>
        <s:include value="layout/slideShow.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <!-- search -->
                <div class="col-lg-3 mt-3 bg-light rounded">
                    <h1 class="text-center">SEARCH BOX</h1>
                    <form action="SearchingCarAction" class="form-signin">
                        <input class="form-control mb-5 mt-3" type="text" name="txtSearch" placeholder="Name" value="<s:property value="txtSearch"/>"/>
                        <!-- Type -->
                        <select class="form-control browser-default custom-select mb-5" name="cbCategory">
                            <option value="0">CATEGORY</option>
                            <s:iterator value="#session.HASHTABLE_CATEGORY" var="category">
                                <option value="<s:property value="#category.key"/>" <s:if test="%{#category.key == cbCategory}"> selected</s:if>><s:property value="#category.value"/></option>
                            </s:iterator>
                        </select>
                        <!--Date-->
                        <h4 class="text-center mb-3">Date rental</h5>
                            <div class="row">
                                <div class='col-md-6 mr-auto ml-auto'>
                                    <div class="form-group">
                                        <div class='input-group date' id='datetimepicker1'>
                                            <input type="text" class="form-control" name="fromDate" value="<s:property value="fromDate"/>"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class='mr-auto ml-auto col-md-6'>
                                    <div class="form-group">
                                        <div class='input-group date' id='datetimepicker2'>
                                            <input type="text" class="form-control" name="toDate" value="<s:property value="toDate"/>"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h4 class="text-center mb-3">Price</h5>
                                <div class="row mb-5">
                                    <div class="mr-auto ml-auto col-md-6">
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="text" id="price-min-input" name="priceMin" data-toggle="dropdown" class="form-control" placeholder="Min Price" value="<s:property value="priceMin"/>" readonly="true"/>
                                            <ul id="price-min" class="dropdown-menu">
                                                <li><a href="#" data-value="0" data-toggle="dropdown" >0</a></li>
                                                <li><a href="#" data-value="100" data-toggle="dropdown" >100</a></li>
                                                <li><a href="#" data-value="200" data-toggle="dropdown" >200</a></li>
                                                <li><a href="#" data-value="1000" data-toggle="dropdown" >1000</a></li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="mr-auto ml-auto col-md-6">
                                        <div id="max-group" class="input-group ">
                                            <span class="input-group-addon">$</span>
                                            <input type="text" id="price-max-input" name="priceMax" data-toggle="dropdown" class="form-control" placeholder="Max Price" value="<s:property value="priceMax"/>" readonly="true"/>
                                            <ul id="price-max" class="dropdown-menu">
                                                <li><a href="#" data-value="300">300</a></li>
                                                <li><a href="#" data-value="500">500</a></li>
                                                <li><a href="#" data-value="1000">1000</a></li>
                                                <li><a href="#" data-value="2000">2000</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <input class="form-control btn btn-outline-success bg-warning mb-5" type="submit" value="SEARCH" />
                                </form>
                                </div>

                                <div class="col-lg-9">
                                    <div class="row">
                                        <s:if test="%{#request.LIST_CAR != null}">
                                            <s:iterator value="#request.LIST_CAR" var="carDTO">
                                                <section class="col-lg-4" style="max-height: 500px; min-height: 500px;">
                                                    <div class="card brand">
                                                        <img src="#" alt="NO IMAGE" width="100%" height="250">
                                                        <div class="card-body">
                                                            <hr>
                                                            <h4 class="card-title font-italic font-weight-bold"><s:property value="#carDTO.carName"/> - <s:property value="#carDTO.model"/></h4>
                                                            <table class="row">
                                                                <tr>
                                                                    <td class="col-lg-4"><p class="card-text font-weight-bold">Model:</p></td>
                                                                    <td class="col-lg-8"><p class="card-text"><s:property value="#carDTO.model"/></p></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="col-lg-4"><p class="card-text font-weight-bold">Color:</p></td>
                                                                    <td class="col-lg-8"><p class="card-text"><s:property value="#carDTO.color"/></p></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="col-lg-4"><p class="card-text font-weight-bold">Type:</p></td>
                                                                    <td class="col-lg-8"><p class="card-text"><s:property value="#session.HASHTABLE_CATEGORY[#carDTO.categoryID]"/></p></td>
                                                                </tr>
                                                            </table>
                                                                <hr>
                                                            <div class="row">
                                                                <div class="col-lg-6"><h4 class="card-text text-center mr-auto ml-auto">Price: <s:property value="#carDTO.price"/></h4></div>
                                                                <div class="col-lg-6"><h4 class="card-text text-center mr-auto ml-auto">Quantity: <s:property value="#carDTO.quantity"/></h4></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </section>
                                            </s:iterator>
                                        </s:if>
                                    </div>
                                </div>

                                </div>
                                </div>
                                </div>
                                <s:include value="layout/footer.jsp"/>
                                <script type="text/javascript">
                                    $(function () {
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

                                        // Set Min Price
                                        $("#price-min li a").click(function () {
                                            $('#price-min-input').val($(this).attr('data-value'));
                                            $('#price-max-input').focus();
                                            $('#price-max').show();

                                        });

                                        // Set Max Price
                                        $("#price-max li a").click(function () {
                                            $('#price-max-input').val($(this).attr('data-value'));
                                            $('#price-max').hide();
                                        });

                                        // Show Max list when cleck on max input 
                                        $("#price-max-input").click(function () {
                                            $('#price-max').show();
                                        });

                                        // Hide Max List when keypress on max input
                                        $("#price-max-input").keypress(function () {
                                            $('#price-max').hide();
                                        });
                                    });
                                </script>
                                </body>
                                </html>
