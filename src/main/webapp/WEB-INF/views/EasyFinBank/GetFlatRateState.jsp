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
        <ul>
            <li>referenceID (계좌 아이디) : ${State.referenceID}</li>
            <li>contractDT (정액제 서비스 시작일시) : ${State.contractDT}</li>
            <li>useEndDate (정액제 서비스 종료일) : ${State.useEndDate}</li>
            <li>baseDate (자동연장 결제일) : ${State.baseDate}</li>
            <li>state (정액제 서비스 상태) : ${State.state}</li>
            <li>closeRequestYN (정액제 서비스 해지신청 여부) : ${State.closeRequestYN}</li>
            <li>useRestrictYN (정액제 서비스 사용제한 여부) : ${State.useRestrictYN}</li>
            <li>closeOnExpired (정액제 서비스 만료 시 해지 여부) : ${State.closeOnExpired}</li>
            <li>unPaidYN (미수금 보유 여부) : ${State.unPaidYN}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>
