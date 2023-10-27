<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${dto.headTitle}"/></title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="stylesheet" href="<c:url value="/css/profileWithRecipes.css" />">
    <link rel="stylesheet" href="<c:url value="/css/showRecipes.css" />">
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
    <div class="main-content-container main-profile">
        <div class="user-header">
            <div class="user-photo-container">
                <img class="user-photo"
                     id="userPhoto"
                     src="<c:url value="/userImage/${user.id}"/>" alt="profile photo not found">
            </div>
            <c:set var="userRecipes" value="${dto.recipeList}"/>
            <div class="user-data">
                <div class="user-data-container">
                    <div class="profile-name">
                        <div class="first-name"><c:out value="${user.name}"/></div>
                        <div><c:out value="${user.lastname}"/></div>
                        <c:if test="${user.admin}">
                            <div class="adminProfile">
                                <i class="material-icons icons admin-icon">verified_user</i>
                            </div>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${user.id eq currentUser.get().id}">
                            <div class="edit-profile">
                                <a class="edit_button" href="<c:url value="/editProfile"/>">
                                    <i class="material-icons icons padding-icon">create</i>
                                </a>
                            </div>
                            <div class="logout-btn-pos margin-bottom-last">
                                <a class="logout-btn btn" href=<c:url value="/logout"/>>
                                    <i class="material-icons icons logout-icon">logout</i>
                                    <span class="features-item-text logout-txt"><spring:message code="profile.logout"/></span>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="follow">
                                <c:choose>
                                    <c:when test="${isFollowed}">
                                        <c:url var="followUrl" value="/profile/unfollow/${user.id}"/>
                                        <c:set var="followingtxt" value="profile.unfollow"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="followUrl" value="/profile/follow/${user.id}"/>
                                        <c:set var="followingtxt" value="profile.follow"/>
                                    </c:otherwise>
                                </c:choose>
                                <form action="${followUrl}" method="POST">
                                    <button type="submit" class="btn follow-btn">
                                        <i class="material-icons bookmark margin-right-icon">group</i>
                                        <input type="hidden" value="${dto.pageNumber}" name="page">
                                        <spring:message code="${followingtxt}"/>
                                    </button>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="user-stats">
                    <div class="recipes-count">
                        <spring:message code="profile.recipes"/>:
                        <c:out value="${dto.totalRecipes}"/>
                    </div>
                    <a class="modal-trigger a-black" href="#modal-followers" >
                        <spring:message code="profile.followers"/>:
                        <c:out value="${user.followersCount}"/>
                    </a>
                    <a  class="modal-trigger a-black" href="#modal-following">
                            <spring:message code="profile.following"/>:
                            <c:out value="${user.followingCount}"/>
                    </a>
                </div>
                <div id="modal-followers" class="modal followers-modal">
                    <div class="modal-content">
                        <div class="modal-title">
                            <spring:message code="profile.followers"/>
                            <a href="javascript:void(0)" class="modal-close waves-effect waves-green btn-flat">
                                <i class="material-icons icons">clear</i>
                            </a>
                        </div>
                        <c:if test="${user.followersCount == 0}">
                            <c:choose>
                                <c:when test="${currentUser.get().id eq user.id}">
                                    <div>
                                        <spring:message code="profile.noFollowers"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <spring:message code="profile.noFollowersNoCurrent"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:forEach var="user_i" items="${followers}"  varStatus="loopStatus">
                            <div class="user-name-follow-container">
                                <c:choose>
                                    <c:when test="${currentUser.present and currentUser.get().id eq user_i.id}">
                                        <c:url value="/profile" var="profileUrl"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/profile/${user_i.id}" var="profileUrl"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${profileUrl}" class="follows-container">
                                    <img src="<c:url value="/userImage/${user_i.id}"/>" alt="" class="follows-photo">
                                    <div class="margin-right-info"><c:out value="${user_i.name}"/></div>
                                    <div><c:out value="${user_i.lastname}"/></div>
                                </a>
                                <div class="follow">
                                    <c:if test="${ not (currentUser.get().id eq user_i.id)}" >
                                        <c:choose>
                                            <c:when test="${user_i.isFollowedByCurrentUser eq true}">
                                                <c:url var="followUrl" value="/profile/${user.id}/unfollow/${user_i.id}"/>
                                                <c:set var="followingtxt" value="profile.unfollow"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url var="followUrl" value="/profile/${user.id}/follow/${user_i.id}"/>
                                                <c:set var="followingtxt" value="profile.follow"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <form action="${followUrl}" method="POST">
                                            <button type="submit" class="btn follow-btn">
                                                <input type="hidden" value="${dto.pageNumber}" name="page">
                                                <input type="hidden" value="true" name="openFollowersModal" id="openFollowersModal">
                                                <spring:message code="${followingtxt}"/>
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                            <c:if test="${!loopStatus.last}">
                                <hr class="users-line"/>
                            </c:if>
                        </c:forEach>
                    </div>
                    <c:if test="${user.followersCount > followers.size()}">
                        <c:choose>
                            <c:when test="${currentUser.get().id eq user.id}">
                                    <c:url var="showMoreFollowersUrl" value="/profile"/>
                            </c:when>
                            <c:otherwise>
                                <c:url var="showMoreFollowersUrl" value="/profile/${user.id}"/>
                            </c:otherwise>
                        </c:choose>
                        <form action="${showMoreFollowersUrl}" method="get" class="more-form">
                            <input type="hidden" name="followersToBring" value="${followers.size()+ 4}"/>
                            <input type="hidden" value="${dto.pageNumber}" name="page">
                            <input type="hidden" value="true" name="openFollowersModal">
                            <button type="submit" class="btn follow-btn">
                                <spring:message code="profile.showMore"/>
                            </button>
                        </form>
                    </c:if>
                </div>
                <div id="modal-following" class="modal followers-modal">
                    <div class="modal-content">
                        <div class="modal-title">
                            <spring:message code="profile.following"/>
                            <a href="javascript:void(0)" class="modal-close waves-effect waves-green btn-flat">
                                <i class="material-icons icons">clear</i>
                            </a>
                        </div>
                        <c:if test="${user.followingCount == 0}">
                            <c:choose>
                                <c:when test="${currentUser.get().id eq user.id}">
                                    <div>
                                        <spring:message code="profile.noFollowing"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <spring:message code="profile.noFollowingNoCurrent"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:forEach var="user_i" items="${following}"  varStatus="loopStatus">
                            <div class="user-name-follow-container">
                                <c:choose>
                                    <c:when test="${currentUser.present and currentUser.get().id eq user_i.id}">
                                        <c:url value="/profile" var="profileUrl"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/profile/${user_i.id}" var="profileUrl"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${profileUrl}" class="follows-container">
                                    <img src="<c:url value="/userImage/${user_i.id}"/>" alt="" class="follows-photo">
                                    <div class="margin-right-info"><c:out value="${user_i.name}"/></div>
                                    <div><c:out value="${user_i.lastname}"/></div>
                                </a>
                                <div class="follow">
                                    <c:if test="${ not (currentUser.get().id eq user_i.id)}" >
                                    <c:choose>
                                        <c:when test="${user_i.isFollowedByCurrentUser eq true}">
                                            <c:url var="followUrl" value="/profile/${user.id}/unfollow/${user_i.id}"/>
                                            <c:set var="followingtxt" value="profile.unfollow"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="followUrl" value="/profile/${user.id}/follow/${user_i.id}"/>
                                            <c:set var="followingtxt" value="profile.follow"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <form action="${followUrl}" method="POST">
                                        <input type="hidden" value="${dto.pageNumber}" name="page">
                                        <input type="hidden" value="true" name="openFollowingModal" id="openFollowingModal">
                                        <button type="submit" class="btn follow-btn">
                                            <spring:message code="${followingtxt}"/>
                                        </button>
                                    </form>
                                    </c:if>
                                </div>

                            </div>
                            <c:if test="${!loopStatus.last}">
                                <hr class="users-line"/>
                            </c:if>
                        </c:forEach>
                    </div>
                    <c:if test="${user.followingCount > following.size() }">
                        <c:choose>
                            <c:when test="${currentUser.get().id eq user.id}">
                                <c:url var="showMoreFollowingUrl" value="/profile"/>
                            </c:when>
                            <c:otherwise>
                                <c:url var="showMoreFollowingUrl" value="/profile/${user.id}"/>
                            </c:otherwise>
                        </c:choose>
                        <form action="${showMoreFollowingUrl}" method="get" class="more-form">
                            <input type="hidden" name="followingToBring" value="${following.size()+4}"/>
                            <input type="hidden" value="${dto.pageNumber}" name="page">
                            <input type="hidden" value="true" name="openFollowingModal">
                            <button type="submit" class="btn follow-btn">
                                <spring:message code="profile.showMore"/>
                            </button>
                        </form>
                    </c:if>
                </div>


            </div>
        </div>
        <hr class="divider-profile"/>
        <div class="user-recipes">
            <div class="cards">
                <div class="row">
                    <c:if test="${empty userRecipes}">
                        <c:choose>
                            <c:when test="${currentUser.get().id eq user.id}">
                                <div class="no-recipes">
                                    <spring:message code="profile.noRecipesCurrent"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="no-recipes">
                                    <spring:message code="profile.noRecipesNoCurrent"/>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:forEach var="recipe" items="${userRecipes}">
                        <div class="col s3 m6 l4 offset-s3">
                            <div class="recipe-card">
                                <jsp:include page="/WEB-INF/jsp/components/recipecard/recipecard.jsp">
                                    <jsp:param name="title" value="${recipe.title}"/>
                                    <jsp:param name="description" value="${recipe.description}"/>
                                    <jsp:param name="recipe_id" value="${recipe.recipeId}"/>
                                    <jsp:param name="minutes" value="${recipe.minutes}"/>
                                    <jsp:param name="hours" value="${recipe.hours}"/>
                                    <jsp:param name="difficulty" value="${recipe.difficultyString}"/>
                                    <jsp:param name="image_id" value="${recipe.recipeImages.get(0).imageId}"/>
                                    <jsp:param name="likes" value="${recipe.likes}"/>
                                    <jsp:param name="dislikes" value="${recipe.dislikes}"/>
                                </jsp:include>
                            </div>
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
</div>
</body>
<script id="scriptLoader"
        following-modal="${openFollowingModal}"
        followers-modal="${openFollowersModal}"
>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems);


        var openModal = document.getElementById("scriptLoader").getAttribute("following-modal");
        debugger;
        if (openModal === 'true') {
            var modalFollowing = document.getElementById('modal-following');
            var modalInstanceFollowing = M.Modal.getInstance(modalFollowing);
            modalInstanceFollowing.open();
        }
        var openModalFollowers = document.getElementById("scriptLoader").getAttribute("followers-modal");
        if (openModalFollowers === 'true') {
            var modalFollowers = document.getElementById('modal-followers');
            var modalInstanceFollowers = M.Modal.getInstance(modalFollowers);
            modalInstanceFollowers.open();
        }
    });
    function submitPage(pageNumber) {
        document.getElementById("pageInput").value = pageNumber;
        document.getElementById("paginationForm").submit();
    }
</script>
</html>