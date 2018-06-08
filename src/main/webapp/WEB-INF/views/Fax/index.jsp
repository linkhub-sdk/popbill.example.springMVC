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

			<p class="heading1">팝빌 팩스 API SDK SpringMVC Example.</p>
			
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원사 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원사 가입 요청</li>
						<li><a href="FaxService/getChargeInfo">getChargeInfo</a> - 과금정보 확인</li>
						<li><a href="BaseService/getBalance">getBalance</a> - 연동회원사 잔여포인트 확인</li>
						<li><a href="BaseService/getPopbillURL">getPopbillURL</a> - 팝빌 SSO 팝업 URL 요청</li>
						<li><a href="BaseService/getPartnerBalance">getPartnerBalance</a> - 파트너 잔여포인트 확인</li>
						<li><a href="BaseService/getPartnerURL">getPartnerURL</a> - 파트너 포인트충전 팝업 URL 확인</li>
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
				<legend>팩스 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>팩스 전송</legend>
					<ul>
						<li><a href="FaxService/sendFAX">sendFAX</a> - 팩스 단건 전송</li>
						<li><a href="FaxService/sendFAX_Multi">sendFAX</a> - 팩스 동보 전송(수신번호 최대 1000개)</li>
						<li><a href="FaxService/resendFAX">resendFAX</a> - 팩스 재전송</li>
						<li><a href="FaxService/resendFAX_Multi">resendFAX</a> - 팩스 동보 재전송</li>
					</ul>
				</fieldset>
			
				<fieldset class="fieldset2">
					<legend>전송결과/예약취소</legend>
					<ul>
						<li><a href="FaxService/getFaxResult">getFaxResult</a> - 접수번호에 해당하는 팩스전송 전송결과 확인</li>
						<li><a href="FaxService/search">search</a> - 팩스전송내역 조회</li>
						<li><a href="FaxService/cancelReserve">cancelReserve</a> - 예약 전송 팩스의 예약 취소. 예약시간 10분전까지만 가능.</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>기타</legend>
					<ul>
						<li><a href="FaxService/getURL">getURL</a> - 팩스 관련 SSO URL 확인</li>
						<li><a href="FaxService/getUnitCost">getUnitCost</a> - 팩스 전송단가 확인</li>
						<li><a href="FaxService/getSenderNumberList">getSenderNumberList</a> - 발신번호 목록 확인</li>
					</ul>
				</fieldset>
			</fieldset>
		 </div>
	</body>
</html>