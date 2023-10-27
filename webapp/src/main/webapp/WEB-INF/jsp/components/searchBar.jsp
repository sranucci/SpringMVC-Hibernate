<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="nav-wrapper">
    <form method="get" class="search-form" id="searchForm">
        <div class="input-field white">
            <i class="material-icons grey-text text-darken-1 loop search-icon">search</i>
            <input placeholder="<spring:message code="searchBar.search"/>" id="search"
                   type="search" name="searchBarQuery" class="search-input"
            value="${param.searchBarQuery}">
            <label for="search"></label>
            <div id="clearSearch">
                <a href="<c:url value="/"/>">
                <i class="material-icons"
                   style="color: gray !important; user-select:none; cursor: pointer;">close</i>
                </a>
            </div>
        </div>
    </form>
</div>

<link rel="stylesheet" href="<c:url value="/css/searchBar.css"/>">

<script>
    document.getElementById("search").addEventListener("keydown", function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            event.stopPropagation();
            this.form.submit();
        }
    });


</script>