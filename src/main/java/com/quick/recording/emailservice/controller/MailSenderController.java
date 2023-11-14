package com.quick.recording.emailservice.controller;

import com.quick.recording.emailservice.dto.request.MailDTO;
import com.quick.recording.emailservice.dto.response.Success;
import com.quick.recording.emailservice.service.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mails")
@RequiredArgsConstructor
public class MailSenderController {

  private final MailSenderService mailSenderService;

  @PostMapping
  public Success send(
      @Valid MailDTO mail,
      @RequestPart(value = "files", required = false) MultipartFile[] files)
      throws MessagingException {
    mailSenderService.send(mail, files);
    return new Success(String.format("Message sent to %s", mail.getTo()));
  }

}
