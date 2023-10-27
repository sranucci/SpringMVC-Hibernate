<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Invalid Token</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/register.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/forgotPassword.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="stylesheet" href="<c:url value="/css/verifyAccountSend.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body>
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp"/>
    </div>

    <div class="main-content-container main-register">
        <div class="form-container reset-form-container">
            <div class="resetform-card">
                <div class="reset-form display-items">
                    <div class="form-title">
                        <spring:message code="invalidVerifyToken.title"/>
                    </div>
                    <div class="margin-top">
                        <spring:message code="invalidVerifyToken.text"/>

                    </div>
                    <div class="margin-top">
                        <spring:message code="invalidVerifyToken.text1"/>
                        <a href="<c:url value="/resendVerification" />" class="link"><spring:message
                                code="invalidVerifyToken.here"/></a>
                    </div>
                    <div class="margin-top">
                        <spring:message code="invalidVerifyToken.text2"/>
                    </div>
                    <div class="login-container">
                        <a href="<c:url value="/login" />" class="login-btn btn-small margin-left"><spring:message
                                code="invalidVerifyToken.login"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
