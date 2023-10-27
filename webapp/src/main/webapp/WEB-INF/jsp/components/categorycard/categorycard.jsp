<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<c:url value="/css/categorycard.css"/>">

<c:url var="filter" value="/"/>
<div>
    <form action="${filter}" method="get">
        <button type="submit" class="category-card btn">
            <spring:message code="${param.name}"/>
        </button>
        <input type="hidden" id="selectedCategories" name="selectedCategories" value="${param.id}"/>
    </form>
</div>