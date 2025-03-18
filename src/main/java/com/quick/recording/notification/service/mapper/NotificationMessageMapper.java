package com.quick.recording.notification.service.mapper;

import com.quick.recording.gateway.dto.notification.message.NotificationMessageDto;
import com.quick.recording.gateway.mapper.MainMapper;
import com.quick.recording.notification.service.entity.NotificationMessageEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface NotificationMessageMapper extends MainMapper<NotificationMessageEntity, NotificationMessageDto> {

}
