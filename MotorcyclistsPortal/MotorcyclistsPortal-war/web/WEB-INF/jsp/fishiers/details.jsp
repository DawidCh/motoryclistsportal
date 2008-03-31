<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/fishiers.jspf" %>
<table>
    <th><fmt:message key="fishiers.formTitle.details">
            <fmt:param value='<c:out value="${fishier.description}"'/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="id"/></td><td><c:out value="${fishier.id}"/></td></tr>
    <tr><td><fmt:message key="fishiers.description"/></td><td><c:out value="${fishier.description}"/></td></tr>
    <tr><td><a href="<c:url value="/fishiers/delete.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.deletefishier"/></a></td>
        <td><a href="<c:url value="/fishiers/edit.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.editfishier"/></a></td>
    </tr>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>