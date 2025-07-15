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
        <c:if test="${TaxinvoiceInfo != null}">
            <fieldset class="fieldset2">
                <legend>TaxinvoiceInfo</legend>
                <ul>
                    <li>itemKey (팝빌에서 할당한 식별번호) : ${TaxinvoiceInfo.itemKey}</li>
                    <li>taxType (과세형태) : ${TaxinvoiceInfo.taxType}</li>
                    <li>writeDate (작성일자) : ${TaxinvoiceInfo.writeDate}</li>
                    <li>regDT (임시저장 일시) : ${TaxinvoiceInfo.regDT}</li>
                    <li>issueType (발행형태) : ${TaxinvoiceInfo.issueType}</li>
                    <li>supplyCostTotal (공급가액 합계) : ${TaxinvoiceInfo.supplyCostTotal}</li>
                    <li>taxTotal (세액 합계) : ${TaxinvoiceInfo.taxTotal}</li>
                    <li>purposeType (영수/청구) : ${TaxinvoiceInfo.purposeType}</li>
                    <li>issueDT (발행일시) : ${TaxinvoiceInfo.issueDT}</li>
                    <li>lateIssueYN (지연발행 여부) : ${TaxinvoiceInfo.lateIssueYN}</li>
                    <li>openYN (개봉 여부) : ${TaxinvoiceInfo.openYN}</li>
                    <li>openDT (개봉일시) : ${TaxinvoiceInfo.openDT}</li>
                    <li>stateMemo (상태메모) : ${TaxinvoiceInfo.stateMemo}</li>
                    <li>stateCode (상태코드) : ${TaxinvoiceInfo.stateCode}</li>
                    <li>stateDT (상태 변경일시) : ${TaxinvoiceInfo.stateDT}</li>
                    <li>ntsconfirmNum (국세청승인번호) : ${TaxinvoiceInfo.NTSConfirmNum}</li>
                    <li>ntsresult (국세청 전송결과) : ${TaxinvoiceInfo.NTSResult}</li>
                    <li>ntssendDT (국세청 전송일시) : ${TaxinvoiceInfo.NTSSendDT}</li>
                    <li>ntsresultDT (국세청 결과 수신일시) : ${TaxinvoiceInfo.NTSResultDT}</li>
                    <li>ntssendErrCode (결과코드) : ${TaxinvoiceInfo.NTSSendErrCode}</li>
                    <li>modifyCode (수정세금계산서 발행시 입력한 사유코드) : ${TaxinvoiceInfo.modifyCode}</li>
                    <li>interOPYN (연동문서 여부) : ${TaxinvoiceInfo.interOPYN}</li>

                    <li>invoicerCorpName (공급자 상호) : ${TaxinvoiceInfo.invoicerCorpName}</li>
                    <li>invoicerCorpNum (공급자 사업자번호) : ${TaxinvoiceInfo.invoicerCorpNum}</li>
                    <li>invoicerMgtKey (공급자 문서번호) : ${TaxinvoiceInfo.invoicerMgtKey}</li>
                    <li>invoicerPrintYN (공급자 인쇄여부) : ${TaxinvoiceInfo.invoicerPrintYN}</li>

                    <li>invoiceeCorpName (공급받는자 상호) : ${TaxinvoiceInfo.invoiceeCorpName}</li>
                    <li>invoiceeCorpNum (공급받는자 사업자번호) : ${TaxinvoiceInfo.invoiceeCorpNum}</li>
                    <li>invoiceeMgtKey (공급받는자 문서번호) : ${TaxinvoiceInfo.invoiceeMgtKey}</li>
                    <li>invoiceePrintYN (공급받는자 인쇄여부) : ${TaxinvoiceInfo.invoiceePrintYN}</li>
                    <li>closeDownState (공급받는자 휴폐업상태) : ${TaxinvoiceInfo.closeDownState}</li>
                    <li>closeDownStateDate (공급받는자 휴폐업일자) : ${TaxinvoiceInfo.closeDownStateDate}</li>

                    <li>trusteeCorpName (수탁자 상호) : ${TaxinvoiceInfo.trusteeCorpName}</li>
                    <li>trusteeCorpNum (수탁사 사업자번호) : ${TaxinvoiceInfo.trusteeCorpNum}</li>
                    <li>trusteeMgtKey (수탁자 문서번호) : ${TaxinvoiceInfo.trusteeMgtKey}</li>
                    <li>trusteePrintYN (수탁자 인쇄여부) : ${TaxinvoiceInfo.trusteePrintYN}</li>
                </ul>
            </fieldset>
        </c:if>

        <c:if test="${TaxinvoiceInfos != null}">
            <c:forEach items="${TaxinvoiceInfos}" var="TaxinvoiceInfo">
                <fieldset class="fieldset2">
                    <ul>
                        <li>itemKey (팝빌에서 할당한 식별번호) : ${TaxinvoiceInfo.itemKey}</li>
                        <li>taxType (과세형태) : ${TaxinvoiceInfo.taxType}</li>
                        <li>writeDate (작성일자) : ${TaxinvoiceInfo.writeDate}</li>
                        <li>regDT (임시저장 일시) : ${TaxinvoiceInfo.regDT}</li>
                        <li>issueType (발행형태) : ${TaxinvoiceInfo.issueType}</li>
                        <li>supplyCostTotal (공급가액 합계) : ${TaxinvoiceInfo.supplyCostTotal}</li>
                        <li>taxTotal (세액 합계) : ${TaxinvoiceInfo.taxTotal}</li>
                        <li>purposeType (영수/청구) : ${TaxinvoiceInfo.purposeType}</li>
                        <li>issueDT (발행일시) : ${TaxinvoiceInfo.issueDT}</li>
                        <li>lateIssueYN (지연발행 여부) : ${TaxinvoiceInfo.lateIssueYN}</li>
                        <li>openYN (개봉 여부) : ${TaxinvoiceInfo.openYN}</li>
                        <li>openDT (개봉일시) : ${TaxinvoiceInfo.openDT}</li>
                        <li>stateMemo (상태메모) : ${TaxinvoiceInfo.stateMemo}</li>
                        <li>stateCode (상태코드) : ${TaxinvoiceInfo.stateCode}</li>
                        <li>stateDT (상태 변경일시) : ${TaxinvoiceInfo.stateDT}</li>
                        <li>ntsconfirmNum (국세청승인번호) : ${TaxinvoiceInfo.NTSConfirmNum}</li>
                        <li>ntsresult (국세청 전송결과) : ${TaxinvoiceInfo.NTSResult}</li>
                        <li>ntssendDT (국세청 전송일시) : ${TaxinvoiceInfo.NTSSendDT}</li>
                        <li>ntsresultDT (국세청 결과 수신일시) : ${TaxinvoiceInfo.NTSResultDT}</li>
                        <li>ntssendErrCode (결과코드) : ${TaxinvoiceInfo.NTSSendErrCode}</li>
                        <li>modifyCode (수정 사유코드) : ${TaxinvoiceInfo.modifyCode}</li>
                        <li>interOPYN (연동문서 여부) : ${TaxinvoiceInfo.interOPYN}</li>

                        <li>invoicerCorpName (공급자 상호) : ${TaxinvoiceInfo.invoicerCorpName}</li>
                        <li>invoicerCorpNum (공급자 사업자번호) : ${TaxinvoiceInfo.invoicerCorpNum}</li>
                        <li>invoicerMgtKey (공급자 문서번호) : ${TaxinvoiceInfo.invoicerMgtKey}</li>
                        <li>invoicerPrintYN (공급자 인쇄여부) : ${TaxinvoiceInfo.invoicerPrintYN}</li>

                        <li>invoiceeCorpName (공급받는자 상호) : ${TaxinvoiceInfo.invoiceeCorpName}</li>
                        <li>invoiceeCorpNum (공급받는자 사업자번호) : ${TaxinvoiceInfo.invoiceeCorpNum}</li>
                        <li>invoiceeMgtKey (공급받는자 문서번호) : ${TaxinvoiceInfo.invoiceeMgtKey}</li>
                        <li>invoiceePrintYN (공급받는자 인쇄여부) : ${TaxinvoiceInfo.invoiceePrintYN}</li>
                        <li>closeDownState (공급받는자 휴폐업상태) : ${TaxinvoiceInfo.closeDownState}</li>
                        <li>closeDownStateDate (공급받는자 휴폐업일자) : ${TaxinvoiceInfo.closeDownStateDate}</li>

                        <li>trusteeCorpName (수탁자 상호) : ${TaxinvoiceInfo.trusteeCorpName}</li>
                        <li>trusteeCorpNum (수탁사 사업자번호) : ${TaxinvoiceInfo.trusteeCorpNum}</li>
                        <li>trusteeMgtKey (수탁자 문서번호) : ${TaxinvoiceInfo.trusteeMgtKey}</li>
                        <li>trusteePrintYN (수탁자 인쇄여부) : ${TaxinvoiceInfo.trusteePrintYN}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>
