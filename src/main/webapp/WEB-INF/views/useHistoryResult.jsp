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
    <p class="heading1">UseHistoryResult</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <c:if test="${UseHistoryResult != null}">
            <fieldset class="fieldset2">
                <legend>검색결과 정보</legend>
                <ul>
                    <li>code (응답코드) : ${UseHistoryResult.code}</li>
                    <li>total (총 검색결과 건수) : ${UseHistoryResult.total}</li>
                    <li>perPage (페이지당 목록 건수) : ${UseHistoryResult.perPage}</li>
                    <li>pageNum (페이지 번호) : ${UseHistoryResult.pageNum}</li>
                    <li>pageCount (페이지 게수) : ${UseHistoryResult.pageCount}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${UseHistoryResult.list != null}">
            <c:forEach items="${UseHistoryResult.list}" var="UseHistory" varStatus="status">
                <fieldset class="fieldset2">
                    <legend>[ ${status.index+1} / ${UseHistoryResult.perPage} ]</legend>
                    <ul>
                        <li>itemCode (서비스 코드) : ${UseHistory.itemCode}</li>
                        <li>txType (포인트 증감 유형) : ${UseHistory.txType}</li>
                        <li>txPoint (증감 포인트) : ${UseHistory.txPoint}</li>
                        <li>balance (잔여포인트) : ${UseHistory.balance}</li>
                        <li>txDT (포인트 증감 일시) : ${UseHistory.txDT}</li>
                        <li>userID (담당자 아이디) : ${UseHistory.userID}</li>
                        <li>userName (담당자명) : ${UseHistory.userName}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
