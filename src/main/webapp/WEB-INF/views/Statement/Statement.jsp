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
					<legend>Statement</legend>
					<ul>
						<li>writeDate : ${Statement.writeDate}</li>
						<li>itemCode : ${Statement.itemCode}</li>
						<li>mgtKey : ${Statement.mgtKey}</li>
						<li>formCode : ${Statement.formCode}</li>
						<li>writeDate : ${Statement.writeDate}</li>
						<li>taxType : ${Statement.taxType}</li>

						<li>senderCorpNum : ${Statement.senderCorpNum}</li>
						<li>senderTaxRegID : ${Statement.senderTaxRegID}</li>
						<li>senderCorpName : ${Statement.senderCorpName}</li>
						<li>senderCEOName : ${Statement.senderCEOName}</li>
						<li>senderAddr : ${Statement.senderAddr}</li>
						<li>senderBizClass : ${Statement.senderBizClass}</li>
						<li>senderBizType : ${Statement.senderBizType}</li>
						<li>senderContactName : ${Statement.senderContactName}</li>
						<li>senderDeptName : ${Statement.senderDeptName}</li>
						<li>senderTEL : ${Statement.senderTEL}</li>
						<li>senderHP : ${Statement.senderHP}</li>
						<li>senderEmail : ${Statement.senderEmail}</li>
						<li>senderFAX : ${Statement.senderFAX}</li>

						<li>receiverCorpNum : ${Statement.receiverCorpNum}</li>
						<li>receiverTaxRegID : ${Statement.receiverTaxRegID}</li>
						<li>receiverCorpName : ${Statement.receiverCorpName}</li>
						<li>receiverCEOName : ${Statement.receiverCEOName}</li>
						<li>receiverAddr : ${Statement.receiverAddr}</li>
						<li>receiverBizClass : ${Statement.receiverBizClass}</li>
						<li>receiverBizType : ${Statement.receiverBizType}</li>
						<li>receiverContactName : ${Statement.receiverContactName}</li>
						<li>receiverDeptName : ${Statement.receiverDeptName}</li>
						<li>receiverTEL : ${Statement.receiverTEL}</li>
						<li>receiverHP : ${Statement.receiverHP}</li>
						<li>receiverEmail : ${Statement.receiverEmail}</li>
						<li>receiverFAX : ${Statement.receiverFAX}</li>

						<li>taxTotal : ${Statement.taxTotal}</li>
						<li>supplyCostTotal : ${Statement.supplyCostTotal}</li>
						<li>totalAmount : ${Statement.totalAmount}</li>
						<li>purposeType : ${Statement.purposeType}</li>
						<li>serialNum : ${Statement.serialNum}</li>
						<li>remark1 : ${Statement.remark1}</li>
						<li>remark2 : ${Statement.remark2}</li>
						<li>remark3 : ${Statement.remark3}</li>

						<li>businessLicenseYN : ${Statement.businessLicenseYN}</li>
						<li>bankBookYN : ${Statement.bankBookYN}</li>
						<li>smssendYN : ${Statement.smssendYN}</li>
						<li>autoacceptYN : ${Statement.autoacceptYN}</li>
					</ul>
					<fieldset class="fieldset3">
						<legend>detailList</legend>
						<c:forEach items="${Statement.detailList}" var="StatementDetail">
						<legend>SerialNum : ${StatementDetail.serialNum}</legend>
						<ul>
							<li>purchaseDT : ${StatementDetail.purchaseDT}</li>
							<li>itemName : ${StatementDetail.itemName}</li>
							<li>spec : ${StatementDetail.spec}</li>
							<li>qty : ${StatementDetail.qty}</li>
							<li>unitCost : ${StatementDetail.unitCost}</li>
							<li>supplyCost : ${StatementDetail.supplyCost}</li>
							<li>tax : ${StatementDetail.tax}</li>
							<li>remark : ${StatementDetail.remark}</li>
						</ul>
						</c:forEach>
					</fieldset>
					<fieldset class="fieldset3">
					<c:if test="${Statement.propertyBag != null}">
						<legend>propertyBag</legend>
						<ul>
						<c:forEach items="${Statement.propertyBag}" var="propertyBag">
							<li>${propertyBag.key} : ${propertyBag.value}</li>
						</c:forEach>
						</ul>
						</c:if>
					</fieldset>
				</fieldset>
				
				
			</fieldset>
		 </div>
	</body>
</html>