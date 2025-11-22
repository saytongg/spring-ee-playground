package com.saytongg.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/mock")
public class MockWebsocketEndpoint extends SpringBeanAutowiringSupport{

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Session added: {}", session.getId());
	}

	@OnClose
	public void onClose(Session session) {
		logger.info("Session removed: {}", session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("Message is {} from session {}", message, session.getId());
		
		try {
			session.getBasicRemote().sendText("Echoing message: " + message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Throwable t, Session session) throws Throwable {
		logger.info("Error for session {}: {}", t.getMessage(), session.getId());
	}
}
