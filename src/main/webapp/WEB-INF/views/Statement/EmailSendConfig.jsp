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
				<ul>
					<c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
					<c:if test="${EmailSendConfig.emailType == 'SMT_ISSUE'}">
						<li>${EmailSendConfig.emailType}(공급받는자에게 전자명세서가 발행 되었음을 알려주는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
					</c:if>
					
					<c:if test="${EmailSendConfig.emailType == 'SMT_ACCEPT'}">
						<li>${EmailSendConfig.emailType}(공급자에게 전자명세서가 승인 되었음을 알려주는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
					</c:if>
					
					<c:if test="${EmailSendConfig.emailType == 'SMT_DENY'}">
						<li>${EmailSendConfig.emailType}(공급자에게 전자명세서가 거부 되었음을 알려주는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
					</c:if>
					
					<c:if test="${EmailSendConfig.emailType == 'SMT_CANCEL'}">
						<li>${EmailSendConfig.emailType}(공급받는자에게 전자명세서가 취소 되었음을 알려주는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
					</c:if>
					
					<c:if test="${EmailSendConfig.emailType == 'SMT_CANCEL_ISSUE'}">
						<li>${EmailSendConfig.emailType}(공급받는자에게 전자명세서가 발행취소 되었음을 알려주는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
					</c:if>
					</c:forEach>
				</ul>
			</fieldset>
		 </div>
	</body>
</html>
