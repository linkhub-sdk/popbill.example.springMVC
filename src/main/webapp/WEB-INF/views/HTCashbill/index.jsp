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
    <p class="heading1">팝빌 홈택스수집(현금영수증) SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>홈택스 수집 요청</legend>
        <ul>
            <li><a href="HTCashbillService/requestJob">requestJob</a> - 수집 요청</li>
            <li><a href="HTCashbillService/getJobState">getJobState</a> - 수집 상태 확인</li>
            <li><a href="HTCashbillService/listActiveJob">listActiveJob</a> - 수집 상태 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>수집 내역 확인</legend>
        <ul>
            <li><a href="HTCashbillService/search">search</a> - 수집 내역 확인</li>
            <li><a href="HTCashbillService/summary">summary</a> - 수집 내역 합계</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>홈택스 인증 관리</legend>
        <ul>
            <li><a href="HTCashbillService/getCertificatePopUpURL">getCertificatePopUpURL</a> - 홈택스 인증정보 등록 팝업 URL</li>
            <li><a href="HTCashbillService/getCertificateExpireDate">getCertificateExpireDate</a> - 인증서 만료일자 확인</li>
            <li><a href="HTCashbillService/checkCertValidation">checkCertValidation</a> - 인증서 유효성 검증</li>
            <li><a href="HTCashbillService/registDeptUser">registDeptUser</a> - 부서사용자 계정 등록</li>
            <li><a href="HTCashbillService/checkDeptUser">checkDeptUser</a> - 부서사용자 등록 여부</li>
            <li><a href="HTCashbillService/checkLoginDeptUser">checkLoginDeptUser</a> - 부서사용자 유효성 검증</li>
            <li><a href="HTCashbillService/deleteDeptUser">deleteDeptUser</a> - 부서사용자 계정 삭제</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>정액제 신청 / 포인트 관리</legend>
        <ul>
            <li><a href="HTCashbillService/getFlatRateState">GetFlatRateState</a> - 정액제 서비스 상태 확인</li>
            <li><a href="HTCashbillService/getFlatRatePopUpURL">GetFlatRatePopUpURL</a> - 정액제 서비스 신청 팝업 URL</li>
            <li><a href="HTCashbillService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
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
            <li><a href="BaseService/getRefundableBalance">getRefundableBalance</a> - 환불 가능 포인트 조회</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>회원 관리</legend>
        <ul>
            <li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입여부 확인</li>
            <li><a href="BaseService/checkID">checkID</a> - 연동회원 아이디 중복 확인</li>
            <li><a href="BaseService/joinMember">joinMember</a> - 연동회원 신규가입</li>
            <li><a href="BaseService/quitMember">QuitMember</a> - 연동회원 탈퇴</li>
            <li><a href="BaseService/getCorpInfo">getCorpInfo</a> - 회사정보 확인</li>
            <li><a href="BaseService/updateCorpInfo">updateCorpInfo</a> - 회사정보 수정</li>
            <li><a href="BaseService/registContact">registContact</a> - 담당자 추가</li>
            <li><a href="BaseService/getContactInfo">getContactInfo</a> - 담당자 정보 확인</li>
            <li><a href="BaseService/listContact">listContact</a> - 담당자 목록 확인</li>
            <li><a href="BaseService/updateContact">UpdateContact</a> - 담당자 정보 수정</li>
            <li><a href="BaseService/deleteContact">DeleteContact</a> - 담당자 삭제</li>
        </ul>
    </fieldset>
</div>
</body>
</html>

