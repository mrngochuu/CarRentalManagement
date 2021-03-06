<!-- Header -->
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <s:url var="homeLink" action="SearchingCarAction"/>
    <a href="<s:property value="#homeLink"/>"><img src="http://localhost:8084/Image/logo.png" width="40" height="40"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="<s:property value="#homeLink"/>">Home</a>
            </li>
        </ul>
    </div>
    <div class="collapse navbar-collapse flex-grow-0" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <s:url action="LogoutAction" var="logoutLink" />

            <s:if test="%{#session.ROLE.roleName == 'customer'}">
                <s:url action="SearchingHistoryAction" var="searchHistoryLink"/>
                <li class="nav-item">
                    <a class="nav-link ml-3" href="<s:property value="#searchHistoryLink"/>"><i class="fas fa-history mr-1"></i>History</a>
                </li>
                <s:url action="ShowingCartAction" var="showingCartLink"/>
                <li class="nav-item">
                    <a class="nav-link ml-3" href="<s:property value="#showingCartLink"/>"><i class="fas fa-shopping-cart mr-1"></i>Cart</a>
                </li>

                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.email}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="<s:property value="#logoutLink"/>"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
                    </div>
                </li>
            </s:if>

            <s:if test="%{#session.ROLE.roleName == 'admin'}">
                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Manage
                    </a>
                    <s:url action="ManagingTransactionAction" var="manageTransactionLink"/>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="promotion.jsp">Promotion</a>
                        <a class="dropdown-item" href="createCar.jsp">Car</a>
                        <a class="dropdown-item" href="<s:property value="#manageTransactionLink"/>">Transaction</a>
                    </div>
                </li>

                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.email}
                    </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="<s:property value="#logoutLink"/>"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
                        </div>
                    </li>
            </s:if>

            <s:if test="%{#session.ROLE == null}">
                <li class="nav-item">
                    <a class="nav-link ml-3" href="login.jsp"><i class="fas fa-sign-in-alt"></i>${sessionScope.USER.username}Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ml-3" href="registration.jsp"><i class="fas fa-sign-out-alt mr-1"></i>Register</a>
                </li>
            </s:if>    
        </ul>
    </div>
</nav>
<!-- end header -->