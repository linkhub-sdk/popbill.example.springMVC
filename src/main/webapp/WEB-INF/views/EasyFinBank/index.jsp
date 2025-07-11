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
    <p class="heading1">팝빌 계좌 간편조회 SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>걔좌 관리</legend>
        <ul>
            <li><a href="EasyFinBankService/registBankAccount">RegistBankAccount</a> - 계좌 등록</li>
            <li><a href="EasyFinBankService/updateBankAccount">UpdateBankAccount</a> - 계좌정보 수정</li>
            <li><a href="EasyFinBankService/getBankAccountInfo">GetBankAccountInfo</a> - 계좌정보 확인</li>
            <li><a href="EasyFinBankService/listBankAccount">ListBankAccount</a> - 계좌정보 목록 조회</li>
            <li><a href="EasyFinBankService/getBankAccountMgtURL">GetBankAccountMGTURL</a> - 계좌 등록 팝업 URL</li>
            <li><a href="EasyFinBankService/closeBankAccount">CloseBankAccount</a> - 정액제 해지요청</li>
            <li><a href="EasyFinBankService/revokeCloseBankAccount">RevokeCloseBankAccount</a> - 정액제 해지요청 취소</li>
            <li><a href="EasyFinBankService/deleteBankAccount">DeleteBankAccount</a> - 계좌 삭제</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>계좌 거래내역 수집 요청</legend>
        <ul>
            <li><a href="EasyFinBankService/requestJob">RequestJob</a> - 수집 요청</li>
            <li><a href="EasyFinBankService/getJobState">GetJobState</a> - 수집 상태 확인</li>
            <li><a href="EasyFinBankService/listActiveJob">ListActiveJob</a> - 수집 상태 목록 확인</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>계좌 거래내역 확인</legend>
        <ul>
            <li><a href="EasyFinBankService/search">Search</a> - 수집 내역 확인</li>
            <li><a href="EasyFinBankService/summary">Summary</a> - 수집 내역 합계</li>
            <li><a href="EasyFinBankService/saveMemo">SaveMemo</a> - 거래 내역 메모저장</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>정액제 신청 / 포인트 관리</legend>
        <ul>
            <li><a href="EasyFinBankService/getFlatRateState">GetFlatRateState</a> - 정액제 서비스 상태 확인</li>
            <li><a href="EasyFinBankService/getFlatRatePopUpURL">GetFlatRatePopUpURL</a> - 정액제 신청 팝업 URL</li>
            <li><a href="EasyFinBankService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
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
            <li><a href="BaseService/getRefundableBalance">GetRefundableBalance</a> - 환불 가능 포인트 조회</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>회원 관리</legend>
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

