<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/trips.jspf" %>

<table>
    <th><fmt:message key="trips.formTitle.add"/>
    </th>
    <tr><td><fmt:message key="trips.date"/></td><td>${trip.date}</td></tr>
    <tr><td><fmt:message key="trips.bike"/></td><td><a href="<c:url value="/bikes/details.html?bike=${trip.bike.id}"/>">${trip.bike.nickname}</a></td></tr>
    <tr><td><fmt:message key="trips.type"/></td><td><fmt:message key="trips.${trip.type.description}"/></td></tr>
    <tr>
        <td><a href="<c:url value="/trips/delete.html?trip=${trip.id}"/>"><fmt:message key="trips.deletetrip"/></a></td>
        <td><a href="<c:url value="/trips/edit.html?trip=${trip.id}"/>"><fmt:message key="trips.edittrip"/></a></td>
    </tr>
    <tr><td colspan="2">
        ${trip.description}
    </td>
</table>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>