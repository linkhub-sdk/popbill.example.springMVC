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
                    <li>total (전체 검색개수) : ${SearchResult.total}</li>
                    <li>perPage (페이지당 목록개수) : ${SearchResult.perPage}</li>
                    <li>pageNum (페이지번호) : ${SearchResult.pageNum}</li>
                    <li>pageCount (페이지수) : ${SearchResult.pageCount}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${SearchResult.list != null}">
            <c:forEach items="${SearchResult.list}" var="SearchInfo" varStatus="status">
                <fieldset class="fieldset2">
                    <legend>[ ${status.index+1} / ${SearchResult.perPage} ]</legend>
                    <ul>
                        <li>subject (메시지 제목) : ${SearchInfo.subject}</li>
                        <li>content (메시지 내용) : ${SearchInfo.content}</li>
                        <li>sendNum (발신번호) : ${SearchInfo.sendNum}</li>
                        <li>senderName (발신자명) : ${SearchInfo.senderName}</li>
                        <li>receiveNum (수신번호) : ${SearchInfo.receiveNum}</li>
                        <li>receiveName (수신자명) : ${SearchInfo.receiveName}</li>
                        <li>receiptDT (접수일시) : ${SearchInfo.receiptDT}</li>
                        <li>sendDT (전송일시) : ${SearchInfo.sendDT}</li>
                        <li>resultDT (전송결과 수신일시) : ${SearchInfo.resultDT}</li>
                        <li>reserveDT (예약일시) : ${SearchInfo.reserveDT}</li>
                        <li>state (상태코드) : ${SearchInfo.state}</li>
                        <li>result (결과코드) : ${SearchInfo.result}</li>
                        <li>messageType (메시지 유형) : ${SearchInfo.messageType}</li>
                        <li>tranNet (전송처리 이동통신사명) : ${SearchInfo.tranNet}</li>
                        <li>receiptNum (접수번호) : ${SearchInfo.receiptNum}</li>
                        <li>requestNum (요청번호) : ${SearchInfo.requestNum}</li>
                        <li>interOPRefKey (파트너 지정키) : ${SearchInfo.interOPRefKey}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
