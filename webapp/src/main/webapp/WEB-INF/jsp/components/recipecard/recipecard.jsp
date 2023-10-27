<!--recibe title y description -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="card not-selectable recipe-card ">
    <link rel="stylesheet" href="<c:url value="/css/recipecard.css"/>">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <div class="card-image pointer-style">
        <a href=<c:url value="/recipeDetail/${param.recipe_id}"/>><img
                src="<c:url value="/recipeImage/${param.image_id}"/>"
                alt="Picture" class="responsive-img"></a>

    </div>
    <div class="card-content">
        <span class="card-title activator"><c:out value="${param.title}"/></span>
        <div class="card-content-subtitles">
            <div class="card-info">
                <div class="time">
                    <i class="material-icons icon gray">access_time</i>
                    <div>
                        <c:choose>
                            <c:when test="${param.hours >= 1}"><c:out value="${param.hours}"/>h
                                <c:if test="${param.minutes > 0}"> <c:out value="${param.minutes}"/>min</c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${param.minutes >= 1}"><c:out value="${param.minutes}"/> min</c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="difficulty">
                    <i class="material-icons rotate-icon icon gray">sort</i>
                    <spring:message code="${param.difficulty}" var="difficultyMessage" />
                    <div><spring:message code="recipeCard.difficulty"/>: <c:out value="${difficultyMessage}"/></div>
                </div>
                <div class="likes-container">
                    <div class="likes">
                        <i class="material-icons thumb thumb-up">thumb_up</i>
                        <span class="features-item-text rating-text">
                            <c:out value="${param.likes}"/>
                        </span>
                    </div>
                    <div class="dislikes">
                        <i class="material-icons thumb thumb-down">thumb_down</i>
                        <span class="features-item-text rating-text">
                            <c:out value="${param.dislikes}"/>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card-reveal">
        <span class="card-title"><c:out value="${param.title}"/><i class="material-icons right">close</i></span>
        <p class="description"><c:out value="${param.description}"/></p>
    </div>
    <div class="card-action center-align">
        <p class="seeRecipe activator"><spring:message code="recipeCard.seeDescription"/></p>
    </div>
</div>