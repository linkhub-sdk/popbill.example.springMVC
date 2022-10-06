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
        <c:if test="${BulkResult != null}">
            <fieldset class="fieldset2">
                <legend>대량발행 접수결과 </legend>
                <ul>
                    <li>code (응답코드) : ${BulkResult.code}</li>
                    <li>message (응답 메시지) : ${BulkResult.message}</li>
                    <li>submitID (제출아이디) : ${BulkResult.submitID}</li>
                    <li>submitCount (접수건수) : ${BulkResult.successCount}</li>
                    <li>successCount (발행 성공건수) : ${BulkResult.successCount}</li>
                    <li>failCount (발행 실패건수) : ${BulkResult.failCount}</li>
                    <li>txState (접수 상태코드) : ${BulkResult.txState}</li>
                    <li>txResultCode (접수 결과코드) : ${BulkResult.txResultCode}</li>
                    <li>txStartDT (발행처리 시작일시) : ${BulkResult.txStartDT}</li>
                    <li>txEndDT (발행처리 완료일시) : ${BulkResult.txEndDT}</li>
                    <li>receiptID (접수아이디) : ${BulkResult.receiptID}</li>
                    <li>receiptDT (접수일시) : ${BulkResult.receiptDT}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${BulkResult.issueResult != null}">
            <c:forEach items="${BulkResult.issueResult}" var="CashbillInfo" varStatus="status">
                <fieldset class="fieldset2">
                    <legend>issueResult[${status.index}]</legend>
                    <ul>
                        <li>code (응답코드) : ${CashbillInfo.code}</li>
                        <li>message (응답메시지) : ${CashbillInfo.message}</li>
                        <li>mgtKey (문서번호) : ${CashbillInfo.mgtKey}</li>
                        <li>confirmNum (국세청 승인번호) : ${CashbillInfo.confirmNum}</li>
                        <li>tradeDate (거래일자) : ${CashbillInfo.tradeDate}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>

    </fieldset>
</div>
</body>
</html>
