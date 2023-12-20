package it.unipi.dsmt.jakartaee.lab_07.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import it.unipi.dsmt.jakartaee.lab_07.serializers.MessageDTOEncoder;
import it.unipi.dsmt.jakartaee.lab_07.dto.MessageDTO;
import it.unipi.dsmt.jakartaee.lab_07.serializers.MessageDTODecoder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDTODecoder.class, encoders = MessageDTOEncoder.class)
public class ChatEndpoint {
    private static final Set<Session> chatEndpoints = new CopyOnWriteArraySet<Session>();
    private static Map<String, String> users = new HashMap<String, String>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        chatEndpoints.add(session);
        users.put(session.getId(), username);
        MessageDTO message = new MessageDTO();
        message.setFrom(username);
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, MessageDTO message) throws IOException, EncodeException {
        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(session);
        MessageDTO message = new MessageDTO();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(MessageDTO message) throws IOException, EncodeException {
        chatEndpoints.forEach(session -> {
            synchronized (session) {
                try {
                    session.getBasicRemote()
                            .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}