<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${dto.headTitle}"/></title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/showRecipes.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/searchResults.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp">
            <jsp:param name="pageTitle" value="${dto.pageTitle}"/>
        </jsp:include>
    </div>
    <div class="main-content-container">
        <div class="main-content">
            <div class="heading-container">
                <spring:message code="${dto.pageTitle}" var="title"/>
                <div class="main-title dark"><c:out value="${title}"/></div>
                <jsp:include page="/WEB-INF/jsp/components/searchBar.jsp">
                    <jsp:param name="searchBarQuery" value="${dto.searchBarQuery}"/>
                </jsp:include>
            </div>

            <hr class="divider"/>

            <div class="filter-recipe-container">
                <div class="filter-container">
                    <jsp:include page="/WEB-INF/jsp/components/filter.jsp">
                        <jsp:param name="selectedIngredients" value="${dto.selectedIngredients}"/>
                        <jsp:param name="dto" value="${dto}"/>
                    </jsp:include>
                </div>
                <div class="cards">
                    <div class="row">
                        <c:if test="${empty dto.recipeList}">
                            <div class="no-results"> <spring:message code="showRecipes.noResults"/></div>
                        </c:if>
                        <c:forEach var="recipe" items="${dto.recipeList}">
                            <div class="col s3 m6 l4 offset-s3">
                                <div class="recipe-card">
                                    <jsp:include page="/WEB-INF/jsp/components/recipecard/recipecard.jsp">
                                        <jsp:param name="title" value="${recipe.title}"/>
                                        <jsp:param name="description" value="${recipe.description}"/>
                                        <jsp:param name="recipe_id" value="${recipe.recipeId}"/>
                                        <jsp:param name="minutes" value="${recipe.minutes}"/>
                                        <jsp:param name="hours" value="${recipe.hours}"/>
                                        <jsp:param name="difficulty" value="${recipe.difficultyString}"/>
                                        <jsp:param name="image_id" value="${recipe.mainImageId}"/>                          
                                        <jsp:param name="likes" value="${recipe.likes}"/>
                                        <jsp:param name="dislikes" value="${recipe.dislikes}"/>
                                    </jsp:include>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <c:if test="${dto.pageNumber <= dto.totalPages}">
                <jsp:include page="/WEB-INF/jsp/components/paginationBar.jsp">
                    <jsp:param name="dto" value="${dto}"/>
                </jsp:include>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
