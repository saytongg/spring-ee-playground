package com.saytongg.mock;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired
	@Qualifier("mockService")
	private MockService mockService;
	
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
			Long id = Long.valueOf(message);
			Optional<MockEntity> mockEntity = mockService.getById(id);
			
			if(mockEntity.isEmpty()) {
				session.getBasicRemote().sendText("There is no mock entity associated with id " + message);
			}	
			else {
				session.getBasicRemote().sendText("Name of mock entity is " + mockEntity.get().getName());
			}
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
