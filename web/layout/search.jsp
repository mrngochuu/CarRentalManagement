<!-- Search-->
<div class="container-fluid mt-3">
    <p class="title">Search Real Estate</p>
    <div class="row">
        <form action="MainController" class="form offset-lg-4 col-lg-4 bg-light row">
            <input class="form-control mb-2 mt-3" type="text" name="txtSearch" placeholder="Title" value="${param.txtSearch}"/>

            <!-- Type -->
            <select class="form-control browser-default custom-select mb-2" name="cbCategory" onchange="this.form.submit();">
                <option value="0" >Choose type of real estate</option>
                <!-- 1 vòng for -->
                <c:forEach items="${sessionScope.LIST_CATEGORY}" var="categoryDTO">
                    <option value="${categoryDTO.categoryID}" <c:if test="${categoryDTO.categoryID eq param.cbCategory}">selected</c:if> >${categoryDTO.categoryName}</option>
                </c:forEach>
            </select>

            <!-- City -->
            <select selected class="form-control browser-default custom-select mb-2" name="cbCity" onchange="this.form.submit();">
                <option value="0">Choose the city</option>
                <c:forEach items="${sessionScope.LIST_CITY}" var="cityDTO">
                    <option value="${cityDTO.cityID}" <c:if test="${cityDTO.cityID eq param.cbCity}">selected</c:if>>${cityDTO.cityName}</option>
                </c:forEach>
            </select>

            <!-- State -->
            <select selected class="form-control browser-default custom-select mb-2" name="cbState" onchange="this.form.submit();">
                <option value="0">Choose the state</option>
                <c:forEach items="${sessionScope.LIST_STATE}" var="stateDTO">
                    <option value="${stateDTO.stateID}" <c:if test="${stateDTO.stateID eq param.cbState}">selected</c:if>>${stateDTO.stateName}</option>
                </c:forEach>
            </select>
            <button class="form-control btn btn-outline-success mb-2" type="submit" name="action" value="SearchProduct">SEARCH</button>
            <input type="hidden" name="action" value="SearchProduct"/>
        </form>
    </div>
</div>
<!-- end search -->