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
				<c:if test="${TaxinvoiceInfo != null}">
				<fieldset class="fieldset2">
					<legend>TaxinvoiceInfo</legend>
					<ul>
						<li>itemKey : ${TaxinvoiceInfo.itemKey}</li>
						<li>taxType : ${TaxinvoiceInfo.taxType}</li>
						<li>writeDate : ${TaxinvoiceInfo.writeDate}</li>
						<li>regDT : ${TaxinvoiceInfo.regDT}</li>
						<li>invoicerCorpName : ${TaxinvoiceInfo.invoicerCorpName}</li>
						<li>invoicerCorpNum : ${TaxinvoiceInfo.invoicerCorpNum}</li>
						<li>invoicerMgtKey : ${TaxinvoiceInfo.invoicerMgtKey}</li>
						<li>invoicerPrintYN : ${TaxinvoiceInfo.invoicerPrintYN}</li>
						<li>invoiceeCorpName : ${TaxinvoiceInfo.invoiceeCorpName}</li>
						<li>invoiceeCorpNum : ${TaxinvoiceInfo.invoiceeCorpNum}</li>
						<li>invoiceeMgtKey : ${TaxinvoiceInfo.invoiceeMgtKey}</li>
						<li>invoiceePrintYN : ${TaxinvoiceInfo.invoiceePrintYN}</li>
						<li>closeDownState : ${TaxinvoiceInfo.closeDownState}</li>
						<li>closeDownStateDate : ${TaxinvoiceInfo.closeDownStateDate}</li>
						<li>trusteeCorpName : ${TaxinvoiceInfo.trusteeCorpName}</li>
						<li>trusteeCorpNum : ${TaxinvoiceInfo.trusteeCorpNum}</li>
						<li>trusteeMgtKey : ${TaxinvoiceInfo.trusteeMgtKey}</li>
						<li>supplyCostTotal : ${TaxinvoiceInfo.supplyCostTotal}</li>
						<li>taxTotal : ${TaxinvoiceInfo.taxTotal}</li>
						<li>purposeType : ${TaxinvoiceInfo.purposeType}</li>
						<li>modifyCode : ${TaxinvoiceInfo.modifyCode}</li>
						<li>issueType : ${TaxinvoiceInfo.issueType}</li>
						<li>issueDT : ${TaxinvoiceInfo.issueDT}</li>
						<li>lateIssueYN : ${TaxinvoiceInfo.lateIssueYN}</li>
						<li>preIssueDT : ${TaxinvoiceInfo.preIssueDT}</li>
						<li>stateCode : ${TaxinvoiceInfo.stateCode}</li>
						<li>stateDT : ${TaxinvoiceInfo.stateDT}</li>
						<li>stateMemo : ${TaxinvoiceInfo.stateMemo}</li>
						<li>openYN : ${TaxinvoiceInfo.openYN}</li>
						<li>openDT : ${TaxinvoiceInfo.openDT}</li>
						<li>ntsresult : ${TaxinvoiceInfo.NTSResult}</li>
						<li>ntsconfirmNum : ${TaxinvoiceInfo.NTSConfirmNum}</li>
						<li>ntssendDT : ${TaxinvoiceInfo.NTSSendDT}</li>
						<li>ntsresultDT : ${TaxinvoiceInfo.NTSResultDT}</li>
						<li>ntssendErrCode : ${TaxinvoiceInfo.NTSSendErrCode}</li>
					</ul>
				</fieldset>
				</c:if>
				
				<c:if test="${TaxinvoiceInfos != null}">
				<c:forEach items="${TaxinvoiceInfos}" var="TaxinvoiceInfo">
				<fieldset class="fieldset2">
					<legend>TaxinvoiceInfo : ${TaxinvoiceInfo.invoicerMgtKey}</legend>
					<ul>
						<li>itemKey : ${TaxinvoiceInfo.itemKey}</li>
						<li>taxType : ${TaxinvoiceInfo.taxType}</li>
						<li>writeDate : ${TaxinvoiceInfo.writeDate}</li>
						<li>regDT : ${TaxinvoiceInfo.regDT}</li>
						<li>invoicerCorpName : ${TaxinvoiceInfo.invoicerCorpName}</li>
						<li>invoicerCorpNum : ${TaxinvoiceInfo.invoicerCorpNum}</li>
						<li>invoicerMgtKey : ${TaxinvoiceInfo.invoicerMgtKey}</li>
						<li>invoicerPrintYN : ${TaxinvoiceInfo.invoicerPrintYN}</li>
						<li>invoiceeCorpName : ${TaxinvoiceInfo.invoiceeCorpName}</li>
						<li>invoiceeCorpNum : ${TaxinvoiceInfo.invoiceeCorpNum}</li>
						<li>invoiceeMgtKey : ${TaxinvoiceInfo.invoiceeMgtKey}</li>
						<li>invoiceePrintYN : ${TaxinvoiceInfo.invoiceePrintYN}</li>
						<li>closeDownState : ${TaxinvoiceInfo.closeDownState}</li>
						<li>closeDownStateDate : ${TaxinvoiceInfo.closeDownStateDate}</li>
						<li>trusteeCorpName : ${TaxinvoiceInfo.trusteeCorpName}</li>
						<li>trusteeCorpNum : ${TaxinvoiceInfo.trusteeCorpNum}</li>
						<li>trusteeMgtKey : ${TaxinvoiceInfo.trusteeMgtKey}</li>
						<li>supplyCostTotal : ${TaxinvoiceInfo.supplyCostTotal}</li>
						<li>taxTotal : ${TaxinvoiceInfo.taxTotal}</li>
						<li>purposeType : ${TaxinvoiceInfo.purposeType}</li>
						<li>modifyCode : ${TaxinvoiceInfo.modifyCode}</li>
						<li>issueType : ${TaxinvoiceInfo.issueType}</li>
						<li>issueDT : ${TaxinvoiceInfo.issueDT}</li>
						<li>lateIssueYN : ${TaxinvoiceInfo.lateIssueYN}</li>
						<li>preIssueDT : ${TaxinvoiceInfo.preIssueDT}</li>
						<li>stateCode : ${TaxinvoiceInfo.stateCode}</li>
						<li>stateDT : ${TaxinvoiceInfo.stateDT}</li>
						<li>stateMemo : ${TaxinvoiceInfo.stateMemo}</li>
						<li>openYN : ${TaxinvoiceInfo.openYN}</li>
						<li>openDT : ${TaxinvoiceInfo.openDT}</li>
						<li>ntsresult : ${TaxinvoiceInfo.NTSResult}</li>
						<li>ntsconfirmNum : ${TaxinvoiceInfo.NTSConfirmNum}</li>
						<li>ntssendDT : ${TaxinvoiceInfo.NTSSendDT}</li>
						<li>ntsresultDT : ${TaxinvoiceInfo.NTSResultDT}</li>
						<li>ntssendErrCode : ${TaxinvoiceInfo.NTSSendErrCode}</li>
					</ul>
				</fieldset>
				</c:forEach>
				</c:if>
			</fieldset>
		 </div>
	</body>
</html>
