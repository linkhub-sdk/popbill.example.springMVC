<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>사업자등록상태조회 (휴폐업조회) API SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">사업자등록상태조회 (휴폐업조회) API SDK SpringMVC Example.</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <div class="fieldset4">
            <form method="GET" id="corpnum_form" action="checkCorpNum">
                <input class="txtCorpNum left" type="text" placeholder="사업자번호 기재" id="CorpNum" name="CorpNum"
                       value="${CorpState.corpNum}" tabindex=1/>
                <p class="find_btn find_btn01 hand" onclick="search()" tabindex=2>조회</p>
            </form>
        </div>
    </fieldset>
    <c:if test="${CorpState != null}">
        <fieldset class="fieldset2">
            <legend>사업자등록상태조회 (휴폐업조회) - 단건</legend>
            <p class="info"> state : null (확인실패), 0 (등록되지 않은 사업자번호), 1 (사업중), 2 (폐업), 3 (휴업)</p>
            <p class="info"> taxType : null (확인실패), 10 (일반과세자), 20 (면세과세자), 30 (간이과세자), 31 (간이과세자 세금계산서 발급사업자), 40 (비영리법인 또는 국가기관, 고유번호가 할당된 단체)</p>
            <ul>
                <li>corpNum (조회한 사업자번호) : ${CorpState.corpNum}</li>
                <li>taxType (사업자 과세유형) : ${CorpState.taxType}</li>
                <li>typeDate (과세유형 전환일자) : ${CorpState.typeDate}</li>
                <li>state (휴폐업상태) : ${CorpState.state}</li>
                <li>stateDate (휴폐업일자) : ${CorpState.stateDate}</li>
                <li>checkDate (국세청 확인일자) : ${CorpState.checkDate}</li>
            </ul>
        </fieldset>
    </c:if>
    <br/>
</div>

<script type="text/javascript">
    window.onload = function () {
        document.getElementById('CorpNum').focus();
    }

    function search() {
        document.getElementById('corpnum_form').submit();
    }
</script>
</body>
</html>
