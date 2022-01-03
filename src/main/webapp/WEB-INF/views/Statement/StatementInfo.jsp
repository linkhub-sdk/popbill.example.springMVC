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
        <c:if test="${StatementInfo != null}">
            <fieldset class="fieldset2">
                <legend>StatementInfo</legend>
                <ul>
                    <li>itemCode (명세서 코드) : ${StatementInfo.itemCode}</li>
                    <li>itemKey (팝빌번호) : ${StatementInfo.itemKey}</li>
                    <li>invoiceNum (팝빌 승인번호) : ${StatementInfo.invoiceNum}</li>
                    <li>mgtKey (문서번호) : ${StatementInfo.mgtKey}</li>
                    <li>taxType (세금형태) : ${StatementInfo.taxType}</li>
                    <li>writeDate (작성일자) : ${StatementInfo.writeDate}</li>
                    <li>regDT (임시저장일시) : ${StatementInfo.regDT}</li>
                    <li>senderCorpName (발신자 상호) : ${StatementInfo.senderCorpName}</li>
                    <li>senderCorpNum (발신자 사업자등록번호) : ${StatementInfo.senderCorpNum}</li>
                    <li>senderPrintYN (발신자 인쇄여부) : ${StatementInfo.senderPrintYN}</li>
                    <li>receiverCorpName (수신자 상호): ${StatementInfo.receiverCorpName}</li>
                    <li>receiverCorpNum (수신자 사업자등록번호) : ${StatementInfo.receiverCorpNum}</li>
                    <li>receiverPrintYN (수신자 인쇄여부) : ${StatementInfo.receiverPrintYN}</li>
                    <li>supplyCostTotal (공급가액 합계) : ${StatementInfo.supplyCostTotal}</li>
                    <li>taxTotal (세액 합계) : ${StatementInfo.taxTotal}</li>
                    <li>purposeType (영수/청구) : ${StatementInfo.purposeType}</li>
                    <li>issueDT (발행일시) : ${StatementInfo.issueDT}</li>
                    <li>stateCode (상태코드) : ${StatementInfo.stateCode}</li>
                    <li>stateDT (상태 변경일시) : ${StatementInfo.stateDT}</li>
                    <li>stateMemo (상태메모) : ${StatementInfo.stateMemo}</li>
                    <li>openYN (개봉 여부) : ${StatementInfo.openYN}</li>
                    <li>openDT (개봉 일시) : ${StatementInfo.openDT}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${StatementInfos != null}">
            <c:forEach items="${StatementInfos}" var="StatementInfo">
                <fieldset class="fieldset2">
                    <ul>
                        <li>itemCode (명세서 코드) : ${StatementInfo.itemCode}</li>
                        <li>itemKey (팝빌번호) : ${StatementInfo.itemKey}</li>
                        <li>invoiceNum (팝빌 승인번호) : ${StatementInfo.invoiceNum}</li>
                        <li>mgtKey (문서번호) : ${StatementInfo.mgtKey}</li>
                        <li>taxType (세금형태) : ${StatementInfo.taxType}</li>
                        <li>writeDate (작성일자) : ${StatementInfo.writeDate}</li>
                        <li>regDT (임시저장일시) : ${StatementInfo.regDT}</li>
                        <li>senderCorpName (발신자 상호) : ${StatementInfo.senderCorpName}</li>
                        <li>senderCorpNum (발신자 사업자등록번호) : ${StatementInfo.senderCorpNum}</li>
                        <li>senderPrintYN (발신자 인쇄여부) : ${StatementInfo.senderPrintYN}</li>
                        <li>receiverCorpName (수신자 상호): ${StatementInfo.receiverCorpName}</li>
                        <li>receiverCorpNum (수신자 사업자등록번호) : ${StatementInfo.receiverCorpNum}</li>
                        <li>receiverPrintYN (수신자 인쇄여부) : ${StatementInfo.receiverPrintYN}</li>
                        <li>supplyCostTotal (공급가액 합계) : ${StatementInfo.supplyCostTotal}</li>
                        <li>taxTotal (세액 합계) : ${StatementInfo.taxTotal}</li>
                        <li>purposeType (영수/청구) : ${StatementInfo.purposeType}</li>
                        <li>issueDT (발행일시) : ${StatementInfo.issueDT}</li>
                        <li>stateCode (상태코드) : ${StatementInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${StatementInfo.stateDT}</li>
                        <li>stateMemo (상태메모) : ${StatementInfo.stateMemo}</li>
                        <li>openYN (개봉 여부) : ${StatementInfo.openYN}</li>
                        <li>openDT (개봉 일시) : ${StatementInfo.openDT}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
