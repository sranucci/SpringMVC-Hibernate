<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recipe Success</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/success.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body>
<div class="container">
    <div class="card-panel">
        <!-- Use the card-panel class to create a card, and add green lighten-3 class for the success color -->
        <span class="white-text"> <!-- Use the white-text class to set the text color to white -->
            <i class="material-icons icons">check</i> <!-- Use the material-icons class to display an icon -->
            <spring:message code="successScreen.text"/>
        </span>
    </div>
    <a href="<c:url value="/" />" class="link"><spring:message code="successScreen.return"/></a>
</div>
</body>
</html>
