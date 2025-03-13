package com.quick.recording.notification.service.mapper;

import com.quick.recording.gateway.dto.notification.mail.NotificationMailDto;
import com.quick.recording.gateway.mapper.MainMapper;
import com.quick.recording.notification.service.entity.NotificationMailEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface NotificationMailMapper extends MainMapper<NotificationMailEntity, NotificationMailDto> {


}
