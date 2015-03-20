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
					<legend>Cashbill</legend>
					<ul>
						<li>mgtKey : ${Cashbill.mgtKey}</li>
						<li>tradeDate : ${Cashbill.tradeDate}</li>
						<li>tradeUsage : ${Cashbill.tradeUsage}</li>
						<li>tradeType : ${Cashbill.tradeType}</li>
						
						<li>taxationType : ${Cashbill.taxationType}</li>
						<li>supplyCost : ${Cashbill.supplyCost}</li>
						<li>tax : ${Cashbill.tax}</li>
						<li>serviceFee : ${Cashbill.serviceFee}</li>
						<li>totalAmount : ${Cashbill.totalAmount}</li>
						
						<li>franchiseCorpNum : ${Cashbill.franchiseCorpNum}</li>
						<li>franchiseCorpName : ${Cashbill.franchiseCorpName}</li>
						<li>franchiseCEOName : ${Cashbill.franchiseCEOName}</li>
						<li>franchiseAddr : ${Cashbill.franchiseAddr}</li>
						<li>franchiseTEL : ${Cashbill.franchiseTEL}</li>

						<li>identityNum : ${Cashbill.identityNum}</li>
						<li>customerName : ${Cashbill.customerName}</li>
						<li>itemName : ${Cashbill.itemName}</li>
						<li>orderNumber : ${Cashbill.orderNumber}</li>

						<li>email : ${Cashbill.email}</li>
						<li>hp : ${Cashbill.hp}</li>
						<li>fax : ${Cashbill.fax}</li>
						<li>faxsendYN : ${Cashbill.faxsendYN}</li>
						<li>smssendYN : ${Cashbill.smssendYN}</li>

						<li>confirmNum : ${Cashbill.confirmNum}</li>
						<li>orgConfirmNum : ${Cashbill.orgConfirmNum}</li>
					</ul>
					</fieldset>
				</fieldset>
		 </div>
	</body>
</html>