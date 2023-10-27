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
            <c:url value="/resetPassword" var="resetPassword"/>
            <form:form modelAttribute="resetPasswordForm" method="post" action="${resetPassword}">
                <input type="hidden" name="token" value="${token}"/>
                <div class="resetform-card">
                    <div class="reset-form">
                        <div class="form-title margin-bottom">
                            <spring:message code="resetPasswordForm.title"/>
                        </div>
                        <div class="input-field margin-top">
                            <input type="password"
                                   class="profile-value margin-right" id="password" name="password">
                                <%--                                <form:input path="firstName" id="first_name" type="text" />--%>
                            <label for="password"><spring:message code="resetPasswordForm.password"/>
                            </label>
                            <form:errors path="password" cssClass="error" element="span"/>
                        </div>
                        <div class="input-field margin-top">
                            <input type="password"
                                   class="profile-value margin-right" id="repeatPassword" name="repeatPassword">
                                <%--                                <form:input path="firstName" id="first_name" type="text" />--%>
                            <label for="repeatPassword"><spring:message code="resetPasswordForm.repeatPassword"/>
                            </label>
                            <form:errors path="repeatPassword" cssClass="error" element="span"/>
                            <form:errors cssClass="error" element="span"/>
                        </div>
                        <c:if test="${error}">
                            <span class="error">
                                <spring:message code="resetPasswordForm.error"/>
                            </span>
                        </c:if>
                        <div class="login-container">
                            <button class="btn-small register-btn" id="register-btn" type="submit"><spring:message
                                    code="resetPasswordForm.title"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
