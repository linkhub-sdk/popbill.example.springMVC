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
				<fieldset class="fieldset2">
					<legend>Taxinvoice</legend>
					<ul>
						<li>writeDate : ${Taxinvoice.writeDate}</li>
						<li>chargeDirection : ${Taxinvoice.chargeDirection}</li>
						<li>issueType : ${Taxinvoice.issueType}</li>
						<li>issueTiming : ${Taxinvoice.issueTiming}</li>
						<li>taxType : ${Taxinvoice.taxType}</li>
						
						<li>invoicerCorpNum : ${Taxinvoice.invoicerCorpNum}</li>
						<li>invoicerCorpName : ${Taxinvoice.invoicerCorpName}</li>
						<li>invoicerMgtKey : ${Taxinvoice.invoicerMgtKey}</li>
						<li>invoicerTaxRegID : ${Taxinvoice.invoicerTaxRegID}</li>
						<li>invoicerCEOName : ${Taxinvoice.invoicerCEOName}</li>
						<li>invoicerAddr : ${Taxinvoice.invoicerAddr}</li>
						<li>invoicerBizClass : ${Taxinvoice.invoicerBizClass}</li>
						<li>invoicerBizType : ${Taxinvoice.invoicerBizType}</li>
						<li>invoicerContactName : ${Taxinvoice.invoicerContactName}</li>
						<li>invoicerDeptName : ${Taxinvoice.invoicerDeptName}</li>
						<li>invoicerTEL : ${Taxinvoice.invoicerTEL}</li>
						<li>invoicerHP : ${Taxinvoice.invoicerHP}</li>
						<li>invoicerEmail : ${Taxinvoice.invoicerEmail}</li>
						<li>invoicerSMSSendYN : ${Taxinvoice.invoicerSMSSendYN}</li>
						
						<li>invoiceeType : ${Taxinvoice.invoiceeType}</li>
						<li>invoiceeCorpNum : ${Taxinvoice.invoiceeCorpNum}</li>
						<li>invoiceeMgtKey : ${Taxinvoice.invoiceeMgtKey}</li>
						<li>invoiceeTaxRegID : ${Taxinvoice.invoiceeTaxRegID}</li>
						<li>invoiceeCorpName : ${Taxinvoice.invoiceeCorpName}</li>
						<li>invoiceeCEOName : ${Taxinvoice.invoiceeCEOName}</li>
						<li>invoiceeAddr : ${Taxinvoice.invoiceeAddr}</li>
						<li>invoiceeBizClass : ${Taxinvoice.invoiceeBizClass}</li>
						<li>invoiceeBizType : ${Taxinvoice.invoiceeBizType}</li>
						<li>invoiceeContactName1 : ${Taxinvoice.invoiceeContactName1}</li>
						<li>invoiceeDeptName1 : ${Taxinvoice.invoiceeDeptName1}</li>
						<li>invoiceeTEL1 : ${Taxinvoice.invoiceeTEL1}</li>
						<li>invoiceeHP1 : ${Taxinvoice.invoiceeHP1}</li>
						<li>invoiceeEmail1 : ${Taxinvoice.invoiceeEmail1}</li>
						<li>invoiceeContactName2 : ${Taxinvoice.invoiceeContactName2}</li>
						<li>invoiceeDeptName2 : ${Taxinvoice.invoiceeDeptName2}</li>
						<li>invoiceeTEL2 : ${Taxinvoice.invoiceeTEL2}</li>
						<li>invoiceeHP2 : ${Taxinvoice.invoiceeHP2}</li>
						<li>invoiceeEmail2 : ${Taxinvoice.invoiceeEmail2}</li>
						<li>invoiceeSMSSendYN : ${Taxinvoice.invoiceeSMSSendYN}</li>
						
						<li>trusteeCorpNum : ${Taxinvoice.trusteeCorpNum}</li>
						<li>trusteeCorpName : ${Taxinvoice.trusteeCorpName}</li>
						<li>trusteeMgtKey : ${Taxinvoice.trusteeMgtKey}</li>
						<li>trusteeTaxRegID : ${Taxinvoice.trusteeTaxRegID}</li>
						<li>trusteeCEOName : ${Taxinvoice.trusteeCEOName}</li>
						<li>trusteeAddr : ${Taxinvoice.trusteeAddr}</li>
						<li>trusteeBizClass : ${Taxinvoice.trusteeBizClass}</li>
						<li>trusteeBizType : ${Taxinvoice.trusteeBizType}</li>
						<li>trusteeContactName : ${Taxinvoice.trusteeContactName}</li>
						<li>trusteeDeptName : ${Taxinvoice.trusteeDeptName}</li>
						<li>trusteeTEL : ${Taxinvoice.trusteeTEL}</li>
						<li>trusteeHP : ${Taxinvoice.trusteeHP}</li>
						<li>trusteeEmail : ${Taxinvoice.trusteeEmail}</li>
						<li>trusteeSMSSendYN : ${Taxinvoice.trusteeSMSSendYN}</li>
						
						<li>supplyCostTotal : ${Taxinvoice.supplyCostTotal}</li>
						<li>taxTotal : ${Taxinvoice.taxTotal}</li>
						<li>totalAmount : ${Taxinvoice.totalAmount}</li>
						
						<li>modifyCode : ${Taxinvoice.modifyCode}</li>
						<li>orgNTSConfirmNum : ${Taxinvoice.orgNTSConfirmNum}</li>
						<li>purposeType : ${Taxinvoice.purposeType}</li>
						
						<li>serialNum : ${Taxinvoice.serialNum}</li>
						<li>cash : ${Taxinvoice.cash}</li>
						<li>chkBill : ${Taxinvoice.chkBill}</li>
						<li>credit : ${Taxinvoice.credit}</li>
						<li>note : ${Taxinvoice.note}</li>
						
						<li>remark1 : ${Taxinvoice.remark1}</li>
						<li>remark2 : ${Taxinvoice.remark2}</li>
						<li>remark3 : ${Taxinvoice.remark3}</li>
						
						<li>kwon : ${Taxinvoice.kwon}</li>
						<li>ho : ${Taxinvoice.ho}</li>
						
						<li>businessLicenseYN : ${Taxinvoice.businessLicenseYN}</li>
						<li>bankBookYN : ${Taxinvoice.bankBookYN}</li>
						<li>NTSConfirmNum : ${Taxinvoice.NTSConfirmNum}</li>
						<li>originalTaxinvoiceKey : ${Taxinvoice.originalTaxinvoiceKey}</li>
						
					</ul>
					<fieldset class="fieldset3">
						<legend>detailList</legend>
						<c:forEach items="${Taxinvoice.detailList}" var="TaxinvoiceDetail">
						<legend>SerialNum : ${TaxinvoiceDetail.serialNum}</legend>
						<ul>
							<li>purchaseDT : ${TaxinvoiceDetail.purchaseDT}</li>
							<li>itemName : ${TaxinvoiceDetail.itemName}</li>
							<li>spec : ${TaxinvoiceDetail.spec}</li>
							<li>qty : ${TaxinvoiceDetail.qty}</li>
							<li>unitCost : ${TaxinvoiceDetail.unitCost}</li>
							<li>supplyCost : ${TaxinvoiceDetail.supplyCost}</li>
							<li>tax : ${TaxinvoiceDetail.tax}</li>
							<li>remark : ${TaxinvoiceDetail.remark}</li>
						</ul>
						</c:forEach>
					</fieldset>
					<fieldset class="fieldset3">
						<legend>addContactList</legend>
						<c:forEach items="${Taxinvoice.addContactList}" var="TaxinvoiceAddContact">
						<legend>SerialNum : ${TaxinvoiceAddContact.serialNum}</legend>
						<ul>
							<li>contactName : ${TaxinvoiceAddContact.contactName}</li>
							<li>email : ${TaxinvoiceAddContact.email}</li>
						</ul>
						</c:forEach>
					</fieldset>
				</fieldset>
				
				
			</fieldset>
		 </div>
	</body>
</html>