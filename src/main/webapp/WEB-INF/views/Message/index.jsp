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
    <p class="heading1">팝빌 문자메시지 API SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>발신번호 관리</legend>
        <ul>
            <li><a href="MessageService/checkSenderNumber">checkSenderNumber</a> - 발신번호 등록여부 확인</li>
            <li><a href="MessageService/getSenderNumberMgtURL">getSenderNumberMgtURL</a> - 발신번호 관리 팝업 URL</li>
            <li><a href="MessageService/getSenderNumberList">getSenderNumberList</a> - 발신번호 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>문자 전송</legend>
        <ul>
            <li><a href="MessageService/sendSMS">SendSMS</a> - 단문 전송</li>
            <li><a href="MessageService/sendSMS_Multi">SendSMS</a> - 단문 전송 [대량]</li>
            <li><a href="MessageService/sendLMS">SendLMS</a> - 장문 전송</li>
            <li><a href="MessageService/sendLMS_Multi">SendLMS</a> - 장문 전송 [대량]</li>
            <li><a href="MessageService/sendMMS">SendMMS</a> - 포토 전송</li>
            <li><a href="MessageService/sendMMS_Multi">SendMMS</a> - 포토 전송 [대량]</li>
            <li><a href="MessageService/sendMMS_Binary">SendMMSBinary</a> - 포토 전송 (Binary)</li>
            <li><a href="MessageService/sendMMS_Multi_Binary">SendMMSBinary</a> - 포토 전송 [대량] (Binary)</li>
            <li><a href="MessageService/sendXMS">SendXMS</a> - 단문/장문 자동인식 전송</li>
            <li><a href="MessageService/sendXMS_Multi">SendXMS</a> - 단문/장문 자동인식 전송 [대량]</li>
            <li><a href="MessageService/cancelReserve">CancelReserve</a> - 예약전송 취소</li>
            <li><a href="MessageService/cancelReserveRN">CancelReserveRN</a> - 예약전송 취소 (요청번호 할당)</li>
            <li><a href="MessageService/cancelReservebyRCV">CancelReservebyRCV</a> - 예약전송 취소 (접수번호, 수신번호)</li>
            <li><a href="MessageService/cancelReserveRNbyRCV">CancelReserveRNbyRCV</a> - 예약전송 취소 (요청번호, 수신번호)</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>정보확인</legend>
        <ul>
            <li><a href="MessageService/getMessages">GetMessages</a> - 전송내역 확인</li>
            <li><a href="MessageService/getMessagesRN">GetMessagesRN</a> - 전송내역 확인 (요청번호 할당)</li>
            <li><a href="MessageService/search">Search</a> - 전송내역 목록 조회</li>
            <li><a href="MessageService/getSentListURL">GetSentListURL</a> - 문자 전송내역 팝업 URL</li>
            <li><a href="MessageService/autoDenyList">GetAutoDenyList</a> - 080 수신거부 목록 확인</li>
            <li><a href="MessageService/checkAutoDenyNumber">CheckAutoDenyNumber</a> - 발신번호 등록여부 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트 관리</legend>
        <ul>
            <li><a href="MessageService/getUnitCost">GetUnitCost</a> - 전송 단가 확인</li>
            <li><a href="MessageService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
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