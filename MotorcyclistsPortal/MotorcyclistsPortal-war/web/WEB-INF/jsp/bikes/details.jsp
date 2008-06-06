<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/motorcycles.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.details">
            <fmt:param value="${bike.nickname}"/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="bikes.manufacturer"/></td><td>${bike.manufacturer}</td></tr>
    <tr><td><fmt:message key="bikes.model"/></td><td>${bike.model}</td></tr>
    <tr><td><fmt:message key="bikes.year"/></td><td>${bike.year}</td></tr>
    <tr><td><fmt:message key="bikes.mileage"/></td><td>${bike.mileage} ${user.mileageType}</td></tr>
    <tr><td><fmt:message key="bikes.torque"/></td><td>${bike.torque}</td></tr>
    <tr><td><fmt:message key="bikes.power"/></td><td>${bike.power}</td></tr>
    <tr><td><fmt:message key="bikes.displacement"/></td><td>${bike.enginecapacity}</td></tr>    
    <tr><td><a href="<c:url value="/bikes/delete.html?bike=${bike.id}"/>"><fmt:message key="bikes.deletebike"/></a></td>
        <td><a href="<c:url value="/bikes/edit.html?bike=${bike.id}"/>"><fmt:message key="bikes.editbike"/></a></td>
    </tr>
    <tr><td colspan="2"><fmt:message key="bikes.fishier"/></td></tr>
    <tr>
        <c:choose>
            <c:when test="${bike.fishier != null}">
                <td><a href="<c:url value="/fishiers/details.html?fishier=${bike.fishier.id}"/>">${bike.fishier.description}</a></td>
                <td><a href="<c:url value="/bikes/reassign.html?fishier=${bike.fishier.id}&bike=${bike.id}"/>"><fmt:message key="fishiers.deletefishier"/></a></td>
            </c:when>
            <c:otherwise>
                <td colspan="2"><a href="<c:url value="/fishiers/new.html?bike=${bike.id}"/>"><fmt:message key="fishiers.newFishier"/></a>
                <a href="<c:url value="/bikes/assignFishier.html?bike=${bike.id}"/>"><fmt:message key="bikes.assignFishier"/></a>
                </td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>