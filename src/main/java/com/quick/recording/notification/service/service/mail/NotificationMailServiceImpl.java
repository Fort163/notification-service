package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.dto.notification.mail.NotificationMailDto;
import com.quick.recording.gateway.main.service.local.MainServiceAbstract;
import com.quick.recording.notification.service.entity.NotificationMailEntity;
import com.quick.recording.notification.service.mapper.NotificationMailMapper;
import com.quick.recording.notification.service.repository.NotificationMailRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class NotificationMailServiceImpl extends MainServiceAbstract<NotificationMailEntity, NotificationMailDto>
        implements NotificationMailService {

    @Autowired
    public NotificationMailServiceImpl(NotificationMailRepository notificationMailRepository,
                                       NotificationMailMapper notificationMailMapper,
                                       MessageUtil messageUtil){
        super(notificationMailRepository,notificationMailMapper,messageUtil,NotificationMailEntity.class);
    }

    @Override
    public ExampleMatcher prepareExampleMatcher(ExampleMatcher exampleMatcher) {
        return exampleMatcher;
    }

    @Override
    public Class<NotificationMailDto> getType() {
        return NotificationMailDto.class;
    }
}
