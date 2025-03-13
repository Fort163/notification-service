package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.dto.notification.mail.NotificationMailDto;
import com.quick.recording.gateway.main.service.MainService;
import com.quick.recording.notification.service.entity.NotificationMailEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationMailService extends MainService<NotificationMailEntity, NotificationMailDto> {

}
