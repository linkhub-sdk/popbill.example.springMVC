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
      <li>환불 가능 포인트 (refundableBalance) : ${refundableBalance}</li>
    </ul>
  </fieldset>
</div>
</body>
</html>