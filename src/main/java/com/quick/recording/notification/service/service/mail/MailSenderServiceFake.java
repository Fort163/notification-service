package com.quick.recording.notification.service.service.mail;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.QRInternalServerErrorException;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.dto.notification.mail.MailResult;
import com.quick.recording.gateway.enumerated.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "notification.mail" , havingValue = "fake")
public class MailSenderServiceFake implements MailSenderService{

    private final Map<String, String> map = new HashMap<>();
    private final MessageUtil messageUtil;

    @Override
    public MailResult send(MailDto mail, MultipartFile[] files) throws QRInternalServerErrorException  {
        return MailResult.builder()
                .result(Result.SUCCESS)
                .resultText(messageUtil.create("mail.success.text", "FakeMessageID"))
                .build();
    }

    @Override
    public MailResult code(MailCodeDto code) throws QRInternalServerErrorException {
        map.put(code.getEmail(),"9999");
        return MailResult.builder()
                .result(Result.SUCCESS)
                .resultText(messageUtil.create("mail.success.text", "FakeMessageID"))
                .build();
    }

    @Override
    public Boolean checkCode(MailCodeDto mailCodeDto) {
        return map.get(mailCodeDto.getEmail()).equals(mailCodeDto.getCode());
    }

}
