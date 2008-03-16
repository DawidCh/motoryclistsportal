<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<fmt:message key="profile.formTitle"/>
<form action ="profile.html" method="POST">
    <input type="hidden" name="form" value="submitted"/>
    <table>
        <tr>
            <td><fmt:message key="profile.login"/></td>
            <td><c:out value="${login}"/></td>
        </tr>
        <tr><td>*
        <fmt:message key="profile.password"/></td><td><input name="password" type="password" value="<c:out value="${password}"/>"/></td></tr>
        <tr><td>*
        <fmt:message key="profile.passwordagain"/></td><td><input name="passwordAgain" type="password" value="<c:out value="${passwordAgain}"/>"/></td></tr>
        <tr><td<c:if test='${(name == null || name=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="profile.name"/></td><td><input name="name" type="text" value="<c:out value="${name}"/>"/></td></tr>
        <tr><td<c:if test='${(surname == null || surname=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="profile.surname"/></td><td><input name="surname" type="text" value="<c:out value="${surname}"/>"/></td></tr>
        <tr><td<c:if test='${(city == null || city=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="profile.city"/></td><td><input name="city" type="text" value="<c:out value="${city}"/>"/></td></tr>
        <tr>
            <td<c:if test='${(mileageType == null || mileageType=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
                <fmt:message key="measures.lengthUnit"/></td>
            <td><radiogroup>
                <fmt:message key="measures.mil"/>:
                <input type="radio" name="mileageType" value="mil"
                       <c:if test='${mileageType == "mil"}'> checked</c:if>/>
                       <fmt:message key="measures.km"/>: <input type="radio" name="mileageType" value="km"
                       <c:if test='${mileageType == "km"}'> checked</c:if>/>
                       </radiogroup>
            </td>
        </tr>
        <tr>
            <td<c:if test='${(gender == null || gender=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
                <fmt:message key="profile.gender"/></td>
            <td><radiogroup>
                <fmt:message key="profile.female"/>:
                <input type="radio" name="gender" value="female"
                       <c:if test='${gender == "female"}'> checked</c:if>/>
                       <fmt:message key="profile.male"/>: <input type="radio" name="gender" value="male"
                       <c:if test='${gender == "male"}'> checked</c:if>/>
                       </radiogroup>
            </td>
        </tr>
        <tr><td<c:if test='${(birthdate == null || birthdate=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="profile.birthdate"/></td><td><input name="birthdate" type="text" value="<c:out value="${birthdate}"/>"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>