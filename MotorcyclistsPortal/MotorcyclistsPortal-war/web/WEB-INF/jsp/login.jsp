<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<fmt:message key="loginpage.formTitle"/>
<form method="POST" action="j_security_check">
    <table>
        <tr><td><fmt:message key="loginpage.login"/></td><td><input type="text" name="j_username" /></td></tr>
        <tr><td><fmt:message key="loginpage.password"/></td><td><input type="password" name="j_password" /></td></tr>
        <tr><td colspan="2"><input type="submit" value="Login" /></td></tr>
    </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>