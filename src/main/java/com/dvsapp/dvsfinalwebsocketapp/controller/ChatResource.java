package com.dvsapp.dvsfinalwebsocketapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dvsapp.dvsfinalwebsocketapp.dto.ChatNotification;
import com.dvsapp.dvsfinalwebsocketapp.entity.ChatMessage;
import com.dvsapp.dvsfinalwebsocketapp.service.IChatService;

@Controller
@CrossOrigin
public class ChatResource {

	@Autowired
	private IChatService ichatService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage chatMessage) {
		Optional<String> chatId = ichatService.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
		chatMessage.setChatId(chatId.get());

		ChatMessage saved = ichatService.save(chatMessage);
		messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages",
				new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName()));
	}

	@GetMapping("/messages/{senderId}/{recipientId}/count")
	public ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {
		long totalUnreadMsg = ichatService.getUnreadMsgCountBySenderNRecipient(senderId, recipientId);
		return ResponseEntity.ok(totalUnreadMsg);
	}

	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<?> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
		List<ChatMessage> chatMsgList = ichatService.findAllChatMessagesBySenderNReciverId(senderId, recipientId);
		return ResponseEntity.ok(chatMsgList);
	}

	@GetMapping("/messages/{id}")
	public ResponseEntity<?> findMessage(@PathVariable Integer id) {
		return ResponseEntity.ok(ichatService.findChatMessageNUpdateDeliverStatusById(id));
	}
}
