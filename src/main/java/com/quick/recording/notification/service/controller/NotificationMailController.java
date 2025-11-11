package com.quick.recording.notification.service.controller;

import com.quick.recording.gateway.dto.notification.mail.NotificationMailDto;
import com.quick.recording.gateway.main.controller.MainControllerAbstract;
import com.quick.recording.gateway.service.notification.NotificationServiceNotificationMailApi;
import com.quick.recording.notification.service.entity.NotificationMailEntity;
import com.quick.recording.notification.service.service.mail.NotificationMailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification/mail")
@NoArgsConstructor
public class NotificationMailController extends MainControllerAbstract<NotificationMailDto,
        NotificationMailEntity, NotificationMailService> implements NotificationServiceNotificationMailApi {

    @Autowired
    private NotificationMailController(NotificationMailService service) {
        super(service);
    }

}
