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

			<p class="heading1">팝빌 세금계산서 SDK SpringMVC Example.</p>
			
			<br/>

			<fieldset class="fieldset1">
				<legend>팝빌 기본 API</legend>

				<fieldset class="fieldset2">
					<legend>회원사 정보</legend>
					<ul>
						<li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원사 가입 여부 확인</li>
						<li><a href="BaseService/joinMember">joinMember</a> - 연동회원사 가입 요청</li>
						<li><a href="TaxinvoiceService/getChargeInfo">getChargeInfo</a> - 과금정보 확인</li>
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
				<legend>전자세금계산서 관련 API</legend>
				
				<fieldset class="fieldset2">
					<legend>등록/수정/확인/삭제</legend>
					<ul>
						<li><a href="TaxinvoiceService/checkMgtKeyInUse">checkMgtKeyInUse</a> - 연동관리번호의 등록/사용여부 확인</li>
						<li><a href="TaxinvoiceService/register">register</a> - 세금계산서 등록</li>
						<li><a href="TaxinvoiceService/update">update</a> - 세금계산서 수정</li>
						<li><a href="TaxinvoiceService/getInfo">getInfo</a> - 세금계산서 상태/요약 정보 확인</li>
						<li><a href="TaxinvoiceService/getInfos">getInfos</a> - 다량(최대 1000건)의 세금계산서 상태/요약 정보 확인</li>
						<li><a href="TaxinvoiceService/getDetailInfo">getDetailInfo</a> - 세금계산서 상세 정보 확인</li>
						<li><a href="TaxinvoiceService/delete">delete</a> - 세금계산서 삭제</li>
						<li><a href="TaxinvoiceService/getLogs">getLogs</a> - 세금계산서 문서이력 확인</li>
						<li><a href="TaxinvoiceService/attachFile">attachFile</a> - 세금계산서 첨부파일 추가</li>
						<li><a href="TaxinvoiceService/getFiles">getFiles</a> - 세금계산서 첨부파일 목록확인</li>
						<li><a href="TaxinvoiceService/deleteFile">deleteFile</a> - 세금계산서 첨부파일 1개 삭제</li>
						<li><a href="TaxinvoiceService/search">search</a> - 세금계산서 목록 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>처리 프로세스</legend>
					<ul>
						<li><a href="TaxinvoiceService/registIssue">registIssue</a> - 세금계산서 즉시발행 처리</li>
						<li><a href="TaxinvoiceService/send">send</a> - 정발행/위수탁 세금계산서 발행예정 처리</li>
						<li><a href="TaxinvoiceService/cancelSend">cancelSend</a> - 정발행/위수탁 세금계산서 발행예정 취소 처리</li>
						<li><a href="TaxinvoiceService/accept">accept</a> - 정발행/위수탁 세금계산서 발행예정에 대한 공급받는자의 승인 처리</li>
						<li><a href="TaxinvoiceService/deny">deny</a> - 정발행/위수탁 세금계산서 발행예정에 대한 공급받는자의 거부 처리</li>
						<li><a href="TaxinvoiceService/issue">issue</a> - 세금계산서 발행 처리</li>
						<li><a href="TaxinvoiceService/cancelIssue">cancelIssue</a> - 세금계산서 발행취소 처리 (국세청 전송전까지만 취소 가능)</li>
						<li><a href="TaxinvoiceService/request">request</a> - 세금계산서 역)발행요청 처리.</li>
						<li><a href="TaxinvoiceService/cancelRequest">cancelRequest</a> - 세금계산서 역)발행요청 취소 처리.</li>
						<li><a href="TaxinvoiceService/refuse">refuse</a> - 세금계산서 역)발행요청에 대한 공급자의 발행거부 처리.</li>
						<li><a href="TaxinvoiceService/sendToNTS">sendToNTS</a> - 발행된 세금계산서의 국세청 즉시전송 요청.</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>부가 기능</legend>
					<ul>
						<li><a href="TaxinvoiceService/sendEmail">sendEmail</a> - 처리 프로세스에 대한 이메일 재전송 요청</li>
						<li><a href="TaxinvoiceService/sendSMS">sendSMS</a> - 발행예정/발행/역)발행요청 에 대한 문자메시지 안내 재전송 요청.</li>
						<li><a href="TaxinvoiceService/sendFAX">sendFAX</a> - 세금계산서에 대한 팩스 전송 요청..</li>
						<li><a href="TaxinvoiceService/attachStatement">attachStatement</a> - 전자명세서 첨부</li>
						<li><a href="TaxinvoiceService/detachStatement">detachStatement</a> - 전자명세서 첨부해제</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>팝빌 세금계산서 SSO URL 기능</legend>
					<ul>
						<li><a href="TaxinvoiceService/getURL">getURL</a> - 세금계산서 관련 SSO URL 확인</li>
						<li><a href="TaxinvoiceService/getPopUpURL">getPopUpURL</a> - 해당 세금계산서의 팝빌 화면을 표시하는 URL 확인</li>
						<li><a href="TaxinvoiceService/getPrintURL">getPrintURL</a> - 해당 세금계산서의 팝빌 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="TaxinvoiceService/getMassPrintURL">getMassPrintURL</a> - 다량(최대100건)의 세금계산서 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="TaxinvoiceService/getEPrintURL">getEPrintURL</a> - 해당 세금계산서의 공급받는자용 팝빌 인쇄 화면을 표시하는 URL 확인</li>
						<li><a href="TaxinvoiceService/getMailURL">getMailURL</a> - 해당 세금계산서의 전송메일상의 "보기" 버튼에 해당하는 URL 확인</li>
					</ul>
				</fieldset>
				
				<fieldset class="fieldset2">
					<legend>기타</legend>
					<ul>
						<li><a href="TaxinvoiceService/getUnitCost">getUnitCost</a> - 세금계산서 발행 단가 확인</li>
						<li><a href="TaxinvoiceService/getCertificateExpireDate">getCertificateExpireDate</a> - 연동회원이 등록한 공인인증서의 만료일시 확인</li>
						<li><a href="TaxinvoiceService/getEmailPublicKeys">getEmailPublicKeys</a> - Email 유통을 위한 대용량 연계사업자 이메일 목록 확인</li>
					</ul>
				</fieldset>


			</fieldset>

			

		 </div>

	</body>

</html>
