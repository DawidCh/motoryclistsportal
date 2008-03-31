<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/fishiers.jspf" %>

<form action="<c:out value="${action}"/>" method="post">
    <input type="hidden" name="form" value="submitted"/>
    <input type="hidden" name="fishier" value="${fishier.id}"/>
<table>
    <th><c:out value="${formTitle}"/></th>
    <tr><td><fmt:message key="id"/></td>
            <td><c:out value="${fishier.id}"/></td>
    </tr>
    <tr><td<c:if test='${(fishier.description == null || fishier.description=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="fishiers.description"/></td>
            <td><input name="description" value="<c:out value="${fishier.description}"/>"/></td>
    </tr>
    <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
</table>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>