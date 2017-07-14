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
				<c:forEach items="${SentMessages}" var="SentMessage" varStatus="status">
				<fieldset class="fieldset2">
					<legend>문자 전송결과</legend>
					<ul>
						<li>state (전송상태 코드) : ${SentMessage.state}</li>
						<li>result (전송결과 코드) : ${SentMessage.result}</li>
						<li>subject (메시지 제목) : ${SentMessage.subject}</li>
						<li>messageType (메시지타입) : ${SentMessage.messageType}</li>
						<li>content (메시지 내용) : ${SentMessage.content}</li>
						<li>sendNum (발신번호) : ${SentMessage.sendNum}</li>
						<li>senderName (발신자명) : ${SentMessage.senderName}</li>
						<li>receiveNum (수신번호) : ${SentMessage.receiveNum}</li>
						<li>receiveName (수신자명) : ${SentMessage.receiveName}</li>
						<li>receiptDT (접수일시) : ${SentMessage.receiptDT}</li>
						<li>sendDT (전송일시) : ${SentMessage.sendDT}</li>
						<li>resultDT (전송결과 수신일시) : ${SentMessage.resultDT}</li>
						<li>reserveDT (예약일시) : ${SentMessage.reserveDT}</li>
						<li>tranNet (전송처리 이동통신사명) : ${SentMessage.tranNet}</li>
					</ul>
				</fieldset>
				</c:forEach>
			</fieldset>
		 </div>
	</body>
</html>
