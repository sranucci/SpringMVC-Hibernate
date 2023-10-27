<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css" />">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css" />">
    <link rel="stylesheet" href="<c:url value="/css/profile.css" />">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body class="font-family">
<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp">
            <jsp:param name="pageTitle" value="Profile"/>
            <jsp:param name="username" value="${user.name}"/>
        </jsp:include>
    </div>
    <div class="main-content-container main-profile">
        <div class="main-content">
            <div class="form-container">
                <c:url var="updateProfile" value="/editProfile"/>
                <form:form modelAttribute="editProfileForm" method="post" action="${updateProfile}"
                           enctype="multipart/form-data">
                    <div class="profile-container">
                        <div class="header">
                            <div class="edit-profile">
                                <i class="material-icons icons create-icon">create</i>
                            </div>
                        </div>
                        <div class="profile-info">
                            <div class="photo-container">
                                <img id="userPhotoPreview"
                                     class="user-photo"
                                     src="<c:url value="/userImage/${user.id}"/>"
                                     alt="profile photo not found">
                                <form:input path="userPhoto" name="userPhoto" type="file" id="userPhoto"
                                            style="display:none;" onchange="previewUserPhoto(this)"
                                            accept="image/jpeg, image/jpg"/>
                                <div class="photo-buttons">
                                    <label for="userPhoto" class="change-btn"
                                           onclick="unsetDeleteFlag()"><spring:message
                                            code="editProfile.changePhoto"/></label>
                                    <form:input type="hidden" path="deleteProfilePhoto" id="deleteProfilePhoto"
                                                value="0"/>
                                    <label for="deleteProfilePhoto" class="delete-btn"
                                           onclick="setDeleteFlag()"><spring:message
                                            code="editProfile.deletePhoto"/></label>
                                </div>
                                <form:errors path="userPhoto" cssStyle="color: red" element="p"/>

                            </div>
                            <div class="info-container">
                                <form:input type="text"
                                            class="profile-value margin-right" id="firstName" name="firstName"
                                            path="firstName"/>
                                <label for="firstName"><spring:message code="editProfile.firstName"/></label>
                                <form:errors path="firstName" cssClass="error" element="p"/>

                            </div>
                            <div class="info-container">
                                <form:input type="text"
                                            class="profile-value margin-right" id="lastName" name="lastName"
                                            path="lastName"/>
                                <label for="lastName"><spring:message code="editProfile.lastName"/>
                                </label>
                                <form:errors path="lastName" cssClass="error" element="p"/>
                            </div>
                        </div>
                        <div class="save-container">
                            <button class="btn-small save-btn" id="save-btn" type="submit"><spring:message code="editProfile.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    function previewUserPhoto(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("userPhotoPreview").setAttribute("src", e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    function setDeleteFlag() {
        document.getElementById("deleteProfilePhoto").value = "1";
        document.getElementById("userPhotoPreview").setAttribute("src", "<c:url value='/images/defaultUser.png'/>");


    }

    function unsetDeleteFlag() {
        document.getElementById("deleteProfilePhoto").value = "0";
    }

</script>