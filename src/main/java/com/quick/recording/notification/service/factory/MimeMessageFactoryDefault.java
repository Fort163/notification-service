package com.quick.recording.notification.service.factory;

import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.notification.service.dto.ResultMailCodeMessage;
import com.quick.recording.notification.service.util.Constant;
import com.quick.recording.notification.service.util.TemplateUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MimeMessageFactoryDefault implements MimeMessageFactory {

    @Value("${spring.mail.from}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final MessageUtil messageUtil;
    private final TemplateUtil templateUtil;

    @Override
    public MimeMessage create(@NotNull MailDto mail, @Nullable Map<String, Object> contextMap, @Nullable MultipartFile[] files)
            throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setHeader("Message-ID",UUID.randomUUID().toString());
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Context context = new Context();
        context.setVariable("mail", mail);
        context.setVariable("message", messageUtil);
        if (Objects.nonNull(contextMap)) {
            context.setVariables(contextMap);
        }
        Pair<String, Boolean> template = templateUtil.getTemplate(mail.getTemplate());
        helper.setText(templateEngine.process(template.getFirst(), context), template.getSecond());
        for (File image : templateUtil.getImage(mail.getTemplate())) {
            helper.addInline(image.getName(), image);
        }
        helper.setFrom(fromEmail);
        helper.setTo(new InternetAddress(mail.getTo()));
        if (Objects.nonNull(mail.getCc())) helper.setCc(mail.getCc().toArray(String[]::new));
        if (Objects.nonNull(mail.getBcc())) helper.setBcc(mail.getBcc().toArray(String[]::new));
        helper.setSubject(mail.getSubject());
        if (Objects.nonNull(files)) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) helper.addAttachment(file.getOriginalFilename(), file);
            }
        }
        return mimeMessage;
    }

    @Override
    public ResultMailCodeMessage createCodeMessage(MailCodeDto code) throws MessagingException {
        MailDto mailDto = new MailDto();
        mailDto.setTemplate(code.getTemplate());
        mailDto.setSubject(messageUtil.create("mail.code.verification.code"));
        mailDto.setTo(code.getEmail());
        Map<String, Object> context = new HashMap<>();
        String codeStr = generateCode();
        context.put("code",codeStr);
        MimeMessage mimeMessage = create(mailDto, context);
        return ResultMailCodeMessage.builder()
                .message(mimeMessage)
                .email(code.getEmail())
                .code(codeStr)
                .build();
    }

    private String generateCode() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, Constant.CODE_MAIL_SIZE);
    }

}
