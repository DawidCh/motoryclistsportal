<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

Welcome page.
<c:out value="${message}"/>
<form method="POST" action="j_security_check">
        <table>
        <tr><td>User name:</td><td><input type="text" name="j_username" /></td></tr>
        <tr><td>Password:</td><td><input type="password" name="j_password" /></td></tr>
        <tr><td colspan="2"><input type="submit" value="Login" /></td></tr>
        </table>
    </form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
