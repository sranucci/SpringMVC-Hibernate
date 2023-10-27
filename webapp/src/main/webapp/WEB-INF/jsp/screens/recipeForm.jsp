<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Recipe Form</title>
    <jsp:include page="/resources/externalResources.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/navbar.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/recipeForm.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/mainPagesStructure.css"/>">
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/favicon.ico" />">
</head>
<body>

<div class="main-structure">
    <div class="nav-bar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar/navbar.jsp">
            <jsp:param name="pageTitle" value="AddRecipe"/>
        </jsp:include>
    </div>
    <div class="main-content-container">

        <c:choose>
            <c:when test="${recipeEdit}">
                <c:url var="uploadDir" value="/editRecipe/${recipeId}"/>
            </c:when>
            <c:otherwise>
                <c:url var="uploadDir" value="/upload-recipe"/>
            </c:otherwise>
        </c:choose>

        <form:form modelAttribute="recipeForm" method="post" action="${uploadDir}" id="recipeFormData"
                   enctype="multipart/form-data" onsubmit="submitForm()">
            <div class="form-header">
                <a class="waves-effect btn-flat modal-trigger" href="#confirmation-modal">
                    <i class="material-icons">arrow_back</i>
                </a>
                <div class="modal-container">
                    <div id="confirmation-modal" class="modal">
                        <div class="modal-content">
                            <p class="modal-header"><spring:message code="recipeForm.leave?"/></p>
                            <p><spring:message code="recipeForm.recipeNotSaved"/></p>
                        </div>
                        <div class="modal-footer">
                            <a class="modal-close waves-effect btn save-btn "><spring:message code="recipeForm.stay"/></a>
                            <a href="<c:url value="/"/>" class="modal-close waves-effect btn red leave-btn"><spring:message code="recipeForm.leave"/></a>
                        </div>
                    </div>
                </div>
                <div class="header-title"><spring:message code="recipeForm.addRecipe"/></div>
            </div>
            <div class="form-content">


                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.languageTitle"/></div>
                    <form:select path="language">
                        <form:option value="en"><spring:message code="recipeForm.languageEnglish"/></form:option>
                        <form:option value="es"><spring:message code="recipeForm.languageSpanish"/></form:option>
                    </form:select>
                    <form:errors path="language" cssClass="error-text-styling" cssStyle="color: #ff0000;" element="p" />
                </div>





                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.title"/></div>
                    <label>
                        <spring:message code="recipeForm.titlePlaceHolder" var="titlePlaceHolder" />
                        <form:input path="title" placeholder="${titlePlaceHolder}" type="text" id="recipe-title"/>

                        <span class="helper-text" data-error="Mandatory field" data-success=""></span>
                    </label>
                    <form:errors path="title" cssClass="error-text-styling" cssStyle="color: #ff0000;" element="p"/>
                </div>





                <c:if test="${recipeEdit}">
                    <div class="section-container">
                        <div class="section-photo-title"><spring:message code="recipeForm.previousPhoto"/></div>
                    </div>
                </c:if>


                <div class="preview-container">
                    <c:if test="${recipeEdit}">
                        <c:set value="${recipeForm.mainImageId}" var="imageId"/>
                            <img src="<c:url value='/recipeImage/${imageId}'/>" alt=""
                                 class="preview-image">
                        <br>
                    </c:if>
                </div>

                <c:if test="${recipeEdit}">
                    <div class="section-container">
                        <div class="section-photo-title"><spring:message code="recipeForm.newPhoto"/></div>
                    </div>
                </c:if>




                <div class="section-container preview-container" id="main-image-preview">
                </div>

                <div class="section-container">
                    <div class="photo-text"><spring:message code="recipeForm.mainPhotoText"/> </div>
                </div>

                <div id="mainImageInp" class="file-field add-photo-btn">
                    <i class="material-icons" id="main-image-icon" >add_a_photo</i>
                    <span id="main-message-success-image"><spring:message code="recipeForm.addPhoto"/></span>
                    <form:input path="newMainImage" id="mainImageInpTag" type="file" accept="image/jpeg, image/jpg"/>
                    <form:errors path="newMainImage" cssStyle="color: red" element="p"/>
                </div>

                <button class="btn-flat" id="clearMainImageButton" disabled><i class="material-icons left">delete</i>
                    <spring:message code="recipeForm.clearImages"/>
                </button>

                <c:if test="${recipeEdit}">
                    <div class="section-container">
                        <div class="section-photo-title"><spring:message code="recipeForm.previousPhoto"/></div>
                    </div>
                </c:if>


                <div class="preview-container">
                    <c:if test="${recipeEdit}">
                        <c:forEach items="${recipeForm.imageIdLists}" var="imageId" varStatus="status">
                            <img src="<c:url value='/recipeImage/${imageId}'/>" alt=""
                                 class="preview-image">
                        </c:forEach>
                        <br>
                    </c:if>
                </div>

                <c:if test="${recipeEdit}">
                    <div class="section-container">
                        <div class="section-photo-title"><spring:message code="recipeForm.newPhoto"/></div>
                    </div>
                </c:if>

                <div class="section-container preview-container" id="image-preview">
                </div>

                <div class="section-container">
                    <div class="photo-text"><spring:message code="recipeForm.secondaryPhotoText"/> </div>
                    <div class="photo-text"><spring:message code="recipeForm.photoText"/> </div>
                </div>
                <div id="imageInp" class="file-field add-photo-btn">
                    <i class="material-icons" id="image-icon">add_a_photo</i>
                    <span id="message-success-image"> <spring:message code="recipeForm.addPhoto"/></span>
                    <!-- COMENTARIO: multiple solo acepta que cargues todas las fotos en bundle -->
                    <form:input path="recipeImages" id="imageInpTag" type="file" accept="image/jpeg, image/jpg"
                                multiple="multiple"/>

                    <!--using form:input doesnt seem to work-->
                    <form:errors path="recipeImages" cssStyle="color: red" element="p"/>
                </div>
                <button class="btn-flat" id="clearImageButton" disabled><i class="material-icons left">delete</i>
                    <spring:message code="recipeForm.clearImages"/>
                </button>
                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.description"/></div>
                    <label>
                        <spring:message code="recipeForm.descriptionPlaceholder" var="descriptionPlaceholder" />
                        <form:textarea path="description"
                                       placeholder="${descriptionPlaceholder}"
                                       type="text"
                                       data-length="512" id="description" class="materialize-textarea"/>
                        <span class="helper-text" data-error="Mandatory field" data-success=""></span>
                    </label>
                    <form:errors path="description" cssStyle="color: red;" cssClass="error-text-styling" element="p"/>
                </div>

                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.ingredients"/></div>
                    <span> <spring:message code="recipeForm.addIngredients"/> </span>

                    <c:set var="ingredientsIterable" value="${recipeForm.recipeIngredients}"/>
                    <div id="ingredients-container">
                        <c:forEach var="ingredient" varStatus="loopStatus" items="${ingredientsIterable}">
                            <div id="ingredient=${loopStatus.index}">
                                <div class="input-container">
                                    <div class="ingredient-input">
                                        <div class="input-field">
                                            <input name="ingredients[${loopStatus.index}]"
                                                   id="ingredient${loopStatus.index}" class="ing autocomplete"
                                                   type="text"
                                                   data-autocomplete="ingredient-autocomplete"
                                                   value="<c:out value="${ingredient.name}" escapeXml="true" />"/>
                                            <label for="ingredient${loopStatus.index}"><spring:message code="recipeForm.ingredient"/></label>
                                        </div>
                                    </div>
                                    <div id="ingredient-autocomplete" class="autocomplete-content"></div>
                                    <div class="quantity-container">
                                        <div class="input-field ingredient-input">
                                            <input name="quantitys[${loopStatus.index}]"
                                                   id="quantity${loopStatus.index}"
                                                   type="number" min="0" step="any"
                                                   value="<c:out value="${ingredient.quantity}" escapeXml="true" />">
                                            <label for="quantity${loopStatus.index}"><spring:message code="recipeForm.quantity"/></label>
                                        </div>
                                        <div class="input-field measure-select">
                                            <select name="measureIds[${loopStatus.index}]"
                                                    id="measure${loopStatus.index}" class="text-indent-for-recipe">
                                                <option value="1" <c:if test="${ingredient.units eq 1}">selected</c:if>><spring:message code="g"/></option>
                                                <option value="2" <c:if test="${ingredient.units eq 2}">selected</c:if>><spring:message code="ml"/></option>
                                                <option value="3" <c:if test="${ingredient.units eq 3}">selected</c:if>><spring:message code="cup"/></option>
                                                <option value="4" <c:if test="${ingredient.units eq 4}">selected</c:if>><spring:message code="tblSpoon"/></option>
                                                <option value="5" <c:if test="${ingredient.units eq 5}">selected</c:if>><spring:message code="teaSpoon"/></option>
                                                <option value="6" <c:if test="${ingredient.units eq 6}">selected</c:if>><spring:message code="unit"/></option>
                                            </select>
                                            <label for="measure${loopStatus.index}"><spring:message code="recipeForm.measure"/></label>
                                            <span class="helper-text" data-error="Mandatory field"></span>
                                        </div>
                                    </div>
                                    <button class="btn-clear btn-flat">
                                        <i class="material-icons">
                                        <spring:message code="recipeForm.clear"/></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- javascript injects code here
                         -->
                    </div>
                    <form:errors path="ingredients" cssStyle="color: red" element="p"/>
                    <form:errors path="measureIds" cssStyle="color: red" element="p"/>
                    <form:errors path="quantitys" cssStyle="color: red" element="p"/>
                    <button class="btn-flat add-btn" id="add-ingredient">
                        <i class="material-icons left">add</i>
                        <spring:message code="recipeForm.add"/>
                    </button>
                </div>

                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.instructions"/></div>
                    <span> <spring:message code="recipeForm.addInstructions"/></span>
                    <div id="instructions-container">

                        <c:set var="instructionIterable" value="${recipeForm.cleanInstructions}"/>
                        <c:forEach var="instruction" varStatus="loopStatus" items="${instructionIterable}">
                            <div id="target-instruction=${loopStatus.index}" class="instruction-with-clear-btn">
                                <div class="input-field input-step">
                                    <spring:message code="recipeForm.addStep" var="addStep" />
                                    <input name="instructions[${loopStatus.index}]"
                                           class="materialize-textarea textarea"
                                           placeholder="${addStep}"
                                           type="text" value="<c:out value="${instruction}" escapeXml="true"/>"/>
                                    <span class="helper-text" data-error="Mandatory field"></span>
                                </div>
                                <button class="btn-clear btn-flat"><i class="material-icons"><spring:message code="recipeForm.clear"/></i></button>
                            </div>
                        </c:forEach>
                        <!-- javascript will inject code here-->
                    </div>
                    <form:errors path="instructions" cssStyle="color: red;" element="p"/>
                    <button class="btn-flat add-btn" id="add-instruction"><i class="material-icons left">add</i><spring:message code="recipeForm.add"/>
                    </button>
                </div>
                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.servings"/></div>
                    <span><spring:message code="recipeForm.addServings"/></span>
                    <label for="portions"></label>
                    <form:input path="servings" placeholder="#" type="number" id="portions" max="10000000"/>
                    <span class="helper-text" data-error="Mandatory field"></span>
                    <form:errors path="servings" cssClass="error-text-styling" cssStyle="color: red;" element="p"/>
                </div>
                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.totalTime"/></div>
                    <span><spring:message code="recipeForm.timeText"/></span>
                    <div class="input-container">
                        <div class="input-field ingredient-input">
                            <form:input path="totalHours" id="hours" type="number" max="100000000"/>
                            <label for="hours"><spring:message code="recipeForm.hours"/></label>
                        </div>
                        <div class="input-field">
                            <form:input path="totalMinutes" id="minutes" type="number"
                                        class="minutes" max="59"/>
                            <label for="minutes"><spring:message code="recipeForm.minutes"/></label>
                        </div>
                    </div>
                    <form:errors path="totalHours" cssClass="error-text-styling" cssStyle="color: red;" element="p"/>
                    <form:errors path="totalMinutes" cssClass="error-text-styling" cssStyle="color: red;" element="p"/>
                    <form:errors cssStyle="color: red"/>
                </div>
                <div class="section-container">
                    <div class="visibility-container">
                        <div class="input-field">
                            <div class="section-title"> <spring:message code="recipeForm.visibility"/> </div>
                            <form:select path="visibility">
                                <form:option value="Public"><spring:message code="recipeForm.public"/></form:option>
                                <form:option value="Private"><spring:message code="recipeForm.private"/></form:option>
                            </form:select>
                        </div>
                    </div>
                    <form:errors path="visibility" cssStyle="color: red;" element="p"/>
                </div>
                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.difficulty"/></div>
                    <div class="difficulty-btns">
                        <form:select path="difficulty">
                            <c:forEach var="difficultyEntry" items="${recipeFormDto.difficulties}">
                                <spring:message code="${difficultyEntry.difficultyName}" var="difficultyMessage" />
                                <form:option name="difficulty" type="radio" value="${difficultyEntry.difficultyId}"
                                             label="${difficultyMessage}"/>
                            </c:forEach>
                        </form:select>
                    </div>
                    <form:errors path="difficulty" cssClass="error-text-styling" cssStyle="color: red;" element="p"/>
                </div>
                <div class="section-container">
                    <div class="section-title"><spring:message code="recipeForm.categories"/></div>
                    <div class="checkbox-display">

                        <c:forEach var="entry" items="${recipeFormDto.categories}">
                            <label>
                                <input type="checkbox" class="position-checkbox" name="categories"
                                       value="<c:out value="${entry.categoryId}" escapeXml="true" />"

                                <c:forEach items="${recipeForm.categories}" var="cat">
                                <c:if test="${cat == entry.categoryId}">
                                       checked
                                </c:if>
                                </c:forEach>
                                >
                                <spring:message code="${entry.name}" var="categoryMassage" />
                                <span><c:out value="${categoryMassage}" escapeXml="true"/></span>
                            </label>
                        </c:forEach>
                        <form:errors path="categories" cssStyle="color: red" element="p"/>
                        <div class="save-container">

                            <button class="btn-small save-btn" id="save-btn" type="submit"><spring:message code="recipeForm.save"/></button>

                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('select');
        elems.forEach( element => M.FormSelect.init(element));
    });
</script>

<script id="scriptId" data-autocomplete="<c:out value="${recipeFormDto.autocompleteJsonMap}" escapeXml="true"/>"
        data-instruction-length="${fn:length(instructionIterable)}"
        data-ingredients-length="${fn:length(ingredientsIterable)}"
        data-units="<c:out value="${recipeFormDto.unitsJsonMap}" escapeXml="true" />"
        data-ingredient-label="<spring:message code="recipeForm.ingredient"/>"
        data-measure-label="<spring:message code="recipeForm.measure"/>"
        data-quantity-label="<spring:message code="recipeForm.quantity"/>"
        data-step-label="<spring:message code="recipeForm.addStep"/>"
        data-image-label="<spring:message code="recipeForm.uploadedImages"/>"
        data-add-photo-label="<spring:message code="recipeForm.addPhoto"/>"
        type="text/javascript"
        src="<c:url value="/js/recipeForm.js"/>"></script>
</body>

</html>