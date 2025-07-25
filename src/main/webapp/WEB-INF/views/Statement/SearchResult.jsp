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
                    <li>message (응답 메시지) : ${SearchResult.message}</li>
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
                    <legend>전자명세서 상태/요약정보 [ ${status.index+1} / ${SearchResult.perPage} ]</legend>
                    <ul>
                        <li>itemCode (전자명세서 문서 유형) : ${SearchInfo.itemCode}</li>
                        <li>itemKey (팝빌번호) : ${SearchInfo.itemKey}</li>
                        <li>invoiceNum (팝빌 승인번호) : ${SearchInfo.invoiceNum}</li>
                        <li>mgtKey (파트너가 할당한 문서번호) : ${SearchInfo.mgtKey}</li>
                        <li>taxType (과세형태) : ${SearchInfo.taxType}</li>
                        <li>writeDate (작성일자) : ${SearchInfo.writeDate}</li>
                        <li>regDT (임시저장일시) : ${SearchInfo.regDT}</li>
                        <li>senderCorpName (발신자 상호) : ${SearchInfo.senderCorpName}</li>
                        <li>senderCorpNum (발신자 사업자번호) : ${SearchInfo.senderCorpNum}</li>
                        <li>senderPrintYN (발신자 인쇄여부) : ${SearchInfo.senderPrintYN}</li>
                        <li>receiverCorpName (수신자 상호) : ${SearchInfo.receiverCorpName}</li>
                        <li>receiverCorpNum (수신자 사업자번호) : ${SearchInfo.receiverCorpNum}</li>
                        <li>receiverPrintYN (수신자 인쇄여부) : ${SearchInfo.receiverPrintYN}</li>
                        <li>supplyCostTotal (공급가액 합계) : ${SearchInfo.supplyCostTotal}</li>
                        <li>taxTotal (세액 합계) : ${SearchInfo.taxTotal}</li>
                        <li>purposeType (영수/청구) : ${SearchInfo.purposeType}</li>
                        <li>issueDT (발행일시) : ${SearchInfo.issueDT}</li>
                        <li>stateCode (상태코드) : ${SearchInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${SearchInfo.stateDT}</li>
                        <li>stateMemo (상태메모) : ${SearchInfo.stateMemo}</li>
                        <li>openYN (개봉여부) : ${SearchInfo.openYN}</li>
                        <li>openDT (개봉 일시) : ${SearchInfo.openDT}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
