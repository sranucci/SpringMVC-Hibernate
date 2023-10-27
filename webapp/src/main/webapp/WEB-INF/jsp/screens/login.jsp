<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/register.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp">
            <jsp:param name="pageTitle" value="${Login}"/>
        </jsp:include>
    </div>
    <div class="main-content-container main-register">
        <div class="info-container">
            <h1 class="sign-up-title">
                <span><spring:message code="loginForm.title"/></span>
            </h1>
            <span class="sign-up-list list-item">
                <spring:message code="loginForm.subtitle"/>
            </span>
        </div>

        <div class="form-container">
            <c:url value="/login" var="loginEndpoint"/>
            <form method="post" action="${loginEndpoint}">
                <div class="form-card reset-padding">
                    <div class="form">
                        <div class="form-title">
                            <spring:message code="loginForm.logIn"/>
                        </div>
                        <div class="input-field">
                            <input name="email" id="email" type="text"/>
                            <label for="email"><spring:message code="loginForm.email"/>
                            </label>
                        </div>
                        <div class="input-field">
                            <input name="password" id="password" type="password"/>
                            <label for="password"><spring:message code="loginForm.password"/></label>
                            <form:errors path="password" cssClass="error" element="span"/>
                        </div>
                        <c:if test="${param.error != null}">
                            <span class="error"><spring:message code="loginForm.error"/></span>
                        </c:if>
                        <div>
                            <label class="remember-container">
                                <span><spring:message code="loginForm.rememberMe"/></span>
                                <input type="checkbox" class="filled-in remember-box" checked="checked"
                                       name="rememberme"/>
                            </label>
                        </div>

                        <div class="login-container">
                            <button class="btn-small login-btn" id="login-btn" type="submit"><spring:message
                                    code="loginForm.logIn"/></button>
                        </div>
                        <div class="forgotPassword-container">
                            <a href="<c:url value="/forgotPassword"/>" class="forgotPassword decorate-text">
                                <spring:message
                                        code="loginForm.forgotPassword"/> </a>
                        </div>
                        <div class="register-container">
                            <span><spring:message code="loginForm.registerQuestion"/></span>
                            <a href="<c:url value="/register"/>" class="decorate-text margin-left"> <spring:message
                                    code="loginForm.register"/> </a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
