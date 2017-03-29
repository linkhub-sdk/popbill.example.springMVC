<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />
		
		<title>팝빌 SDK SpringMVC Example.</title>
	</head>
	<body>
		<div id="content">
			<p class="heading1">Response</p>
			<br/>
			<fieldset class="fieldset1">
				<legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
				<c:forEach items="${TaxinvoiceLogs}" var="TaxinvoiceLog">
				<fieldset class="fieldset2">
					<legend>TaxinvoiceLog.docLogType : ${TaxinvoiceLog.docLogType}</legend>
					<ul>
						<li>log : ${TaxinvoiceLog.log}</li>
						<li>procType : ${TaxinvoiceLog.procType}</li>
						<li>procCorpName : ${TaxinvoiceLog.procCorpName}</li>
						<li>procContactName : ${TaxinvoiceLog.procContactName}</li>
						<li>procMemo : ${TaxinvoiceLog.procMemo}</li>
						<li>regDT : ${TaxinvoiceLog.regDT}</li>
						<li>ip : ${TaxinvoiceLog.IP}</li>
					</ul>
				</fieldset>
				</c:forEach>
			</fieldset>
		 </div>
	</body>
</html>
