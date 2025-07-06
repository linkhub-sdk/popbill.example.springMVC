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
        <c:if test="${CashbillInfo != null}">
            <fieldset class="fieldset2">
                <legend>CashbillInfo</legend>
                <ul>
                    <li>itemKey (팝빌번호) : ${CashbillInfo.itemKey}</li>
                    <li>mgtKey (문서번호) : ${CashbillInfo.mgtKey}</li>
                    <li>tradeDate (거래일자) : ${CashbillInfo.tradeDate}</li>
                    <li>tradeDT (거래일시) : ${CashbillInfo.tradeDT}</li>
                    <li>tradeType (문서형태) : ${CashbillInfo.tradeType}</li>
                    <li>tradeUsage (거래구분) : ${CashbillInfo.tradeUsage}</li>
                    <li>tradeOpt (거래유형) : ${CashbillInfo.tradeOpt}</li>
                    <li>taxationType (과세형태) : ${CashbillInfo.taxationType}</li>
                    <li>totalAmount (거래금액) : ${CashbillInfo.totalAmount}</li>
                    <li>supplyCost (공급가액) : ${CashbillInfo.supplyCost}</li>
                    <li>tax (부가세) : ${CashbillInfo.tax}</li>
                    <li>serviceFee (봉사료) : ${CashbillInfo.serviceFee}</li>
                    <li>issueDT (발행일시) : ${CashbillInfo.issueDT}</li>
                    <li>regDT (등록일시) : ${CashbillInfo.regDT}</li>
                    <li>stateCode (상태코드) : ${CashbillInfo.stateCode}</li>
                    <li>stateDT (상태 변경일시) : ${CashbillInfo.stateDT}</li>
                    <li>identityNum (식별번호) : ${CashbillInfo.identityNum}</li>
                    <li>itemName (주문상품명) : ${CashbillInfo.itemName}</li>
                    <li>orderNumber (주문번호) : ${CashbillInfo.orderNumber}</li>
                    <li>email (구매자 이메일) : ${CashbillInfo.email}</li>
                    <li>hp (구매자 휴대폰) : ${CashbillInfo.hp}</li>
                    <li>customerName (구매자(고객) 성명) : ${CashbillInfo.customerName}</li>
                    <li>confirmNum (국세청승인번호) : ${CashbillInfo.confirmNum}</li>
                    <li>orgConfirmNum (당초 국세청승인번호) : ${CashbillInfo.orgConfirmNum}</li>
                    <li>orgTradeDate (당초 거래일자) : ${CashbillInfo.orgTradeDate}</li>
                    <li>ntssendDT (국세청 전송일시) : ${CashbillInfo.ntssendDT}</li>
                    <li>ntsresultDT (국세청 처리결과 수신일시) : ${CashbillInfo.ntsresultDT}</li>
                    <li>ntsresultCode (결과코드) : ${CashbillInfo.ntsresultCode}</li>
                    <li>ntsresultMessage (결과메시지) : ${CashbillInfo.ntsresultMessage}</li>
                    <li>printYN (인쇄여부) : ${CashbillInfo.printYN}</li>
                    <li>interOPYN (연동문서 여부) : ${CashbillInfo.interOPYN}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${CashbillInfos != null}">
            <c:forEach items="${CashbillInfos}" var="CashbillInfo">
                <fieldset class="fieldset2">
                    <legend>CashbillInfo : ${CashbillInfo.mgtKey}</legend>
                    <ul>
                        <li>itemKey (팝빌번호) : ${CashbillInfo.itemKey}</li>
                        <li>mgtKey (문서번호) : ${CashbillInfo.mgtKey}</li>
                        <li>tradeDate (거래일자) : ${CashbillInfo.tradeDate}</li>
                        <li>tradeDT (거래일시) : ${CashbillInfo.tradeDT}</li>
                        <li>tradeType (문서형태) : ${CashbillInfo.tradeType}</li>
                        <li>tradeUsage (거래구분) : ${CashbillInfo.tradeUsage}</li>
                        <li>tradeOpt (거래유형) : ${CashbillInfo.tradeOpt}</li>
                        <li>taxationType (과세형태) : ${CashbillInfo.taxationType}</li>
                        <li>totalAmount (거래금액) : ${CashbillInfo.totalAmount}</li>
                        <li>supplyCost (공급가액) : ${CashbillInfo.supplyCost}</li>
                        <li>tax (부가세) : ${CashbillInfo.tax}</li>
                        <li>serviceFee (봉사료) : ${CashbillInfo.serviceFee}</li>
                        <li>issueDT (발행일시) : ${CashbillInfo.issueDT}</li>
                        <li>regDT (등록일시) : ${CashbillInfo.regDT}</li>
                        <li>stateCode (상태코드) : ${CashbillInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${CashbillInfo.stateDT}</li>
                        <li>identityNum (식별번호) : ${CashbillInfo.identityNum}</li>
                        <li>itemName (주문상품명) : ${CashbillInfo.itemName}</li>
                        <li>orderNumber (주문번호) : ${CashbillInfo.orderNumber}</li>
                        <li>email (구매자 이메일) : ${CashbillInfo.email}</li>
                        <li>hp (구매자 휴대폰) : ${CashbillInfo.hp}</li>
                        <li>customerName (구매자(고객) 성명) : ${CashbillInfo.customerName}</li>
                        <li>confirmNum (국세청승인번호) : ${CashbillInfo.confirmNum}</li>
                        <li>orgConfirmNum (당초 국세청승인번호) : ${CashbillInfo.orgConfirmNum}</li>
                        <li>orgTradeDate (당초 거래일자) : ${CashbillInfo.orgTradeDate}</li>
                        <li>ntssendDT (국세청 전송일시) : ${CashbillInfo.ntssendDT}</li>
                        <li>ntsresultDT (국세청 처리결과 수신일시) : ${CashbillInfo.ntsresultDT}</li>
                        <li>ntsresultCode (결과코드) : ${CashbillInfo.ntsresultCode}</li>
                        <li>ntsresultMessage (결과메시지) : ${CashbillInfo.ntsresultMessage}</li>
                        <li>printYN (인쇄여부) : ${CashbillInfo.printYN}</li>
                        <li>interOPYN (연동문서 여부) : ${CashbillInfo.interOPYN}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>