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
    <p class="heading1">Response</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <c:if test="${sentInfos != null}">
            <fieldset class="fieldset2">
                <ul>

                    <li>contentType (카카오톡 유형) : ${sentInfos.contentType}</li>
                    <li>templateCode (템플릿 코드) : ${sentInfos.templateCode}</li>
                    <li>plusFriendID (카카오톡 채널 아이디) : ${sentInfos.plusFriendID}</li>
                    <li>sendNum (발신번호) : ${sentInfos.sendNum}</li>
                    <li>altSubject (대체문자 제목) : ${sentInfos.altSubject}</li>
                    <li>altContent (대체문자 내용) : ${sentInfos.altContent}</li>
                    <li>altSendType (대체문자 전송유형) : ${sentInfos.altSendType}</li>
                    <li>reserveDT (예약일시) : ${sentInfos.reserveDT}</li>
                    <li>adsYN (광고전송 여부) : ${sentInfos.adsYN}</li>
                    <li>imageURL (친구톡 이미지 URL) : ${sentInfos.imageURL}</li>
                    <li>sendCnt (전송건수) : ${sentInfos.sendCnt}</li>
                    <li>successCnt (성공건수) : ${sentInfos.successCnt}</li>
                    <li>failCnt (실패건수) : ${sentInfos.failCnt}</li>
                    <li>altCnt (대체문자 건수) : ${sentInfos.altCnt}</li>
                    <li>cancelCnt (취소건수) : ${sentInfos.cancelCnt}</li>
                </ul>
            </fieldset>
        </c:if>
        <fieldset class="fieldset2">
            <legend>버튼정보</legend>
            <c:if test="${sentInfos.btns != null}">
                <c:forEach items="${sentInfos.btns}" var="btnInfo" varStatus="status">
                    <fieldset class="fieldset3">
                        <legend>버튼정보 [ ${status.index+1} ]</legend>
                        <ul>
                            <li>n (버튼명) : ${btnInfo.n}</li>
                            <li>t (버튼유형) : ${btnInfo.t}</li>
                            <li>u1 (버튼링크1) : ${btnInfo.u1}</li>
                            <li>u2 (버튼링크2) : ${btnInfo.u2}</li>
                        </ul>
                    </fieldset>
                </c:forEach>
            </c:if>
        </fieldset>

        <fieldset class="fieldset2">
            <legend>전송결과 정보</legend>
            <c:if test="${sentInfos.msgs != null}">
                <c:forEach items="${sentInfos.msgs}" var="msgInfo" varStatus="status">
                    <fieldset class="fieldset3">
                        <legend>개별전송 정보 [ ${status.index+1} ]</legend>
                        <ul>
                            <li>serialNum </li>
                            <li>state (전송상태 코드) : ${msgInfo.state}</li>
                            <li>sendDT (전송일시) : ${msgInfo.sendDT}</li>
                            <li>receiveNum (수신번호) : ${msgInfo.receiveNum}</li>
                            <li>receiveName (수신자명) : ${msgInfo.receiveName}</li>
                            <li>content (알림톡/친구톡 내용) : ${msgInfo.content}</li>
                            <li>result (알림톡/친구톡 전송결과 코드) : ${msgInfo.result}</li>
                            <li>resultDT (알림톡/친구톡 전송결과 수신일시) : ${msgInfo.resultDT}</li>
                            <li>altSubject (대체문자 제목) : ${msgInfo.altSubject}</li>
                            <li>altContent (대체문자 내용) : ${msgInfo.altContent}</li>
                            <li>altContentType (대체문자 전송유형) : ${msgInfo.altContentType}</li>
                            <li>altSendDT (대체문자 전송일시) : ${msgInfo.altSendDT}</li>
                            <li>altResult (대체문자 전송결과 코드) : ${msgInfo.altResult}</li>
                            <li>altResultDT (대체문자 전송결과 수신일시) : ${msgInfo.altResultDT}</li>
                            <li>receiptNum (접수번호) : ${msgInfo.receiptNum}</li>
                            <li>requestNum (요청번호) : ${msgInfo.requestNum}</li>
                            <li>interOPRefKey (참조키) : ${msgInfo.interOPRefKey}</li>
                        </ul>
                    </fieldset>
                </c:forEach>
            </c:if>
        </fieldset>
    </fieldset>
</div>
</body>
</html>
