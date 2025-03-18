package com.quick.recording.notification.service.controller;


import com.quick.recording.gateway.dto.notification.message.NotificationMessageDto;
import com.quick.recording.gateway.main.controller.MainControllerAbstract;
import com.quick.recording.gateway.service.notification.NotificationServiceNotificationMessageApi;
import com.quick.recording.notification.service.entity.NotificationMessageEntity;
import com.quick.recording.notification.service.service.mail.NotificationMessageServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification/message")
@NoArgsConstructor
public class NotificationMessageController extends MainControllerAbstract<NotificationMessageDto,
        NotificationMessageEntity, NotificationMessageServiceImpl> implements NotificationServiceNotificationMessageApi {

    @Autowired
    private NotificationMessageController(NotificationMessageServiceImpl notificationMessageService) {
        super(notificationMessageService);
    }

}
