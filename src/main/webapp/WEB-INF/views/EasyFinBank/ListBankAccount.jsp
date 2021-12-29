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
        <c:forEach items="${BankAccountList}" var="Account">
            <fieldset class="fieldset2">
                <ul>
                    <li>bankCode (기관코드) : ${Account.bankCode}</li>
                    <li>accountNumber (계좌번호) : ${Account.accountNumber}</li>
                    <li>accountName (계좌 별칭) : ${Account.accountName}</li>
                    <li>accountType (계좌 유형) : ${Account.accountType}</li>
                    <li>state (계좌 정액제 상태) : ${Account.state}</li>
                    <li>regDT (등록일시) : ${Account.regDT}</li>
                    <li>memo (메모) : ${Account.memo}</li>
                    
                    <li>contractDT (정액제 서비스 시작일시) : ${Account.contractDT}</li>
                    <li>useEndDate (정액제 서비스 종료일) : ${Account.useEndDate}</li>
                    <li>baseDate (자동연장 결제일) : ${Account.baseDate}</li>
                    <li>contractState (정액제 서비스 상태) : ${Account.contractState}</li>
                    <li>closeRequestYN (정액제 서비스 해지신청 여부) : ${Account.closeRequestYN}</li>
                    <li>useRestrictYN (정액제 서비스 사용제한 여부) : ${Account.useRestrictYN}</li>
                    <li>closeOnExpired (정액제 서비스 만료 시 해지 여부) : ${Account.closeOnExpired}</li>
                    <li>unPaidYN (미수금 보유 여부) : ${Account.unPaidYN}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>