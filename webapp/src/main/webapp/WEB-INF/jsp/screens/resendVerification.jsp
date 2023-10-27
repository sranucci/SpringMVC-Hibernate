<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resend Verification Token</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/register.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="stylesheet" href="<c:url value="/css/verifyAccountSend.css" />">
    <link rel="stylesheet" href="<c:url value="/css/forgotPassword.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp"/>
    </div>
    <div class="main-content-container main-register">
        <div class="form-container reset-form-container">
            <c:url value="/resendVerification" var="resendVerification"/>
            <form:form modelAttribute="verifyAccountForm" method="post" action="${resendVerification}">
                <div class="resetform-card">
                    <div class="reset-form display-items">
                        <div class="form-title">
                            <spring:message code="resendVerification.title"/>
                        </div>
                        <div class="input-field margin-top">
                            <input name="email" id="email" type="text"/>
                            <label for="email"><spring:message code="resendVerification.email"/>
                            </label>
                            <form:errors path="email" cssClass="error" element="span"/>
                        </div>
                        <div>
                            <c:if test="${error.length() > 0}">
                            <span class="error">
                                <spring:message code="${error}"/>
                            </span>
                            </c:if>
                        </div>
                        <div class="login-container">
                            <button class="btn-small register-btn" id="register-btn" type="submit"><spring:message
                                    code="resendVerification.send"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
