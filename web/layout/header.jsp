<!-- Header -->
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <c:url var="homeLink" value="MainController">
        <c:param name="action" value="SearchProduct"/>
    </c:url>
    <a class="navbar-brand" href="${homeLink}"><img src="http://localhost:8084/Image/logo.png" width="40" height="40"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${homeLink}">Home</a>
            </li>
        </ul>
    </div>
    <div class="collapse navbar-collapse flex-grow-0" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            
            <s:if test="%{#session.ROLE.roleName == 'customer'}">
                <s:url action="ShowingHistoryAction" var="showingHistoryLink"/>
                <li class="nav-item">
                    <a class="nav-link ml-3" href="<s:property value="#showingHistoryLink"/>"><i class="fas fa-history mr-1"></i>History</a>
                </li>
                <s:url action="ShowingCartAction" var="showingCartLink"/>
                <li class="nav-item">
                    <a class="nav-link ml-3" href="<s:property value="#showingCartLink"/>"><i class="fas fa-shopping-cart mr-1"></i>Cart</a>
                </li>
                
                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.email}
                    </a>
                        <s:url action="LogoutAction" var="logoutLink" />
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="<s:property value="#logoutLink"/>"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
                    </div>
                </li>
            </s:if>

            <s:if test="%{#session.ROLE.roleName == 'admin'}">
                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropdown link
                    </a>

                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>

                <li class="dropdown show">
                    <a class="dropdown-toggle nav-link ml-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.email}
                    </a>
                        <s:url action="LogoutAction" var="logoutLink" />
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="#logoutLink"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
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