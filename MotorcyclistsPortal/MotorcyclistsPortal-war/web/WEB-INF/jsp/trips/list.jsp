<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/trips.jspf" %>

<p><fmt:message key="report.fuzzyAverageTrip"/>: <fmt:message key="fuzzyValues.trips.${fuzzyAverageValue}"/></p>
<table>
    <th><fmt:message key="trips.formTitle.list"/></th>
    <tr style="border-style:solid;">
        <td><fmt:message key="trips.date"/></td>
        <td><fmt:message key="trips.title"/></td>
        <td><fmt:message key="trips.bike"/></td>
        <td><fmt:message key="trips.type"/></td>
        <td><fmt:message key="trips.distance"/></td>
        <td><fmt:message key="action"/></td>
        <td><fmt:message key="report.fuzzyTrip"/></td>
    </tr>
    <c:forEach var="trip" items="${trips}" varStatus="status">
        <tr>
            <td>${trip.date}</td>
            <td><a href="<c:url value="/trips/details.html?trip=${trip.id}"/>">${trip.title}</a></td>
            <td><a href="<c:url value="/bikes/details.html?bike=${trip.bike.id}"/>">${trip.bike.nickname}</a></td>
            <td><fmt:message key="trips.${trip.type.description}"/></td>
            <td>${trip.distance}  ${user.mileageType}</td>
            <td>
                <a href="<c:url value="/trips/delete.html?trip=${trip.id}"/>"><fmt:message key="trips.deletetrip"/></a>
                <a href="<c:url value="/trips/edit.html?trip=${trip.id}"/>"><fmt:message key="trips.edittrip"/></a>
            </td>
            <td><fmt:message key="fuzzyValues.trips.${fuzzyTripLength[status.count-1].description}"/></td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>