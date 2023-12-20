package it.unipi.dsmt.jakartaee.lab_07.websockets;

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