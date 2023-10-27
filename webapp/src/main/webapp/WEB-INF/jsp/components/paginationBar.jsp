<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dto" scope="request" type="ar.edu.itba.paw.dtos.ShowRecipesDto"/>

<form method="get" id="paginationForm" class="pagination-bar">
    <c:if test="${dto.moreThanOnePage()}">
        <ul class="pagination">
            <c:if test="${dto.prevPage.present}">
                <li class="waves-effect page-button"><a onclick="submitPage(${dto.prevPage.get()})"><spring:message code="paginationBar.previous"/></a>
                </li>
            </c:if>

            <c:choose>
                <c:when test="${dto.pageNumber > dto.totalPages}">
                    <c:set value="${dto.totalPages}" var="actualPage"/>
                </c:when>
                <c:otherwise>
                    <c:set value="${dto.pageNumber}" var="actualPage"/>
                </c:otherwise>
            </c:choose>
            
            <li class="disabled"><span class="total-pages"><spring:message code="pagination.page" arguments="${actualPage}, ${dto.totalPages}" /></span></li>

            <c:if test="${dto.nextPage.present}">
                <li class="waves-effect page-button"><a onclick="submitPage(${dto.nextPage.get()})"><spring:message code="paginationBar.next"/></a></li>
            </c:if>

        </ul>
    </c:if>


    <!-- esto de aca es necesario para que se mantengan las opciones de search y filter al cambiar de pagina -->
    <c:forEach var="category" items="${dto.selectedCategories}">
        <input type="hidden" name="selectedCategories" value="${category}"/>
    </c:forEach>
    <input type="hidden" name="difficulty" value="${dto.difficulty}"/>
    <input type="hidden" name="sort" value="${dto.sort}"/>
    <input type="hidden" name="language" value="${dto.language}"/>
    <input type="hidden" id="selectedIngredients2" name="selectedIngredients" value="${dto.selectedIngredients}"/>
    <input type="hidden" id="searchBarQuery2" name="searchBarQuery" value="${dto.searchBarQuery}"/>
    <input type="hidden" id="pageInput" name="page">


</form>

<link rel="stylesheet" href="<c:url value="/css/paginationBar.css"/>">

<script>
    function submitPage(pageNumber) {
        document.getElementById("pageInput").value = pageNumber;
        let searchBarQuery2 = document.querySelector('#searchBarQuery2');
        let selectedIngredients2 = document.querySelector('#selectedIngredients2');
        if (searchBarQuery2.value === "") {
            searchBarQuery2.removeAttribute("name");
        }
        if (selectedIngredients2.value === "") {
            selectedIngredients2.removeAttribute("name");
        }
        document.getElementById("paginationForm").submit();
    }
</script>