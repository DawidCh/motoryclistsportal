<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.details">
            <fmt:param value='<c:out value="${bike.nickname}"'/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="bikes.manufacturer"/></td><td><c:out value="${bike.manufacturer}"/></td></tr>
    <tr><td><fmt:message key="bikes.model"/></td><td><c:out value="${bike.model}"/></td></tr>
    <tr><td><fmt:message key="bikes.year"/></td><td><c:out value="${bike.year}"/></td></tr>
    <tr><td><fmt:message key="bikes.mileage"/></td><td><c:out value="${bike.mileage}"/> <c:out value="${user.mileageType}"/></td></tr>
    <tr><td><fmt:message key="bikes.torque"/></td><td><c:out value="${bike.torque}"/></td></tr>
    <tr><td><fmt:message key="bikes.power"/></td><td><c:out value="${bike.power}"/></td></tr>
    <tr><td><fmt:message key="bikes.displacement"/></td><td><c:out value="${bike.enginecapacity}"/></td></tr>    
    <tr><td><a href="<c:url value="/bikes/delete.html?bike=${bike.id}"/>"><fmt:message key="bikes.deletebike"/></a></td>
        <td><a href="<c:url value="/bikes/edit.html?bike=${bike.id}"/>"><fmt:message key="bikes.editbike"/></a></td>
    </tr>
    <tr><td colspan="2"><fmt:message key="bikes.fishier"/></td></tr>
    <tr>
        <c:choose>
            <c:when test="${bike.fishier != null}">
                <td><a href="<c:url value="/fishiers/show.html?fishier=${bike.fishier.id}"/>"><c:out value="${bike.fishier.description}"/></a></td>
                <td><a href="<c:url value="/fishiers/delete.html?fishier=${bike.fishier.id}"/>"><fmt:message key="fishiers.deleteFishier"/></a></td>
            </c:when>
            <c:otherwise>
                <td colspan="2"><a href="/fishiers/addfishier.html"><fmt:message key="fishiers.newFishier"/></a></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>