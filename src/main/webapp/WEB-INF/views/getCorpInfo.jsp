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
            <ul>
                <li>ceoname (대표자 성명) : ${CorpInfo.ceoname}</li>
                <li>corpName (상호) : ${CorpInfo.corpName}</li>
                <li>addr (주소) : ${CorpInfo.addr}</li>
                <li>bizType (업태) : ${CorpInfo.bizType}</li>
                <li>bizClass (업종) : ${CorpInfo.bizClass}</li>
            </ul>
        </fieldset>
    </fieldset>
</div>
</body>
</html>
