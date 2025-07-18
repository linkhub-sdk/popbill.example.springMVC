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
        <ul>
            <fieldset class="fieldset2">
                <legend>정발행</legend>
                <ul>
                    <c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
                        <c:if test="${EmailSendConfig.emailType == 'TAX_ISSUE'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 발행 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                        <c:if test="${EmailSendConfig.emailType == 'TAX_ISSUE_INVOICER'}">
                            <li>${EmailSendConfig.emailType} (공급자에게 세금계산서가 발행 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_CHECK'}">
                            <li>${EmailSendConfig.emailType} (공급자에게 세금계산서가 수신확인 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_CANCEL_ISSUE'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 발행취소 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </fieldset>

            <fieldset class="fieldset2">
                <legend>역발행</legend>
                <ul>
                    <c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
                        <c:if test="${EmailSendConfig.emailType == 'TAX_REQUEST'}">
                            <li>${EmailSendConfig.emailType} (공급자에게 세금계산서를 전자서명 하여 발행을 요청하는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_CANCEL_REQUEST'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 취소 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_REFUSE'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 거부 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </fieldset>

            <fieldset class="fieldset2">
                <legend>위수탁발행</legend>
                <ul>
                    <c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
                        <c:if test="${EmailSendConfig.emailType == 'TAX_TRUST_ISSUE'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 발행 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_TRUST_ISSUE_TRUSTEE'}">
                            <li>${EmailSendConfig.emailType} (수탁자에게 세금계산서가 발행 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_TRUST_ISSUE_INVOICER'}">
                            <li>${EmailSendConfig.emailType} (공급자에게 세금계산서가 발행 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_TRUST_CANCEL_ISSUE'}">
                            <li>${EmailSendConfig.emailType} (공급받는자에게 세금계산서가 발행취소 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_TRUST_CANCEL_ISSUE_INVOICER'}">
                            <li>${EmailSendConfig.emailType} (공급자에게 세금계산서가 발행취소 되었음을 알려주는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </fieldset>

            <fieldset class="fieldset2">
                <legend>처리결과</legend>
                <ul>
                    <c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
                        <c:if test="${EmailSendConfig.emailType == 'TAX_CLOSEDOWN'}">
                            <li>${EmailSendConfig.emailType} (거래처의 휴폐업 여부를 확인하여 안내하는 메일 전송 여부) : ${EmailSendConfig.sendYN}</li>
                        </c:if>

                        <c:if test="${EmailSendConfig.emailType == 'TAX_NTSFAIL_INVOICER'}">
                            <li>${EmailSendConfig.emailType} (세금계산서 국세청 전송실패를 안내하는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </fieldset>

            <fieldset class="fieldset2">
                <legend>정기발송</legend>
                <ul>
                    <c:forEach items="${EmailSendConfigs}" var="EmailSendConfig">
                        <c:if test="${EmailSendConfig.emailType == 'ETC_CERT_EXPIRATION'}">
                            <li>${EmailSendConfig.emailType} (팝빌에 등록된 인증서의 만료예정을 안내하는 메일 전송 여부)
                                : ${EmailSendConfig.sendYN}</li>
                        </c:if>
                    </c:forEach>
                </ul>
            </fieldset>
        </ul>
    </fieldset>
</div>
</body>
</html>
