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
    <p class="heading1">PaymentHistory</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <ul>
            <li>productType (결제 내용) : ${PaymentHistory.productType}</li>
            <li>productName (정액제 상품명) : ${PaymentHistory.productName}</li>
            <li>settleType (결제유형) : ${PaymentHistory.settleType}</li>
            <li>settlerName (담당자명) : ${PaymentHistory.settlerName}</li>
            <li>settlerEmail (담당자메일) : ${PaymentHistory.settlerEmail}</li>
            <li>settleCost (결제금액) : ${PaymentHistory.settleCost}</li>
            <li>settlePoint (충전포인트) : ${PaymentHistory.settlePoint}</li>
            <li>settleState (결제상태) : ${PaymentHistory.settleState}</li>
            <li>regDT (등록일시) : ${PaymentHistory.regDT}</li>
            <li>stateDT (상태일시) : ${PaymentHistory.stateDT}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>
