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
            <fieldset class="fieldset2">
                <legend>TaxinvoiceXML</legend>
                <ul>
                    <li>code (응답코드) : ${TaxinvoiceXML.code}</li>
                    <li>message (응답메시지) : ${TaxinvoiceXML.message}</li>
                    <li>retObject (전자세금계산서 XML 문서) : ${TaxinvoiceXML.retObject}</li>
                </ul>
            </fieldset>
    </fieldset>
</div>
</body>
</html>
