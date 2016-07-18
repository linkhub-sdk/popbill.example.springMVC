<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />
		
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
					<legend>JobID (작업아이디) : ${JobState.jobID}</legend>
					<ul>
						<li>jobState (수집상태) : ${JobState.jobState}</li>
						<li>queryType (수집유형) : ${JobState.queryType}</li>
						<li>queryDateType (일자유형) : ${JobState.queryDateType}</li>
						<li>queryStDate (시작일자) : ${JobState.queryStDate}</li>
						<li>queryEnDate (종료일자) : ${JobState.queryEnDate}</li>
						<li>errorCode (오류코드) : ${JobState.errorCode}</li>
						<li>jobStartDT (작업 시작일시) : ${JobState.jobStartDT}</li>
						<li>jobEndDT (작업 종료일시) : ${JobState.jobEndDT}</li>
						<li>collectCount (수집개수) : ${JobState.collectCount}</li>
						<li>regDT (수집 요청일시) : ${JobState.regDT}</li>
					</ul>
				</fieldset>
				</c:forEach>
			</fieldset>
		 </div>
	</body>
</html>