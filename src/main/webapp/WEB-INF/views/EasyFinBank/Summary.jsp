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
            <li>count (수집 건수) : ${SummaryResult.count}</li>
            <li>cntAccIn (입금거래 건수) : ${SummaryResult.cntAccIn}</li>
            <li>cntAccOut (출금거래 건수) : ${SummaryResult.cntAccOut}</li>
            <li>totalAccIn (입금액 합계) : ${SummaryResult.totalAccIn}</li>
            <li>totalAccOut (출금액 합게) : ${SummaryResult.totalAccOut}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>