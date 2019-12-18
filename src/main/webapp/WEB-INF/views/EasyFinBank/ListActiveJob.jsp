<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <c:forEach items="${JobStates}" var="JobState">
            <fieldset class="fieldset2">
                <ul>
                    <li>jobID (작업아이디) : ${JobState.jobID}</li>
	                <li>jobState (수집상태) : ${JobState.jobState}</li>
	                <li>startDate (시작일자) : ${JobState.startDate}</li>
	                <li>endDate (종료일자) : ${JobState.endDate}</li>
	                <li>errorCode (오류코드) : ${JobState.errorCode}</li>
	                <li>errorReason (오류메시지) : ${JobState.errorReason}</li>
	                <li>jobStartDT (작업 시작일시) : ${JobState.jobStartDT}</li>
	                <li>jobEndDT (작업 종료일시) : ${JobState.jobEndDT}</li>
	                <li>regDT (수집 요청일시) : ${JobState.regDT}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>