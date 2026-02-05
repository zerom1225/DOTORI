package com.pcwk.ehr.websocket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.pcwk.ehr.chat.service.ChatService;
import com.pcwk.ehr.chat_message.service.Chat_MessageService;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Component
public class EchoHandler extends TextWebSocketHandler {

	final Logger log = LogManager.getLogger(getClass());

	// 채팅 ID별로 세션을 관리하는 맵
	private Map<Integer, WebSocketSession[]> chats = new HashMap<>();
	
	@Qualifier("chatServiceImpl")
	@Autowired
	private ChatService chatService;

	@Qualifier("chat_MessageServiceImpl")
	@Autowired
	Chat_MessageService chat_MessageService;

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	// WebSocket 연결이 이루어졌을 때
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

		log.debug("afterConnectionEstablished 실행");

		String query = webSocketSession.getUri().getQuery();
		log.debug("WebSocket URI 쿼리: {}", query);

		int chat_Id = Integer.parseInt(query.split("&")[0].split("=")[1]);
		int user_Id = Integer.parseInt(query.split("&")[1].split("=")[1]);

		log.debug("afterConnectionEstablished chat_Id : {}", chat_Id);
		log.debug("handleTextMessage user_Id : {}", user_Id);

		// 채팅방이 이미 존재하는지 확인
		if (!chats.containsKey(chat_Id)) {
			chats.put(chat_Id, new WebSocketSession[2]); // 두 클라이언트 세션을 담을 배열
		}

		WebSocketSession[] clientSessions = chats.get(chat_Id);

		// 첫 번째 클라이언트 또는 두 번째 클라이언트 세션으로 설정
		if (clientSessions[0] == null) {
			clientSessions[0] = webSocketSession; // 첫 번째 클라이언트
		} else if (clientSessions[1] == null) {
			clientSessions[1] = webSocketSession; // 두 번째 클라이언트
		} else {
			// 이미 두 클라이언트가 연결된 경우 세션 종료
			webSocketSession.close();
		}
	}

	// 클라이언트에서 메시지를 받았을 때
	@Override
	protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {

		log.debug("handleTextMessage 실행");

		String query = webSocketSession.getUri().getQuery();
		log.debug("WebSocket URI 쿼리: {}", query);

		int chat_Id = Integer.parseInt(query.split("&")[0].split("=")[1]);
		int user_Id = Integer.parseInt(query.split("&")[1].split("=")[1]);
		WebSocketSession[] clientSessions;

		log.debug("handleTextMessage chat_Id : {}", chat_Id);
		log.debug("handleTextMessage user_Id : {}", user_Id);

		// 현재  로그인 되어 있는 유저 가져오기
		UserVO user = userService.doSelectOne_With_User_Id(user_Id);

		log.debug("handleTextMessage 유저 세션 {}", user.toString());
		
		// 메시지가 "exit#dotori#" 로 시작하는 경우
		if(message.getPayload().startsWith("exit#dotori#")) {
			log.debug("채팅방 나가기 요청 메시지 : {}", message.getPayload());
			
			// DB에서 채팅방 삭제
			int flag = chatService.exit_Chat(chat_Id);
			log.debug("채팅방 나가기 DB 요청 : flag {}", flag);
			
			// 채팅방이 종료된 후 모든 클라이언트에게 종료 메시지를 보냄
			clientSessions = chats.get(chat_Id);
			
			log.debug("웹소켓 클라이언트0 로그 : {}", clientSessions[0]);
			log.debug("웹소켓 클라이언트1 로그 : {}", clientSessions[0]);
			
			// 두 클라이언트 모두 웹소켓 세션이 연결되어 있다면 메시지를 전달
			if (clientSessions[0] != null && clientSessions[1] != null) {
				if (webSocketSession.equals(clientSessions[0])) {
					clientSessions[1].sendMessage(new TextMessage("상대가 채팅방을 나갔습니다.")); // 첫 번째 클라이언트 -> 두 번째 클라이언트
					System.out.println("채팅방 삭제 메시지 전송 실행");
				} else if (webSocketSession.equals(clientSessions[1])) {
					clientSessions[0].sendMessage(new TextMessage("상대가 채팅방을 나갔습니다.")); // 두 번째 클라이언트 -> 첫 번째 클라이언트
					System.out.println("채팅방 삭제 메시지 전송 실행");
				}
			}
			
			chats.remove(chat_Id);
			
			return;
			
		} // 메시지가 "exit#dotori#" 로 시작하는 경우 -- END
		
		
		

		int flag = chat_MessageService.saveChatMessage(chat_Id, user, message.getPayload());

		log.debug("saveChatMessage : {}", flag);

		clientSessions = chats.get(chat_Id);

		log.debug("웹소켓 클라이언트0 로그 : {}", clientSessions[0]);
		log.debug("웹소켓 클라이언트1 로그 : {}", clientSessions[0]);

		// 두 클라이언트 모두 웹소켓 세션이 연결되어 있다면 메시지를 전달
		if (clientSessions[0] != null && clientSessions[1] != null) {
			if (webSocketSession.equals(clientSessions[0])) {
				clientSessions[1].sendMessage(new TextMessage(message.getPayload())); // 첫 번째 클라이언트 -> 두 번째 클라이언트
				System.out.println("메시지 전송 실행");
			} else if (webSocketSession.equals(clientSessions[1])) {
				clientSessions[0].sendMessage(new TextMessage(message.getPayload())); // 두 번째 클라이언트 -> 첫 번째 클라이언트
				System.out.println("메시지 전송 실행");
			}
		}
	}

	// WebSocket 연결 종료 시
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws Exception {

		log.debug("afterConnectionClosed 실행");

		String query = webSocketSession.getUri().getQuery();
		log.debug("WebSocket URI 쿼리: {}", query);

		int chat_Id = Integer.parseInt(query.split("&")[0].split("=")[1]);

		log.debug("handleTextMessage chat_Id : {}", chat_Id);

		WebSocketSession[] clientSessions = chats.get(chat_Id);

		if (webSocketSession.equals(clientSessions[0])) {
			clientSessions[0] = null; // 첫 번째 클라이언트 세션 종료
		} else if (webSocketSession.equals(clientSessions[1])) {
			clientSessions[1] = null; // 두 번째 클라이언트 세션 종료
		}

		// 두 클라이언트 모두 연결이 종료된 경우 맵에서 제거
		if (clientSessions[0] == null && clientSessions[1] == null) {
			chats.remove(chat_Id);
			log.debug("채팅방 {}의 모든 클라이언트 연결 종료", chat_Id);
		}
	}
}
