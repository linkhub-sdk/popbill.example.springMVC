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
						<li>mgtKey (파트너 관리번호) : ${Cashbill.mgtKey}</li>
						<li>tradeDate (거래일자) : ${Cashbill.tradeDate}</li>
						<li>tradeUsage (거래용도) : ${Cashbill.tradeUsage}</li>
						<li>tradeType (현금영수증 형태) : ${Cashbill.tradeType}</li>
						
						<li>taxationType (과세형태) : ${Cashbill.taxationType}</li>
						<li>supplyCost (공급가액) : ${Cashbill.supplyCost}</li>
						<li>tax (세액) : ${Cashbill.tax}</li>
						<li>serviceFee (봉사료) : ${Cashbill.serviceFee}</li>
						<li>totalAmount (거래금액) : ${Cashbill.totalAmount}</li>
						
						<li>franchiseCorpNum (발행자 사업자번호) : ${Cashbill.franchiseCorpNum}</li>
						<li>franchiseCorpName (발행자 상호) : ${Cashbill.franchiseCorpName}</li>
						<li>franchiseCEOName (발행자 대표자성명) : ${Cashbill.franchiseCEOName}</li>
						<li>franchiseAddr (가맹점 주소) : ${Cashbill.franchiseAddr}</li>
						<li>franchiseTEL (가맹점 전화번호) : ${Cashbill.franchiseTEL}</li>

						<li>confirmNum (현금영수증 승인번호) : ${Cashbill.confirmNum}</li>
						<li>identityNum (거래처 식별번호) : ${Cashbill.identityNum}</li>
						<li>customerName (고객명) : ${Cashbill.customerName}</li>
						<li>itemName (상품명) : ${Cashbill.itemName}</li>
						<li>orderNumber (가맹점 주문번호) : ${Cashbill.orderNumber}</li>
						<li>email (이메일) : ${Cashbill.email}</li>
						<li>hp (휴대폰) : ${Cashbill.hp}</li>
						<li>smssendYN (발행시 안내문자 전송여부) : ${Cashbill.smssendYN}</li>
						<li>orgConfirmNum (원본 현금영수증 승인번호) : ${Cashbill.orgConfirmNum}</li>
						<li>orgTradeDate (원본 현금영수증 거래일자) : ${Cashbill.orgTradeDate}</li>
					</ul>
					</fieldset>
				</fieldset>
		 </div>
	</body>
</html>