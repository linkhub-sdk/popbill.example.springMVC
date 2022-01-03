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
        <c:forEach items="${AttachedFiles}" var="AttachedFile">
            <fieldset class="fieldset2">
                <ul>
                    <li>serialNum (첨부파일 일련번호) : ${AttachedFile.serialNum}</li>
                    <li>attachedFile (파일아이디-첨부파일 삭제시 사용) : ${AttachedFile.attachedFile}</li>
                    <li>displayName (첨부파일명) : ${AttachedFile.displayName}</li>
                    <li>regDT (첨부일시) : ${AttachedFile.regDT}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>
