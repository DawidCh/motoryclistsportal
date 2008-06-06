<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/fishiers.jspf" %>
<table>
    <th><fmt:message key="fishiers.formTitle.details">
            <fmt:param value="${fishier.description}"/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="id"/></td><td>${fishier.id}</td></tr>
    <tr><td><fmt:message key="fishiers.bike"/></td><td>${fishier.motorcycle.nickname}</td></tr>
    <tr><td><fmt:message key="fishiers.description"/></td><td>${fishier.description}</td></tr>
    <tr>
        <td><a href="<c:url value="/fishiers/delete.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.deletefishier"/></a></td>
        <td><a href="<c:url value="/fishiers/edit.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.editfishier"/></a></td>
    </tr>
</table>

<table>
    <th><fmt:message key="fishiers.fishierelements"/></th>
    <tr>
        <td><fmt:message key="fishiers.description"/></td>
        <td><fmt:message key="fishiers.every"/></td>
        <td><fmt:message key="fishiers.period"/></td>
        <td><fmt:message key="activity"/></td>
        <td><fmt:message key="report.lastChange"/></td>
        <td><fmt:message key="report.mileageChange"/></td>
        <td><fmt:message key="action"/></td></tr>
    <c:forEach var="fishierElement" items="${fishierElements}">
        <tr>
            <td><fmt:message key="parts.${fishierElement.fishierelement.description}"/></td>
            <td><fmt:message key="fishiers.every"/> ${fishierElement.periodlength}</td>
            <td><fmt:message key="parts.${fishierElement.activityperiod.description}"/></td>
            <td><fmt:message key="activity.${fishierElement.action.description}"/></td>
            <td>${fishierElement.changedate}</td>
            <td>${fishierElement.changemileage}</td>
            <td>
                <form action="<c:url value="/fishier_elements/delete.html"/>" method="post">
                    <input type="hidden" name="element" value="${fishierElement.id}"/>
                    <input type="hidden" name="fishier" value="${fishier.id}"/>
                    <input type="submit" value="<fmt:message key="fishiers.deleteelement"/>"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${!empty elements}">
    <form action="<c:url value="/fishier_elements/add.html"/>" method="post">
        <input type="hidden" name="fishier" value="${fishier.id}"/>
        <tr>
            <td>
                <select name="fishierElement">
                    <c:forEach var="element" items="${elements}">
                        <option value="${element.id}"><fmt:message key="parts.${element.description}"/></option>
                    </c:forEach>
                </select>
            </td>
            <td><input name="periodLength" value="${periodLength}"/></td>
            <td>
                <select name="activityPeriod">
                    <c:forEach var="period" items="${periods}">
                        <option value="${period.description}"><fmt:message key="parts.${period.description}"/></option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name="action">
                    <c:forEach var="action" items="${actions}">
                        <option value="${action.description}"><fmt:message key="activity.${action.description}"/></option>
                    </c:forEach>
                </select>
            </td>
            <td><input name="changeDate" value="${changeDate}"/></td>
            <td><input name="changeMileage" value="${changeMileage}"/></td>
            <td><input type="submit" value="<fmt:message key="submit"/>"/></td>
        </tr>
    </form>
    </c:if>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>