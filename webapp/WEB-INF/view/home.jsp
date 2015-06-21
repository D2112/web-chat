<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head lang="en">
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>"/>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
<div class="centrize">
    <div class="vertical-inline-ext-box">
        <form id="start-chat-form" action="<c:url value="/"/>" method="POST">
            <input type="hidden" name="chatStart" value="true">
            <input type="text" class="input-field" placeholder="Enter your name here" name="clientName"/>
        </form>
        <button type="submit" form="start-chat-form" class="start-button">Start Chat</button>
    </div>
</div>
</body>
</html>