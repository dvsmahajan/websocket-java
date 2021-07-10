package com.dvsapp.dvsfinalwebsocketapp.repository;

import java.util.List; 

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dvsapp.dvsfinalwebsocketapp.entity.ChatMessage;
import com.dvsapp.dvsfinalwebsocketapp.entity.MessageStatus;

public interface IChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
	
	long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

	List<ChatMessage> findByChatId(String chatId);

	@Query("UPDATE ChatMessage cm SET cm.status= :status WHERE cm.senderId = :senderId AND cm.recipientId = :recipientId")
	@Transactional
	@Modifying
	void updateDeliveryStatusBySenderNRecipientIdNStatus(@Param("senderId") String senderId, @Param("recipientId") String recipientId,@Param("status") MessageStatus delivered);

	
}
