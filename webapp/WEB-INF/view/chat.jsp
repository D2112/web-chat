<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>"/>
    <meta charset="UTF-8">
    <script src="<c:url value="/js/websocket-chat.js"/>"></script>
    <title></title>
</head>
<body>
<textarea id="output" readonly class="output-text-area"></textarea>
<textarea id="input" class="input-text-area"></textarea>

<script>
    var webSocketUri = "ws://" + window.location.host + "/ws";
    var webSocket = new WebSocket(webSocketUri);
    var outputArea = document.getElementById("output");
    var inputArea = document.getElementById("input");
    var clientName = "${clientName}";
    var chat = new Chat(webSocket, clientName, inputArea, outputArea);
</script>
</body>
</html>
