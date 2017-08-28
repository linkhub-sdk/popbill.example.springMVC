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
						<li> writeDate (작성일자) : ${SearchInfo.writeDate}</li>
						<li> issueDate (발행일자) : ${SearchInfo.issueDate}</li>
						<li> sendDate (전송일자) : ${SearchInfo.sendDate}</li>
						<li> taxType (과세형태) : ${SearchInfo.taxType}</li>
						<li> purposeType (영수/청구) : ${SearchInfo.purposeType}</li>
						<li> supplyCostTotal (공급가액 합계) : ${SearchInfo.supplyCostTotal}</li>
						<li> taxTotal (세액 합계) : ${SearchInfo.totalAmount}</li>
						<li> totalAmount (합계금액) : ${SearchInfo.totalAmount}</li>
						<li> remark1 (비고) : ${SearchInfo.remark1}</li>
						<li> invoiceType (구분) : ${SearchInfo.invoiceType}</li>
						
						<li> modifyYN (수정 전자세금계산서 여부) : ${SearchInfo.modifyYN}</li>
						<li> orgNTSConfirmNum (원본 전자세금계산서 국세청승인번호) : ${SearchInfo.orgNTSConfirmNum}</li>
						<li> purchaseDate (거래일자) : ${SearchInfo.purchaseDate}</li>
						<li> itemName (품명) : ${SearchInfo.itemName}</li>
						<li> spec (규격) : ${SearchInfo.spec}</li>
						<li> qty (수량) : ${SearchInfo.qty}</li>
						<li> unitCost (단가) : ${SearchInfo.unitCost}</li>
						<li> supplyCost (공급가액) : ${SearchInfo.supplyCost}</li>
						<li> tax (세액) : ${SearchInfo.tax}</li>
						<li> remark (비고) : ${SearchInfo.remark}</li>
						
						<li> invoicerCorpNum (공급자 사업자번호) : ${SearchInfo.invoicerCorpNum}</li>
						<li> invoicerTaxRegID (공급자 종사업장번호) : ${SearchInfo.invoicerTaxRegID}</li>
						<li> invoicerCorpName (공급자 상호) : ${SearchInfo.invoicerCorpName}</li>
						<li> invoicerCEOName (공급자 대표자성명) : ${SearchInfo.invoicerCEOName}</li>
						<li> invoicerEmail (공급자 이메일) : ${SearchInfo.invoicerEmail}</li>
						
						<li> invoiceeCorpNum (공급받는자 사업자번호) : ${SearchInfo.invoiceeCorpNum}</li>
						<li> invoiceeType (공급받는자 구분) : ${SearchInfo.invoiceeType}</li>
						<li> invoiceeTaxRegID (공급받는자 종사업장번호) : ${SearchInfo.invoiceeTaxRegID}</li>
						<li> invoiceeCorpName (공급받는자 상호) : ${SearchInfo.invoiceeCorpName}</li>
						<li> invoiceeCEOName (공급받는자 대표자성명) : ${SearchInfo.invoiceeCEOName}</li>
						<li> invoiceeEmail1 (공급받는자 이메일) : ${SearchInfo.invoiceeEmail1}</li>
						
					</ul>
				</fieldset>
				</c:forEach>
				</c:if> 
			</fieldset>
		 </div>
	</body>
</html>