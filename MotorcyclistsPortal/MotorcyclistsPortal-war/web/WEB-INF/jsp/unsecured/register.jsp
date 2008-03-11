<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

Profile register page.
<form action ="register.html" method="POST">
    <input type="hidden" name="form" value="submitted"/>
    <table>
        <tr><td>
            <c:choose><c:when test='${(login == null || login=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.login"/></td><td><input name="newLogin" value="<c:out value="${newLogin}"/>"/></td></tr>
        <tr><td>
            <c:choose><c:when test='${(password == null || password=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.password"/></td><td><input name="password" type="password"/></td></tr>
        <tr><td>
            <c:choose><c:when test='${(passwordAgain == null || passwordAgain=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.passwordagain"/></td><td><input name="passwordAgain" type="password"/></td></tr>
        <tr><td>
            <c:choose><c:when test='${(name == null || name=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.name"/></td><td><input name="name" type="text" value="<c:out value="${name}"/>"/></td></tr>
        <tr><td>
            <c:choose><c:when test='${(surname == null || surname=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.surname"/></td><td><input name="surname" type="text" value="<c:out value="${surname}"/>"/></td></tr>
        <tr><td>
            <c:choose><c:when test='${(city == null || city=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.city"/></td><td><input name="city" type="text" value="<c:out value="${city}"/>"/></td></tr>
        <tr>
            <td>
                <c:choose><c:when test='${gender == null && form != null}'><font color="red">*</font></c:when>
                <c:otherwise>*</c:otherwise></c:choose>
                <fmt:message key="register.gender"/></td>
            <td><radiogroup>
                    <fmt:message key="register.female"/>: <input type="radio" name="gender" value="female"
                    <c:if test='${gender == "female"}'> checked</c:if>/>
                    <fmt:message key="register.male"/>: <input type="radio" name="gender" value="male"
                    <c:if test='${gender == "male"}'> checked</c:if>/>
                </radiogroup>
            </td>
        </tr>
        <tr><td>
            <c:choose><c:when test='${(birthdate == null || birthdate=="") && form != null}'><font color="red">*</font></c:when>
            <c:otherwise>*</c:otherwise></c:choose>
            <fmt:message key="register.birthdate"/></td><td><input name="birthdate" type="text" value="<c:out value="${birthdate}"/>"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>