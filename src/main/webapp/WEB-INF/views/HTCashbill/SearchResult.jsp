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
					<ul>
						<li> ntsconfirmNum (국세청승인번호) : ${SearchInfo.ntsconfirmNum}</li>
						<li> tradeDT (거래일시) : ${SearchInfo.tradeDT}</li>
						<li> tradeUsage (거래유형) : ${SearchInfo.tradeUsage}</li>
						<li> tradeType (현금영수증 형태) : ${SearchInfo.tradeType}</li>
						<li> supplyCost (공급가액) : ${SearchInfo.supplyCost}</li>
						<li> tax (세액) : ${SearchInfo.tax}</li>
						<li> serviceFee (봉사료) : ${SearchInfo.serviceFee}</li>
						<li> totalAmount (거래금액) : ${SearchInfo.totalAmount}</li>
						
						<li> franchiseCorpNum (발행자 사업자번호) : ${SearchInfo.franchiseCorpNum}</li>
						<li> franchiseCorpName (발행자 상호) : ${SearchInfo.franchiseCorpName}</li>
						<li> franchiseCorpType (발행자 사업자유형) : ${SearchInfo.franchiseCorpType}</li>
						
						<li> identityNum (거래처 식별번호) : ${SearchInfo.identityNum}</li>
						<li> identityNumType (식별번호유형) : ${SearchInfo.identityNumType}</li>
						<li> customerName (고객명) : ${SearchInfo.customerName}</li>
						<li> cardOwnerName (카드소유자명) : ${SearchInfo.cardOwnerName}</li>
						<li> deductionType (공제유형) : ${SearchInfo.deductionType}</li>
						
					</ul>
				</fieldset>
				</c:forEach>
				</c:if> 
			</fieldset>
		 </div>
	</body>
</html>