package com.quick.recording.notification.service.dto;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultPhoneCodeMessage {

    private MimeMessage message;
    private String code;
    private String phone;

}
