<!-- Header -->
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

            <li class="nav-item">
                <a class="nav-link ml-3" href="${showHistoryLink}"><i class="fas fa-history mr-1"></i>History</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ml-3" href="${showCartLink}"><i class="fas fa-shopping-cart mr-1"></i>Cart</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ml-3" href="${showInfoLink}"><i class="fas fa-users-cog mr-1"></i>${sessionScope.USER.username}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ml-3" href="${logoutLink}"><i class="fas fa-sign-out-alt mr-1"></i>Logout</a>
            </li>

        </ul>
    </div>
</nav>
<!-- end header -->