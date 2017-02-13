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
					<legend>SentMessage : ${status.index}</legend>
					<ul>
						<li>sendState : ${SentMessage.state}</li>
						<li>subject : ${SentMessage.subject}</li>
						<li>messageType : ${SentMessage.messageType}</li>
						<li>content : ${SentMessage.content}</li>
						<li>sendNum : ${SentMessage.sendNum}</li>
						<li>senderName : ${SentMessage.senderName}</li>
						<li>receiveNum : ${SentMessage.receiveNum}</li>
						<li>receiveName : ${SentMessage.receiveName}</li>
						<li>reserveDT : ${SentMessage.reserveDT}</li>
						<li>sendDT : ${SentMessage.sendDT}</li>
						<li>resultDT : ${SentMessage.resultDT}</li>
						<li>result : ${SentMessage.result}</li>
						<li>receiptDT : ${SentMessage.receiptDT}</li>
					</ul>
				</fieldset>
				</c:forEach>
			</fieldset>
		 </div>
	</body>
</html>
