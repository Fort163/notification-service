package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.notification.service.entity.VerificationMail;
import org.springframework.stereotype.Service;

@Service
public interface VerificationMailService {

    VerificationMail save(VerificationMail verificationMail);

    Boolean checkCode(MailCodeDto mailCodeDto);
}
