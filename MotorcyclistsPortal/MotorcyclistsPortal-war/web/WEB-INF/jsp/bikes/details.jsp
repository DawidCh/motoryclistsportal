<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table>
    <th><fmt:message key="bikes.formTitle.details">
            <fmt:param value='<c:out value="${bike.nickname}"'/>
        </fmt:message>
    </th>
    <tr><td><fmt:message key="bikes.manufacturer"/></td><td><c:out value="${bike.manufacturer}"/></td></tr>
    <tr><td><fmt:message key="bikes.model"/></td><td><c:out value="${bike.model}"/></td></tr>
    <tr><td><fmt:message key="bikes.year"/></td><td><c:out value="${bike.year}"/></td></tr>
    <tr><td><fmt:message key="bikes.mileage"/></td><td><c:out value="${bike.mileage}"/> <c:out value="${user.mileageType}"/></td></tr>
    <tr><td><fmt:message key="bikes.torque"/></td><td><c:out value="${bike.torque}"/></td></tr>
    <tr><td><fmt:message key="bikes.power"/></td><td><c:out value="${bike.power}"/></td></tr>
    <tr><td><fmt:message key="bikes.displacement"/></td><td><c:out value="${bike.enginecapacity}"/></td></tr>    
    <tr><td>
            <form name="deletebike" method="post" action="/bikes/delete.html">
                <input type="hidden" name="bike" value="<c:out value="${bike.id}"/>"/>
                <a href="javascript:document.deletebike.submit()"><fmt:message key="bikes.deletebike"/></a>
            </form>
        </td>
        <td>
            <form name="editbike" method="post" action="/bikes/edit.html">
                <input type="hidden" name="bike" value="<c:out value="${bike.id}"/>"/>
                <a href="javascript:document.editbike.submit()"><fmt:message key="bikes.editbike"/></a>
            </form>
        </td>
    </tr>
    <tr><td colspan="2"><fmt:message key="bikes.fishier"/></td></tr>
    <tr>
        <c:choose>
            <c:when test="${bike.fishier != null}">
                <td>
                    <form name="showfishier" method="post" action="/fishiers/show.html">
                        <input type="hidden" name="fisher" value="<c:out value="${bike.fishier.id}"/>"/>
                        <a href="javascript:document.showfishier.submit()"><c:out value="${bike.fishier.description}"/></a>
                    </form>
                </td>
                <td>
                    <form name="deletefishier" method="post" action="/fishiers/delete.html">
                        <input type="hidden" name="fishier" value="<c:out value="${bike.fishier.id}"/>"/>
                        <a href="javascript:document.deletefishier.submit()"><fmt:message key="bikes.deletefishier"/></a>
                    </form>
                </td>
            </c:when>
            <c:otherwise>
                <td colspan="2"><a href="/fishiers/addfishier.html"><fmt:message key="bikes.newFishier"/></a></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>