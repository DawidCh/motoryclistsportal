<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ include file="/WEB-INF/jspf/subheaders/motorcycles.jspf" %>

<form action="<c:out value="${action}"/>" method="post">
    <input type="hidden" name="form" value="submitted"/>
    <input type="hidden" name="bike" value="${bike}"/>
<table>
    <th><c:out value="${formTitle}"/></th>
    <tr><td<c:if test='${(nickname == null || nickname=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.nickname"/></td>
            <td><input name="nickname" value="<c:out value="${nickname}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(manufacturer == null || manufacturer=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.manufacturer"/></td>
            <td><input name="manufacturer" value="<c:out value="${manufacturer}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(model == null || model=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.model"/></td>
            <td><input name="model" value="<c:out value="${model}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(year == null || year=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.year"/></td>
            <td><input name="year" value="<c:out value="${year}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(mileage == null || mileage=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.mileage"/></td>
            <td><input name="mileage" value="<c:out value="${mileage}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(torque == null || torque=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.torque"/></td>
            <td><input name="torque" value="<c:out value="${torque}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(power == null || power=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.power"/></td>
            <td><input name="power" value="<c:out value="${power}"/>"/></td>
    </tr>
    <tr><td<c:if test='${(displacement == null || displacement=="") && form != null}'>
            style="color: <c:out value='${failColor}'/>"</c:if>>*<fmt:message key="bikes.displacement"/></td>
            <td><input name="displacement" value="<c:out value="${displacement}"/>"/></td>
    </tr>
    <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
</table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>