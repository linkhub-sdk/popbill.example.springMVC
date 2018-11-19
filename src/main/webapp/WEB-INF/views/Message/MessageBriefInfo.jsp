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
        <c:forEach items="${MessageBriefInfos}" var="MessageBriefInfo">
            <fieldset class="fieldset2">
                <legend>접수번호 : ${MessageBriefInfo.rNum}</legend>
                <ul>
                    <li>rNum (접수번호) : ${MessageBriefInfo.sn}</li>
                    <li>sn (일련번호) : ${MessageBriefInfo.rNum}</li>
                    <li>stat (전송 상태코드) : ${MessageBriefInfo.stat}</li>
                    <li>rlt (전송 결과코드) : ${MessageBriefInfo.sDT}</li>
                    <li>sDT (전송일시) : ${MessageBriefInfo.rDT}</li>
                    <li>rDT (결과코드 수신일시) : ${MessageBriefInfo.rlt}</li>
                    <li>net (전송 이동통신사명) : ${MessageBriefInfo.net}</li>
                    <li>srt (구 전송결과 코드) : ${MessageBriefInfo.srt}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>
