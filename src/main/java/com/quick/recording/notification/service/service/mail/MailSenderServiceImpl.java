package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.QRInternalServerErrorException;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.dto.notification.mail.MailResult;
import com.quick.recording.gateway.enumerated.Result;
import com.quick.recording.notification.service.dto.ResultMailCodeMessage;
import com.quick.recording.notification.service.entity.NotificationMailEntity;
import com.quick.recording.notification.service.entity.VerificationMail;
import com.quick.recording.notification.service.factory.MimeMessageFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "notification.mail" , havingValue = "real", matchIfMissing = true)
public class MailSenderServiceImpl implements MailSenderService{
    
    private final JavaMailSender javaMailSender;
    private final MimeMessageFactory mimeMessageFactory;
    private final MessageUtil messageUtil;
    private final NotificationMailService notificationMailService;
    private final VerificationMailService verificationMailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MailResult send(MailDto mail, MultipartFile[] files) throws QRInternalServerErrorException {
        try {
            MimeMessage mimeMessage = mimeMessageFactory.create(mail, files);
            NotificationMailEntity send = send(mimeMessage, mimeMessage.getMessageID());
            return MailResult.builder()
                    .result(Result.SUCCESS)
                    .messageId(send.getMessageID())
                    .notificationId(send.getUuid())
                    .resultText(messageUtil.create("mail.success.text", mimeMessage.getMessageID()))
                    .build();
        }
        catch (MessagingException exception){
            return MailResult.builder()
                    .result(Result.ERROR)
                    .resultText(messageUtil.create("mail.error.messaging.text"))
                    .build();
        }
    }

    @Override
    @Transactional
    public MailResult code(MailCodeDto code) throws QRInternalServerErrorException {
        try {
            ResultMailCodeMessage codeMessage = mimeMessageFactory.createCodeMessage(code);
            MimeMessage mimeMessage = codeMessage.getMessage();
            NotificationMailEntity send = send(mimeMessage, mimeMessage.getMessageID());
            VerificationMail verificationMail = VerificationMail.builder()
                    .code(passwordEncoder.encode(codeMessage.getCode()))
                    .mail(codeMessage.getEmail())
                    .verified(false)
                    .notificationMail(send)
                    .build();
            verificationMailService.save(verificationMail);
            return MailResult.builder()
                    .result(Result.SUCCESS)
                    .messageId(send.getMessageID())
                    .notificationId(send.getUuid())
                    .resultText(messageUtil.create("mail.success.text", mimeMessage.getMessageID()))
                    .build();
        }
        catch (MessagingException exception){
            return MailResult.builder()
                    .result(Result.ERROR)
                    .resultText(messageUtil.create("mail.error.messaging.text"))
                    .build();
        }
    }

    @Override
    public Boolean checkCode(MailCodeDto mailCodeDto) {
        return verificationMailService.checkCode(mailCodeDto);
    }

    private NotificationMailEntity send(MimeMessage mimeMessage, String id) throws QRInternalServerErrorException {
        try {
            NotificationMailEntity entity = notificationMailService.save(convert(mimeMessage));
            log.info("EMAIL_SERVICE: Date start sent {}. Sending email with ID {}", ZonedDateTime.now(), id);
            javaMailSender.send(mimeMessage);
            log.info("EMAIL_SERVICE: Date finish sent {}. Completed email with ID {}", ZonedDateTime.now(), id);
            entity.setSend(true);
            return notificationMailService.save(entity);
        }
        catch (MailException exception){
            throw new QRInternalServerErrorException(messageUtil.create("mail.error.send.text"));
        }
    }

    private NotificationMailEntity convert(MimeMessage mimeMessage) throws QRInternalServerErrorException {
        try {
            NotificationMailEntity result = new NotificationMailEntity();
            result.setContent(cutFilesFromContent(
                    new String(mimeMessage.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
            ));
            result.setMessageID(UUID.fromString(mimeMessage.getMessageID()));
            result.setMessageID(UUID.fromString(mimeMessage.getMessageID()));
            result.setFromEmail(Arrays.stream(mimeMessage.getFrom())
                    .map(item -> ((InternetAddress)item).getAddress())
                    .collect(Collectors.joining()));
            result.setRecipients(Arrays.stream(mimeMessage.getAllRecipients())
                    .map(item -> ((InternetAddress)item).getAddress())
                    .collect(Collectors.joining()));
            result.setSubject(mimeMessage.getSubject());
            return result;
        }
        catch (MessagingException | IOException exception){
            throw new QRInternalServerErrorException(messageUtil.create("mail.error.convert.data"));
        }
    }

    private String cutFilesFromContent(String content) {
        if(content.contains("Content-ID")) {
            return content.substring(0, content.indexOf("Content-ID"));
        }
        return content;
    }
}
