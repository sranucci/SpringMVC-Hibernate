<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Alfa+Slab+One&display=swap" rel="stylesheet">
<div class="side-bar">
    <a class="brand" href="<c:url value="/"/>">
        <div class="brand-name">
            <div class="brand-1 not-selectable">Taste</div>
            <div class="brand-1 not-selectable">Tales</div>
        </div>
    </a>
    <c:if test="${param.pageTitle.equals('AddRecipe')}">
        <c:set value="activate-pressed" var="activateAddRecipe"/>
    </c:if>
    <a class="features-item ${activateAddRecipe}" href="<c:url value="/upload-recipe"/>">
        <i class="material-icons icons">add</i>
        <span class="features-item-text hideable"><spring:message code="navbar.createNew"/></span>
    </a>
    <c:if test="${param.pageTitle.equals('Discover')}">
        <c:set value="activate-pressed" var="activateDiscover"/>
    </c:if>
    <a class="features-item ${activateDiscover}" href=<c:url value="/"/>>
        <i class="material-icons icons">search</i>
        <span class="features-item-text hideable"><spring:message code="navBar.discover"/></span>
    </a>
    <c:choose>
        <c:when test="${not currentUser.present}">
            <c:if test="${param.pageTitle.equals('Login')}">
                <c:set value="activate-pressed" var="activateLogin"/>
            </c:if>
            <a class="features-item ${activateLogin}" href="<c:url value="/login"/>">
                <i class="material-icons icons">login</i>
                <span class="features-item-text hideable"><spring:message code="navbar.login"/></span>
            </a>
        </c:when>
        <c:otherwise>
            <c:if test="${param.pageTitle.equals('Saved')}">
                <c:set value="activate-pressed" var="activateSaved"/>
            </c:if>
            <a class="features-item ${activateSaved}" href="<c:url value="/saved"/>">
                <i class="material-icons icons">bookmark</i>
                <span class="features-item-text hideable"><spring:message code="navbar.saved"/></span>
            </a>
            <c:if test="${param.pageTitle.equals('MyRecipes')}">
                <c:set value="activate-pressed" var="activateMyRecipes"/>
            </c:if>
            <a class="features-item ${activateMyRecipes}" href="<c:url value="/myRecipes"/>">
                <i class="material-icons icons">receipt</i>
                <span class="features-item-text hideable"><spring:message code="navbar.myRecipes"/></span>
            </a>
            <c:if test="${param.pageTitle.equals('Following')}">
                <c:set value="activate-pressed" var="activateFeed"/>
            </c:if>
            <a class="features-item ${activateFeed}" href="<c:url value="/following"/>">
                <i class="material-icons icons">home</i>
                <span class="features-item-text hideable"><spring:message code="navbar.following"/></span>
            </a>
            <c:if test="${param.pageTitle.equals('MyProfile')}">
                <c:set value="activate-pressed" var="activateProfile"/>
            </c:if>
            <a class="features-item ${activateProfile}" href="<c:url value="/profile"/>">
                <i class="material-icons icons">person</i>
                <span class="features-item-text hideable"
                      id="username">
                    ${currentUser.get().name}
<%--                    <span class="name">${currentUser.get().name.substring(0,1).toUpperCase()}${currentUser.get().name.substring(1).toLowerCase()}</span>--%>
                </span>
                <c:if test="${currentUser.get().admin}">
                    <div class="admin">
                        <div><spring:message code="navbar.admin"/></div>
                    </div>
                </c:if>

            </a>
            <div class="logout">
                <a class="features-item" href=<c:url value="/logout"/>>
                    <i class="material-icons icons">logout</i>
                    <span class="features-item-text hideable"><spring:message code="navbar.logout"/></span>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</div>