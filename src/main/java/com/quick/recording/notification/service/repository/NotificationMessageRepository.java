package com.quick.recording.notification.service.repository;

import com.quick.recording.notification.service.entity.NotificationMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessageEntity, UUID> {
}
