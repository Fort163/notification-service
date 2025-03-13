package com.quick.recording.notification.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "VerificationMail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationMail {

    @Id
    @Column(name = "mail")
    private String mail;
    @Column(name = "code")
    private String code;
    @Column(name = "verified")
    private Boolean verified;
    @OneToOne
    @JoinColumn(name = "notification_mail_id")
    private NotificationMailEntity notificationMail;

}
