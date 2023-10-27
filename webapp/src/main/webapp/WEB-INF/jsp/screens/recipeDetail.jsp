<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recipe Detail</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/recipeDetail.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp"/>
    </div>
    <div class="main-content-container">
        <div class="recipe-header">
            <div class="column1">
                <div class="image-section">
                    <c:set var="images" value="${recipe.recipeImages}"/>
                    <div class="main-image">
                        <img src="<c:url value='/recipeImage/${images.get(0).imageId}'/>" alt="">
                    </div>
                    <c:if test="${images.size() > 1}">
                        <div class="thumbnail-images">
                            <c:forEach items="${images}" var="image" varStatus="status">
                                <div class="thumbnail-image">
                                    <c:url var="url" value='/recipeImage/${image.imageId}'/>
                                    <img src="${url}" alt=""
                                         data-image-id="${image.imageId}" onclick="showAsMainImage('${url}')">
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="column2">
                <div class="description-container">

                    <div class="column2-header">
                        <div>
                            <c:url value="/profile/${recipeUser.id}" var="profile"/>
                            <a href="${profile}" class="username">
                                <div class="margin-right">
                                    <img src="<c:url value="/userImage/${recipeUser.id}"/>" alt="" class="user-image"> <!-- notice the "circle" class -->
                                </div>
                                <c:out value="${recipeUser.name}" escapeXml="true"/>
                                <c:out value="${recipeUser.lastname}" escapeXml="true"/> •
                                <fmt:formatDate value="${recipe.createdAt}" pattern="dd-MM-yyyy"/>
                            </a>
                        </div>
                        <div class="save-and-share save-and-share-recipe">
                            <c:if test="${currentUser.present and currentUser.get().id != recipe.user.id}">
                                    <c:choose>
                                        <c:when test="${isFollowed}">
                                            <c:url var="followUrl" value="/recipeDetail/${recipeId}/unfollow/${recipe.user.id}"/>
                                            <spring:message code="recipeDetail.unfollow" var="followingtxt"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="followUrl" value="/recipeDetail/${recipeId}/follow/${recipe.user.id}"/>
                                            <spring:message code="recipeDetail.follow" var="followingtxt"/>

                                        </c:otherwise>
                                    </c:choose>
                                    <form action="${followUrl}" method="POST" class="no-margin">
                                        <button type="submit" class="btn save">
                                            <i class="material-icons bookmark">group</i>
                                            <span class="save-button-text"><c:out value="${followingtxt}"/></span>
                                        </button>
                                    </form>
                            </c:if>
                            <c:choose>
                                <c:when test="${isSaved}">
                                    <c:url var="saveUrl" value="/unsave/${recipe.recipeId}"/>
                                    <spring:message code="recipeDetail.saved" var="Saved"/>
                                    <c:set var="text" value="${Saved}"/>
                                    <c:set var="saveIcon" value="bookmark"/>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="saveUrl" value="/save/${recipe.recipeId}"/>
                                    <spring:message code="recipeDetail.save" var="Save"/>
                                    <c:set var="text" value="${Save}"/>
                                    <c:set var="saveIcon" value="bookmark_border"/>
                                </c:otherwise>
                            </c:choose>
                            <form action="${saveUrl}" method="POST" class="no-margin">
                                <div class="margin-left">
                                    <button type="submit" class="btn save">
                                        <i class="material-icons bookmark"><c:out value="${saveIcon}"/></i>
                                        <span class="save-button-text"><c:out value="${text}"/></span>
                                    </button>
                                </div>
                            </form>

                            <c:if test="${currentUser.present and currentUser.get().id == recipe.user.id}">
                                <div class="margin-left">
                                    <form method="get" action="<c:url value="/editRecipe/${recipe.recipeId}"/>" class="no-margin">
                                        <button class="btn save">
                                            <i class="material-icons bookmark">create</i>
                                            <span class="save-button-text"><spring:message code="recipeDetail.edit"/></span>
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${currentUser.present and currentUser.get().admin and currentUser.get().id != recipe.user.id}">
                                <div id="delete-modal" class="modal">
                                    <div class="modal-content">
                                        <h4><spring:message code="recipeDetail.deleteModalHeader"/></h4>
                                        <p><spring:message code="recipeDetail.deleteModalAdminText"/></p>
                                        <br>

                                        <c:if test="${param.size() >= 1}">
                                            <span style="color: red"><spring:message
                                                    code="recipeDetail.deleteModalError"/></span>
                                        </c:if>

                                        <div class="row">
                                            <c:url value="/deleteRecipeAdmin/${recipe.recipeId}" var="deleteRecipeAdmin"/>
                                            <form:form class="col s12" id="delete-recipe-form" method="post"
                                                       modelAttribute="deleteForm"
                                                       action="${deleteRecipeAdmin}">
                                                <div class="row">
                                                    <div class="input-field col s12">
                                                        <form:textarea id="textarea1"
                                                                       class="materialize-textarea"
                                                                       path="deletionMotive"/>
                                                        <label for="textarea1"><spring:message
                                                                code="recipeDetail.deleteModalPlaceholder"/></label>
                                                    </div>
                                                </div>
                                            </form:form>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button form="delete-recipe-form"
                                                class="btn red waves-effect waves-light delete-button-modal"
                                                type="submit"
                                                name="action"><spring:message code="recipeDetail.delete"/>
                                            <i class="material-icons left">delete</i>
                                        </button>
                                    </div>
                                </div>
                                <a class="modal-trigger delete-button" id="modal-delete-button" data-target="delete-modal">
                                    <i class="material-icons bookmark">delete</i>
                                    <span class="features-item-text"><spring:message code="recipeDetail.delete"/></span>
                                </a>
                            </c:if>


                            <c:if test="${recipe.isPrivate}">
                                <div class="share share-div">
                                    <i class="material-icons icons">lock_outline</i>
                                </div>
                            </c:if>
                            <c:if test="${ ( currentUser.present and not currentUser.get().admin and currentUser.get().id eq recipe.user.id )
                                    or ( currentUser.present and currentUser.get().admin and currentUser.get().id eq recipe.user.id)}">

                                <!-- Modal Structure For user deletion -->
                                <div id="delete-modal" class="modal">
                                    <div class="modal-content">
                                        <h4><spring:message code="recipeDetail.deleteModalHeader"/></h4>
                                        <p><spring:message code="recipeDetail.deleteModalUserText"/></p>
                                    </div>
                                    <div class="modal-footer">
                                        <form method="post"
                                              action="<c:url value="/deleteUserRecipe/${recipe.recipeId}"/>">
                                            <button class="btn red waves-effect waves-light delete-button-modal red"
                                                    type="submit" name="action"><spring:message code="recipeDetail.delete"/>
                                                <i class="material-icons left">delete</i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                                <a class="modal-trigger delete-button" id="modal-delete-button" data-target="delete-modal">
                                    <i class="material-icons bookmark ">delete</i>
                                    <span class="features-item-text"><spring:message code="recipeDetail.delete"/></span>
                                </a>
                            </c:if>
                        </div>
                    </div>

                    <div class="recipe-title dark">
                        <c:out value="${recipe.title}" escapeXml="true"/>
                    </div>
                    <div class="description">
                        <c:out value="${recipe.description}"/>
                    </div>
                    <div class="description-bottom-container">
                        <div class="description-bottom">
                            <div class="icon">
                                <i class="material-icons icons time">assistant_photo</i>
                                <span class="icon icon-text info"><spring:message code="recipeDetail.language"/>:  </span>
                            </div>
                            <spring:message code="recipeDetail.${recipe.language}"/>
                        </div>

                        <div class="description-bottom">
                            <div class="icon">
                                <i class="material-icons icons time">access_time</i>
                                <span class="icon icon-text info"><spring:message code="recipeDetail.time"/>:</span>
                            </div>
                            <c:choose>
                                <c:when test="${recipe.hours >= 1}"><c:out value="${recipe.hours}"/>h<c:if
                                        test="${recipe.minutes > 0}"> <c:out
                                        value="${recipe.minutes}"/>min</c:if></c:when>
                                <c:otherwise><c:if
                                        test="${recipe.minutes >= 1}"><c:out
                                        value="${recipe.minutes}"/>min</c:if></c:otherwise>
                            </c:choose>
                        </div>
                        <div class="description-bottom">
                            <div class="icon">
                                <i class="material-icons rotate-icon">sort</i>
                                <div class="difficulty-text icon-text info"><spring:message
                                        code="recipeDetail.difficulty"/></div>
                            </div>
                            <div class="description-bottom-text"><spring:message
                                    code="${recipe.difficultyString}"/></div>
                        </div>
                        <div class="description-bottom">
                            <div class="icon">
                                <i class="material-icons rotate-icon">pie_chart_outlined</i>
                                <div class="difficulty-text icon-text info"><spring:message
                                        code="recipeDetail.servings"/></div>
                            </div>
                            <div class="description-bottom-text"><c:out value="${recipe.servings}"/></div>
                        </div>
                    </div>
                    <hr class="line"/>

                    <c:if test="${commented}">
                        <div id="popup" class="popup">
                            <div class="comment-modal">
                                <i class="material-icons icons thumb">check</i>
                                <spring:message code="recipeDetail.commentSuccess"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="comment">
                        <div class="likes-container">
                            <div class="likes">
                                <c:choose>
                                    <c:when test="${isLiked}">
                                        <c:url var="likeUrl" value="/rate/remove/${recipe.recipeId}"/>
                                        <c:set var="cssClass" value="thumb-up-activated"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="likeUrl" value="/rate/like/${recipe.recipeId}"/>
                                        <c:set var="cssClass" value="thumb-deactivated"/>
                                    </c:otherwise>
                                </c:choose>
                                <form action="${likeUrl}" method="POST" class="no-margin">
                                    <button type="submit" class="btn like-btn ${cssClass}">
                                        <i class="material-icons icons thumb">thumb_up</i>
                                    </button>
                                </form>

                                <span class="features-item-text rating-text">
                                    <spring:message code="recipeDetail.likes" arguments="${recipe.likes}"/>
                                </span>
                            </div>
                            <div class="dislikes">
                                <c:choose>
                                    <c:when test="${isDisliked}">
                                        <c:url var="dislikeUrl" value="/rate/remove/${recipe.recipeId}"/>
                                        <c:set var="cssClass" value="thumb-down-activated"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="dislikeUrl" value="/rate/dislike/${recipe.recipeId}"/>
                                        <c:set var="cssClass" value="thumb-deactivated"/>

                                    </c:otherwise>
                                </c:choose>
                                <form action="${dislikeUrl}" method="POST" id="likeForm" class="no-margin">
                                    <button type="submit" class="btn like-btn ${cssClass}">
                                        <i class="material-icons icons thumb ">thumb_down</i>
                                    </button>
                                </form>

                                <span class="features-item-text rating-text">
                                    <spring:message code="recipeDetail.dislikes" arguments="${recipe.dislikes}"/>
                                </span>
                            </div>
                        </div>
                        <c:url value="/commentRecipe/${recipe.recipeId}" var="commentEndpoint"/>
                        <form:form modelAttribute="commentForm" action="${commentEndpoint}" method="post" class="comment-form" id="commentForm">
                            <div class="comment-container">
                                <c:if test="${currentUser.present}">
                                    <img class="user-photo" src="<c:url value="/userImage/${currentUser.get().id}"/>" alt="">
                                </c:if>
                                <div class="comment-input">
                                    <spring:message code="recipeDetail.commentPlaceholder" var="commentPlaceholder"/>
                                    <form:input type="text" id="input" placeholder="${commentPlaceholder}" path="comment"/>
                                    <input type="hidden" name="commented" value="false" id="commentValue"/>
                                    <button id="btn" class="btn submit" onclick="comment()">
                                        <spring:message code="recipeDetail.commentBtn"/>
                                    </button>
                                </div>
                            </div>
                            <form:errors path="comment" cssClass="error commentError" element="span"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <hr class="divider"/>
        <div class="recipe-description">
            <div class="column1-description">
                <div class="ingredients-container">
                    <div>
                        <div class="subtitle dark"><spring:message code="recipeDetail.categories"/></div>
                        <div class="categories">
                            <c:set var="categories" value="${recipe.recipeCategories}"/>
                            <c:forEach items="${categories}" var="category">
                                <jsp:include page="/WEB-INF/jsp/components/categorycard/categorycard.jsp">
                                    <jsp:param name="name" value="${category.category.name}"/>
                                    <jsp:param name="id" value="${category.category.categoryId}"/>
                                </jsp:include>
                            </c:forEach>
                        </div>
                    </div>
                    <hr class="divider-child margin"/>
                    <div class="subtitle dark"><spring:message code="recipeDetail.ingredients"/></div>
                    <div>
                        <c:set var="ingredients" value="${recipe.ingredients}"/>
                        <ol class="detail_ol">
                            <c:forEach items="${ingredients}" var="ingredient">
                                <li class="bullet list-font">
                                    <fmt:formatNumber value="${ingredient.quantity}" type="number"
                                                      pattern="#.##;-#.##"/>
                                    <c:if test="${ingredient.unit.unitName != 'unit'}">
                                        <spring:message code="${ingredient.unit.unitName}"/>
                                    </c:if>
                                    <c:out value="${ingredient.ingredient.name}" />
                                </li>
                            </c:forEach>
                        </ol>
                    </div>
                    <hr class="divider-child margin"/>
                    <div>
                        <div class="subtitle dark"><spring:message code="recipeDetail.instructions"/></div>
                        <c:set var="instructions" value="${recipe.instructions}"/>
                        <ol class="detail_ol list-font">
                            <c:forEach items="${instructions}" var="instruction">
                                <li class="number">
                                    <c:out value="${instruction}"/>
                                </li>
                            </c:forEach>
                        </ol>
                    </div>
                    <hr class="divider-child margin"/>


                    <%-- seccion comentarios --%>



                    <div class="give-margin">
                        <div class="subtitle dark"><spring:message code="recipeDetail.comments"/> (<c:out
                                value="${recipe.comments.size()}"/>)
                        </div>
                        <c:choose>
                            <c:when test="${recipe.comments.size() > 0}">
                                <ul class="collection">
                                    <c:forEach var="comment" items="${recipe.paginatedComments}">
                                        <li class="collection-item avatar">
                                            <c:choose>
                                                <c:when test="${currentUser.present and currentUser.get().id eq comment.user.id}">
                                                    <c:url value="/profile" var="profile"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:url value="/profile/${comment.user.id}" var="profile"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="${profile}">
                                                <img src="<c:url value="/userImage/${comment.user.id}"/>" alt="" class="circle">
                                            </a>
                                            <div class="comment-header">
                                        <span class="title">
                                            <a href="${profile}">
                                                <span class="comment-user">
                                                    <c:out value="${comment.user.name}" escapeXml="true"/>
                                                </span>
                                            </a>
                                            <spring:message code="recipeDetail.commentBoxTitle"/>
                                        </span>
                                                <span class="comment-date">
                                            <fmt:formatDate value="${comment.createdAt}" pattern="dd-MM-yyyy"/>
                                        </span>
                                            </div>
                                            <p class="comment-text">
                                                <c:out value="${comment.commentContent}" escapeXml="true"/>
                                            </p>

                                            <div class="comments-likes-container">
                                                <div class="display-thumbs-position">
                                                    <form action="<c:url value="likeComment/${comment.commentId}" />"
                                                          method="post" class="no-margin">
                                                        <button class="btn-floating btn-small
                                                             <c:choose>
                                                                <c:when test="${currentUser.present and comment.userLikes(currentUser.get().id)}">
                                                                green
                                                                </c:when>
                                                            <c:otherwise>
                                                                grey
                                                            </c:otherwise>
                                                            </c:choose>"
                                                        ><i class="material-icons text-white">thumb_up</i>
                                                        </button>
                                                        <span><c:out value="${comment.commentLikes}"/> </span>
                                                    </form>
                                                </div>
                                                <div class="display-thumbs-position">
                                                    <form action="<c:url value="dislikeComment/${comment.commentId}" />"
                                                          method="post" class="no-margin">
                                                        <button class="btn-floating btn-small
                                                   <c:choose>
                                                    <c:when test="${currentUser.present and comment.userDislikes(currentUser.get().id)}">
                                                    red
                                                 </c:when>
                                                <c:otherwise>
                                                    grey
                                                </c:otherwise>
                                                </c:choose>">
                                                            <i class="material-icons">thumb_down</i>
                                                        </button>
                                                        <span><c:out value="${comment.commentDislikes}"/> </span>
                                                    </form>
                                                </div>
                                                <c:if test="${currentUser.present and currentUser.get().admin}">
                                                <div id="modal-delete-comment" class="modal">
                                                    <div class="modal-content">
                                                        <h4><spring:message code="recipeDetail.deleteComment"/></h4>
                                                        <p><spring:message code="recipeDetail.deleteCommentAdminText"/></p>

                                                        <c:if test="${param.size() >= 1}">
                                                    <span style="color: red"><spring:message
                                                            code="recipeDetail.deleteModalError"/></span>
                                                        </c:if>

                                                        <div class="row">
                                                            <c:url value="/deleteRecipeComment/${recipe.recipeId}/${comment.commentId}"
                                                                   var="deleteCommentAdmin"/>
                                                            <form:form class="col s12" id="delete-comment-form" method="post"
                                                                       modelAttribute="deleteCommentForm"
                                                                       action="${deleteCommentAdmin}">
                                                                <div class="row">
                                                                    <div class="input-field col s12">
                                                                        <form:textarea id="textarea1z"
                                                                                       class="materialize-textarea"
                                                                                       path="deletionMotive"/>
                                                                        <label for="textarea1"><spring:message
                                                                                code="recipeDetail.deleteModalPlaceholderComment"/></label>
                                                                    </div>
                                                                </div>
                                                            </form:form>
                                                        </div>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <button form="delete-comment-form"
                                                                class="btn red waves-effect waves-light delete-button-modal red"
                                                                type="submit"
                                                                name="action"><spring:message code="recipeDetail.delete"/>
                                                            <i class="material-icons left">delete</i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <a class="modal-trigger delete-position" id="modal-delete-comment-button"
                                                   data-target="modal-delete-comment">
                                                    <i class="material-icons bookmark ">delete</i>
                                                </a>
                                                </c:if>
                                        </li>
                                    </c:forEach>
                                </ul>

                                <c:if test="${recipe.totalComments > recipe.paginatedComments.size()}">
                                    <div class="see-more-container">

                                        <form action="<c:url value="/recipeDetail/${recipe.recipeId}"/>">
                                            <input type="hidden" name="commentsToBring" value="${recipe.paginatedComments.size()+10}"/>
                                            <button type="submit" class="save">
                                        <span class="save-button-text">
                                            <spring:message code="recipeDetail.moreComments"/>
                                        </span>
                                            </button>
                                        </form>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <h3 class="no-comments"><spring:message code="recipeDetail.noComments"/></h3>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/recipeDetail.js"/>"></script>
<script id="scriptLoader"
        data-error="<c:if test="${not empty param.error}">error</c:if>"
        data-commented="${commented}"
        src="<c:url value="/js/recipeDetailModalInitialization.js"/>">
</script>
</body>
</html>