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
				<c:if test="${CashbillInfo != null}">
				<fieldset class="fieldset2">
					<legend>CashbillInfo</legend>
					<ul>
						<li>itemKey : ${CashbillInfo.itemKey}</li>
						<li>mgtKey : ${CashbillInfo.mgtKey}</li>
						<li>tradeDate : ${CashbillInfo.tradeDate}</li>
						<li>issueDT : ${CashbillInfo.issueDT}</li>
						<li>customerName : ${CashbillInfo.customerName}</li>
						<li>itemName : ${CashbillInfo.itemName}</li>
						<li>identityNum : ${CashbillInfo.identityNum}</li>
						<li>taxationType : ${CashbillInfo.taxationType}</li>
						<li>totalAmount : ${CashbillInfo.totalAmount}</li>
						<li>tradeUsage : ${CashbillInfo.tradeUsage}</li>
						<li>tradeType : ${CashbillInfo.tradeType}</li>
						<li>stateCode : ${CashbillInfo.stateCode}</li>
						<li>stateDT : ${CashbillInfo.stateDT}</li>
						<li>printYN : ${CashbillInfo.printYN}</li>
						<li>confirmNum : ${CashbillInfo.confirmNum}</li>
						<li>orgTradeDate : ${CashbillInfo.orgTradeDate}</li>
						<li>orgConfirmNum : ${CashbillInfo.orgConfirmNum}</li>
						<li>ntssendDT : ${CashbillInfo.ntssendDT}</li>
						<li>ntsresult : ${CashbillInfo.ntsresult}</li>
						<li>ntsresultDT : ${CashbillInfo.ntsresultDT}</li>
						<li>ntsresultCode : ${CashbillInfo.ntsresultCode}</li>
						<li>ntsresultMessage : ${CashbillInfo.ntsresultMessage}</li>
						<li>regDT : ${CashbillInfo.regDT}</li>
					</ul>
				</fieldset>
				</c:if>
				
				<c:if test="${CashbillInfos != null}">
				<c:forEach items="${CashbillInfos}" var="CashbillInfo">
				<fieldset class="fieldset2">
					<legend>CashbillInfo : ${CashbillInfo.mgtKey}</legend>
					<ul>
						<li>itemKey : ${CashbillInfo.itemKey}</li>
						<li>mgtKey : ${CashbillInfo.mgtKey}</li>
						<li>tradeDate : ${CashbillInfo.tradeDate}</li>
						<li>issueDT : ${CashbillInfo.issueDT}</li>
						<li>customerName : ${CashbillInfo.customerName}</li>
						<li>itemName : ${CashbillInfo.itemName}</li>
						<li>identityNum : ${CashbillInfo.identityNum}</li>
						<li>taxationType : ${CashbillInfo.taxationType}</li>
						<li>totalAmount : ${CashbillInfo.totalAmount}</li>
						<li>tradeUsage : ${CashbillInfo.tradeUsage}</li>
						<li>tradeType : ${CashbillInfo.tradeType}</li>
						<li>stateCode : ${CashbillInfo.stateCode}</li>
						<li>stateDT : ${CashbillInfo.stateDT}</li>
						<li>printYN : ${CashbillInfo.printYN}</li>
						<li>confirmNum : ${CashbillInfo.confirmNum}</li>
						<li>orgTradeDate : ${CashbillInfo.orgTradeDate}</li>
						<li>orgConfirmNum : ${CashbillInfo.orgConfirmNum}</li>
						<li>ntssendDT : ${CashbillInfo.ntssendDT}</li>
						<li>ntsresult : ${CashbillInfo.ntsresult}</li>
						<li>ntsresultDT : ${CashbillInfo.ntsresultDT}</li>
						<li>ntsresultCode : ${CashbillInfo.ntsresultCode}</li>
						<li>ntsresultMessage : ${CashbillInfo.ntsresultMessage}</li>
						<li>regDT : ${CashbillInfo.regDT}</li>
										
					</ul>
				</fieldset>
				</c:forEach>
				</c:if>
			</fieldset>
		 </div>
	</body>
</html>
