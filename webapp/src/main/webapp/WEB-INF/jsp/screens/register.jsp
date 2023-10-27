<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
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
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp"/>
    </div>
    <div class="main-content-container main-register">
        <div class="info-container">
            <h1 class="sign-up-title">
                <spring:message code="registerForm.title1"/>
                <span class="sign-up-title-color"> <spring:message code="registerForm.title2"/></span>
                <spring:message code="registerForm.title3"/>
            </h1>
            <ul class="sign-up-list">
                <li class="list-item">
                    <i class="material-icons list-icons">share</i>
                    <spring:message code="registerForm.listItem1"/>
                </li>
                <li class="list-item">
                    <i class="material-icons list-icons">mode_comment</i>
                    <spring:message code="registerForm.listItem2"/>
                </li>
                <li class="list-item">
                    <i class="material-icons list-icons">thumb_up</i>
                    <spring:message code="registerForm.listItem3"/>
                </li>
            </ul>
        </div>

        <div class="form-container">
            <c:url var="registerUrl" value="/register"/>
            <form:form modelAttribute="registerForm" method="post" action="${registerUrl}">
                <div class="form-card">
                    <div class="form">
                        <div class="form-title">
                            <spring:message code="registerForm.register"/>
                        </div>
                        <div class="user-name">
                            <div class="input-field name-input">
                                <form:input path="firstName" id="first_name" type="text"/>
                                <label for="first_name"><spring:message code="registerForm.firstName"/>
                                </label>
                                <form:errors path="firstName" cssClass="error" element="span"/>
                            </div>
                            <div class="input-field ">
                                <form:input path="lastName" id="last_name" type="text"/>
                                <label for="last_name"><spring:message code="registerForm.lastName"/></label>
                                <form:errors path="lastName" cssClass="error" element="span"/>
                            </div>
                        </div>
                        <div class="input-field">
                            <form:input path="email" id="email" type="text"/>
                            <label for="email"><spring:message code="registerForm.email"/></label>
                            <form:errors path="email" cssClass="error" element="span"/>
                        </div>
                        <div class="input-field">
                            <form:input path="password" id="password" type="password"/>
                            <label for="password"><spring:message code="registerForm.password"/></label>
                            <form:errors path="password" cssClass="error" element="span"/>
                        </div>
                        <div class="input-field">
                            <form:input path="repeatPassword" id="repeat_password" type="password"/>
                            <label for="repeat_password"><spring:message code="registerForm.repeatPassword"/></label>
                            <form:errors path="repeatPassword" cssClass="error" element="span"/>
                            <form:errors cssClass="error" element="span"/>
                        </div>
                        <c:if test="${error}">
                            <span class="error">
                                <spring:message code="registerForm.error"/>
                            </span>
                        </c:if>
                        <div class="register-container">
                            <button class="btn-small register-btn" id="register-btn" type="submit"><spring:message
                                    code="registerForm.register"/></button>
                        </div>
                        <div>
                            <span> <spring:message code="registerForm.logInQuestion"/> </span>
                            <a href="<c:url value="/login"/>"> <spring:message code="registerForm.logIn"/></a>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
