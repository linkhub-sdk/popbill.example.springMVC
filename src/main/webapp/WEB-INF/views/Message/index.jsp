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
            <li><a href="MessageService/sendSMS">sendSMS</a> - 단문 전송</li>
            <li><a href="MessageService/sendSMS_Multi">sendSMS</a> - 단문 전송 [대량]</li>
            <li><a href="MessageService/sendLMS">sendLMS</a> - 장문 전송</li>
            <li><a href="MessageService/sendLMS_Multi">sendLMS</a> - 장문 전송 [대량]</li>
            <li><a href="MessageService/sendMMS">sendMMS</a> - 포토 전송</li>
            <li><a href="MessageService/sendMMS_Multi">sendMMS</a> - 포토 전송 [대량]</li>
            <li><a href="MessageService/sendXMS">sendXMS</a> - 단문/장문 자동인식 전송</li>
            <li><a href="MessageService/sendXMS_Multi">sendXMS</a> - 단문/장문 자동인식 전송 [대량]</li>
            <li><a href="MessageService/cancelReserve">cancelReserve</a> - 예약전송 취소</li>
            <li><a href="MessageService/cancelReserveRN">cancelReserveRN</a> - 예약전송 취소 (요청번호 할당)</li>
            <li><a href="MessageService/cancelReservebyRCV">cancelReservebyRCV</a> - 예약전송 취소 (접수번호, 수신번호)</li>
            <li><a href="MessageService/cancelReserveRNbyRCV">cancelReserveRNbyRCV</a> - 예약전송 취소 (요청번호, 수신번호)</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>정보확인</legend>
        <ul>
            <li><a href="MessageService/getMessages">getMessages</a> - 전송내역 확인</li>
            <li><a href="MessageService/getMessagesRN">getMessagesRN</a> - 전송내역 확인 (요청번호 할당)</li>
            <li><a href="MessageService/search">search</a> - 전송내역 목록 조회</li>
            <li><a href="MessageService/getSentListURL">getSentListURL</a> - 문자 전송내역 팝업 URL</li>
            <li><a href="MessageService/autoDenyList">getAutoDenyList</a> - 080 수신거부 목록 확인</li>
            <li><a href="MessageService/checkAutoDenyNumber">checkAutoDenyNumber</a> - 발신번호 등록여부 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트 관리</legend>
        <ul>
            <li><a href="MessageService/getUnitCost">GetUnitCost</a> - 전송 단가 확인</li>
            <li><a href="MessageService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
            <li><a href="BaseService/getBalance">GetBalance</a> - 연동회원 잔여포인트 확인</li>
            <li><a href="BaseService/getChargeURL">GetChargeURL</a> - 연동회원 포인트 충전 팝업 URL</li>
            <li><a href="BaseService/paymentRequest">paymentRequest</a> - 연동회원 무통장 입금신청</li>
            <li><a href="BaseService/getSettleResult">GetSettleResult</a> - 연동회원 무통장 입금신청 정보확인</li>
            <li><a href="BaseService/getPaymentHistory">GetPaymentHistory</a> - 연동회원 포인트 결제내역 확인</li>
            <li><a href="BaseService/getPaymentURL">GetPaymentURL</a> - 연동회원 포인트 결제내역 팝업 URL</li>
            <li><a href="BaseService/getUseHistory">GetUseHistory</a> - 연동회원 포인트 사용내역 확인</li>
            <li><a href="BaseService/getUseHistoryURL">GetUseHistoryURL</a> - 연동회원 포인트 사용내역 팝업 URL</li>
            <li><a href="BaseService/refund">refund</a> - 연동회원 포인트 환불신청</li>
            <li><a href="BaseService/getRefundHistory">GetRefundHistory</a> - 연동회원 포인트 환불내역 확인</li>
            <li><a href="BaseService/getPartnerBalance">GetPartnerBalance</a> - 파트너 잔여포인트 확인</li>
            <li><a href="BaseService/getPartnerURL">GetPartnerURL</a> - 파트너 포인트충전 팝업 URL</li>
            <li><a href="BaseService/getRefundInfo">getRefundInfo</a> - 환불 신청 상태 확인</li>
            <li><a href="BaseService/getRefundableBalance">getRefundableBalance</a> - 환불가능 포인트 조회</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>회원정보</legend>
        <ul>
            <li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입여부 확인</li>
            <li><a href="BaseService/checkID">checkID</a> - 아이디 중복 확인</li>
            <li><a href="BaseService/joinMember">joinMember</a> - 연동회원 신규가입</li>
            <li><a href="BaseService/getAccessURL">getAccessURL</a> - 팝빌 로그인 URL</li>
            <li><a href="BaseService/registContact">registContact</a> - 담당자 등록</li>
            <li><a href="BaseService/getContactInfo">getContactInfo</a> - 담당자 정보 확인</li>
            <li><a href="BaseService/listContact">listContact</a> - 담당자 목록 확인</li>
            <li><a href="BaseService/updateContact">UpdateContact</a> - 담당자 정보 수정</li>
            <li><a href="BaseService/getCorpInfo">getCorpInfo</a> - 회사정보 확인</li>
            <li><a href="BaseService/updateCorpInfo">updateCorpInfo</a> - 회사정보 수정</li>
            <li><a href="BaseService/deleteContact">DeleteContact</a> - 담당자 삭제</li>
            <li><a href="BaseService/quitMember">QuitMember</a> - 팝빌회원 탈퇴</li>
        </ul>
    </fieldset>
</div>
</body>
</html>