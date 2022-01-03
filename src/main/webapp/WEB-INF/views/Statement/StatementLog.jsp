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
        <c:forEach items="${StatementLogs}" var="StatementLog">
            <fieldset class="fieldset2">
                <ul>
                    <li>docLogType (로그타입) : ${StatementLog.docLogType}</li>
                    <li>log (이력정보) : ${StatementLog.log}</li>
                    <li>procType (처리형태) : ${StatementLog.procType}</li>
                    <li>procMemo (처리메모) : ${StatementLog.procMemo}</li>
                    <li>regDT (등록일시) : ${StatementLog.regDT}</li>
                    <li>ip (아이피) : ${StatementLog.ip}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>
