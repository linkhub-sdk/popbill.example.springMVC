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
						<li>itemKey (팝빌 관리번호) : ${CashbillInfo.itemKey}</li>
						<li>mgtKey (파트너 관리번호) : ${CashbillInfo.mgtKey}</li>
						<li>tradeDate (거래일자) : ${CashbillInfo.tradeDate}</li>
						<li>issueDT (발행일시) : ${CashbillInfo.issueDT}</li>
						<li>customerName (고객명) : ${CashbillInfo.customerName}</li>
						<li>itemName (상품명) : ${CashbillInfo.itemName}</li>
						<li>identityNum (거래처 식별번호) : ${CashbillInfo.identityNum}</li>
						<li>taxationType (과세형태) : ${CashbillInfo.taxationType}</li>
						<li>totalAmount (거래금액) : ${CashbillInfo.totalAmount}</li>
						<li>tradeUsage (거래용도) : ${CashbillInfo.tradeUsage}</li>
						<li>tradeType (현금영수증 형태) : ${CashbillInfo.tradeType}</li>
						<li>stateCode (상태코드) : ${CashbillInfo.stateCode}</li>
						<li>stateDT (상태변경일시) : ${CashbillInfo.stateDT}</li>
						<li>printYN (인쇄여부) : ${CashbillInfo.printYN}</li>
						<li>regDT (등록일시) : ${CashbillInfo.regDT}</li>
							
						<li>ntssendDT (국세청 전송일시) : ${CashbillInfo.ntssendDT}</li>
						<li>ntsresultDT (국세청 처리결과 수신일시) : ${CashbillInfo.ntsresultDT}</li>
						<li>ntsresultCode (국세청 처리결과 상태코드) : ${CashbillInfo.ntsresultCode}</li>
						<li>ntsresultMessage (국세청 처리결과 메시지) : ${CashbillInfo.ntsresultMessage}</li>
						
						<li>orgConfirmNum (원본 현금영수증 승인번호) : ${CashbillInfo.orgConfirmNum}</li>
						<li>orgTradeDate (원본 현금영수증 거래일자) : ${CashbillInfo.orgTradeDate}</li>
					</ul>
				</fieldset>
				</c:if>
				
				<c:if test="${CashbillInfos != null}">
				<c:forEach items="${CashbillInfos}" var="CashbillInfo">
				<fieldset class="fieldset2">
					<legend>CashbillInfo : ${CashbillInfo.mgtKey}</legend>
					<ul>
						<li>itemKey (팝빌 관리번호) : ${CashbillInfo.itemKey}</li>
						<li>mgtKey (파트너 관리번호) : ${CashbillInfo.mgtKey}</li>
						<li>tradeDate (거래일자) : ${CashbillInfo.tradeDate}</li>
						<li>issueDT (발행일시) : ${CashbillInfo.issueDT}</li>
						<li>customerName (고객명) : ${CashbillInfo.customerName}</li>
						<li>itemName (상품명) : ${CashbillInfo.itemName}</li>
						<li>identityNum (거래처 식별번호) : ${CashbillInfo.identityNum}</li>
						<li>taxationType (과세형태) : ${CashbillInfo.taxationType}</li>
						<li>totalAmount (거래금액) : ${CashbillInfo.totalAmount}</li>
						<li>tradeUsage (거래용도) : ${CashbillInfo.tradeUsage}</li>
						<li>tradeType (현금영수증 형태) : ${CashbillInfo.tradeType}</li>
						<li>stateCode (상태코드) : ${CashbillInfo.stateCode}</li>
						<li>stateDT (상태변경일시) : ${CashbillInfo.stateDT}</li>
						<li>printYN (인쇄여부) : ${CashbillInfo.printYN}</li>
						<li>regDT (등록일시) : ${CashbillInfo.regDT}</li>
						
						<li>ntssendDT (국세청 전송일시) : ${CashbillInfo.ntssendDT}</li>
						<li>ntsresultDT (국세청 처리결과 수신일시) : ${CashbillInfo.ntsresultDT}</li>
						<li>ntsresultCode (국세청 처리결과 상태코드) : ${CashbillInfo.ntsresultCode}</li>
						<li>ntsresultMessage (국세청 처리결과 메시지) : ${CashbillInfo.ntsresultMessage}</li>
						
						<li>orgConfirmNum (원본 현금영수증 승인번호) : ${CashbillInfo.orgConfirmNum}</li>
						<li>orgTradeDate (원본 현금영수증 거래일자) : ${CashbillInfo.orgTradeDate}</li>
					</ul>
				</fieldset>
				</c:forEach>
				</c:if>
			</fieldset>
		 </div>
	</body>
</html>
