package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.notification.service.entity.VerificationMail;
import com.quick.recording.notification.service.repository.VerificationMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationMailServiceImpl implements VerificationMailService{

    private final VerificationMailRepository verificationMailRepository;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public VerificationMail save(VerificationMail verificationMail) {
        return verificationMailRepository.save(verificationMail);
    }

    @Override
    public Boolean checkCode(MailCodeDto mailCodeDto) {
        VerificationMail verificationMail = verificationMailRepository.findById(mailCodeDto.getEmail()).orElseThrow(
                () -> new NotFoundException(messageUtil, VerificationMail.class, mailCodeDto.getEmail())
        );
        return passwordEncoder.matches(mailCodeDto.getCode(), verificationMail.getCode());
    }

}
