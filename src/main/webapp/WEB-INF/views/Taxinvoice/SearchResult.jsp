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
                    <li>code (응답코드) : ${SearchResult.code}</li>
                    <li>message (응답메시지) : ${SearchResult.message}</li>
                    <li>total (총 검색결과 건수) : ${SearchResult.total}</li>
                    <li>perPage (페이지당 목록 건수) : ${SearchResult.perPage}</li>
                    <li>pageNum (페이지 번호) : ${SearchResult.pageNum}</li>
                    <li>pageCount (페이지 개수) : ${SearchResult.pageCount}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${SearchResult.list != null}">
            <c:forEach items="${SearchResult.list}" var="SearchInfo" varStatus="status">
                <fieldset class="fieldset2">
                    <legend>세금계산서 상태/요약정보 [ ${status.index+1} / ${SearchResult.perPage} ]</legend>
                    <ul>
                        <li>itemKey (팝빌에서 할당한 식별번호) : ${SearchInfo.itemKey}</li>
                        <li>taxType (과세형태) : ${SearchInfo.taxType}</li>
                        <li>writeDate (작성일자) : ${SearchInfo.writeDate}</li>
                        <li>regDT (임시저장 일시) : ${SearchInfo.regDT}</li>
                        <li>issueType (발행형태) : ${SearchInfo.issueType}</li>
                        <li>supplyCostTotal (공급가액 합계) : ${SearchInfo.supplyCostTotal}</li>
                        <li>taxTotal (세액 합계) : ${SearchInfo.taxTotal}</li>
                        <li>purposeType (영수/청구) : ${SearchInfo.purposeType}</li>
                        <li>issueDT (발행일시) : ${SearchInfo.issueDT}</li>
                        <li>lateIssueYN (지연발행 여부) : ${SearchInfo.lateIssueYN}</li>
                        <li>openYN (개봉 여부) : ${SearchInfo.openYN}</li>
                        <li>openDT (개봉일시) : ${SearchInfo.openDT}</li>
                        <li>stateMemo (상태메모) : ${SearchInfo.stateMemo}</li>
                        <li>stateCode (상태코드) : ${SearchInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${SearchInfo.stateDT}</li>
                        <li>ntsconfirmNum (국세청승인번호) : ${SearchInfo.NTSConfirmNum}</li>
                        <li>ntsresult (국세청 전송결과) : ${SearchInfo.NTSResult}</li>
                        <li>ntssendDT (국세청 전송일시) : ${SearchInfo.NTSSendDT}</li>
                        <li>ntsresultDT (국세청 결과 수신일시) : ${SearchInfo.NTSResultDT}</li>
                        <li>ntssendErrCode (결과코드) : ${SearchInfo.NTSSendErrCode}</li>
                        <li>modifyCode (수정세금계산서 발행시 입력한 사유코드) : ${SearchInfo.modifyCode}</li>
                        <li>interOPYN (연동문서 여부) : ${SearchInfo.interOPYN}</li>
                        <li>invoicerCorpName (공급자 상호) : ${SearchInfo.invoicerCorpName}</li>
                        <li>invoicerCorpNum (공급자 사업자번호) : ${SearchInfo.invoicerCorpNum}</li>
                        <li>invoicerMgtKey (공급자 문서번호) : ${SearchInfo.invoicerMgtKey}</li>
                        <li>invoicerPrintYN (공급자 인쇄여부) : ${SearchInfo.invoicerPrintYN}</li>
                        <li>invoiceeCorpName (공급받는자 상호) : ${SearchInfo.invoiceeCorpName}</li>
                        <li>invoiceeCorpNum (공급받는자 사업자번호) : ${SearchInfo.invoiceeCorpNum}</li>
                        <li>invoiceeMgtKey (공급받는자 문서번호) : ${SearchInfo.invoiceeMgtKey}</li>
                        <li>invoiceePrintYN (공급받는자 인쇄여부) : ${SearchInfo.invoiceePrintYN}</li>
                        <li>closeDownState (공급받는자 휴폐업상태) : ${SearchInfo.closeDownState}</li>
                        <li>closeDownStateDate (공급받는자 휴폐업일자) : ${SearchInfo.closeDownStateDate}</li>
                        <li>trusteeCorpName (수탁자 상호) : ${SearchInfo.trusteeCorpName}</li>
                        <li>trusteeCorpNum (수탁자 사업자번호) : ${SearchInfo.trusteeCorpNum}</li>
                        <li>trusteeMgtKey (수탁자 문서번호) : ${SearchInfo.trusteeMgtKey}</li>
                        <li>trusteePrintYN (수탁자 인쇄여부) : ${SearchInfo.trusteePrintYN}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
