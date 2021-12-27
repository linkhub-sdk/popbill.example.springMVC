<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <c:forEach items="${FaxResults}" var="FaxResult" varStatus="status">
            <fieldset class="fieldset2">
                <legend>팩스전송 상태정보</legend>
                <ul>
                    <li>state (전송상태 코드) : ${FaxResult.state}</li>
                    <li>result (전송결과 코드) : ${FaxResult.result}</li>
                    <li>sendNum (발신번호) : ${FaxResult.sendNum}</li>
                    <li>senderName (발신자명) : ${FaxResult.senderName}</li>
                    <li>receiveNumType (수신번호 유형) : ${FaxResult.receiveNumType}</li>
                    <li>receiveNum (수신번호) : ${FaxResult.receiveNum}</li>
                    <li>receiveName (수신자명) : ${FaxResult.receiveName}</li>
                    <li>title (팩스제목) : ${FaxResult.title}</li>
                    <li>sendPageCnt (전체 페이지수) : ${FaxResult.sendPageCnt}</li>
                    <li>successPageCnt (성공 페이지수) : ${FaxResult.successPageCnt}</li>
                    <li>failPageCnt (실패 페이지수) : ${FaxResult.failPageCnt}</li>
                    <li>refundPageCnt (환불 페이지수) : ${FaxResult.refundPageCnt}</li>
                    <li>cancelPageCnt (취소 페이지수) : ${FaxResult.cancelPageCnt}</li>
                    <li>receiptDT (접수일시) : ${FaxResult.receiptDT}</li>
                    <li>reserveDT (예약일시) : ${FaxResult.reserveDT}</li>
                    <li>sendDT (전송일시) : ${FaxResult.sendDT}</li>
                    <li>resultDT (전송결과 수신일시) : ${FaxResult.resultDT}</li>
                    <li>receiptNum (접수번호) : ${FaxResult.receiptNum}</li>
                    <li>requestNum (요청번호) : ${FaxResult.requestNum}</li>
                    <li>fileNames (전송 파일명 리스트) : ${fn:join(FaxResult.fileNames,", ")}</li>
                    <li>chargePageCnt (과금 페이지수) : ${FaxResult.chargePageCnt}</li>
                    <li>tiffFileSize (변환파일용량 (단위:byte) ) : ${FaxResult.tiffFileSize}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>