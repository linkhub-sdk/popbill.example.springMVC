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
    <p class="heading1">팝빌 현금영수증 SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>현금영수증 발행</legend>
        <ul>
            <li><a href="CashbillService/checkMgtKeyInUse">CheckMgtKeyInUse</a> - 문서번호 사용여부 확인</li>
            <li><a href="CashbillService/registIssue">RegistIssue</a> - 즉시발행</li>
            <li><a href="CashbillService/bulkSubmit">BulkSubmit</a> - 초대량 발행 접수</li>
            <li><a href="CashbillService/getBulkResult">GetBulkResult</a> - 초대량 접수결과 확인</li>
            <li><a href="CashbillService/delete">Delete</a> - 삭제</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>취소현금영수증 발행</legend>
        <ul>
            <li><a href="CashbillService/revokeRegistIssue">RevokeRegistIssue</a> - 즉시발행</li>
            <li><a href="CashbillService/revokeRegistIssue_part">RevokeRegistIssue_part</a> - 부분) 즉시발행</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>현금영수증 정보확인</legend>
        <ul>
            <li><a href="CashbillService/getInfo">GetInfo</a> - 상태확인</li>
            <li><a href="CashbillService/getInfos">GetInfos</a> - 상태 대량 확인</li>
            <li><a href="CashbillService/getDetailInfo">GetDetailInfo</a> - 상세정보 확인</li>
            <li><a href="CashbillService/search">Search</a> - 목록 조회</li>
            <li><a href="CashbillService/getURL">GetURL</a> - 현금영수증 문서함 관련 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>현금영수증 보기/인쇄</legend>
        <ul>
            <li><a href="CashbillService/getPopUpURL">GetPopUpURL</a> - 현금영수증 보기 URL</li>
            <li><a href="CashbillService/getViewURL">GetViewURL</a> - 현금영수증 보기 URL - 메뉴/버튼 제외</li>
            <li><a href="CashbillService/getPrintURL">GetPrintURL</a> - 현금영수증 인쇄 URL</li>
            <li><a href="CashbillService/getMassPrintURL">GetMassPrintURL</a> - 현금영수증 대량 인쇄 URL</li>
            <li><a href="CashbillService/getMailURL">GetMailURL</a> - 현금영수증 메일링크 URL</li>
            <li><a href="CashbillService/getPDFURL">GetPDFURL</a> - 현금영수증 PDF 다운로드 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>부가기능</legend>
        <ul>
            <li><a href="BaseService/getAccessURL">GetAccessURL</a> - 팝빌 로그인 URL</li>
            <li><a href="CashbillService/sendEmail">SendEmail</a> - 메일 전송</li>
            <li><a href="CashbillService/sendSMS">SendSMS</a> - 문자 전송</li>
            <li><a href="CashbillService/sendFAX">SendFAX</a> - 팩스 전송</li>
            <li><a href="CashbillService/listEmailConfig">ListEmailConfig</a> - 현금영수증 알림메일 전송목록 조회</li>
            <li><a href="CashbillService/updateEmailConfig">UpdateEmailConfig</a> - 현금영수증 알림메일 전송설정 수정</li>
            <li><a href="CashbillService/assignMgtKey">AssignMgtKey</a> - 현금영수증 문서번호 할당</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트관리</legend>
        <ul>
            <li><a href="CashbillService/getUnitCost">GetUnitCost</a> - 발행 단가 확인</li>
            <li><a href="CashbillService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
            <li><a href="BaseService/getBalance">GetBalance</a> - 연동회원 잔여포인트 확인</li>
            <li><a href="BaseService/getChargeURL">GetChargeURL</a> - 연동회원 포인트 충전 팝업 URL</li>
            <li><a href="BaseService/paymentRequest">PaymentRequest</a> - 연동회원 무통장 입금신청</li>
            <li><a href="BaseService/getSettleResult">GetSettleResult</a> - 연동회원 무통장 입금신청 정보확인</li>
            <li><a href="BaseService/getPaymentHistory">GetPaymentHistory</a> - 연동회원 포인트 결제내역 확인</li>
            <li><a href="BaseService/getPaymentURL">GetPaymentURL</a> - 연동회원 포인트 결제내역 팝업 URL</li>
            <li><a href="BaseService/getUseHistory">GetUseHistory</a> - 연동회원 포인트 사용내역 확인</li>
            <li><a href="BaseService/getUseHistoryURL">GetUseHistoryURL</a> - 연동회원 포인트 사용내역 팝업 URL</li>
            <li><a href="BaseService/refund">Refund</a> - 연동회원 포인트 환불신청</li>
            <li><a href="BaseService/getRefundHistory">GetRefundHistory</a> - 연동회원 포인트 환불내역 확인</li>
            <li><a href="BaseService/getPartnerBalance">GetPartnerBalance</a> - 파트너 잔여포인트 확인</li>
            <li><a href="BaseService/getPartnerURL">GetPartnerURL</a> - 파트너 포인트충전 팝업 URL</li>
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
