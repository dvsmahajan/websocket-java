package com.dvsapp.dvsfinalwebsocketapp.service;

import java.util.List;
import java.util.Optional;

import com.dvsapp.dvsfinalwebsocketapp.entity.ChatMessage;

public interface IChatService {

	long getUnreadMsgCountBySenderNRecipient(String senderId, String recipientId);

	ChatMessage findChatMessageNUpdateDeliverStatusById(Integer id);

	List<ChatMessage> findAllChatMessagesBySenderNReciverId(String senderId, String recipientId);

	Optional<String> getChatId(String senderId, String recipientId, boolean b);

	ChatMessage save(ChatMessage chatMessage);

}
