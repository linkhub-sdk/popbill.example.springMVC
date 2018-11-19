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
        <c:if test="${StatementInfo != null}">
            <fieldset class="fieldset2">
                <legend>StatementInfo</legend>
                <ul>
                    <li>itemKey : ${StatementInfo.itemKey}</li>
                    <li>itemCode : ${StatementInfo.itemCode}</li>
                    <li>invoiceNum : ${StatementInfo.invoiceNum}</li>
                    <li>mgtKey : ${StatementInfo.mgtKey}</li>

                    <li>stateCode : ${StatementInfo.stateCode}</li>
                    <li>taxType : ${StatementInfo.taxType}</li>
                    <li>purposeType : ${StatementInfo.purposeType}</li>

                    <li>writeDate : ${StatementInfo.writeDate}</li>
                    <li>senderCorpName : ${StatementInfo.senderCorpName}</li>
                    <li>senderCorpNum : ${StatementInfo.senderCorpNum}</li>
                    <li>senderPrintYN : ${StatementInfo.senderPrintYN}</li>

                    <li>receiverCorpName : ${StatementInfo.receiverCorpName}</li>
                    <li>receiverCorpNum : ${StatementInfo.receiverCorpNum}</li>
                    <li>receiverPrintYN : ${StatementInfo.receiverPrintYN}</li>

                    <li>supplyCostTotal : ${StatementInfo.supplyCostTotal}</li>
                    <li>taxTotal : ${StatementInfo.taxTotal}</li>
                    <li>issueDT : ${StatementInfo.issueDT}</li>
                    <li>stateDT : ${StatementInfo.stateDT}</li>
                    <li>openYN : ${StatementInfo.openYN}</li>
                    <li>openDT : ${StatementInfo.openDT}</li>
                    <li>stateMemo : ${StatementInfo.stateMemo}</li>
                    <li>regDT : ${StatementInfo.regDT}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${StatementInfos != null}">
            <c:forEach items="${StatementInfos}" var="StatementInfo">
                <fieldset class="fieldset2">
                    <legend>StatementInfo : ${StatementInfo.mgtKey}</legend>
                    <ul>
                        <li>itemKey : ${StatementInfo.itemKey}</li>
                        <li>itemCode : ${StatementInfo.itemCode}</li>
                        <li>invoiceNum : ${StatementInfo.invoiceNum}</li>
                        <li>mgtKey : ${StatementInfo.mgtKey}</li>

                        <li>stateCode : ${StatementInfo.stateCode}</li>
                        <li>taxType : ${StatementInfo.taxType}</li>
                        <li>purposeType : ${StatementInfo.purposeType}</li>

                        <li>writeDate : ${StatementInfo.writeDate}</li>
                        <li>senderCorpName : ${StatementInfo.senderCorpName}</li>
                        <li>senderCorpNum : ${StatementInfo.senderCorpNum}</li>
                        <li>senderPrintYN : ${StatementInfo.senderPrintYN}</li>
                        <li>receiverCorpName : ${StatementInfo.receiverCorpName}</li>
                        <li>receiverCorpNum : ${StatementInfo.receiverCorpNum}</li>
                        <li>receiverPrintYN : ${StatementInfo.receiverPrintYN}</li>

                        <li>supplyCostTotal : ${StatementInfo.supplyCostTotal}</li>
                        <li>taxTotal : ${StatementInfo.taxTotal}</li>
                        <li>issueDT : ${StatementInfo.issueDT}</li>
                        <li>stateDT : ${StatementInfo.stateDT}</li>
                        <li>openYN : ${StatementInfo.openYN}</li>
                        <li>openDT : ${StatementInfo.openDT}</li>
                        <li>stateMemo : ${StatementInfo.stateMemo}</li>
                        <li>regDT : ${StatementInfo.regDT}</li>

                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
