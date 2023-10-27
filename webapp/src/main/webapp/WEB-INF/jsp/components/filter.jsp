<%@ page import="com.google.gson.Gson" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dto" scope="request" type="ar.edu.itba.paw.dtos.ShowRecipesDto"/>
<jsp:useBean id="autocompleteDto" scope="request" type="ar.edu.itba.paw.webapp.dtos.FilterAutocompleteDataDto"/>

<div class="filter">
    <div class="filter-title"><spring:message code="filter.title"/></div>
    <form>
        <div class="difficulty-container margin">
            <label for="difficultySelect" class="filter-subtitle"><spring:message code="filter.difficulty"/>: </label>
            <select id="difficultySelect" name="difficulty">
                <c:forEach var="difficulty" items="${autocompleteDto.difficultyOptions}">
                    <spring:message code="${difficulty.difficultyName}" var="difficultyMessage" />
                    <c:choose>
                        <c:when test="${difficulty.difficultyId.equals(dto.difficulty.getDifficultyId())}">
                            <option value="${difficulty}" selected><c:out
                                    value="${difficultyMessage}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="${difficulty}"><c:out value="${difficultyMessage}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="ingredients-container margin">
            <div class="chips chips-autocomplete"></div>
            <p hidden id="placeholder"><spring:message code="filter.ingredient"/></p>
            <input type="hidden" name="selectedIngredients" id="selectedIngredients"/>
        </div>
        <div class="margin">
            <label class="filter-subtitle"><spring:message code="filter.categories"/>: </label>
            <div class="category-checkboxes margin">
                <c:forEach var="entry" items="${autocompleteDto.categoryMap}">
                    <div class="category-checkbox">
                        <label>
                            <input type="checkbox" name="selectedCategories" id="category-${entry.key}"
                                       value="<c:out value="${entry.key}" escapeXml="true"/>"
                                        <c:if test="${dto.selectedCategories.contains(entry.key.toString())}">
                                            checked
                                        </c:if>
                             />
                            <spring:message code="${entry.value}" var="categoryMassage" />
                            <span class="darkgrey">
                                <c:out value="${categoryMassage}" escapeXml="true"/>
                            </span>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="sort-by-container margin">
            <label for="sortSelect" class="filter-subtitle"><spring:message code="filter.sortBy"/>:</label>
            <select class="form-control" id="sortSelect" name="sort">
                <c:forEach var="sort" items="${autocompleteDto.sortOptions}">
                    <c:set var="sortValue" value="${fn:replace(fn:trim(sort.sortOptionFrontEnd), ' ', '')}" />
                    <spring:message code="${sortValue}" var="sortMessage"/>
                    <c:choose>
                        <c:when test="${dto.sort.equals(sort)}">
                            <option value="${sort}" selected><c:out value="${sortMessage}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="${sort}"><c:out value="${sortMessage}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="sort-by-container margin">
            <label for="languageSelect" class="filter-subtitle"><spring:message code="filter.language"/>: </label>
            <select id="languageSelect" name="language">
                <c:forEach var="language" items="${autocompleteDto.languages}">
                    <spring:message code="${language.languageName}" var="languageMessage" />
                    <c:choose>
                        <c:when test="${language.equals(dto.language)}">
                            <option value="${language}" selected><c:out
                                    value="${languageMessage}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="${language}"><c:out value="${languageMessage}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <div class="apply-filter">
<%--            <div class="card category-card apply-filter">--%>
<%--                <div class="card-content content">--%>
            <button type="submit" id="submit" class="apply-filter-card btn"><spring:message code="filter.applyFilter"/></button>
<%--                </div>--%>
<%--            </div>--%>
        </div>
        <a href="<c:url value="/"/>" class="clear-filter"><spring:message code="ClearFilter"/></a>
        <input type="hidden" id="searchBarQuery" name="searchBarQuery" value="${dto.searchBarQuery}"/>
    </form>
</div>

<link rel="stylesheet" href="<c:url value="/css/filter.css"/>">
<link rel="stylesheet" href="<c:url value="/css/categorycard.css"/>">


<script id="scriptId" data-autocomplete="<c:out value="${autocompleteDto.autocompleteJsonMap}" escapeXml="true"/>"
        dto-selected-ingredients="<c:out value="${dto.selectedIngredients}" escapeXml="true"/>"
        type="text/javascript"
        src="<c:url value="/js/filter.js"/>">
</script>