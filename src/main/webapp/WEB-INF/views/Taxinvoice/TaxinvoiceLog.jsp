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
        <c:forEach items="${TaxinvoiceLogs}" var="TaxinvoiceLog">
            <fieldset class="fieldset2">
                <ul>
                    <li>docLogType (로그타입) : ${TaxinvoiceLog.docLogType}</li>
                    <li>log (이력정보) : ${TaxinvoiceLog.log}</li>
                    <li>procType (처리형태) : ${TaxinvoiceLog.procType}</li>
                    <li>procCorpName (처리회사명) : ${TaxinvoiceLog.procCorpName}</li>
                    <li>procContactName (처리담당자) : ${TaxinvoiceLog.procContactName}</li>
                    <li>procMemo (처리메모) : ${TaxinvoiceLog.procMemo}</li>
                    <li>regDT (등록일시) : ${TaxinvoiceLog.regDT}</li>
                    <li>ip (아이피) : ${TaxinvoiceLog.IP}</li>
                </ul>
            </fieldset>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>
