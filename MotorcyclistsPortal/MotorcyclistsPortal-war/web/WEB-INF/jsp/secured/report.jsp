<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/report.jspf" %>

<p><fmt:message key="report.fuzzyAverageTrip"/>: <fmt:message key="fuzzyValues.trips.${fuzzyAverageValue}"/></p>
<table>
    <th><fmt:message key="fishiers.fishierelements"/></th>
    <tr>
        <td>&nbsp</td>
        <td><fmt:message key="fishiers.period"/></td>
        <td><fmt:message key="activity"/></td>
        <td><fmt:message key="report.mileageChange"/></td>
        <td><fmt:message key="report.lastChange"/></td>
        <td><fmt:message key="report.usage"/></td>
    </tr>
    <c:forEach var="fishierElement" items="${fishierElements}" varStatus="status">
        <tr>
            <td><fmt:message key="parts.${fishierElement.fishierelement.description}"/></td>
            <td><fmt:message key="fishiers.every"/> ${fishierElement.periodlength}
                <fmt:message key="parts.${fishierElement.activityperiod.description}"/></td>
            <td><fmt:message key="activity.${fishierElement.action.description}"/></td>
            <td>${fishierElement.changemileage}  ${user.mileageType}</td>
            <td>${fishierElement.changedate}</td>
            <td><fmt:message key="fuzzyValues.usage.${fuzzyPartUsage[status.count-1]}"/></td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>