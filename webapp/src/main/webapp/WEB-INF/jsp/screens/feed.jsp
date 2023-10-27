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
    <link rel="stylesheet" href="<c:url value="/css/feed.css" />">
    <link rel="stylesheet" href="<c:url value="/css/paginationBar.css"/>">

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
            </div>

            <hr class="divider"/>
            <div class="feed-recipe-container">
             <c:if test="${dto.recipeList.size() == 0}">
                    <div class="empty-msg"> <spring:message code="feed.emptyFeedMessage"/></div>
                    <ul class="collection suggestion-container">
                        <c:forEach var="user_i" items="${usersToFollowSuggestion}">
                            <c:if test="${ not (currentUser.get().id eq user_i.id)}" >
                            <li class="collection-item user-name-follow-container">
                                <c:url value="/profile/${user_i.id}" var="profileUrl"/>
                                <a href="${profileUrl}" class="follows-container">
                                    <img src="<c:url value="/userImage/${user_i.id}"/>" alt="" class="follows-photo">
                                    <div class="margin-right-info"><c:out value="${user_i.name}"/></div>
                                    <div><c:out value="${user_i.lastname}"/></div>
                                </a>
                                <div class="follow">
                                        <c:url var="followUrl" value="/feed/follow/${user_i.id}"/>
                                        <c:set var="followingtxt" value="profile.follow"/>
                                        <form action="${followUrl}" method="POST">
                                            <button type="submit" class="btn follow-btn">
                                                <spring:message code="${followingtxt}"/>
                                            </button>
                                        </form>
                                </div>
                            </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:if>

            <c:forEach var="recipe" items="${dto.recipeList}">
                    <c:set var="isliked" value="false" />
                    <c:forEach var="likedRecipeId" items="${recipesLiked}">
                        <c:if test="${likedRecipeId.intValue().equals(recipe.recipeId.intValue())}">
                            <c:set var="isliked" value="true" />
                        </c:if>
                    </c:forEach>

                    <c:set var="isDisliked" value="false" />
                    <c:forEach var="dislikedRecipeId" items="${recipesDisliked}">
                        <c:if test="${dislikedRecipeId.intValue().equals(recipe.recipeId.intValue())}">
                            <c:set var="isDisliked" value="true" />
                        </c:if>
                    </c:forEach>

                    <c:set var="isSaved" value="false" />
                    <c:forEach var="recipesSavedItem" items="${recipesSaved}">
                        <c:if test="${recipesSavedItem.id.recipeId.intValue().equals(recipe.recipeId.intValue())}">
                            <c:set var="isSaved" value="true" />
                        </c:if>
                    </c:forEach>
                    <div class="feed-recipe-card">
                            <jsp:include page="/WEB-INF/jsp/components/recipecard/feedRecipeCard.jsp">
                                <jsp:param name="title" value="${recipe.title}"/>
                                <jsp:param name="description" value="${recipe.description}"/>
                                <jsp:param name="user_name" value="${recipe.user.name}"/>
                                <jsp:param name="user_lastname" value="${recipe.user.lastname}"/>
                                <jsp:param name="user_id" value="${recipe.user.id}"/>
                                <jsp:param name="recipe_id" value="${recipe.recipeId}"/>
                                <jsp:param name="image_id" value="${recipe.recipeImages.get(0).imageId}"/>
                                <jsp:param name="likes" value="${recipe.likes}"/>
                                <jsp:param name="dislikes" value="${recipe.dislikes}"/>
                                <jsp:param name="createdAt" value="${recipe.createdAt}"/>
                                <jsp:param name="commentsCount" value="${recipe.commentsCount}"/>
                                <jsp:param name="isliked" value="${isliked}"/>
                                <jsp:param name="isDisliked" value="${isDisliked}"/>
                                <jsp:param name="isSaved" value="${isSaved}"/>
                                <jsp:param name="time" value="${recipe.createdAt}"/>
                                <jsp:param name="pageNumber" value="${dto.pageNumber}"/>
                            </jsp:include>
                        </div>
                </c:forEach>
            </div>
            <form method="get" id="paginationForm" class="pagination-bar">
                <c:if test="${dto.moreThanOnePage()}">
                    <ul class="pagination">
                        <c:if test="${dto.prevPage.present}">
                            <li class="waves-effect page-button"><a onclick="submitPage(${dto.prevPage.get()})"><spring:message code="paginationBar.previous"/></a>
                            </li>
                        </c:if>

                        <c:choose>
                            <c:when test="${dto.pageNumber > dto.totalPages}">
                                <c:set value="${dto.totalPages}" var="actualPage"/>
                            </c:when>
                            <c:otherwise>
                                <c:set value="${dto.pageNumber}" var="actualPage"/>
                            </c:otherwise>
                        </c:choose>

                        <li class="disabled"><span class="total-pages"><spring:message code="pagination.page" arguments="${actualPage}, ${dto.totalPages}" /></span></li>

                        <c:if test="${dto.nextPage.present}">
                            <li class="waves-effect page-button"><a onclick="submitPage(${dto.nextPage.get()})"><spring:message code="paginationBar.next"/></a></li>
                        </c:if>

                    </ul>
                </c:if>
                <input type="hidden" id="pageInput" name="page">
            </form>
        </div>
    </div>
</div>
</body>


<script>
    function submitPage(pageNumber) {
        document.getElementById("pageInput").value = pageNumber;
        document.getElementById("paginationForm").submit();
    }
</script>

</html>

