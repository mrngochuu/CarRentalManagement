<%-- 
    Document   : createCar
    Created on : Mar 16, 2020, 9:06:58 PM
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
        <title>G.A.U | Car</title>

        <style>
            input[type='file'] {
                color: transparent;
            }
        </style>
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
                                <s:if test="%{#request.IMG == null}">
                                    <img src="http://localhost:8084/Image/noimage.png" alt="" class="img-rounded img-responsive" />
                                </s:if>
                                <s:else>
                                    <img src="http://localhost:8084/Image/<s:property value="#request.IMG"/>" alt="" class="img-rounded img-responsive" />
                                </s:else>

                                <form action="UpdatingImageAction" enctype="multipart/form-data" method="POST">
                                    <input type="file" name="inputFile" onchange="this.form.submit()"/>
                                    <input type="hidden" name="carName" value="<s:property value="carName"/>" class="mb-3 form-control"/>
                                    <input type="hidden" name="year" value="<s:property value="year"/>"/>
                                    <input type="hidden" name="color" value="<s:property value="color"/>"/>
                                    <input type="hidden" name="cbCategory" value="<s:property value="cbCategory"/>"/>
                                    <input type="hidden" name="price" value="<s:property value="price"/>"/>
                                    <input type="hidden" name="quantity" value="<s:property value="quantity"/>"/>
                                </form>
                                <font color="red"><p class="mb-3"><s:fielderror fieldName="imgURL"/></p></font>
                            </div>
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <form action="CreatingCarAction" method="POST">
                                    <input type="hidden" name="imgURL" value="<s:property value="#request.IMG"/>"/>
                                    <h3 class="font-weight-bold mb-3">CAR NAME</h3>
                                    <input type="text" name="carName" value="<s:property value="carName"/>" class="mb-3 form-control"/>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="carName"/></p></font>
                                    <hr>
                                    <h3 class="mb-3">DESCRIPTION</h3>
                                    <input type="text" name="year" value="<s:property value="year"/>" placeholder="Model" class="mb-3 form-control"/>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="year"/></p></font>

                                    <input type="text" name="color" value="<s:property value="color"/>" placeholder="Color"  class="mb-3 form-control"/>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="color"/></p></font>

                                    <select class="form-control browser-default custom-select mb-5" name="cbCategory">
                                        <option value="0">CATEGORY</option>
                                        <s:iterator value="#session.HASHTABLE_CATEGORY" var="category">
                                            <option value="<s:property value="#category.key"/>" <s:if test="%{#category.key == cbCategory}"> selected</s:if>><s:property value="#category.value"/></option>
                                        </s:iterator>
                                    </select>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="cbCategory"/></p></font>

                                    <hr>
                                    <h3 class="mb-3">RENTAL DETAILS</h3>
                                    <table>
                                        <tr>
                                            <td><p class="font-weight-bold">Price:</p></td>
                                            <td>$<input type="number" min="1" step="1" name="price" value="<s:property value="price"/>" style="width: 80px;" class="ml-2 mb-3 text-center"/> /day</td>
                                        </tr>
                                        <tr >
                                            <td><p class="font-weight-bold">Quantity:</p></td>
                                            <td><input type="number" min="1" step="1" name="quantity" value="<s:property value="quantity"/>" style="width: 80px;" class="ml-4 ml-1 mb-3 text-center"/></td>
                                        </tr>
                                    </table>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="price"/></p></font>
                                    <font color="red"><p class="mb-3"><s:fielderror fieldName="quantity"/></p></font>
                                    <input type="submit" class="form-control btn btn-primary btn-lg mb-3 text-center" value="CREATE NEW CAR"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <s:include value="layout/footer.jsp"/>
    </body>
</html>

