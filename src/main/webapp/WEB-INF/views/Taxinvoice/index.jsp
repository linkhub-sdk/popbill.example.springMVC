<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>팝빌 SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">팝빌 전자세금계산서 SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>정방행/역발행/위수탁발행</legend>
        <ul>
            <li><a href="TaxinvoiceService/checkMgtKeyInUse">CheckMgtKeyInUse</a> - 문서번호 사용 여부 확인</li>
            <li><a href="TaxinvoiceService/registIssue">RegistIssue</a> - 즉시 발행</li>
            <li><a href="TaxinvoiceService/bulkSubmit">BulkSubmit</a> - 대량 발행 접수</li>
            <li><a href="TaxinvoiceService/getBulkResult">GetBulkResult</a> - 대량 접수결과 확인</li>
            <li><a href="TaxinvoiceService/register">Register</a> - 임시저장</li>
            <li><a href="TaxinvoiceService/update">Update</a> - 수정</li>
            <li><a href="TaxinvoiceService/issue">Issue</a> - 발행</li>
            <li><a href="TaxinvoiceService/cancelIssue">CancelIssue</a> - 발행취소</li>
            <li><a href="TaxinvoiceService/delete">Delete</a> - 삭제</li>
            <li><a href="TaxinvoiceService/registRequest">RegistRequest</a> - [역발행] 즉시 요청</li>
            <li><a href="TaxinvoiceService/request">Request</a> - 역발행요청</li>
            <li><a href="TaxinvoiceService/cancelRequest">CancelRequest</a> - 역발행요청 취소</li>
            <li><a href="TaxinvoiceService/refuse">Refuse</a> - 역발행요청 거부</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>수정세금계산서 발행</legend>
        <ul>
            <li><a href="TaxinvoiceService/modifyTaxinvoice01minus">RegistIssue</a> - 기재사항 착오정정(취소분)</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice01plus">RegistIssue</a> - 기재사항 착오정정(수정분)</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice02">RegistIssue</a> - 공급가액 변동</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice03">RegistIssue</a> - 환입</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice04">RegistIssue</a> - 계약의 해제</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice05minus">RegistIssue</a> - 내국신용장 사후개설(취소분)</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice05plus">RegistIssue</a> - 내국신용장 사후개설(수정분)</li>
            <li><a href="TaxinvoiceService/modifyTaxinvoice06">RegistIssue</a> - 착오에 의한 이중발급</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>국세청 즉시 전송</legend>
        <ul>
            <li><a href="TaxinvoiceService/sendToNTS">SendToNTS</a> - 국세청 즉시전송</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>세금계산서 정보확인</legend>
        <ul>
            <li><a href="TaxinvoiceService/getInfo">GetInfo</a> - 상태 확인</li>
            <li><a href="TaxinvoiceService/getInfos">GetInfos</a> - 상태 대량 확인</li>
            <li><a href="TaxinvoiceService/getDetailInfo">GetDetailInfo</a> - 상세정보 확인</li>
            <li><a href="TaxinvoiceService/getXML">GetXML</a> - 상세정보 확인 (XML)</li>
            <li><a href="TaxinvoiceService/search">Search</a> - 목록 조회</li>
            <li><a href="TaxinvoiceService/getLogs">GetLogs</a> - 상태 변경이력 확인</li>
            <li><a href="TaxinvoiceService/getURL">GetURL</a> - 세금계산서 문서함 관련 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>세금계산서 보기/인쇄</legend>
        <ul>
            <li><a href="TaxinvoiceService/getPopUpURL">GetPopUpURL</a> - 세금계산서 보기 URL</li>
            <li><a href="TaxinvoiceService/getViewURL">GetViewURL</a> - 세금계산서 보기 URL - 메뉴/버튼 제외</li>
            <li><a href="TaxinvoiceService/getPrintURL">GetPrintURL</a> - 세금계산서 인쇄 [공급자/공급받는자] URL</li>
            <li><a href="TaxinvoiceService/getEPrintURL">GetEPrintURL</a> - 세금계산서 인쇄 [공급받는자용] URL</li>
            <li><a href="TaxinvoiceService/getMassPrintURL">GetMassPrintURL</a> - 세금계산서 대량 인쇄 URL</li>
            <li><a href="TaxinvoiceService/getMailURL">GetMailURL</a> - 세금계산서 메일링크 URL</li>
            <li><a href="TaxinvoiceService/getPDFURL">GetPDFURL</a> - 세금계산서 PDF 다운로드 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>부가기능</legend>
        <ul>
            <li><a href="BaseService/getAccessURL">GetAccessURL</a> - 팝빌 로그인 URL</li>
            <li><a href="TaxinvoiceService/getSealURL">GetSealURL</a> - 인감 및 첨부문서 등록 URL</li>
            <li><a href="TaxinvoiceService/attachFile">AttachFile</a> - 첨부파일 추가</li>
            <li><a href="TaxinvoiceService/deleteFile">DeleteFile</a> - 첨부파일 삭제</li>
            <li><a href="TaxinvoiceService/getFiles">GetFiles</a> - 첨부파일 목록 확인</li>
            <li><a href="TaxinvoiceService/sendEmail">SendEmail</a> - 메일 전송</li>
            <li><a href="TaxinvoiceService/sendSMS">SendSMS</a> - 문자 전송</li>
            <li><a href="TaxinvoiceService/sendFAX">SendFAX</a> - 팩스 전송</li>
            <li><a href="TaxinvoiceService/attachStatement">AttachStatement</a> - 전자명세서 첨부</li>
            <li><a href="TaxinvoiceService/detachStatement">DetachStatement</a> - 전자명세서 첨부해제</li>
            <li><a href="TaxinvoiceService/assignMgtKey">AssignMgtKey</a> - 문서번호 할당</li>
            <li><a href="TaxinvoiceService/listEmailConfig">ListEmailConfig</a> - 세금계산서 알림메일 전송목록 조회</li>
            <li><a href="TaxinvoiceService/updateEmailConfig">UpdateEmailConfig</a> - 세금계산서 알림메일 전송설정 수정</li>
            <li><a href="TaxinvoiceService/getSendToNTSConfig">GetSendToNTSConfig</a> - 국세청 전송 옵션 설정 상태 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>공동인증서 관리</legend>
        <ul>
            <li><a href="TaxinvoiceService/getTaxCertURL">GetTaxCertURL</a> - 공동인증서 등록 URL</li>
            <li><a href="TaxinvoiceService/getCertificateExpireDate">GetCertificateExpireDate</a> - 공동인증서 만료일 확인</li>
            <li><a href="TaxinvoiceService/checkCertValidation">CheckCertValidation</a> - 공동인증서 유효성 확인</li>
            <li><a href="TaxinvoiceService/getTaxCertInfo">GetTaxCertInfo</a> - 공동인증서 정보확인</li>
            <li><a href="TaxinvoiceService/registTaxCert">RegistTaxCert</a> - 공동 인증서 등록</li>
            <li><a href="TaxinvoiceService/registTaxCertPFX">RegistTaxCertPFX</a> - PFX 공동 인증서 등록</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트 관리</legend>
        <ul>
            <li><a href="TaxinvoiceService/getUnitCost">GetUnitCost</a> - 발행 단가 확인</li>
            <li><a href="TaxinvoiceService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
            <li><a href="BaseService/getBalance">GetBalance</a> - 연동회원 잔여포인트 확인</li>
            <li><a href="BaseService/getChargeURL">GetChargeURL</a> - 연동회원 포인트충전 URL</li>
            <li><a href="BaseService/paymentRequest">PaymentRequest</a> - 연동회원 무통장 입금신청</li>
            <li><a href="BaseService/getSettleResult">GetSettleResult</a> - 연동회원 무통장 입금신청정보 확인</li>
            <li><a href="BaseService/getPaymentHistory">GetPaymentHistory</a> - 연동회원 포인트 결제내역</li>
            <li><a href="BaseService/getPaymentURL">GetPaymentURL</a> - 연동회원 포인트 결제내역 URL</li>
            <li><a href="BaseService/getUseHistory">GetUseHistory</a> - 연동회원 포인트 사용내역</li>
            <li><a href="BaseService/getUseHistoryURL">GetUseHistoryURL</a> - 연동회원 포인트 사용내역 URL</li>
            <li><a href="BaseService/refund">Refund</a> - 연동회원 포인트 환불 신청</li>
            <li><a href="BaseService/getRefundHistory">GetRefundHistory</a> - 연동회원 포인트 환불신청내역</li>
            <li><a href="BaseService/getPartnerBalance">GetPartnerBalance</a> - 파트너 잔여포인트 확인</li>
            <li><a href="BaseService/getPartnerURL">GetPartnerURL</a> - 파트너 포인트충전 URL</li>
            <li><a href="BaseService/getRefundInfo">getRefundInfo</a> - 환불 신청 상태 확인</li>
            <li><a href="BaseService/getRefundableBalance">GetRefundableBalance</a> - 환불가능 포인트 조회</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>회원정보</legend>
        <ul>
            <li><a href="BaseService/checkIsMember">CheckIsMember</a> - 연동회원 가입여부 확인</li>
            <li><a href="BaseService/checkID">CheckID</a> - 연동회원 아이디 중복 확인</li>
            <li><a href="BaseService/joinMember">JoinMember</a> - 연동회원 신규가입</li>
			<li><a href="BaseService/quitMember">QuitMember</a> - 연동회원 탈퇴</li>
            <li><a href="BaseService/getCorpInfo">GetCorpInfo</a> - 회사정보 확인</li>
            <li><a href="BaseService/updateCorpInfo">UpdateCorpInfo</a> - 회사정보 수정</li>
            <li><a href="BaseService/registContact">RegistContact</a> - 담당자 추가</li>
            <li><a href="BaseService/getContactInfo">GetContactInfo</a> - 담당자 정보 확인</li>
            <li><a href="BaseService/listContact">ListContact</a> - 담당자 목록 확인</li>
            <li><a href="BaseService/updateContact">UpdateContact</a> - 담당자 정보 수정</li>
            <li><a href="BaseService/deleteContact">DeleteContact</a> - 담당자 삭제</li>
        </ul>
    </fieldset>
</div>
</body>
</html>