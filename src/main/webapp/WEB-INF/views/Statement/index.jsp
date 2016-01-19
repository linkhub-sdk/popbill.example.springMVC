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

			<p class="heading1">팝빌 전자명세서 SDK SpringMVC Example.</p>
			
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원사 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원사 가입 요청</li>
						<li><a href="BaseService/getBalance">getBalance</a> - 연동회원사 잔여포인트 확인</li>
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
				<legend>전자전자명세서 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>등록/수정/확인/삭제</legend>
					<ul>
						<li><a href="StatementService/checkMgtKeyInUse">checkMgtKeyInUse</a> - 문서관리번호의 등록/사용여부 확인</li>
						<li><a href="StatementService/register">register</a> - 전자명세서 등록</li>
						<li><a href="StatementService/update">update</a> - 전자명세서 수정</li>
						<li><a href="StatementService/getInfo">getInfo</a> - 전자명세서 상태/요약 정보 확인</li>
						<li><a href="StatementService/getInfos">getInfos</a> - 다량(최대 1000건)의 전자명세서 상태/요약 정보 확인</li>
						<li><a href="StatementService/getDetailInfo">getDetailInfo</a> - 전자명세서 상세 정보 확인</li>
						<li><a href="StatementService/search">search</a> - 전자명세서 목록 조회</li>
						<li><a href="StatementService/delete">delete</a> - 전자명세서 삭제</li>
						<li><a href="StatementService/getLogs">getLogs</a> - 전자명세서 문서이력 확인</li>
						<li><a href="StatementService/attachFile">attachFile</a> - 전자명세서 첨부파일 추가</li>
						<li><a href="StatementService/getFiles">getFiles</a> - 전자명세서 첨부파일 목록확인</li>
						<li><a href="StatementService/deleteFile">deleteFile</a> - 전자명세서 첨부파일 1개 삭제</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>처리 프로세스</legend>
					<ul>
						<li><a href="StatementService/registIssue">registIssue</a> - 전자명세서 즉시발행 처리</li>
						<li><a href="StatementService/issue">issue</a> - 전자명세서 발행 처리</li>
						<li><a href="StatementService/cancelIssue">cancelIssue</a> - 전자명세서 발행취소 처리 </li>			
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>부가 기능</legend>
					<ul>
						<li><a href="StatementService/sendEmail">sendEmail</a> - 처리 프로세스에 대한 이메일 재전송 요청</li>
						<li><a href="StatementService/sendSMS">sendSMS</a> - 안내 문자메시지 재전송 요청</li>
						<li><a href="StatementService/sendFAX">sendFAX</a> - 전자명세서에 대한 팩스 전송 요청</li>
						<li><a href="StatementService/FAXSend">FAXSend</a> - 선팩스전송 </li>
						<li><a href="StatementService/attachStatement">attachStatement</a> - 다른 전자명세서 첨부 </li>
						<li><a href="StatementService/detachStatement">detachStatement</a> - 다른 전자명세서 첨부해제 </li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>팝빌 전자명세서 SSO URL 기능</legend>
					<ul>
						<li><a href="StatementService/getURL">getURL</a> - 전자명세서 관련 SSO URL 확인</li>
						<li><a href="StatementService/getPopUpURL">getPopUpURL</a> - 해당 전자명세서의 팝빌 화면을 표시하는 URL 확인</li>
						<li><a href="StatementService/getPrintURL">getPrintURL</a> - 해당 전자명세서의 팝빌 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="StatementService/getMassPrintURL">getMassPrintURL</a> - 다량(최대100건)의 전자명세서 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="StatementService/getEPrintURL">getEPrintURL</a> - 해당 전자명세서의 공급받는자용 팝빌 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="StatementService/getMailURL">getMailURL</a> - 해당 전자명세서의 전송메일상의 "보기" 버튼에 해당하는 URL 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>기타</legend>
					<ul>
						<li><a href="StatementService/getUnitCost">getUnitCost</a> - 전자명세서 발행 단가 확인</li>
					</ul>
				</fieldset>


			</fieldset>

			

		 </div>

	</body>

</html>
