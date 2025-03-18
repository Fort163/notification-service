package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.dto.notification.message.NotificationMessageDto;
import com.quick.recording.gateway.main.service.MainService;
import com.quick.recording.notification.service.entity.NotificationMessageEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationMessageService extends MainService<NotificationMessageEntity, NotificationMessageDto> {
}
