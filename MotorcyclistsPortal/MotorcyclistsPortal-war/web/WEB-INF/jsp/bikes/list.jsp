<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.list"/></th>
    <tr style="border:solid;">
        <td><fmt:message key="bikes.nickname"/></td>
        <td><fmt:message key="bikes.manufacturer"/></td>
        <td><fmt:message key="bikes.model"/></td>
        <td><fmt:message key="bikes.year"/></td>
        <td><fmt:message key="bikes.mileage"/></td>
        <td><fmt:message key="bikes.fishier"/></td>
    </tr>
    <c:forEach var="bike" items="${bikes}">
        <tr>
            <td><a href="/bikes/details.html?bike=<c:out value="${bike.id}"/>"><c:out value="${bike.nickname}"/></a></td>
            <td><c:out value="${bike.manufacturer}"/></td>
            <td><c:out value="${bike.model}"/></td>
            <td><c:out value="${bike.year}"/></td>
            <td><c:out value="${bike.mileage}"/> <c:out value="${user.mileageType}"/></td>
            <c:choose>
                <c:when test="${bike.fishier != null}">
                    <td><form name="showfishier" method="post" action="/fishiers/showfishier.html">
                        <input type="hidden" name="fisher" value="<c:out value="${bike.fishier.id}"/>"/>
                        <a href="javascript:document.showfishier.submit()"><c:out value="${bike.fishier.description}"/></a>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td><a href="/fishiers/addfishier.html"><fmt:message key="bikes.newFishier"/></a></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>