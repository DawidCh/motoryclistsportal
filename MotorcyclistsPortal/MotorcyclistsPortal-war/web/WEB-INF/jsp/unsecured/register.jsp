<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

Profile register page.
<form action ="register.html" method="POST">
    <input type="hidden" name="form" value="submitted"/>
    <table>
        <tr><td><fmt:message key="register.login"/></td><td><input name="login"/></td></tr>
        <tr><td><fmt:message key="register.password"/></td><td><input name="password" type="password"/></td></tr>
        <tr><td><fmt:message key="register.passwordagain"/></td><td><input name="passwordAgain" type="password"/></td></tr>
        <tr><td><fmt:message key="register.name"/></td><td><input name="name" type="text"/></td></tr>
        <tr><td><fmt:message key="register.surname"/></td><td><input name="surname" type="text"/></td></tr>
        <tr><td><fmt:message key="register.city"/></td><td><input name="city" type="text"/></td></tr>
        <tr>
            <td><fmt:message key="register.gender"/></td>
            <td><radiogroup>
                    <fmt:message key="register.female"/>: <input type="radio" name="gender" value="female"/>
                    <fmt:message key="register.male"/>: <input type="radio" name="gender" value="male"/>
                </radiogroup>
            </td>
        </tr>
        <tr><td><fmt:message key="register.birthdate"/></td><td><input name="birthdate" type="text"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="<fmt:message key="submit"/>"/></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>