<!--recibe title y description -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="card not-selectable recipe-card ">
    <link rel="stylesheet" href="<c:url value="/css/recipecard.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/recipeDetail.css"/>">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <c:url value="/profile/${param.user_id}" var="profile"/>
    <a href="${profile}" class="user-info user-link">
        <img src="<c:url value="/userImage/${param.user_id}"/>"
             alt="User Photo" class="user-image margin-right">
        <span class="user-name margin-right"><c:out value="${param.user_name}"/> </span>
        <span class="user-name"> <c:out value="${param.user_lastname}"/></span>
    </a>
    <div class="feed-card-container">
        <div class="card-image pointer-style fixed-image-width">
            <a href=<c:url value="/recipeDetail/${param.recipe_id}"/>><img
                    src="<c:url value="/recipeImage/${param.image_id}"/>"
                    alt="Picture" class="responsive-img"></a>

        </div>
        <div class="card-content card-info max-width">
            <div class="card-content-header">
                <div class="title-description">
                    <a class="max-width" href=<c:url value="/recipeDetail/${param.recipe_id}"/>>
                        <span class="card-title text-description-color"><c:out value="${param.title}"/></span>
                    </a>
                    <div class="save-and-share margin-bottom">
                        <c:choose>
                            <c:when test="${param.isSaved}">
                                <c:url var="saveUrl" value="feed/unsave/${param.recipe_id}"/>
                                <spring:message code="recipeDetail.saved" var="Saved"/>
                                <c:set var="text" value="${Saved}"/>
                                <c:set var="saveIcon" value="bookmark"/>
                            </c:when>
                            <c:otherwise>
                                <c:url var="saveUrl" value="feed/save/${param.recipe_id}"/>
                                <spring:message code="recipeDetail.save" var="Save"/>
                                <c:set var="text" value="${Save}"/>
                                <c:set var="saveIcon" value="bookmark_border"/>
                            </c:otherwise>
                        </c:choose>
                        <form action="${saveUrl}" method="POST" class="no-margin">
                            <button type="submit" class="btn save">
                                <i class="material-icons bookmark"><c:out value="${saveIcon}"/></i>
                                <span class="save-button-text"><c:out value="${text}"/></span>
                            </button>
                            <input type="hidden" value="${param.pageNumber}" name="pageNumber">
                        </form>
                    </div>
                </div>
                <div class="text-description">
                    <a href=<c:url value="/recipeDetail/${param.recipe_id}"/>>
                        <span class="card-description text-description-color"><c:out value="${param.description}"/></span>
                    </a>
                </div>
            </div>
            <div class="card-content-subtitles">
                <div class="feed-card-info icon-font">
                    <div class="likes-container">
                        <div class="likes">
                            <c:choose>
                                <c:when test="${param.isliked}">
                                    <c:url var="likeUrl" value="feed/rate/remove/${param.recipe_id}"/>
                                    <c:set var="cssClass" value="thumb-up-activated"/>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="likeUrl" value="feed/rate/like/${param.recipe_id}"/>
                                    <c:set var="cssClass" value="thumb-deactivated"/>
                                </c:otherwise>
                            </c:choose>
                            <form action="${likeUrl}" method="POST" class="no-margin">
                                <button type="submit" class="btn like-btn ${cssClass}">
                                    <i class="material-icons icons thumb">thumb_up</i>
                                </button>
                                <input type="hidden" value="${param.pageNumber}" name="pageNumber">
                            </form>

                            <span class="features-item-text rating-text">
                            <c:out value="${param.likes}"/>
                        </span>
                        </div>
                        <div class="dislikes">
                            <c:choose>
                                <c:when test="${param.isDisliked}">
                                    <c:url var="dislikeUrl" value="/feed/rate/remove/${param.recipe_id}"/>
                                    <c:set var="cssClass" value="thumb-down-activated"/>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="dislikeUrl" value="/feed/rate/dislike/${param.recipe_id}"/>
                                    <c:set var="cssClass" value="thumb-deactivated"/>
                                </c:otherwise>
                            </c:choose>
                            <form action="${dislikeUrl}" method="POST" id="likeForm" class="no-margin">
                                <button type="submit" class="btn like-btn ${cssClass}">
                                    <i class="material-icons icons thumb ">thumb_down</i>
                                </button>
                                <input type="hidden" value="${param.pageNumber}" name="pageNumber">
                            </form>

                            <span class="features-item-text rating-text">
                            <c:out value="${param.dislikes}"/>
                        </span>
                        </div>
                    </div>
                </div>
                <div class="bottom">
                    <div id="time_${param.recipe_id}"></div>
                    <div class="comments-feed">
                        <a href=<c:url value="/recipeDetail/${param.recipe_id}"/>>
                            <div class="comments-feed text-description-color">
                                <div>
                                    <spring:message code="feed.see"/>
                                </div>
                                <span class="features-item-text rating-text comment">
                                    <c:out value="${param.commentsCount}"/>
                                </span>
                                <div>
                                    <spring:message code="feed.comments"/>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card-reveal">
    </div>
</div>
<%--<script id="scriptId" src="<c:url value="/js/feed.js"/>",--%>
<%--        time="<c:out value="${param.time}" />",--%>
<%--        id="<c:out value="${param.recipe_id}" />"--%>
<%--        type="text/javascript" />--%>
<script>
    var time = document.getElementById("time_${param.recipe_id}");
    var currentDate = new Date();
    var recipeDate = new Date("${param.time}")
    var time_string = ""

    // Calculate the difference in milliseconds
    var difference = currentDate - recipeDate;

    // Convert the difference to minutes
    var minutesDifference = Math.floor(difference / (1000 * 60));

    <%--if(minutesDifference <= 60){--%>
    <%--    time_string = "<spring:message code='feed.minute'><spring:argument value="${minutesDifference}"/></spring:message>";--%>
    <%--} else {--%>
    <%--    var hoursDifference = Math.floor(difference / (1000 * 60 * 60));--%>
    <%--    if(hoursDifference <= 24){--%>
    <%--        time_string = "<spring:message code='feed.hour' arguments="${hoursDifference}"/>";--%>
    <%--    }else{--%>
    <%--        var daysDifference = Math.floor(difference / (1000 * 60 * 60 * 24))--%>
    <%--        console.log(daysDifference)--%>
    <%--        time_string = `<spring:message code='feed.day'><spring:argument value='${daysDifference}'/></spring:message>`;--%>
    <%--    }--%>
    <%--}--%>
    if(minutesDifference <= 60){
        time_string = minutesDifference + (minutesDifference == 1 ? " <spring:message code='feed.minute' />" : " <spring:message code='feed.minutes' />");
    } else {
        var hoursDifference = Math.floor(difference / (1000 * 60 * 60));
        if(hoursDifference <= 24){
            time_string = hoursDifference + (hoursDifference == 1 ? " <spring:message code='feed.hour' />" : " <spring:message code='feed.hours' />");
        }else{
            var daysDifference = Math.floor(difference / (1000 * 60 * 60 * 24));
            if(daysDifference < 3){
                time_string = daysDifference + (daysDifference == 1 ? " <spring:message code='feed.day' />" : " <spring:message code='feed.days' />");
            } else {
                time_string = ("0" + recipeDate.getDate()).slice(-2) + "/" + ("0" + (recipeDate.getMonth() + 1)).slice(-2) + "/" + recipeDate.getFullYear();
            }
        }
    }
    time.innerHTML=time_string;
</script>