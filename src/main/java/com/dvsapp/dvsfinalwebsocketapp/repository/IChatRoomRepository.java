package com.dvsapp.dvsfinalwebsocketapp.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvsapp.dvsfinalwebsocketapp.entity.ChatRoom;

public interface IChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

	Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

	
}
