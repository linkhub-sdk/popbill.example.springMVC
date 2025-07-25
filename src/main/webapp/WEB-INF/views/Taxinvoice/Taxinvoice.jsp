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
            <legend>Taxinvoice</legend>
            <ul>
                <li>NTSConfirmNum (국세청승인번호) : ${Taxinvoice.NTSConfirmNum}</li>
                <li>issueType (발행형태) : ${Taxinvoice.issueType}</li>
                <li>taxType (과세형태) : ${Taxinvoice.taxType}</li>
                <li>chargeDirection (과금방향) : ${Taxinvoice.chargeDirection}</li>
                <li>serialNum (일련번호) : ${Taxinvoice.serialNum}</li>
                <li>kwon (책번호 "권" 항목) : ${Taxinvoice.kwon}</li>
                <li>ho (책번호 "호" 항목) : ${Taxinvoice.ho}</li>
                <li>writeDate (작성일자) : ${Taxinvoice.writeDate}</li>
                <li>purposeType (영수/청구) : ${Taxinvoice.purposeType}</li>
                <li>supplyCostTotal (공급가액 합계) : ${Taxinvoice.supplyCostTotal}</li>
                <li>taxTotal (세액 합계) : ${Taxinvoice.taxTotal}</li>
                <li>totalAmount (합계금액) : ${Taxinvoice.totalAmount}</li>
                <li>cash (현금) : ${Taxinvoice.cash}</li>
                <li>chkBill (수표) : ${Taxinvoice.chkBill}</li>
                <li>credit (외상) : ${Taxinvoice.credit}</li>
                <li>note (어음) : ${Taxinvoice.note}</li>
                <li>remark1 (비고1) : ${Taxinvoice.remark1}</li>
                <li>remark2 (비고2) : ${Taxinvoice.remark2}</li>
                <li>remark3 (비고3) : ${Taxinvoice.remark3}</li>

                <li>invoicerMgtKey (공급자 문서번호) : ${Taxinvoice.invoicerMgtKey}</li>
                <li>invoicerCorpNum (공급자 사업자번호) : ${Taxinvoice.invoicerCorpNum}</li>
                <li>invoicerTaxRegID (공급자 종사업장 식별번호) : ${Taxinvoice.invoicerTaxRegID}</li>
                <li>invoicerCorpName (공급자 상호) : ${Taxinvoice.invoicerCorpName}</li>
                <li>invoicerCEOName (공급자 대표자 성명) : ${Taxinvoice.invoicerCEOName}</li>
                <li>invoicerAddr (공급자 주소) : ${Taxinvoice.invoicerAddr}</li>
                <li>invoicerBizType (공급자 업태) : ${Taxinvoice.invoicerBizType}</li>
                <li>invoicerBizClass (공급자 종목) : ${Taxinvoice.invoicerBizClass}</li>
                <li>invoicerContactName (공급자 담당자 성명) : ${Taxinvoice.invoicerContactName}</li>
                <li>invoicerDeptName (공급자 담당자 부서명) : ${Taxinvoice.invoicerDeptName}</li>
                <li>invoicerTEL (공급자 담당자 연락처) : ${Taxinvoice.invoicerTEL}</li>
                <li>invoicerHP (공급자 담당자 휴대폰) : ${Taxinvoice.invoicerHP}</li>
                <li>invoicerEmail (공급자 담당자 메일) : ${Taxinvoice.invoicerEmail}</li>
                <li>invoicerSMSSendYN (공급자 알림문자 전송 여부) : ${Taxinvoice.invoicerSMSSendYN}</li>

                <li>invoiceeMgtKey (공급받는자 문서번호) : ${Taxinvoice.invoiceeMgtKey}</li>
                <li>invoiceeType (공급받는자 유형) : ${Taxinvoice.invoiceeType}</li>
                <li>invoiceeCorpNum (공급받는자 등록번호) : ${Taxinvoice.invoiceeCorpNum}</li>
                <li>invoiceeTaxRegID (공급받는자 종사업장 식별번호) : ${Taxinvoice.invoiceeTaxRegID}</li>
                <li>invoiceeCorpName (공급받는자 상호) : ${Taxinvoice.invoiceeCorpName}</li>
                <li>invoiceeCEOName (공급받는자 대표자 성명) : ${Taxinvoice.invoiceeCEOName}</li>
                <li>invoiceeAddr (공급받는자 주소) : ${Taxinvoice.invoiceeAddr}</li>
                <li>invoiceeBizType (공급받는자 업태) : ${Taxinvoice.invoiceeBizType}</li>
                <li>invoiceeBizClass (공급받는자 종목) : ${Taxinvoice.invoiceeBizClass}</li>
                <li>closeDownState (공급받는자 휴폐업상태) : ${Taxinvoice.closeDownState}</li>
                <li>closeDownStateDate (공급받는자 휴폐업일자) : ${Taxinvoice.closeDownStateDate}</li>
                <li>invoiceeContactName1 (공급받는자 담당자 성명) : ${Taxinvoice.invoiceeContactName1}</li>
                <li>invoiceeDeptName1 (공급받는자 담당자 부서명) : ${Taxinvoice.invoiceeDeptName1}</li>
                <li>invoiceeTEL1 (공급받는자 담당자 연락처) : ${Taxinvoice.invoiceeTEL1}</li>
                <li>invoiceeHP1 (공급받는자 담당자 휴대폰) : ${Taxinvoice.invoiceeHP1}</li>
                <li>invoiceeEmail1 (공급받는자 담당자 메일) : ${Taxinvoice.invoiceeEmail1}</li>
                <li>invoiceeSMSSendYN (공급받는자 알림문자 전송 여부) : ${Taxinvoice.invoiceeSMSSendYN}</li>

                <li>trusteeMgtKey (수탁자 문서번호) : ${Taxinvoice.trusteeMgtKey}</li>
                <li>trusteeCorpNum (수탁자 사업자번호) : ${Taxinvoice.trusteeCorpNum}</li>
                <li>trusteeTaxRegID (수탁자 종사업장 식별번호) : ${Taxinvoice.trusteeTaxRegID}</li>
                <li>trusteeCorpName (수탁자 상호) : ${Taxinvoice.trusteeCorpName}</li>
                <li>trusteeCEOName (수탁자 대표자 성명) : ${Taxinvoice.trusteeCEOName}</li>
                <li>trusteeAddr (수탁자 주소) : ${Taxinvoice.trusteeAddr}</li>
                <li>trusteeBizType (수탁자 업태) : ${Taxinvoice.trusteeBizType}</li>
                <li>trusteeBizClass (수탁자 종목) : ${Taxinvoice.trusteeBizClass}</li>
                <li>trusteeContactName (수탁자 담당자 성명) : ${Taxinvoice.trusteeContactName}</li>
                <li>trusteeDeptName (수탁자 담당자 부서명) : ${Taxinvoice.trusteeDeptName}</li>
                <li>trusteeTEL (수탁자 담당자 연락처) : ${Taxinvoice.trusteeTEL}</li>
                <li>trusteeHP (수탁자 담당자 휴대폰) : ${Taxinvoice.trusteeHP}</li>
                <li>trusteeEmail (수탁자 메일) : ${Taxinvoice.trusteeEmail}</li>
                <li>trusteeSMSSendYN (수탁자 알림문자 전송 여부) : ${Taxinvoice.trusteeSMSSendYN}</li>

                <li>modifyCode (수정 사유코드) : ${Taxinvoice.modifyCode}</li>
                <li>orgNTSConfirmNum (당초 국세청승인번호) : ${Taxinvoice.orgNTSConfirmNum}</li>
                <li>businessLicenseYN (팝빌에 등록된 사업자등록증 첨부 여부) : ${Taxinvoice.businessLicenseYN}</li>
                <li>bankBookYN (팝빌에 등록된 통장사본 첨부 여부) : ${Taxinvoice.bankBookYN}</li>
            </ul>
            <fieldset class="fieldset3">
                <legend>detailList (품목 상세정보)</legend>
                <c:forEach items="${Taxinvoice.detailList}" var="TaxinvoiceDetail">
                    <ul>
                        <li>serialNum (일련번호) : ${TaxinvoiceDetail.serialNum}</li>
                        <li>purchaseDT (거래일자) : ${TaxinvoiceDetail.purchaseDT}</li>
                        <li>itemName (품명) : ${TaxinvoiceDetail.itemName}</li>
                        <li>spec (규격) : ${TaxinvoiceDetail.spec}</li>
                        <li>qty (수량) : ${TaxinvoiceDetail.qty}</li>
                        <li>unitCost (단가) : ${TaxinvoiceDetail.unitCost}</li>
                        <li>supplyCost (공급가액) : ${TaxinvoiceDetail.supplyCost}</li>
                        <li>tax (세액) : ${TaxinvoiceDetail.tax}</li>
                        <li>remark (비고) : ${TaxinvoiceDetail.remark}</li>
                    </ul>
                </c:forEach>
            </fieldset>
            <fieldset class="fieldset3">
                <legend>addContactList (공급받는자 추가담당자 정보)</legend>
                <c:forEach items="${Taxinvoice.addContactList}" var="TaxinvoiceAddContact">
                    <ul>
                        <li>serialNum (일련번호) : ${TaxinvoiceAddContact.serialNum}</li>
                        <li>contactName (담당자 성명) : ${TaxinvoiceAddContact.contactName}</li>
                        <li>email (메일) : ${TaxinvoiceAddContact.email}</li>
                    </ul>
                </c:forEach>
            </fieldset>
        </fieldset>
    </fieldset>
</div>
</body>
</html>