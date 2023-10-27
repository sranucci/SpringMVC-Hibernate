<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Password</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/register.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/forgotPassword.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp"/>
    </div>

    <div class="main-content-container main-register">
        <div class="form-container reset-form-container">
            <div class="resetform-card padding">
                <div class="reset-form display-items">
                    <c:choose>
                        <c:when test="${success}">
                            <div class="form-title">
                                <spring:message code="resetPasswordSuccess.title"/>
                            </div>
                            <div class="margin-top">
                                <spring:message code="resetPasswordSuccess.text1"/>
                            </div>
                            <div class="login-container margin-top">
                                <a href="<c:url value="/" />" class="btn-small register-btn"><spring:message
                                        code="resetPasswordSuccess.discover"/></a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="form-title">
                                <spring:message code="resetPasswordFailure.title"/>
                            </div>
                            <div class="margin-top">
                                <spring:message code="resetPasswordFailure.text1"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
