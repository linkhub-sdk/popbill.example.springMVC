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
            <li><a href="EasyFinBankService/registBankAccount">registBankAccount</a> - 계좌 등록</li>
            <li><a href="EasyFinBankService/updateBankAccount">updateBankAccount</a> - 계좌 정보 수정</li>
            <li><a href="EasyFinBankService/getBankAccountInfo">getBankAccountInfo</a> - 계좌 정보 확인</li>
            <li><a href="EasyFinBankService/listBankAccount">listBankAccount</a> - 계좌 목록 확인</li>
            <li><a href="EasyFinBankService/getBankAccountMgtURL">getBankAccountMGTURL</a> - 계좌 관리 팝업 URL</li>
            <li><a href="EasyFinBankService/closeBankAccount">closeBankAccount</a> - 계좌 정액제 해지신청</li>
            <li><a href="EasyFinBankService/revokeCloseBankAccount">revokeCloseBankAccount</a> - 계좌 정액제 해지신청 취소</li>
            <li><a href="EasyFinBankService/deleteBankAccount">deleteBankAccount</a> - 종량제 계좌 삭제</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>계좌 거래내역 수집</legend>
        <ul>
            <li><a href="EasyFinBankService/requestJob">requestJob</a> - 수집 요청</li>
            <li><a href="EasyFinBankService/getJobState">getJobState</a> - 수집 상태 확인</li>
            <li><a href="EasyFinBankService/listActiveJob">listActiveJob</a> - 수집 상태 목록 확인</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>계좌 거래내역 관리</legend>
        <ul>
            <li><a href="EasyFinBankService/search">search</a> - 거래 내역 조회</li>
            <li><a href="EasyFinBankService/summary">summary</a> - 거래 내역 요약정보 조회</li>
            <li><a href="EasyFinBankService/saveMemo">saveMemo</a> - 거래 내역 메모저장</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>포인트 관리 / 정액제 신청</legend>
        <ul>
            <li><a href="BaseService/getBalance">getBalance</a> - 연동회원 잔여포인트 확인</li>
            <li><a href="BaseService/getUseHistory">getUseHistory</a> - 연동회원 포인트 사용내역</li>
            <li><a href="BaseService/getPaymentHistory">getPaymentHistory</a> - 연동회원 포인트 결제내역</li>
            <li><a href="BaseService/getRefundHistory">getRefundHistory</a> - 연동회원 포인트 환불신청내역</li>
            <li><a href="BaseService/refund">refund</a> - 연동회원 포인트 환불 신청</li>
            <li><a href="BaseService/paymentRequest">paymentRequest</a> - 연동회원 무통장 입금신청</li>
            <li><a href="BaseService/getSettleResult">getSettleResult</a> - 연동회원 무통장 입금신청정보 확인</li>
            <li><a href="BaseService/getChargeURL">getChargeURL</a> - 연동회원 포인트충전 URL</li>
            <li><a href="BaseService/getPaymentURL">getPaymentURL</a> - 연동회원 포인트 결제내역 URL</li>
            <li><a href="BaseService/getUseHistoryURL">getUseHistoryURL</a> - 연동회원 포인트 사용내역 URL</li>
            <li><a href="BaseService/getPartnerBalance">getPartnerBalance</a> - 파트너 잔여포인트 확인</li>
            <li><a href="BaseService/getPartnerURL">getPartnerURL</a> - 파트너 포인트충전 URL</li>
            <li><a href="EasyFinBankService/getChargeInfo">getChargeInfo</a> - 과금정보 확인</li>
            <li><a href="EasyFinBankService/getFlatRatePopUpURL">getFlatRatePopUpURL</a> - 정액제 서비스 신청 URL</li>
            <li><a href="EasyFinBankService/getFlatRateState">getFlatRateState</a> - 정액제 서비스 상태 확인</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>회원정보</legend>
        <ul>
            <li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입여부 확인</li>
            <li><a href="BaseService/checkID">checkID</a> - 아이디 중복 확인</li>
            <li><a href="BaseService/joinMember">joinMember</a> - 연동회원 신규가입</li>
            <li><a href="BaseService/getAccessURL">getAccessURL</a> - 팝빌 로그인 URL</li>
            <li><a href="BaseService/getCorpInfo">getCorpInfo</a> - 회사정보 확인</li>
            <li><a href="BaseService/updateCorpInfo">updateCorpInfo</a> - 회사정보 수정</li>
            <li><a href="BaseService/registContact">registContact</a> - 담당자 등록</li>
            <li><a href="BaseService/getContactInfo">getContactInfo</a> - 담당자 정보 확인</li>
            <li><a href="BaseService/listContact">listContact</a> - 담당자 목록 확인</li>
            <li><a href="BaseService/updateContact">updateContact</a> - 담당자 정보 수정</li>
        </ul>
    </fieldset>
</div>
</body>
</html>

