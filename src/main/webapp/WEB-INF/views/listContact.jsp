<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>팝빌 SDK SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">Response</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <c:if test="${ContactInfos != null}">
            <c:forEach items="${ContactInfos}" var="ContactInfo">
                <fieldset class="fieldset2">
                    <ul>
                        <li>id (아이디) : ${ContactInfo.id}</li>
                        <li>personName (담당자 성명) : ${ContactInfo.personName}</li>
                        <li>email (이메일) : ${ContactInfo.email}</li>
                        <li>hp (휴대폰번호) : ${ContactInfo.hp}</li>
                        <li>fax (팩스번호) : ${ContactInfo.fax}</li>
                        <li>tel (연락처) : ${ContactInfo.tel}</li>
                        <li>regDT (등록일시) : ${ContactInfo.regDT}</li>
                        <li>searchRole (담당자 권한) : ${ContactInfo.searchRole}</li>
                        <li>mgrYN (관리자 여부) : ${ContactInfo.mgrYN}</li>
                        <li>state (상태) : ${ContactInfo.state}</li>
                    </ul>
                </fieldset>
            </c:forEach>
        </c:if>
    </fieldset>
</div>
</body>
</html>