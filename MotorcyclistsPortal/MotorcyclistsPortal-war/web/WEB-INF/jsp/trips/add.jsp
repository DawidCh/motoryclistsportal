<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/trips.jspf" %>

<form action="${action}" method="post">
    <input type="hidden" name="form" value="submitted"/>
    <input type="hidden" name="trip" value="${trip}"/>
    <input type="hidden" name="action" value="${action}"/>
    <table>
        <th>${formTitle}</th>
        <tr><td<c:if test='${(title == null || title=="") && form != null}'>
                style="color: ${failColor}"</c:if>>*<fmt:message key="trips.title"/></td>
            <td><input name="title" value="${title}"/></td>
        </tr>
        <tr><td<c:if test='${(distance == null || distance=="") && form != null}'>
                style="color: ${failColor}"</c:if>>*<fmt:message key="trips.distance"/></td>
            <td><input name="distance" value="${distance}"/></td>
        </tr>
        <tr><td<c:if test='${(date == null || date=="") && form != null}'>
                style="color: ${failColor}"</c:if>>*<fmt:message key="trips.date"/></td>
            <td><input name="date" value="${date}"/></td>
        </tr>
        <tr><td><fmt:message key="trips.bike"/></td>
            <td><select name="bike">
                <c:forEach var="loopBike" items="${bikes}">
                    <option value="${loopBike.id}" <c:if test="${loopBike.id == bike}">selected="selected"</c:if>>${loopBike.nickname}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr><td><fmt:message key="trips.type"/></td>
            <td><select name="type">
                <c:forEach var="loopType" items="${types}">
                    <option value="${loopType.description}" <c:if test="${loopType.description == type}">selected="selected"</c:if>><fmt:message key="trips.${loopType.description}"/></option>
                </c:forEach>
            </select></td>
        </tr>
        <tr><td<c:if test='${(description == null || description=="") && form != null}'>
                style="color: ${failColor}"</c:if>>*<fmt:message key="trips.description"/></td>
            <td><textarea name="description" rows="10" cols="50">${description}</textarea></td>
        </tr>
        <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>