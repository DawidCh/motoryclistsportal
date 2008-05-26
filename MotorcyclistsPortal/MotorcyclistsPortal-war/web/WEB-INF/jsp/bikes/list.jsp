<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/motorcycles.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.list"/></th>
    <tr style="border-style:solid;">
        <td><fmt:message key="bikes.nickname"/></td>
        <td><fmt:message key="bikes.manufacturer"/></td>
        <td><fmt:message key="bikes.model"/></td>
        <td><fmt:message key="bikes.year"/></td>
        <td><fmt:message key="bikes.mileage"/></td>
        <td><fmt:message key="bikes.fishier"/></td>
        <td><fmt:message key="action"/></td>
    </tr>
    <c:forEach var="bike" items="${bikes}">
        <tr>
            <td><a href="<c:url value="/bikes/details.html?bike=${bike.id}"/>">${bike.nickname}</a></td>
            <td>${bike.manufacturer}</td>
            <td>${bike.model}</td>
            <td>${bike.year}</td>
            <td>${bike.mileage} ${user.mileageType}</td>
            <c:choose>
                <c:when test="${bike.fishier != null}">
                    <td><a href="<c:url value="/fishiers/details.html?fishier=${bike.fishier.id}"/>">${bike.fishier.description}</a>
                    </td>
                </c:when>
                <c:otherwise>
                    <td><a href="<c:url value="/fishiers/new.html"/>"><fmt:message key="fishiers.newFishier"/></a>
                        <a href="<c:url value="/bikes/assignFishier.html?bike=${bike.id}"/>"><fmt:message key="bikes.assignFishier"/></a>
                    </td>
                </c:otherwise>
            </c:choose>
            <td>
                <a href="<c:url value="/bikes/delete.html?bike=${bike.id}"/>"><fmt:message key="bikes.deletebike"/></a>
                <a href="<c:url value="/bikes/edit.html?bike=${bike.id}"/>"><fmt:message key="bikes.editbike"/></a>
                <c:if test="${bike.fishier != null}">
                    <a href="<c:url value="/report/generate.html?bike=${bike.id}"/>"><fmt:message key="bikes.generate"/></a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>