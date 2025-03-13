package com.quick.recording.notification.service.controller;

import com.quick.recording.gateway.dto.notification.CheckCode;
import com.quick.recording.gateway.dto.notification.CreateCode;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.dto.notification.mail.MailResult;
import com.quick.recording.gateway.service.notification.NotificationServiceMailSenderApi;
import com.quick.recording.notification.service.service.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/mail/sender")
@RequiredArgsConstructor
public class MailSenderController implements NotificationServiceMailSenderApi {

    private final MailSenderService mailSenderService;

    @Override
    @PostMapping
    public ResponseEntity<MailResult> send(
            @Validated MailDto mail,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {
        MailResult send = mailSenderService.send(mail, files);
        return ResponseEntity.ok(send);
    }

    @Override
    @PostMapping(path = "/code")
    public ResponseEntity<MailResult> code(@RequestBody @Validated({CreateCode.class}) MailCodeDto code) {
        MailResult result = mailSenderService.code(code);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping(path = "/code/check")
    public ResponseEntity<Boolean> check(@RequestBody @Validated({CheckCode.class}) MailCodeDto mailCodeDto) {
        return ResponseEntity.ok(mailSenderService.checkCode(mailCodeDto));
    }

}
