<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/report.jspf" %>
<table>
    <th><fmt:message key="fishiers.fishierelements"/></th>
    <c:forEach var="fishierElement" items="${fishierElements}">
        <tr>
            <td><fmt:message key="parts.${fishierElement.fishierelement.description}"/></td>
            <td><fmt:message key="fishiers.every"/> ${fishierElement.periodlength}</td>
            <td><fmt:message key="parts.${fishierElement.activityperiod.description}"/></td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>