<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<fmt:message key="register.formTitle"/>
<form action ="register.html" method="POST">
    <input type="hidden" name="form" value="submitted"/>
    <table>
        <tr><td<c:if test='${(newLogin == null || newLogin=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.login"/></td><td><input name="newLogin" value="<c:out value="${newLogin}"/>"/></td></tr>
        <tr><td<c:if test='${(password == null || password=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.password"/></td><td><input name="password" type="password" value="<c:out value="${password}"/>"/></td></tr>
        <tr><td<c:if test='${(passwordAgain == null || passwordAgain=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.passwordagain"/></td><td><input name="passwordAgain" type="password" value="<c:out value="${passwordAgain}"/>"/></td></tr>
        <tr><td<c:if test='${(name == null || name=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.name"/></td><td><input name="name" type="text" value="<c:out value="${name}"/>"/></td></tr>
        <tr><td<c:if test='${(surname == null || surname=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.surname"/></td><td><input name="surname" type="text" value="<c:out value="${surname}"/>"/></td></tr>
        <tr><td<c:if test='${(city == null || city=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.city"/></td><td><input name="city" type="text" value="<c:out value="${city}"/>"/></td></tr>
        <tr>
            <td<c:if test='${(gender == null || gender=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
                <fmt:message key="register.gender"/></td>
            <td><radiogroup>
                    <fmt:message key="register.female"/>: <input type="radio" name="gender" value="female"
                    <c:if test='${gender == "female"}'> checked</c:if>/>
                    <fmt:message key="register.male"/>: <input type="radio" name="gender" value="male"
                    <c:if test='${gender == "male"}'> checked</c:if>/>
                </radiogroup>
            </td>
        </tr>
        <tr><td<c:if test='${(birthdate == null || birthdate=="") && form != null}'> style="color: <c:out value="${failColor}"/>"</c:if>>*
            <fmt:message key="register.birthdate"/></td><td><input name="birthdate" type="text" value="<c:out value="${birthdate}"/>"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>