<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />
		
		<title>팝빌 SDK SpringMVC Example.</title>
	</head>

	<body>

		<div id="content">

			<p class="heading1">팝빌 현금영수증 SDK SpringMVC Example.</p>
			
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원 가입 요청</li>
						<li><a href="CashbillService/getChargeInfo">getChargeInfo</a> - 과금정보 확인</li>
						<li><a href="BaseService/getBalance">getBalance</a> - 연동회원 잔여포인트 확인</li>
						<li><a href="BaseService/getPartnerBalance">getPartnerBalance</a> - 파트너 잔여포인트 확인</li>
						<li><a href="BaseService/getPopbillURL">getPopbillURL</a> - 팝빌 SSO URL 요청</li>
						<li><a href="BaseService/listContact">listContact</a> - 담당자 목록 확인 </li>
						<li><a href="BaseService/updateContact">updateContact</a> - 담당자 정보 수정 </li>
						<li><a href="BaseService/registContact">registContact</a> - 담당자 추가 </li>
						<li><a href="BaseService/checkID">checkID</a> - 연동회원 아이디 중복확인 </li>
						<li><a href="BaseService/getCorpInfo">getCorpInfo</a> - 회사정보 확인 </li>
						<li><a href="BaseService/updateCorpInfo">updateCorpInfo</a> - 회사정보 수정 </li>
					</ul>
				</fieldset>

			</fieldset>
			<br />
			
			<fieldset class="fieldset1">
				<legend>현금영수증 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>등록/수정/확인/삭제</legend>
					<ul>
						<li><a href="CashbillService/checkMgtKeyInUse">checkMgtKeyInUse</a> - 문서관리번호 사용여부 확인</li>
						<li><a href="CashbillService/register">register</a> - 현금영수증 등록</li>
						<li><a href="CashbillService/update">update</a> - 현금영수증 수정</li>
						<li><a href="CashbillService/getInfo">getInfo</a> - 현금영수증 상태/요약 정보 확인</li>
						<li><a href="CashbillService/getInfos">getInfos</a> - 현금영수증 상태/요약 정보 확인 - 대량</li>
						<li><a href="CashbillService/search">search</a> - 현금영수증 목록 조회</li>
						<li><a href="CashbillService/getDetailInfo">getDetailInfo</a> - 현금영수증 상세정보 확인</li>
						<li><a href="CashbillService/delete">delete</a> - 현금영수증 삭제</li>
						<li><a href="CashbillService/getLogs">getLogs</a> - 현금영수증 상태변경 이력 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>처리 프로세스</legend>
					<ul>
						<li><a href="CashbillService/registIssue">registIssue</a> - 현금영수증 즉시발행</li>
						<li><a href="CashbillService/issue">issue</a> - 현금영수증 발행</li>
						<li><a href="CashbillService/cancelIssue">cancelIssue</a> - 현금영수증 발행취소</li>			
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>취소현금영수증 발행</legend>
					<ul>
						<li><a href="CashbillService/revokeRegistIssue">revokerRegistIssue</a> - 취소현금영수증 즉시발행</li>
						<li><a href="CashbillService/revokeRegister">revokeRegister</a> - 취소현금영수증 임시저장</li>
									
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>부가 기능</legend>
					<ul>
						<li><a href="CashbillService/sendEmail">sendEmail</a> - 발행 안내메일 전송</li>
						<li><a href="CashbillService/sendSMS">sendSMS</a> - 알림문자 전송</li>
						<li><a href="CashbillService/sendFAX">sendFAX</a> - 현금영수증 팩스 전송</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>팝빌 현금영수증 SSO URL 기능</legend>
					<ul>
						<li><a href="CashbillService/getURL">getURL</a> - 현금영수증 관련 SSO URL 확인</li>
						<li><a href="CashbillService/getPopUpURL">getPopUpURL</a> - 현금영수증 보기 팝업 URL 확인</li>
						<li><a href="CashbillService/getPrintURL">getPrintURL</a> - 현금영수증 인쇄 팝업 URL 확인</li>
						<li><a href="CashbillService/getMassPrintURL">getMassPrintURL</a> - 현금영수증 인쇄 팝업 URL 확인 - 대량</li>
						<li><a href="CashbillService/getEPrintURL">getEPrintURL</a> - 현금영수증 공급받는자용 인쇄 팝업 URL 확인</li>
						<li><a href="CashbillService/getMailURL">getMailURL</a> - 현금영수증 메일링크 URL 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>기타</legend>
					<ul>
						<li><a href="CashbillService/getUnitCost">getUnitCost</a> - 현금영수증 발행단가 확인</li>
					</ul>
				</fieldset>
			</fieldset>
		 </div>
	</body>
</html>
