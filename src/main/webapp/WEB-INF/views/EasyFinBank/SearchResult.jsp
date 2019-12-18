<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <legend>거래내역 검색결과 정보</legend>
                <ul>
                    <li> code(응답코드) : ${SearchResult.code}</li>
                    <li> message(응답 메시지) : ${SearchResult.message}</li>
                    <li> total(전체 검색개수) : ${SearchResult.total}</li>
                    <li> perPage(페이지당 목록개수) : ${SearchResult.perPage}</li>
                    <li> pageNum(페이지번호) : ${SearchResult.pageNum}</li>
                    <li> pageCount(페이지수) : ${SearchResult.pageCount}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${SearchResult.list != null}">
            <c:forEach items="${SearchResult.list}" var="SearchInfo" varStatus="status">
                <fieldset class="fieldset2">
                    <ul>
                    	<li>trdate (거래일자) : ${SearchInfo.trdate}</li>
                    	<li>trdt (거래일시) : ${SearchInfo.trdt}</li>
                    	<li>accIn (입금액) : ${SearchInfo.accIn}</li>
                    	<li>accOut (출금액) : ${SearchInfo.accOut}</li>
                    	<li>balance (잔액) : ${SearchInfo.balance}</li>
                    	<li>remark1 (비고 1) : ${SearchInfo.remark1}</li>
                    	<li>remark2 (비고 2) : ${SearchInfo.remark2}</li>
                    	<li>remark3 (비고 3) : ${SearchInfo.remark3}</li>
                    	<li>remark4 (비고 4) : ${SearchInfo.remark4}</li>
                    	<li>regDT (등록일시) : ${SearchInfo.regDT}</li>
                    	<li>memo (메모) : ${SearchInfo.memo}</li>
                        
                        <li>tid (거래내역 아이디) : ${SearchInfo.tid}</li>
                        <li>trserial (거래일련번호) : ${SearchInfo.trserial}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>