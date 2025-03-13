package com.quick.recording.notification.service.repository;

import com.quick.recording.notification.service.entity.NotificationMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationMailRepository extends JpaRepository<NotificationMailEntity, UUID> {

}
