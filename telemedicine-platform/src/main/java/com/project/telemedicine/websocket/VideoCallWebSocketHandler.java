package com.project.telemedicine.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;

public class VideoCallWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> activeCalls = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("🔗 WebSocket Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type");
        String roomId = jsonMessage.getString("roomId");

        if ("join-room".equals(type)) {
            activeCalls.put(roomId, session);
            System.out.println("📞 User joined room: " + roomId);
        } else if ("offer".equals(type) || "answer".equals(type) || "ice-candidate".equals(type)) {
            WebSocketSession recipientSession = activeCalls.get(roomId);
            if (recipientSession != null && recipientSession.isOpen()) {
                recipientSession.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        activeCalls.values().removeIf(session::equals);
        System.out.println("🔌 WebSocket Disconnected: " + session.getId());
    }
}
