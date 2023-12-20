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
  <title>Lab 07: WebSockets - Video</title>
</head>
<body>
<div class="wrapper">
  <input type="text" id="username" placeholder="Username"/>
  <button type="button" onclick="connect();" >Connect</button>
  <div>
    <h1>Video from webCam</h1>
    <video ></video>
  </div>
  <div>
    <canvas hidden></canvas>
  </div>
  <div id="all-sessions">

  </div>
</div>
</body>
<script type="application/javascript">
  var ws = null;

  function connect(){
    var username = document.getElementById("username").value;
    var host = document.location.host;
    var pathname = "${pageContext.request.contextPath}";
    const url = "wss://" + host  + pathname + "/video/" + username;

    alert('url: ' + url);

    ws = new WebSocket(url);

    ws.addEventListener('message', async function(event){

      let blob = event.data.slice(36, event.data.size);
      let sessionIdBlob = event.data.slice(0, 36);
      const sessionId = await sessionIdBlob.text();
      console.log('sessionId: ' + sessionId);

      let target = document.getElementById("img_" + sessionId);
      if (target == undefined) {
          const image = document.createElement('img');
          image.setAttribute('id', "img_" + sessionId);
          image.setAttribute('style', "display: inline;");
          const box = document.getElementById('all-sessions');
          box.appendChild(image);
          target = image;
      }

      var url = window.webkitURL.createObjectURL(blob);
        target.onload = function() {
          window.webkitURL.revokeObjectURL(url);
        };
        target.src = url;
    });
      ws.addEventListener('open', (event) => {
        navigator.mediaDevices.getUserMedia(options).then(function(stream){
          video.srcObject=stream;
          video.play();
        }).catch(function(err){

        });
        setInterval(main, 500);
      });
  }
  var video = document.querySelector('video');
  var canvas = document.querySelector('canvas');
  var img = document.querySelector('img');
  var context=canvas.getContext('2d');

  var options={
    video:true,
    audio:false
  };

  function convertToBinary (dataURI) {
    // convert base64 to raw binary data held in a string
    // doesn't handle URLEncoded DataURIs
    var byteString = atob(dataURI.split(',')[1]);

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

    // write the bytes of the string to an ArrayBuffer
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }

    // write the ArrayBuffer to a blob, and you're done
    var bb = new Blob([ab]);
    return bb;
  }

  function main(){
    context.drawImage(video,0,0,canvas.width, canvas.height);
    const data = canvas.toDataURL('image/jpeg', 1.0);
    const newblob = convertToBinary(data);
    ws.send(newblob);
  }

</script>
</html>