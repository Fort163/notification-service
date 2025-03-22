package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.context.QRContextHandler;
import com.quick.recording.gateway.config.error.exeption.MethodNotSupported;
import com.quick.recording.gateway.dto.notification.message.NotificationMessageDto;
import com.quick.recording.gateway.main.service.MainServiceAbstract;
import com.quick.recording.notification.service.entity.NotificationMessageEntity;
import com.quick.recording.notification.service.mapper.NotificationMessageMapper;
import com.quick.recording.notification.service.repository.NotificationMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotificationMessageServiceImpl extends MainServiceAbstract<NotificationMessageEntity, NotificationMessageDto>
        implements NotificationMessageService {

    private final SimpMessagingTemplate template;

    @Autowired
    public NotificationMessageServiceImpl(NotificationMessageRepository repository, NotificationMessageMapper mapper,
                                          MessageUtil messageUtil, SimpMessagingTemplate template) {
        super(repository, mapper, messageUtil, NotificationMessageEntity.class);
        this.template = template;
    }

    @Override
    public ExampleMatcher prepareExampleMatcher(ExampleMatcher exampleMatcher) {
        return exampleMatcher;
    }

    @Override
    public NotificationMessageDto post(NotificationMessageDto dto) {
        dto.setSend(false);
        dto.setReceived(false);
        dto = super.post(dto);
        dto = convertAndSend(dto);
        return super.post(dto);
    }

    @Override
    public NotificationMessageDto put(NotificationMessageDto dto) {
        throw new MethodNotSupported(super.messageUtil);
    }

    private NotificationMessageDto convertAndSend(NotificationMessageDto dto) {
        return switch (dto.getProject()){
            case QR -> convertAndSendQR(dto);
        };
    }

    private NotificationMessageDto convertAndSendQR(NotificationMessageDto dto) {
        switch (dto.getSendType()){
            case TO_USER -> {
                template.convertAndSendToUser(dto.getToUser(),createDestination("/queue/notification",dto), dto);
                break;
            }
            case ALL -> {
                template.convertAndSend(createDestination("/topic/notification",dto),dto);
                break;
            }
        }
        dto.setSend(true);
        return dto;
    }

    private String createDestination(String path,NotificationMessageDto dto){
        if(Objects.nonNull(dto.getMessagePath())){
            if(dto.getMessagePath().startsWith("/")){
                path += dto.getMessagePath();
            }
            else {
                path += "/" + dto.getMessagePath();
            }
        }
        return path;
    }

}
