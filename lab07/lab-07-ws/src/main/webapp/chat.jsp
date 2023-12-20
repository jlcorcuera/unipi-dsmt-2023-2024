<%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 12/12/2022
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Lab 07: WebSockets - Chat</title>
  <script type="application/javascript">
    var ws;
    function connect() {
      var username = document.getElementById("username").value;
      var host = document.location.host;
      var pathname = "${pageContext.request.contextPath}";

      const url = "ws://" + host  + pathname + "/chat/" + username;
      alert('url: ' + url);
      ws = new WebSocket(url);

      ws.onmessage = function(event) {
        var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
      };
    }

    function send() {
      var content = document.getElementById("msg").value;
      var json = JSON.stringify({
        "content":content
      });
      ws.send(json);
    }
  </script>
</head>
<body>
<table>
  <tr>
    <td colspan="2">
      <input type="text" id="username" placeholder="Username"/>
      <button type="button" onclick="connect();" >Connect</button>
    </td>
  </tr>
  <tr>
    <td>
      <textarea readonly="true" rows="10" cols="80" id="log"></textarea>
    </td>
  </tr>
  <tr>
    <td>
      <input type="text" size="51" id="msg" placeholder="Message"/>
      <button type="button" onclick="send();" >Send</button>
    </td>
  </tr>
</table>
</body>
</html>