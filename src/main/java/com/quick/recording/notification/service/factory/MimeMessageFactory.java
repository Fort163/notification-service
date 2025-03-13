package com.quick.recording.notification.service.factory;

import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.enumerated.TemplateEnum;
import com.quick.recording.notification.service.dto.ResultMailCodeMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MimeMessageFactory {

    default MimeMessage create(@NotNull MailDto mail) throws MessagingException {
        return create(mail, null, null);
    }

    default MimeMessage create(@NotNull MailDto mail, @Nullable MultipartFile[] files) throws MessagingException {
        return create(mail, null, files);
    }

    default MimeMessage create(@NotNull MailDto mail, @Nullable Map<String, Object> context) throws MessagingException {
        return create(mail, context, null);
    }

    MimeMessage create(@NotNull MailDto mail, @Nullable Map<String, Object> context, @Nullable MultipartFile[] files)
            throws MessagingException;

    ResultMailCodeMessage createCodeMessage(MailCodeDto code) throws MessagingException;

}
