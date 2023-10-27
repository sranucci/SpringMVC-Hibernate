<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
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
            <c:url value="/forgotPassword" var="forgotPassword"/>
            <form:form modelAttribute="forgotPasswordForm" method="post" action="${forgotPassword}">
                <div class="resetform-card">
                    <div class="reset-form">
                        <div class="form-title">
                            <spring:message code="forgotPasswordForm.title"/>
                        </div>
                        <div class="info"><spring:message code="forgotPasswordForm.text"/></div>
                        <div class="input-field margin-top">
                            <input name="email" id="email" type="text"/>
                            <label for="email"><spring:message code="forgotPasswordForm.email"/>
                            </label>
                            <form:errors path="email" cssClass="error" element="span"/>
                        </div>
                        <div class="login-container">
                            <button class="btn-small register-btn" id="register-btn" type="submit"><spring:message
                                    code="forgotPasswordForm.reset"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
