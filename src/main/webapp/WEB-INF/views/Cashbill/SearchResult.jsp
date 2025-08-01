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
        <c:if test="${SearchResult != null}">
            <fieldset class="fieldset2">
                <legend>검색결과 정보</legend>
                <ul>
                    <li> code (응답코드) : ${SearchResult.code}</li>
                    <li> message (응답 메시지) : ${SearchResult.message}</li>
                    <li> total (총 검색결과 건수) : ${SearchResult.total}</li>
                    <li> perPage (페이지당 목록 건수) : ${SearchResult.perPage}</li>
                    <li> pageNum (페이지 번호) : ${SearchResult.pageNum}</li>
                    <li> pageCount (페이지 개수) : ${SearchResult.pageCount}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${SearchResult.list != null}">
            <c:forEach items="${SearchResult.list}" var="SearchInfo" varStatus="status">
                <fieldset class="fieldset2">
                    <legend>현금영수증 상태/요약정보 [ ${status.index+1} / ${SearchResult.perPage} ]</legend>
                    <ul>
                        <li>itemKey (팝빌에서 현금영수증 관리 목적으로 할당한 식별번호) : ${SearchInfo.itemKey}</li>
                        <li>mgtKey (문서번호) : ${SearchInfo.mgtKey}</li>
                        <li>tradeDate (거래일자) : ${SearchInfo.tradeDate}</li>
                        <li>tradeDT (거래일시) : ${SearchInfo.tradeDT}</li>
                        <li>tradeType (문서형태) : ${SearchInfo.tradeType}</li>
                        <li>tradeUsage (거래구분) : ${SearchInfo.tradeUsage}</li>
                        <li>tradeOpt (거래유형) : ${SearchInfo.tradeOpt}</li>
                        <li>taxationType (과세형태) : ${SearchInfo.taxationType}</li>
                        <li>totalAmount (거래금액) : ${SearchInfo.totalAmount}</li>
                        <li>supplyCost (공급가액) : ${SearchInfo.supplyCost}</li>
                        <li>tax (부가세) : ${SearchInfo.tax}</li>
                        <li>serviceFee (봉사료) : ${SearchInfo.serviceFee}</li>
                        <li>issueDT (발행일시) : ${SearchInfo.issueDT}</li>
                        <li>regDT (등록일시) : ${SearchInfo.regDT}</li>
                        <li>stateCode (상태코드) : ${SearchInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${SearchInfo.stateDT}</li>
                        <li>identityNum (식별번호) : ${SearchInfo.identityNum}</li>
                        <li>itemName (주문상품명) : ${SearchInfo.itemName}</li>
                        <li>orderNumber (주문번호) : ${SearchInfo.orderNumber}</li>
                        <li>email (구매자 이메일) : ${SearchInfo.email}</li>
                        <li>hp (구매자 휴대폰) : ${SearchInfo.hp}</li>
                        <li>customerName (구매자(고객) 성명) : ${SearchInfo.customerName}</li>
                        <li>confirmNum (국세청승인번호) : ${SearchInfo.confirmNum}</li>
                        <li>orgConfirmNum (당초 국세청승인번호) : ${SearchInfo.orgConfirmNum}</li>
                        <li>orgTradeDate (당초 거래일자) : ${SearchInfo.orgTradeDate}</li>
                        <li>ntssendDT (국세청 전송일시) : ${SearchInfo.ntssendDT}</li>
                        <li>ntsresultDT (국세청 처리결과 수신일시) : ${SearchInfo.ntsresultDT}</li>
                        <li>ntsresultCode (국세청 결과코드) : ${SearchInfo.ntsresultCode}</li>
                        <li>ntsresultMessage (국세청 결과메시지) : ${SearchInfo.ntsresultMessage}</li>
                        <li>printYN (인쇄여부) : ${SearchInfo.printYN}</li>
                        <li>interOPYN (연동문서 여부) : ${SearchInfo.interOPYN}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>