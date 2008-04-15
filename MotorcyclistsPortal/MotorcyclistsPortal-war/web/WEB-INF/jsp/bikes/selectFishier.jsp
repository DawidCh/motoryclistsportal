<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/motorcycles.jspf" %>

<form action="assignFishier.html" method="post">
    <input type="hidden" name="bike" value="${bike}"/>
    <input type="hidden" name="form" value="submitted"/>
    <table>
        <th><fmt:message key="fishiers.formTitle.choose"/></th>
        <tr><td>&nbsp</td><td><fmt:message key="fishiers.description"/></td></tr>
        <c:forEach items="${fishiers}" var="fishier" varStatus="status">
            <tr><td><input <c:if test="${status.count == 1}">checked="checked"</c:if>
                    type="radio" name="fishier" value="${fishier.id}"/></td>
                <td>${fishier.id}</td>
                <td>${fishier.description}</td>
            </tr>
        </c:forEach>
        <tr><td colspan="3"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>