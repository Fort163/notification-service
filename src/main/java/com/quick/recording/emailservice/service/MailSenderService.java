package com.quick.recording.emailservice.service;

import com.quick.recording.emailservice.dto.request.MailDTO;
import jakarta.mail.MessagingException;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String username;
    
    private final JavaMailSender javaMailSender;
    private final MimeMessageCustom mimeMessageCustom;

    public void send(MailDTO mail, MultipartFile[] files) throws MessagingException {
        log.info("EMAIL_SERVICE: Date start sent {}. Sending email to user {}", ZonedDateTime.now(), mail.getTo());
        javaMailSender.send(mimeMessageCustom.getMimeMessage(mail, files));
        log.info("EMAIL_SERVICE: Date finish sent {}. Completed to send email to user {}", ZonedDateTime.now(), mail.getTo());
    }

}
