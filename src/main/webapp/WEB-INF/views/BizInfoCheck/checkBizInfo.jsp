<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/resources/main.css"
    media="screen" />
<title>팝빌 API SDK SpringMVC Example.</title>
</head>
<body>
    <div id="content">
        <p class="heading1">기업정보조회 API SDK SpringMVC Example.</p>
        <br />
        <fieldset class="fieldset1">
            <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
            <fieldset class="fieldset2">
                <legend>조회 결과</legend>
                <ul>
                    <li>corpNum (사업자번호) : ${BizInfo.corpNum}</li>
                    <li>companyRegNum (법인번호): ${BizInfo.companyRegNum}</li>
                    <li>checkDT (확인일시) : ${BizInfo.checkDT}</li>
                    <li>corpName (상호): ${BizInfo.corpName}</li>
                    <li>CEOName (대표자명) : ${BizInfo.CEOName}</li>
                    <li>corpCode (기업형태코드): ${BizInfo.corpCode}</li>
                    <li>corpScaleCode (기업규모코드): ${BizInfo.corpScaleCode}</li>
                    <li>personCorpCode (개인법인코드): ${BizInfo.personCorpCode}</li>
                    <li>headOfficeCode (본점지점코드) : ${BizInfo.headOfficeCode}</li>
                    <li>industryCode (산업코드) : ${BizInfo.industryCode}</li>
                    <li>establishCode (설립구분코드) : ${BizInfo.establishCode}</li>
                    <li>establishDate (설립일자) : ${BizInfo.establishDate}</li>
                    <li>workPlaceCode (사업장구분코드): ${BizInfo.workPlaceCode}</li>
                    <li>addrCode (주소구분코드) : ${BizInfo.addrCode}</li>
                    <li>zipCode (우편번호) : ${BizInfo.zipCode}</li>
                    <li>addr (주소) : ${BizInfo.addr}</li>
                    <li>addrDetail (상세주소) : ${BizInfo.addrDetail}</li>
                    <li>enAddr (영문주소) : ${BizInfo.enAddr}</li>
                    <li>bizClass (업종) : ${BizInfo.bizClass}</li>
                    <li>bizType (업태) : ${BizInfo.bizType}</li>
                    <li>result (상태코드) : ${BizInfo.result}</li>
                    <li>resultMessage (결과메시지) : ${BizInfo.resultMessage}</li>
                    <li>closeDownState (휴폐업상태) : ${BizInfo.closeDownState}</li>
                    <li>closeDownStateDate (휴폐업일자) : ${BizInfo.closeDownStateDate}</li>
                    <li>closeDownTaxType (사업자과세유형) : ${BizInfo.closeDownTaxType}</li>
                    <li>closeDownTaxTypeDate (과세유형전환일자): ${BizInfo.closeDownTaxTypeDate}</li>
                </ul>
            </fieldset>
        </fieldset>
        <br />
    </div>
</body>
</html>

