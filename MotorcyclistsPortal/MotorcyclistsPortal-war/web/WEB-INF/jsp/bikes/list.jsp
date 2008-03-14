<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.list"/></th>
    <tr>
        <td><fmt:message key="bikes.nickname"/></td>
        <td><fmt:message key="bikes.manufacturer"/></td>
        <td><fmt:message key="bikes.model"/></td>
        <td><fmt:message key="bikes.year"/></td>
        <td><fmt:message key="bikes.displacement"/></td>
    </tr>
    <c:forEach var="bike" items="${bikes}">
        <tr>
            <td><c:out value="${bike.nickname}"/></td>
            <td><c:out value="${bike.manufacturer}"/></td>
            <td><c:out value="${bike.model}"/></td>
            <td><c:out value="${bike.year}"/></td>
            <c:if test="${bike.fishier != null}">
                <td><a href="/bikes/showfishier.html"><c:out value="${bike.fishier.description}"/></a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>