package com.quick.recording.notification.service.entity;

import com.quick.recording.gateway.entity.SmartEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "NotificationMail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMailEntity extends SmartEntity {

    @Column(name = "message_id")
    private UUID messageID;
    @Column(name = "subject")
    private String subject;
    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "recipients")
    private String recipients;
    @Column(name = "content")
    private String content;
    @Column(name = "send")
    private Boolean send = false;
    @OneToOne(mappedBy = "notificationMail", cascade = CascadeType.ALL)
    private VerificationMail verificationMail;


}
