package com.quick.recording.notification.service.entity;

import com.quick.recording.gateway.entity.SmartEntity;
import com.quick.recording.gateway.enumerated.MessageType;
import com.quick.recording.gateway.enumerated.Project;
import com.quick.recording.gateway.enumerated.SendType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "NotificationMessage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageEntity extends SmartEntity {

    @Column(name = "from_user")
    private String fromUser;
    @Column(name="to_user")
    private String toUser;
    @Column(name = "send_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SendType sendType;
    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    @Column(name = "project", nullable = false)
    @Enumerated(EnumType.STRING)
    private Project project;
    @Column(name = "path")
    private String path;
    @Column(name = "message_path")
    private String messagePath;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "message_code")
    private String messageCode;
    @Column(name = "json_object")
    private String jsonObject;
    @Column(name = "send", nullable = false)
    private Boolean send = false;
    @Column(name = "received", nullable = false)
    private Boolean received = false;

}
