<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
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
                <li>mgtKey (관리번호) : ${Cashbill.mgtKey}</li>
                <li>confirmNum (국세청 승인번호) : ${Cashbill.confirmNum}</li>
                <li>orgConfirmNum (원본 현금영수증 국세청승인번호) : ${Cashbill.orgConfirmNum}</li>
                <li>orgTradeDate (원본 현금영수증 거래일자) : ${Cashbill.orgTradeDate}</li>
                <li>tradeDate (거래일자) : ${Cashbill.tradeDate}</li>
                <li>tradeType (문서형태) : ${Cashbill.tradeType}</li>
                <li>tradeUsage (거래구분) : ${CashbillInfo.tradeUsage}</li>
                <li>tradeOpt (거래유형) : ${CashbillInfo.tradeOpt}</li>
                <li>taxationType (과세형태) : ${Cashbill.taxationType}</li>
                <li>totalAmount (거래금액) : ${Cashbill.totalAmount}</li>
                <li>supplyCost (공급가액) : ${Cashbill.supplyCost}</li>
                <li>tax (부가세) : ${Cashbill.tax}</li>
                <li>serviceFee (봉사료) : ${Cashbill.serviceFee}</li>
                <li>franchiseCorpNum (가맹점 사업자번호) : ${Cashbill.franchiseCorpNum}</li>
                <li>franchiseCorpName (가맹점 상호) : ${Cashbill.franchiseCorpName}</li>
                <li>franchiseCEOName (가맹점 대표자성명) : ${Cashbill.franchiseCEOName}</li>
                <li>franchiseAddr (가맹점 주소) : ${Cashbill.franchiseAddr}</li>
                <li>franchiseTEL (가맹점 전화번호) : ${Cashbill.franchiseTEL}</li>
                <li>identityNum (거래처 식별번호) : ${Cashbill.identityNum}</li>
                <li>customerName (거래처 고객명) : ${Cashbill.customerName}</li>
                <li>itemName (거래처 상품명) : ${Cashbill.itemName}</li>
                <li>orderNumber (거래처 주문번호) : ${Cashbill.orderNumber}</li>
                <li>email (거래처 이메일) : ${Cashbill.email}</li>
                <li>hp (거래처 휴대폰) : ${Cashbill.hp}</li>
                <li>smssendYN (발행시 안내문자 전송여부) : ${Cashbill.smssendYN}</li>
                <li>cancelType (취소사유) : ${Cashbill.cancelType}</li>
            </ul>
        </fieldset>
    </fieldset>
</div>
</body>
</html>