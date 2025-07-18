<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>팝빌 API SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">실명조회 API SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <fieldset class="fieldset2">
            <legend>조회 결과</legend>
            <ul>
                <li> result (상태코드) : ${DepositorCheckInfo.result}</li>
                <li> resultMessage (상태메시지) : ${DepositorCheckInfo.resultMessage}</li>
                <li> accountName (예금주 성명) : ${DepositorCheckInfo.accountName}</li>
                <li> bankCode (기관코드) : ${DepositorCheckInfo.bankCode}</li>
                <li> accountNumber (계좌번호) : ${DepositorCheckInfo.accountNumber}</li>
                <li> identityNumType (실명번호 유형) : ${DepositorCheckInfo.identityNumType}</li>
                <li> identityNum (실명번호) : ${DepositorCheckInfo.identityNum}</li>
                <li> checkDT (확인일시) : ${DepositorCheckInfo.checkDT}</li>
            </ul>
        </fieldset>            
    </fieldset>
    <br/>
</div>
</body>
</html>

