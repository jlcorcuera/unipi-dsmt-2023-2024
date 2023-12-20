
# Hands-on session 10: JMS & WebSockets

In this session, the next topics are covered:

- Strategies for getting updates from the server
- WebSockets - definition
- WebSockets - architecture
- WebSockets client side
- WebSockets server side


## Required software

For this session it is required to have installed:

- Java SDK 11. (*)
- Apache Maven 3.x version. (*)
- [Glassfish 6.2.5](https://www.eclipse.org/downloads/download.php?file=/ee4j/glassfish/glassfish-6.2.5.zip) version.
- An IDE (IntelliJ, Eclipse, Netbeans)

Also, do not forget to define the following environment variables:

- `M2_HOME` -> root directory of your Maven installation.
- `JDK_HOME` -> root directory of your JDK installation.
- Update the `PATH` environment variable by adding the `bin` directories of your JDK and Maven installations.

(*) You can avoid doing all these step manually by installing
[SDKMAN](https://sdkman.io/).


## Configuring a New JMS Destination Resource

Create a New JMS Destination Resource with the following information:
- JNDI Name: jms/ContactUsQueue
- Physical Destination Name: contactusqueue
- Resource Type: jakarta.jms.Queue
  ![New JMS Destination Resource](images/configure-jms-destination-resource.png "New JMS Destination Resource")

**Now, you are ready to deploy the EJB and Web application projects in Glassfish.**

## Exercise 01: Shopping Cart Checkout

For this exercise, it is required to [restore the database unipi](src/main/java/it/unipi/dsmt/jakartaee/lab_10_ejb/consumer/OrdersConsumerEJB.java), [create a jdbc/BeerPool JDBC Resource](src/main/java/it/unipi/dsmt/jakartaee/lab_10_ejb/consumer/OrdersConsumerEJB.java)
and create a JMS Destination Resource: **jms/OrdersQueue** (similar steps described in the previous point). 
The following image illustrates the interaction between components.

![Component Diagram](images/component_diagram_exercise_1.jpg "Component Diagram")

#### 1. When a user clicks on the **checkout** button, a **POST** method call is performed to the ShoppingCartServlet servlet and the orderId generated is shown to the user.

**File: src/main/webapp/WEB-INF/jsp/shopping_cart.jsp**
```javascript
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script type="text/javascript">
        function checkout(){
            params = {action: 'checkout'};
            $.post("${pageContext.request.contextPath}/ShoppingCartServlet", params, function(result){
                alert('Order Id: ' + result);
                window.location.href = window.location.href;
            }).fail(function(xhr, status, error) {
                alert('error');
            });
        }
    </script>
```

#### 2. The ShoppingCartServlet servlet, retrieves a ShoppingCartEJB instance. Next, the checkout method is invoked:

**File: src/main/java/it/unipi/dsmt/javaee/lab_10/servlets/ShoppingCartServlet.java**
```java
  private void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String productId = request.getParameter("productId");
      String productName = request.getParameter("productName");
      ShoppingCartEJB shoppingCartEJB = retrieveShoppingCartEJB(request);
      String orderId = shoppingCartEJB.checkout();
      response.getWriter().write(orderId + "");
      response.getWriter().flush();
      response.getWriter().close();
  }
```

#### 3. The ShoppingCartEJB EJB has injected a reference of a stateless OrderEJBImpl EJB and the method createOrder is called during the execution of the checkout operation. OrderEJBImpl leaves a message into the OrdersQueue queue to let the warehouse know about this new order.

**File: src/main/java/it/unipi/dsmt/jakartaee/lab_10_ejb/producers/ShoppingCartEJBImpl.java**
```java
    @EJB
    private OrderEJBImpl orderEJB;

    public String checkout(){
        //1. Creating OrderDTO object
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBeers(beerDTOList);
        //2. Creating Order by calling the OrderEJB.createOrder method
        String orderId = orderEJB.createOrder(orderDTO);
        //3. Since we created an order, we have to remove all products in our shopping cart
        beerDTOList = new ArrayList<>();
        //4. Returning the orderId generated
        return orderId;
    }
```

**File: src/main/java/it/unipi/dsmt/jakartaee/lab_10_ejb/producers/OrderEJBImpl.java**
```java
package it.unipi.dsmt.jakartaee.lab_10_ejb.producers;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.OrderDTO;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import java.util.UUID;

@Stateless
public class OrderEJBImpl {

  @Resource(lookup = "jms/OrdersQueue")
  private Queue queue;
  @Inject
  private JMSContext jmsContext;

  public String createOrder(OrderDTO orderDTO){
    //1. Insert OrderDTO into a database
    String id = UUID.randomUUID().toString();
    orderDTO.setId(id);
        /*
            2. Adding the orderDTO object to a queue for further processing.
            For instance: The warehouse will receive this order for preparing
            the products to be delivered.
        */
    jmsContext.createProducer().send(queue, orderDTO);
    return id;
  }

}
```

#### 4. In the consumer project, OrdersConsumerEJB EJB is created to pick up from the queue messages of type OrderDTO.

**File: src/main/java/it/unipi/dsmt/jakartaee/lab_10_ejb/consumer/OrdersConsumerEJB.java**
```java
package it.unipi.dsmt.jakartaee.lab_10_ejb.consumer;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.OrderDTO;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(name = "OrdersConsumer",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/OrdersQueue"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
        })
public class OrdersConsumerEJB implements MessageListener {
  @Override
  public void onMessage(Message message) {
    OrderDTO orderDTO = null;
    try {
      orderDTO = message.getBody(OrderDTO.class);
      System.out.println("Warehouse, receiving order: " + orderDTO);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}
```

## Exercise 02: Chat direct message

For this question, we are going to create new files. At the UI level, it is required to add a new input text to allow users enter their username in addition to their name.

#### 1. To identify a direct message, the message should start with **@**, follow the username. 
   - Take a look at the **WebSocket URL**.
   - Pay attention to the **send** javascript function. This function adds the **to** attribute to the JSON object when it is a direct message.

**File: src/main/webapp/chat-direct.jsp**
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Lab 10: WebSockets - Chat</title>
  <script type="application/javascript">
    var ws;
    function connect() {
      var username = document.getElementById("username").value;
      var name = document.getElementById("name").value;
      var host = document.location.host;
      var pathname = "${pageContext.request.contextPath}";

      const url = "ws://" + host  + pathname + "/chat-direct/" + username + "/" + name;
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
      var username = document.getElementById("username").value;
      var message = {
        "from": username,
        "content":content
      };
      if (content.startsWith('@')) {
          message['to'] = content.substring(1, content.indexOf(' '));
          message['content'] = content.substring(content.indexOf(' ') + 1);
      }
      var json = JSON.stringify(message);
      alert(json);
      ws.send(json);
    }
  </script>
</head>
<body>
<table>
  <tr>
    <td colspan="3">
      <input type="text" id="username" placeholder="Username"/>
      <input type="text" id="name" placeholder="Name"/>
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
```

#### 2. The WebSocket URL includes two Path Variables: username and name. At the Java code, a WebSocket is defined by annotating the ChatDirectEndpoint class with the @ServerEndpoint annotation.
  - There were defined two Map objets:
    * usernameSessionIdMap: To map a username with their session.
    * usernameNameMap: To map a username with their name.
  - Upon the arrival of a new message, the onMessage method is fired. This method identify, whether a message is direct or not by inspecting **to** attribute of the incoming message.
  - In case the username doesn't exist, the content of the message changes to "server> User doesn't exist.".

**File: src/main/java/it/unipi/dsmt/javaee/lab_10/websockets/ChatDirectEndpoint.java**

```java
package it.unipi.dsmt.javaee.lab_07.websockets;

import dto.it.unipi.dsmt.jakartaee.lab_07.MessageDTO;
import serializers.it.unipi.dsmt.jakartaee.lab_07.MessageDTODecoder;
import serializers.it.unipi.dsmt.jakartaee.lab_07.MessageDTOEncoder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat-direct/{username}/{name}", decoders = MessageDTODecoder.class, encoders = MessageDTOEncoder.class)
public class ChatDirectEndpoint {
    private static final Set<Session> chatEndpoints = new CopyOnWriteArraySet<Session>();

    private static Map<String, Session> usernameSessionIdMap = new HashMap<String, Session>();
    private static Map<String, String> usernameNameMap = new HashMap<String, String>();

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("username") String username,
                       @PathParam("name") String name) throws IOException, EncodeException {
        chatEndpoints.add(session);
        usernameSessionIdMap.put(username, session);
        usernameNameMap.put(username, name);
        MessageDTO message = new MessageDTO();
        message.setFrom(name + "(" + username + ")");
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, MessageDTO message) throws IOException, EncodeException {
        String toUsername = message.getTo();
        String username = message.getFrom();
        String name = usernameNameMap.get(username);
        message.setFrom(name + "(" + username + ")");
        if (toUsername != null && !toUsername.isEmpty()) {
            Session sessionToNotify = usernameSessionIdMap.get(toUsername);
            if (sessionToNotify != null) {
                sendMessageToSession(sessionToNotify, message);
            } else {
                message.setContent("server> User doesn't exist.");
            }
            sendMessageToSession(session, message);
        } else {
            broadcast(message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(session);
        String username = null;
        for (Map.Entry<String, Session> entry : usernameSessionIdMap.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                username = entry.getKey();
                break;
            }
        }
        String name = usernameNameMap.get(username);
        MessageDTO message = new MessageDTO();
        message.setFrom(name);
        message.setContent("Disconnected!");
        broadcast(message);
        usernameSessionIdMap.remove(username);
        usernameNameMap.remove(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(MessageDTO message) throws IOException, EncodeException {
        chatEndpoints.forEach(session -> {
            sendMessageToSession(session, message);
        });
    }

    private static void sendMessageToSession(Session session, MessageDTO message) {
        synchronized (session) {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Exercise 03: Video Streaming

It is fascinating the kind of applications we can develop. We can use of WebSockets to send a stream of bytes which represents a frame of a video. Let's review the code step by step:

#### 1. When a user enters their name and clicks on the Connect button, three main actions happen:
   1. The browser asks for permissions to access the WebCam. If the user accepts then an HTML video element is bind to a stream produced by the WebCam.
   2. Every 333 ms, the function main is called.
   3. The function main gets a frame from the HTML video element and draw it on a canvas, next, the content of the canvas is converted into an image. Finally, this image is converted into a byte array and send to the WebSocket.

**File: src/main/webapp/video.jsp**
```javascript
      ws.addEventListener('open', (event) => {
        navigator.mediaDevices.getUserMedia(options).then(function(stream){
          video.srcObject=stream;
          video.play();
        }).catch(function(err){

        });
        setInterval(main, 333);
      });

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
```

#### 2. The WebSocket will receive this stream of bytes (represented by an array of bytes). Remember that at the UI level, for each user an image of them should be displayed, so we have to create dynamically these images. 
In the onMessage method, the byte array representation of the sender's sessionId is appended to the imageData byte array (this represents the byte streaming sent).

```java
package it.unipi.dsmt.javaee.lab_07.websockets;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/video/{username}")
public class VideoEndpoint {
    private static final Set<Session> videoEndpoints = new CopyOnWriteArraySet<Session>();
    private static Map<String, String> users = new HashMap<String, String>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        videoEndpoints.add(session);
        users.put(session.getId(), username);
        System.out.println("Session ID: " + session.getId());
        byte[] sessionIdBytes = session.getId().getBytes();
        System.out.println("Session LEN: " + sessionIdBytes.length);
    }

    @OnMessage
    public void onMessage(byte[] imageData, Session session) throws IOException, EncodeException {
        byte[] sessionIdBytes = session.getId().getBytes();
        ByteBuffer buf = ByteBuffer.wrap(concat(sessionIdBytes, imageData));
        broadcast(session.getId(), buf);
    }

    public static byte[] concat(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        videoEndpoints.remove(session);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private static void broadcast(String sessionIdNotToNotify, ByteBuffer video) throws IOException, EncodeException {
        videoEndpoints.forEach(session -> {
            if (!sessionIdNotToNotify.equals(session.getId())) {
                synchronized (session) {
                    try {
                        session.getBasicRemote()
                                .sendBinary(video);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
```

#### 3. Finally, when a message is received, we have to get the sessionId and the stream of bytes which represent the image of a person. The first 36 bytes represent the sessionId. In case an "image" HTML element with id "img_<session_id>" doesn't exist, we proceed to create it and display the received image there; otherwise, we just update its image content.

**File: src/main/webapp/video.jsp**
```javascript
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
```

#### 4. By the way, remember to access this Web Application by using https. For more information read [this](https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia). Luckily, Glassfish also starts https at the 8181 port, example: https://localhost:8181/lab-10-chat/.