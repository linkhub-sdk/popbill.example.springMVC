<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>휴폐업조회 API SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">휴폐업조회 API SDK SpringMVC Example.</p>
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
            <legend>휴폐업조회 - 단건</legend>
            <ul>
                <li>corpNum [사업자번호] : ${CorpState.corpNum}</li>
                <li>type [사업 유형] : ${CorpState.type}</li>
                <li>state [휴폐업 상태] : ${CorpState.state}</li>
                <li>stateDate [휴폐업일자] : ${CorpState.stateDate}</li>
                <li>typeDate [과세유형 전환일자] : ${CorpState.typeDate}</li>
                <li>checkDate [확인일자] : ${CorpState.checkDate}</li>
            </ul>
            <p class="info"> type : null (알수없음), 1 (부가가치세 일반과세자), 2 (부가가치세 면세과세자), 3 (부가치세 간이과세자) 4 (비영리법인 또는 국가기관,
                고유번호가 부여된 단체)</p>
            <p class="info"> state : null (알수없음), 0 (등록되지 않은 사업자번호), 1 (사업중), 2 (폐업), 3 (휴업)</p>
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