<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/report.jspf" %>
<table>
    <th><fmt:message key="fishiers.fishierelements"/></th>
    <tr>
        <td>&nbsp</td>
        <td><fmt:message key="fishiers.period"/></td>
        <td><fmt:message key="report.mileageChange"/></td>
        <td><fmt:message key="report.lastChange"/></td>
        <td><fmt:message key="action"/></td>
    </tr>
    <c:forEach var="fishierElement" items="${fishierElements}">
        <tr>
            <td><fmt:message key="parts.${fishierElement.fishierelement.description}"/></td>
            <td><fmt:message key="fishiers.every"/> ${fishierElement.periodlength}
                <fmt:message key="parts.${fishierElement.activityperiod.description}"/></td>
            <td>${fishierElement.changemileage}</td>
            <td>${fishierElement.changedate}</td>
            <td>delete</td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>