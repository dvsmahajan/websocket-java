package com.dvsapp.dvsfinalwebsocketapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvsapp.dvsfinalwebsocketapp.entity.ChatMessage;
import com.dvsapp.dvsfinalwebsocketapp.entity.ChatRoom;
import com.dvsapp.dvsfinalwebsocketapp.entity.MessageStatus;
import com.dvsapp.dvsfinalwebsocketapp.repository.IChatMessageRepository;
import com.dvsapp.dvsfinalwebsocketapp.repository.IChatRoomRepository;

@Service
public class ChatServiceImpl implements IChatService {
	
	@Autowired
	private IChatRoomRepository  iChatRoomRepository;
	@Autowired
	private IChatMessageRepository iChatMessageRepository;
	
	public long getUnreadMsgCountBySenderNRecipient(String senderId, String recipientId) {
		return iChatMessageRepository.countBySenderIdAndRecipientIdAndStatus(senderId, recipientId, MessageStatus.RECEIVED);
	}
	public ChatMessage findChatMessageNUpdateDeliverStatusById(Integer id) {
		Optional<ChatMessage> optChatMessage = iChatMessageRepository.findById(id);
		if(optChatMessage.isPresent()) {
			optChatMessage.get().setStatus(MessageStatus.DELIVERED);
            return iChatMessageRepository.save(optChatMessage.get());
		}
		throw new RuntimeException("can't find message (" + id + ")");
	}
	public List<ChatMessage> findAllChatMessagesBySenderNReciverId(String senderId, String recipientId) {
		Optional<ChatRoom> optChatRoom = iChatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId);
		List<ChatMessage> chatMsgList = null;
		if(optChatRoom.isPresent()) {
			chatMsgList = iChatMessageRepository.findByChatId(optChatRoom.get().getChatId());
			if(chatMsgList.isEmpty()) {
				chatMsgList = new ArrayList<>();
			}
			if(chatMsgList.size()>0) {
				updateStatusBySenderIdAndRecipientIdAndStatus(senderId,recipientId,MessageStatus.DELIVERED);
			}
		}
		return chatMsgList;
	}
	private void updateStatusBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId,
			MessageStatus delivered) {
		iChatMessageRepository.updateDeliveryStatusBySenderNRecipientIdNStatus(senderId,recipientId,delivered);
		
	}
	@Override
	public Optional<String> getChatId(String senderId, String recipientId, boolean canCreateNew) {
		Optional<ChatRoom> optChatMsg = iChatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
		if(optChatMsg.isPresent()) {
			return Optional.of(optChatMsg.get().getChatId());
		}else {
			if(canCreateNew) {
				String chatId = String.format("%s_%s", senderId, recipientId);
				ChatRoom chatRoom = ChatRoom.builder().chatId(chatId).recipientId(recipientId).senderId(senderId).build();
				ChatRoom chatRoom1 = ChatRoom.builder().chatId(chatId).recipientId(senderId).senderId(recipientId).build();
				iChatRoomRepository.save(chatRoom);
				iChatRoomRepository.save(chatRoom1);
				Optional.of(chatId);
			}
		}
		return Optional.empty();
	}
	@Override
	public ChatMessage save(ChatMessage chatMessage) {
		return iChatMessageRepository.save(chatMessage);
	}

}
