<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/fishiers.jspf" %>
<table>
    <th><fmt:message key="fishiers.formTitle.list"/></th>
    <tr style="border-style:solid;">
        <td><fmt:message key="id"/></td>
        <td><fmt:message key="fishiers.description"/></td>
        <td><fmt:message key="action"/></td>
    </tr>
    <c:forEach var="fishier" items="${fishiers}" varStatus="status">
        <tr>
            <td><a href="<c:url value="/fishiers/details.html?fishier=${fishier.id}"/>">${status.count}</a></td>
            <td>${fishier.description}</td>
            <td>
                <a href="<c:url value="/fishiers/delete.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.deletefishier"/></a>
                <a href="<c:url value="/fishiers/edit.html?fishier=${fishier.id}"/>"><fmt:message key="fishiers.editfishier"/></a>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>