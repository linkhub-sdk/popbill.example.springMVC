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

        <fieldset class="fieldset2">
            <legend>전송결과 정보</legend>
            <c:if test="${SearchResult.list != null}">
                <c:forEach items="${SearchResult.list}" var="msgInfo" varStatus="status">
                    <fieldset class="fieldset3">
                        <legend>개별전송 정보 [ ${status.index+1} ]</legend>
                        <ul>
                            <li>state (전송상태 코드) : ${msgInfo.state}</li>
                            <li>sendDT (전송일시) : ${msgInfo.sendDT}</li>
                            <li>receiveNum (수신번호) : ${msgInfo.receiveNum}</li>
                            <li>receiveName (수신자명) : ${msgInfo.receiveName}</li>
                            <li>content (알림톡/친구톡 내용) : ${msgInfo.content}</li>
                            <li>result (알림톡/친구톡 전송결과 코드) : ${msgInfo.result}</li>
                            <li>resultDT (알림톡/친구톡 전송결과 수신일시) : ${msgInfo.resultDT}</li>
                            <li>altSubject (대체문자 제목) : ${msgInfo.altSubject}</li>
                            <li>altContent (대체문자 내용) : ${msgInfo.altContent}</li>
                            <li>altContentType (대체문자 전송유형) : ${msgInfo.altContentType}</li>
                            <li>altSendDT (대체문자 전송일시) : ${msgInfo.altSendDT}</li>
                            <li>altResult (대체문자 전송결과 코드) : ${msgInfo.altResult}</li>
                            <li>altResultDT (대체문자 전송결과 수신일시) : ${msgInfo.altResultDT}</li>
                            <li>receiptNum (접수번호) : ${msgInfo.receiptNum}</li>
                            <li>requestNum (요청번호) : ${msgInfo.requestNum}</li>
                        </ul>
                    </fieldset>
                </c:forEach>
            </c:if>
        </fieldset>
    </fieldset>
</div>
</body>
</html>
