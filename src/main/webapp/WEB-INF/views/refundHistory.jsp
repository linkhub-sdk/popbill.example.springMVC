<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page session="false" %>
        <html xmlns="http://www.w3.org/1999/xhtml">

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen" />
            <title>팝빌 SDK SpringMVC Example.</title>
        </head>

        <body>
            <div id="content">
                <p class="heading1">RefundHistory</p>
                <br />
                <fieldset class="fieldset1">
                    <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
                    <fieldset class="fieldset2">
                        <legend>검색결과 정보</legend>
                        <ul>
                            <li>reqDT (신청일시) : ${RefundHistory.reqDT}</li>
                            <li>requestPoint (환불 신청포인트) : ${RefundHistory.requestPoint}</li>
                            <li>accountBank (환불계좌 은행명) : ${RefundHistory.accountBank}</li>
                            <li>accountNum (환불계좌번호) : ${RefundHistory.accountNum}</li>
                            <li>accountName (환불계좌 예금주명) : ${RefundHistory.accountName}</li>
                            <li>state (상태) : ${RefundHistory.state}</li>
                            <li>reason (환불사유) : ${RefundHistory.reason}</li>
                        </ul>
                    </fieldset>
                </fieldset>
            </div>

        </body>

        </html>