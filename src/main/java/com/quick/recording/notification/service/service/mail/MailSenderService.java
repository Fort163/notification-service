package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.error.exeption.QRInternalServerErrorException;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MailSenderService {

    MailResult send(MailDto mail, MultipartFile[] files) throws QRInternalServerErrorException;

    MailResult code(MailCodeDto code) throws QRInternalServerErrorException ;

    Boolean checkCode(MailCodeDto mailCodeDto);

}
