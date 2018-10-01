
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

			<p class="heading1">팝빌 홈택스연동(전자세금계산서) SDK SpringMVC Example.</p>
			
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원사 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원사 가입 요청</li>
						<li><a href="HTTaxinvoiceService/getChargeInfo">getChargeInfo</a> - 파트너 잔여포인트 확인</li>
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
				<legend>홈택스연계 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>매출/매입 내역 수집</legend>
					<ul>
						<li><a href="HTTaxinvoiceService/requestJob">requestJob</a> - 수집 요청</li>
						<li><a href="HTTaxinvoiceService/getJobState">getJobState</a> - 수집 상태 확인</li>
						<li><a href="HTTaxinvoiceService/listActiveJob">listActiveJob</a> -  수집 상태 목록 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>매출/매입 내역 수집 결과 조회</legend>
					<ul>
						<li><a href="HTTaxinvoiceService/search">search</a> - 수집 결과 조회</li>
						<li><a href="HTTaxinvoiceService/summary">summary</a> - 수집 결과 요약정보 조회</li>
						<li><a href="HTTaxinvoiceService/getTaxinvoice">getTaxinvoice</a> - 상세정보 확인</li>
						<li><a href="HTTaxinvoiceService/getXML">getXML</a> - 상세정보 확인 - XML</li>
						<li><a href="HTTaxinvoiceService/getPopUpURL">getPopUpURL</a> - 세금계산서 보기 팝업 URL</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>부가 기능</legend>
					<ul>
						<li><a href="HTTaxinvoiceService/getFlatRatePopUpURL">getFlatRatePopUpURL</a> - 정액제 서비스 신청 URL</li>
						<li><a href="HTTaxinvoiceService/getFlatRateState">getFlatRateState</a> - 정액제 서비스 상태 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>홈택스 인증관련 기능</legend>
					<ul>
						<li><a href="HTTaxinvoiceService/getCertificatePopUpURL">getCertificatePopUpURL</a> - 홈택스연동 인증관리 팝업 URL</li>
						<li><a href="HTTaxinvoiceService/getCertificateExpireDate">getCertificateExpireDate</a> - 홈택스연계 공인인증서 만료일자 확인</li>
						<li><a href="HTTaxinvoiceService/checkCertValidation">checkCertValidation</a> - 홈택스 공인인증서 로그인 테스트</li>
						<li><a href="HTTaxinvoiceService/registDeptUser">registDeptUser</a> - 홈택스 전자세금계산서 부서사용자 계정등록</li>
						<li><a href="HTTaxinvoiceService/checkDeptUser">checkDeptUser</a> - 홈택스 부서사용자 등록정보 확인</li>
						<li><a href="HTTaxinvoiceService/checkLoginDeptUser">checkLoginDeptUser</a> - 홈택스 부서사용자 로그인 테스트</li>
						<li><a href="HTTaxinvoiceService/deleteDeptUser">deleteDeptUser</a> - 홈택스 부서사용자 등록정보 삭제</li>
					</ul>
				</fieldset>								
			</fieldset>
		 </div>
	</body>
</html>

