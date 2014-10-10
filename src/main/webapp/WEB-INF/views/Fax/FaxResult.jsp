<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
				<c:forEach items="${FaxResults}" var="FaxResult" varStatus="status">
				<fieldset class="fieldset2">
					<legend>FaxResult : ${status.index}</legend>
					<ul>
						<li>sendState : ${FaxResult.sendState}</li>
						<li>convState : ${FaxResult.convState}</li>
						<li>sendNum : ${FaxResult.sendNum}</li>
						<li>receiveNum : ${FaxResult.receiveNum}</li>
						<li>receiveName : ${FaxResult.receiveName}</li>
						<li>sendPageCnt : ${FaxResult.sendPageCnt}</li>
						<li>successPageCnt : ${FaxResult.successPageCnt}</li>
						<li>failPageCnt : ${FaxResult.failPageCnt}</li>
						<li>refundPageCnt : ${FaxResult.refundPageCnt}</li>
						<li>cancelPageCnt : ${FaxResult.cancelPageCnt}</li>
						<li>reserveDT : ${FaxResult.reserveDT}</li>
						<li>sendDT : ${FaxResult.sendDT}</li>
						<li>resultDT : ${FaxResult.resultDT}</li>
						<li>sendResult : ${FaxResult.sendResult}</li>
					</ul>
				</fieldset>
				</c:forEach>
			</fieldset>
		 </div>
	</body>
</html>
