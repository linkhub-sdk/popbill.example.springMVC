<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>팝빌 SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">Response</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <c:if test="${CheckAutoDeny != null}">
            <fieldset class="fieldset2">
                <ul>
                    <li>smsdenyNumber (전용 080 번호) : ${CheckAutoDeny.smsdenyNumber}</li>
                    <li>regDT (등록일시) : ${CheckAutoDeny.regDT}</li>
                </ul>
            </fieldset>
        </c:if>
    </fieldset>
</div>
</body>
</html>
