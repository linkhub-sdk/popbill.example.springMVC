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
    <p class="heading1">팝빌 팩스 API SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>발신번호 관리</legend>
        <ul>
            <li><a href="FaxService/checkSenderNumber">CheckSenderNumber</a> - 발신번호 등록여부 확인</li>
            <li><a href="FaxService/getSenderNumberMgtURL">GetSenderNumberMgtURL</a> - 발신번호 관리 팝업 URL</li>
            <li><a href="FaxService/getSenderNumberList">GetSenderNumberList</a> - 발신번호 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>팩스 전송</legend>
        <ul>
            <li><a href="FaxService/sendFAX">SendFAX</a> - 팩스 전송</li>
            <li><a href="FaxService/sendFAX_Multi">SendFAX</a> - 팩스 동보전송</li>
            <li><a href="FaxService/sendFAXBinary">SendFAXBinary</a> - 팩스 전송(Binary)</li>
            <li><a href="FaxService/sendFAXBinary_Multi">SendFAXBinary</a> - 팩스 동보전송(Binary)</li>
            <li><a href="FaxService/resendFAX">ResendFAX</a> - 팩스 재전송</li>
            <li><a href="FaxService/resendFAXRN">ResendFAXRN</a> - 팩스 재전송 (요청번호 할당)</li>
            <li><a href="FaxService/resendFAX_Multi">ResendFAX</a> - 팩스 동보재전송</li>
            <li><a href="FaxService/resendFAXRN_Multi">ResendFAXRN</a> - 팩스 동보재전송 (요청번호 할당)</li>
            <li><a href="FaxService/cancelReserve">CancelReserve</a> - 예약전송 취소</li>
            <li><a href="FaxService/cancelReserveRN">CancelReserveRN</a> - 예약전송 취소 (요청번호 할당)</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>정보확인</legend>
        <ul>
            <li><a href="FaxService/getFaxResult">GetFaxResult</a> - 전송내역 및 전송상태 확인</li>
            <li><a href="FaxService/getFaxResultRN">GetFaxResultRN</a> - 전송내역 및 전송상태 확인 (요청번호 할당)</li>
            <li><a href="FaxService/search">Search</a> - 전송내역 목록 조회</li>
            <li><a href="FaxService/getSentListURL">GetSentListURL</a> - 팩스 전송내역 팝업 URL</li>
            <li><a href="FaxService/getPreviewURL">GetPreviewURL</a> - 팩스 미리보기 팝업 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트 관리</legend>
        <ul>
            <li><a href="FaxService/getUnitCost">GetUnitCost</a> - 전송 단가 확인</li>
            <li><a href="FaxService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
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
            <li><a href="BaseService/getRefundInfo">GetRefundInfo</a> - 환불 신청 상태 확인</li>
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