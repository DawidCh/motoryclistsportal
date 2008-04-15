<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/fishiers.jspf" %>
<table>
    <th><fmt:message key="fishiers.formTitle.details">
            <fmt:param value="${fishier.description}"/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="id"/></td><td>${fishier.id}</td></tr>
    <tr><td><fmt:message key="fishiers.description"/></td><td>${fishier.description}</td></tr>
    <tr>
        <td><a href="<c:url value="/fishiers/delete.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.deletefishier"/></a></td>
        <td><a href="<c:url value="/fishiers/edit.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.editfishier"/></a></td>
    </tr>
</table>

<table>
    <th><fmt:message key="fishiers.fishierelements"/></th>
    <c:forEach var="fishierElement" items="${fishierElements}">
        <tr>
            <td><fmt:message key="parts.${fishierElement.fishierelement.description}"/></td>
            <td><fmt:message key="fishiers.every"/> ${fishierElement.periodlength}</td>
            <td><fmt:message key="parts.${fishierElement.activityperiod.description}"/></td>
            <td>
                <form action="<c:url value="/fishier_elements/delete.html"/>" method="post">
                    <input type="hidden" name="element" value="${fishierElement.id}"/>
                    <input type="hidden" name="fishier" value="${fishier.id}"/>
                    <input type="submit" value="<fmt:message key="submit"/>"/>
                </form>
            </td>
        </tr>
    </c:forEach>
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
            <td><input type="submit" value="<fmt:message key="submit"/>"/></td>
        </tr>
    </form>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>