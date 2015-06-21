function Chat(webSocket, clientName, inputArea, outputArea) {

    var sendMessage = function (message) {
        webSocket.send(JSON.stringify(message));
    };

    webSocket.onmessage = onMessage;
    webSocket.onopen = onOpen;
    webSocket.onerror = onError;

    //Enter sends message instead next line
    inputArea.onkeydown = function (e) {
        if (e.keyCode == 13) { //13 is enter key's code
            e.preventDefault();
            if (webSocket.readyState) { //if connection is established
                var text = inputArea.value;
                sendMessage(new Message(text, "MESSAGE"));
                inputArea.value = ""; //clear inputArea
            }
        }
    };

    var sendName = function (name) {
        var message = new Message(name, "NAME");
        sendMessage(message);
    };

    function Message(text, type) {
        this.text = text;
        this.type = type;
    }

    function writeToOutput(text) {
        outputArea.value += "\n" + text;
    }

    function onMessage(e) {
        var message = JSON.parse(e.data);
        if (message.type == "MESSAGE") {
            writeToOutput(message.senderName + ": " + message.text);
        } else if (message.type == "NOTICE") {
            writeToOutput(message.text);
        }
        outputArea.scrollTop = outputArea.scrollHeight; //scroll output down
    }

    function onOpen(e) {
        writeToOutput("Connected to connection");
        sendName(clientName);
    }

    function onError(e) {
        alert("error");
    }
}






