<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

Welcome page.
<br/><c:out value="${message}"/>
<form method="POST" action="j_security_check">
        <table>
        <tr><td>User name:</td><td><input type="text" name="j_username" /></td></tr>
        <tr><td>Password:</td><td><input type="password" name="j_password" /></td></tr>
        <tr><td colspan="2"><input type="submit" value="Login" /></td></tr>
        </table>
    </form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>