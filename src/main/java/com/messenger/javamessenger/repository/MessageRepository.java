package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query("""
    SELECT m FROM MessageEntity m
    WHERE 
        (m.senderId = :userId1 AND m.receiverId = :userId2)
        OR 
        (m.senderId = :userId2 AND m.receiverId = :userId1)
    ORDER BY m.timestamp ASC
    """)
    List<MessageEntity> findMessagesByUserIds(
            @Param("userId1") UUID userId1,
            @Param("userId2") UUID userId2
    );
}
