<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />
		
		<title>팝빌 SDK SpringMVC Example.</title>
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
					<legend>[ ${status.index+1} / ${SearchResult.perPage} ]</legend>
					<ul>
						<li> sendState : ${SearchInfo.sendState}</li>
						<li> convState : ${SearchInfo.convState}</li>
						<li> sendNum : ${SearchInfo.sendNum}</li>
						<li> receiveNum : ${SearchInfo.receiveNum}</li>
						<li> receiveName : ${SearchInfo.receiveName}</li>
						<li> sendPageCnt : ${SearchInfo.sendPageCnt}</li>
						<li> successPageCnt : ${SearchInfo.successPageCnt}</li>
						<li> failPageCnt : ${SearchInfo.failPageCnt}</li>
						<li> refundPageCnt : ${SearchInfo.refundPageCnt}</li>
						<li> cancelPageCnt : ${SearchInfo.cancelPageCnt}</li>
						<li> reserveDT : ${SearchInfo.reserveDT}</li>
						<li> sendDT : ${SearchInfo.sendDT}</li>
						<li> resultDT : ${SearchInfo.resultDT}</li>
						<li> sendResult : ${SearchInfo.sendResult}</li>
						<li> fileNames : ${fn:join(SearchInfo.fileNames,", ")}</li>
						<li> receiptDT : ${SearchInfo.receiptDT}</li>
					</ul>
				</fieldset>
				</c:forEach>
				</c:if> 
			</fieldset>
		 </div>
	</body>
</html>