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
            <legend>Statement</legend>
            <ul>
                <li> itemCode(문서종류코드) : ${Statement.itemCode}</li>
                <li> mgtKey(문서번호) : ${Statement.mgtKey}</li>
                <li> formCode(맞춤양식 코드) : ${Statement.formCode}</li>
                <li> writeDate(작성일자) : ${Statement.writeDate}</li>
                <li> taxType(세금형태) : ${Statement.taxType}</li>
                <li> purposeType(영수/청구) : ${Statement.purposeType}</li>
                <li> serialNum(일련번호) : ${Statement.serialNum}</li>
                <li> taxTotal(세액 합계) : ${Statement.taxTotal}</li>
                <li> supplyCostTotal(공급가액 합계) : ${Statement.supplyCostTotal}</li>
                <li> totalAmount(합계금액) : ${Statement.totalAmount}</li>
                <li> remark1(비고1) : ${Statement.remark1}</li>
                <li> remark2(비고2) : ${Statement.remark2}</li>
                <li> remark3(비고3) : ${Statement.remark3}</li>

                <li> senderCorpNum(발신자 사업자번호) : ${Statement.senderCorpNum}</li>
                <li> senderTaxRegID(발신자 종사업장번호) : ${Statement.senderTaxRegID}</li>
                <li> senderCorpName(발신자 상호) : ${Statement.senderCorpName}</li>
                <li> senderCEOName(발신자 대표자 성명) : ${Statement.senderCEOName}</li>
                <li> senderAddr(발신자 주소) : ${Statement.senderAddr}</li>
                <li> senderBizType(발신자 업태) : ${Statement.senderBizType}</li>
                <li> senderBizClass(발신자 종목) : ${Statement.senderBizClass}</li>
                <li> senderContactName(발신자 성명) : ${Statement.senderContactName}</li>
                <li> senderDeptName(발신자 부서명) : ${Statement.senderDeptName}</li>
                <li> senderTEL(발신자 연락처) : ${Statement.senderTEL}</li>
                <li> senderHP(발신자 휴대전화) : ${Statement.senderHP}</li>
                <li> senderEmail(발신자 이메일주소) : ${Statement.senderEmail}</li>
                <li> senderFAX(발신자 팩스번호) : ${Statement.senderFAX}</li>

                <li> receiverCorpNum(수신자 사업자번호) : ${Statement.receiverCorpNum}</li>
                <li> receiverTaxRegID(수신자 종사업장번호) : ${Statement.receiverTaxRegID}</li>
                <li> receiverCorpName(수신자 상호) : ${Statement.receiverCorpName}</li>
                <li> receiverCEOName(수신자 대표자 성명) : ${Statement.receiverCEOName}</li>
                <li> receiverAddr(수신자 주소) : ${Statement.receiverAddr}</li>
                <li> receiverBizType(수신자 업태) : ${Statement.receiverBizType}</li>
                <li> receiverBizClass(수신자 종목) : ${Statement.receiverBizClass}</li>
                <li> receiverContactName(수신자 성명) : ${Statement.receiverContactName}</li>
                <li> receiverDeptName(수신자 부서명) : ${Statement.receiverDeptName}</li>
                <li> receiverTEL(수신자 연락처) : ${Statement.receiverTEL}</li>
                <li> receiverHP(수신자 휴대전화) : ${Statement.receiverHP}</li>
                <li> receiverEmail(수신자 이메일주소) : ${Statement.receiverEmail}</li>
                <li> receiverFAX(수신자 팩스번호) : ${Statement.receiverFAX}</li>
            </ul>

            <c:forEach items="${Statement.detailList}" var="StatementDetail" varStatus="status">
                <fieldset class="fieldset3">
                    <legend>전자명세서 상세항목 [ ${status.index+1} ] </legend>
                    <ul>
                        <li>serialNum (일련번호) : ${StatementDetail.serialNum}</li>
                        <li>purchaseDT (거래일자) : ${StatementDetail.purchaseDT}</li>
                        <li>itemName (품목명) : ${StatementDetail.itemName}</li>
                        <li>spec (규격) : ${StatementDetail.spec}</li>
                        <li>qty (수량) : ${StatementDetail.qty}</li>
                        <li>unitCost (단가) : ${StatementDetail.unitCost}</li>
                        <li>supplyCost (공급가액) : ${StatementDetail.supplyCost}</li>
                        <li>tax (세액) : ${StatementDetail.tax}</li>
                        <li>remark (비고) : ${StatementDetail.remark}</li>
                        <li>spare1 (비고) : ${StatementDetail.spare1}</li>
                        <li>spare2 (비고) : ${StatementDetail.spare2}</li>
                        <li>spare3 (비고) : ${StatementDetail.spare3}</li>
                        <li>spare4 (비고) : ${StatementDetail.spare4}</li>
                        <li>spare5 (비고) : ${StatementDetail.spare5}</li>
                    </ul>
                </fieldset>
            </c:forEach>
            <fieldset class="fieldset3">
                <c:if test="${Statement.propertyBag != null}">
                    <legend>propertyBag</legend>
                    <ul>
                        <c:forEach items="${Statement.propertyBag}" var="propertyBag">
                            <li>${propertyBag.key} : ${propertyBag.value}</li>
                        </c:forEach>
                    </ul>
                </c:if>
            </fieldset>
        </fieldset>
    </fieldset>
</div>
</body>
</html>