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

			<p class="heading1">팝빌 전자세금계산서 SDK SpringMVC Example.</p>
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원 가입 요청</li>
						<li><a href="TaxinvoiceService/getChargeInfo">getChargeInfo</a> - 과금정보 확인</li>
						<li><a href="BaseService/getBalance">getBalance</a> - 연동회원 잔여포인트 확인</li>
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
				<legend>전자세금계산서 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>등록/수정/확인/삭제</legend>
					<ul>
						<li><a href="TaxinvoiceService/checkMgtKeyInUse">checkMgtKeyInUse</a> - 문서관리번호의 사용여부 확인</li>
						<li><a href="TaxinvoiceService/register">register</a> - 세금계산서 임시저장</li>
						<li><a href="TaxinvoiceService/update">update</a> - 세금계산서 수정</li>
						<li><a href="TaxinvoiceService/getInfo">getInfo</a> - 세금계산서 상태/요약 정보 확인</li>
						<li><a href="TaxinvoiceService/getInfos">getInfos</a> - 다량(최대 1000건)의 세금계산서 상태/요약 정보 확인</li>
						<li><a href="TaxinvoiceService/getDetailInfo">getDetailInfo</a> - 세금계산서 상세정보 확인</li>
						<li><a href="TaxinvoiceService/delete">delete</a> - 세금계산서 삭제</li>
						<li><a href="TaxinvoiceService/getLogs">getLogs</a> - 세금계산서 상태변경 이력 확인</li>
						<li><a href="TaxinvoiceService/attachFile">attachFile</a> - 세금계산서 첨부파일 추가</li>
						<li><a href="TaxinvoiceService/getFiles">getFiles</a> - 세금계산서 첨부파일 목록확인</li>
						<li><a href="TaxinvoiceService/deleteFile">deleteFile</a> - 세금계산서 첨부파일 삭제</li>
						<li><a href="TaxinvoiceService/search">search</a> - 세금계산서 목록 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">-
					<legend>처리 프로세스</legend>
					<ul>
						<li><a href="TaxinvoiceService/registIssue">registIssue</a> - 세금계산서 즉시발행</li>
						<li><a href="TaxinvoiceService/send">send</a> - 세금계산서 발행예정</li>
						<li><a href="TaxinvoiceService/cancelSend">cancelSend</a> - 세금계산서 발행예정 취소</li>
						<li><a href="TaxinvoiceService/accept">accept</a> - 세금계산서 발행예정 승인</li>
						<li><a href="TaxinvoiceService/deny">deny</a> - 세금계산서 발행예정 거부</li>
						<li><a href="TaxinvoiceService/issue">issue</a> - 세금계산서 발행</li>
						<li><a href="TaxinvoiceService/cancelIssue">cancelIssue</a> - 세금계산서 발행취소</li>
						<li><a href="TaxinvoiceService/request">request</a> - 세금계산서 역발행요청</li>
						<li><a href="TaxinvoiceService/cancelRequest">cancelRequest</a> - 세금계산서 역발행요청 취소</li>
						<li><a href="TaxinvoiceService/refuse">refuse</a> - 세금계산서 역발행요청 거부</li>
						<li><a href="TaxinvoiceService/sendToNTS">sendToNTS</a> - 세금계산서 국세청 즉시전송</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>부가 기능</legend>
					<ul>
						<li><a href="TaxinvoiceService/sendEmail">sendEmail</a> - 발행안내메일 재전송</li>
						<li><a href="TaxinvoiceService/sendSMS">sendSMS</a> - 알림문자메시지 전송</li>
						<li><a href="TaxinvoiceService/sendFAX">sendFAX</a> - 세금계산서 팩스 전송</li>
						<li><a href="TaxinvoiceService/attachStatement">attachStatement</a> - 전자명세서 첨부</li>
						<li><a href="TaxinvoiceService/detachStatement">detachStatement</a> - 전자명세서 첨부해제</li>
						<li><a href="TaxinvoiceService/assignMgtKey">assignMgtKey</a> - 문서관리번호 할당</li>
						<li><a href="TaxinvoiceService/listEmailConfig">listEmailConfig</a> - 알림메일 전송목록 확인</li>
						<li><a href="TaxinvoiceService/updateEmailConfig">updateEmailConfig</a> - 알림메일 전송설정 수정</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>팝빌 세금계산서 SSO URL 기능</legend>
					<ul>
						<li><a href="TaxinvoiceService/getURL">getURL</a> - 세금계산서 관련 SSO URL 확인</li>
						<li><a href="TaxinvoiceService/getPopUpURL">getPopUpURL</a> - 세금계산서 보기 팝업 URL</li>
						<li><a href="TaxinvoiceService/getPrintURL">getPrintURL</a> - 세금계산서 인쇄 팝업 URL</li>
						<li><a href="TaxinvoiceService/getMassPrintURL">getMassPrintURL</a> - 세금계산서 인쇄 팝업 URL - 대량</li>
						<li><a href="TaxinvoiceService/getEPrintURL">getEPrintURL</a> - 세금계산서 인쇄 팝업 URL - 공급받는자</li>
						<li><a href="TaxinvoiceService/getMailURL">getMailURL</a> - 세금계산서 메일링크 URL</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>기타</legend>
					<ul>
						<li><a href="TaxinvoiceService/getUnitCost">getUnitCost</a> - 세금계산서 발행단가 확인</li>
						<li><a href="TaxinvoiceService/getCertificateExpireDate">getCertificateExpireDate</a> - 공인인증서 만료일시 확인</li>
						<li><a href="TaxinvoiceService/checkCertValidation">checkCertValidation</a> - 공인인증서 유효성 확인</li>
						<li><a href="TaxinvoiceService/getEmailPublicKeys">getEmailPublicKeys</a> - 대용량 연계사업자 이메일 목록</li>
					</ul>
				</fieldset>
			</fieldset>
		 </div>
	</body>
</html>
