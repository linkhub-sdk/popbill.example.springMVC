<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />

		<title>팝빌 SDK SpringMVC Example.</title>
	</head>
	<body>
		<div id="content">
			<p class="heading1">팝빌 카카오톡 SDK SpringMVC Example.</p>
			<br/>

      		<fieldset class="fieldset1">
				<legend>플리스친구 계정관리</legend>
				<ul>
					<li><a href="KakaoService/getURL_PLUSFRIEND">GetURL</a> - 플러스친구 계정관리 팝업 URL</li>
          			<li><a href="KakaoService/listPlusFriendID">ListPlusFriendID</a> - 플러스친구 목록 확인</li>
				</ul>
			</fieldset>

			<fieldset class="fieldset1">
				<legend>발신번호 관리</legend>
				<ul>
					<li><a href="KakaoService/getURL_SENDER">GetURL</a> - 발신번호 관리 팝업 URL</li>
          			<li><a href="KakaoService/getSenderNumberList">GetSenderNumberList</a> - 발신번호 목록 확인</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>알림톡 템플릿 관리</legend>
				<ul>
					<li><a href="KakaoService/getURL_TEMPLATE">GetURL</a> - 알림톡 템플릿관리 팝업 URL</li>
          			<li><a href="KakaoService/listATSTemplate">ListATSTemplate</a> - 알림톡 템플릿 목록 확인</li>
				</ul>
			</fieldset>

		      <fieldset class="fieldset1">
				<legend>알림톡 전송</legend>
				<ul>
					<li><a href="KakaoService/sendATS_one">SendATS</a> - 알림톡 단건 전송</li>
         			<li><a href="KakaoService/sendATS_same">SendATS</a> - 알림톡 동일내용 대량 전송</li>
          			<li><a href="KakaoService/sendATS_multi">SendATS</a> - 알림톡 개별내용 대량 전송</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>친구톡 텍스트 전송</legend>
				<ul>
					<li><a href="KakaoService/sendFTS_one">SendFTS</a> - 친구톡 텍스트 단건 전송</li>
          			<li><a href="KakaoService/sendFTS_same">SendFTS</a> - 친구톡 텍스트 동일내용 대량전송</li>
          			<li><a href="KakaoService/sendFTS_multi">SendFTS</a> - 친구톡 텍스트 개별내용 대량전송</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>친구톡 이미지 전송</legend>
				<ul>
					<li><a href="KakaoService/sendFMS_one">SendFMS</a> - 친구톡 이미지 단건 전송</li>
          			<li><a href="KakaoService/sendFMS_same">SendFMS</a> - 친구톡 이미지 동일내용 대량전송</li>
          			<li><a href="KakaoService/sendFMS_multi">SendFMS</a> - 친구톡 이미지 개별내용 대량전송</li>
				</ul>
			</fieldset>
			
      		<fieldset class="fieldset1">
				<legend>전송내역조회</legend>
				<ul>
          			<li><a href="KakaoService/search">Search</a> - 전송내역 목록 조회</li>
          			<li><a href="KakaoService/getURL_BOX">GetURL</a> - 카카오톡 전송내역 팝업 URL</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>접수번호 관련 기능 (요청번호 미할당)</legend>
				<ul>
					<li><a href="KakaoService/getMessages">GetMessages</a> - 알림톡/친구톡 전송내역 확인</li>
					<li><a href="KakaoService/cancelReserve">CancelReserve</a> - 예약전송 취소</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>요청번호 할당 전송건 관련 기능</legend>
				<ul>
					<li><a href="KakaoService/getMessagesRN">GetMessagesRN</a> - 알림톡/친구톡 전송내역 확인</li>
					<li><a href="KakaoService/cancelReserveRN">CancelReserveRN</a> - 예약전송 취소</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>포인트관리</legend>
				<ul>
					<li><a href="KakaoService/getUnitCost">GetUnitCost</a> - 전송단가 확인</li>
          			<li><a href="KakaoService/getChargeInfo">GetChargeInfo</a> - 과금정보 확인</li>
          			<li><a href="BaseService/getBalance">GetBalance</a> - 연동회원 잔여포인트 확인</li>
          			<li><a href="BaseService/getPopbillURL_CHRG">GetPopbillURL</a> - 연동회원 포인트 충전 팝업 URL</li>
          			<li><a href="BaseService/getPartnerBalance">GetPartnerBalance</a> - 파트너 잔여포인트 확인</li>
          			<li><a href="BaseService/getPartnerURL">GetPartnerURL</a> - 파트너 포인트충전 URL</li>
				</ul>
			</fieldset>

      		<fieldset class="fieldset1">
				<legend>회원관리</legend>
				<ul>
					<li><a href="BaseService/checkIsMember">CheckIsMember</a> - 연동회원 가입여부 확인</li>
					<li><a href="BaseService/checkID">CheckID</a> - 연동회원 아이디 중복 확인</li>
					<li><a href="BaseService/joinMember">JoinMember</a> - 연동회원사 신규가입</li>
          			<li><a href="BaseService/getPopbillURL_LOGIN">GetPopbillURL</a> - 팝빌 로그인 URL</li>
          			<li><a href="BaseService/registContact">RegistContact</a> - 담당자 추가</li>
					<li><a href="BaseService/listContact">ListContact</a> - 담당자 목록 확인</li>
					<li><a href="BaseService/updateContact">UpdateContact</a> - 담당자 정보 수정</li>
					<li><a href="BaseService/getCorpInfo">GetCorpInfo</a> - 회사정보 확인</li>
					<li><a href="BaseService/updateCorpInfo">UpdateCorpInfo</a> - 회사정보 수정</li>
				</ul>
			</fieldset>


		 </div>
	</body>
</html>