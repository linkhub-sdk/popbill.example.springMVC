<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>팝빌 SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">팝빌 카카오톡 SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>카카오톡 채널 계정관리</legend>
        <ul>
            <li><a href="KakaoService/getPlusFriendMgtURL">getPlusFriendMgtURL</a> - 카카오톡 채널 계정관리 팝업 URL</li>
            <li><a href="KakaoService/listPlusFriendID">listPlusFriendID</a> - 카카오톡 채널 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>발신번호 관리</legend>
        <ul>
            <li><a href="KakaoService/checkSenderNumber">checkSenderNumber</a> - 발신번호 등록여부 확인</li>
            <li><a href="KakaoService/getSenderNumberMgtURL">getSenderNumberMgtURL</a> - 발신번호 관리 팝업 URL</li>
            <li><a href="KakaoService/getSenderNumberList">getSenderNumberList</a> - 발신번호 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>알림톡 템플릿 관리</legend>
        <ul>
            <li><a href="KakaoService/getATSTemplateMgtURL">getATSTemplateMgtURL</a> - 알림톡 템플릿관리 팝업 URL</li>
            <li><a href="KakaoService/getATSTemplate">getATSTemplate</a> - 알림톡 템플릿 정보 확인</li>
            <li><a href="KakaoService/listATSTemplate">listATSTemplate</a> - 알림톡 템플릿 목록 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>알림톡 / 친구톡 전송</legend>
        <fieldset class="fieldset2">
            <legend>알림톡 전송</legend>
            <ul>
                <li><a href="KakaoService/sendATS_one">sendATS</a> - 알림톡 단건 전송</li>
                <li><a href="KakaoService/sendATS_same">sendATS</a> - 알림톡 동일내용 대량 전송</li>
                <li><a href="KakaoService/sendATS_multi">sendATS</a> - 알림톡 개별내용 대량 전송</li>
            </ul>
        </fieldset>
        <fieldset class="fieldset2">
            <legend>친구톡 텍스트 전송</legend>
            <ul>
                <li><a href="KakaoService/sendFTS_one">sendFTS</a> - 친구톡 텍스트 단건 전송</li>
                <li><a href="KakaoService/sendFTS_same">sendFTS</a> - 친구톡 텍스트 동일내용 대량전송</li>
                <li><a href="KakaoService/sendFTS_multi">sendFTS</a> - 친구톡 텍스트 개별내용 대량전송</li>
            </ul>
        </fieldset>
        <fieldset class="fieldset2">
            <legend>친구톡 이미지 전송</legend>
            <ul>
                <li><a href="KakaoService/sendFMS_one">sendFMS</a> - 친구톡 이미지 단건 전송</li>
                <li><a href="KakaoService/sendFMS_same">sendFMS</a> - 친구톡 이미지 동일내용 대량전송</li>
                <li><a href="KakaoService/sendFMS_multi">sendFMS</a> - 친구톡 이미지 개별내용 대량전송</li>
            </ul>
        </fieldset>
        <fieldset class="fieldset2">
            <legend>예약전송 취소</legend>
            <ul>
                <li><a href="KakaoService/cancelReserve">cancelReserve</a> - 예약전송 취소</li>
                <li><a href="KakaoService/cancelReservebyRCV">cancelReservebyRCV</a> - 예약전송 일부 취소 (접수번호)</li>
                <li><a href="KakaoService/cancelReserveRN">cancelReserveRN</a> - 예약전송 취소 (요청번호 할당)</li>
                <li><a href="KakaoService/cancelReserveRNbyRCV">cancelReserveRNbyRCV</a> - 예약전송 일부 취소 (전송요청번호)</li>
            </ul>
        </fieldset>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>정보확인</legend>
        <ul>
            <li><a href="KakaoService/getMessages">getMessages</a> - 알림톡/친구톡 전송내역 확인</li>
            <li><a href="KakaoService/getMessagesRN">getMessagesRN</a> - 알림톡/친구톡 전송내역 확인 (요청번호 할당)</li>
            <li><a href="KakaoService/search">search</a> - 전송내역 목록 조회</li>
            <li><a href="KakaoService/getSentListURL">getSentListURL</a> - 카카오톡 전송내역 팝업 URL</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>포인트관리</legend>
        <ul>
            <li><a href="KakaoService/getUnitCost">GetUnitCost</a> - 전송 단가 확인</li>
            <li><a href="KakaoService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
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
        <legend>회원관리</legend>
        <ul>
            <li><a href="BaseService/checkIsMember">checkIsMember</a> - 연동회원 가입여부 확인</li>
            <li><a href="BaseService/checkID">checkID</a> - 연동회원 연동회원 아이디 중복 확인</li>
            <li><a href="BaseService/joinMember">joinMember</a> - 연동회원사 신규가입</li>
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